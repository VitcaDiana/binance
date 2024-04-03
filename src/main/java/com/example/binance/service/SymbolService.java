package com.example.binance.service;

import com.example.binance.model.Symbol;
import com.example.binance.repository.SymbolRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SymbolService {



    private RestTemplate restTemplate;
    private ObjectMapper objectMapper;

    //private ObjectMapper objectMapper;
    //construiesc url de baza al request -ului [e care il pun intr o constanta
    private static final String BASE_URL = "https://testnet.binancefuture.com/fapi/v1/ticker/bookTicker";


    //imi injectez din app properties intr o variabila key pe care nu vreau sa il

    private SymbolRepository symbolRepository;

    @Autowired
    public SymbolService(SymbolRepository symbolRepository, RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.symbolRepository = symbolRepository;
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;

    }






    public List<Symbol> addAllSymbols () throws JsonProcessingException {
        // la url de baza adaug locatia lat, lon, apykey dar cheia adaug in apl propert si adaug o constanta
//        String url = UriComponentsBuilder
//                .fromUriString(BASE_URL+"/realtime")
//                .queryParam("location", lat+","+lon)
//                .queryParam("apikey", apiKey)
//                .toUriString();

        // vom primi ca raspuns un json si sa imi returnez
        String response = restTemplate.getForObject(BASE_URL,String.class);
        //primesc ca raspuns un JsonNode, practic un arbore care imi returneaza toate datele
        // din site si ma folosesc de o metoda de mapare in care imi returnez printr-un
        // currentweather dto acele atribute care le am definit in service:
        //humidity, temperetureFeelsLike, data,
        JsonNode root = objectMapper.readTree(response);
        return mapFromJsonToSymbolList(root);
    }
    public List<Symbol> mapFromJsonToSymbolList (JsonNode root) {
        List <Symbol> result = new ArrayList<>();
        for (JsonNode symbolJson: root){
            //transform de la symbolJson la Symbol
            result.add(mapFromSymbolJsonToSymbol(symbolJson));

        }
        return result;
    }
    public Symbol mapFromSymbolJsonToSymbol(JsonNode symbolJson){

        Optional<Symbol> symbolOptional = symbolRepository.findBySymbolName(symbolJson.path("symbol").asText());
        if (symbolOptional.isPresent()){
            Symbol dbSymbol = symbolOptional.get();
            return symbolRepository.save(mapJsonToSymbol(symbolJson,dbSymbol));
        } else {
            Symbol symbol = mapJsonToSymbol(symbolJson, new Symbol());
            symbol.setSymbolName(symbolJson.path("symbol").asText());
            return symbolRepository.save(symbol);
        }

    }

    public Symbol mapJsonToSymbol (JsonNode symbolJson, Symbol symbolToBeSaved){
        symbolToBeSaved.setBidPrice(symbolJson.path("bidPrice").asText());
        symbolToBeSaved.setBidQty(symbolJson.path("bidQty").asText());
        symbolToBeSaved.setAskPrice(symbolJson.path("askPrice").asText());
        symbolToBeSaved.setAskQty(symbolJson.path("askQuantity").asText());
        symbolToBeSaved.setTime(symbolJson.path("time").asLong());
        symbolToBeSaved.setLastUpdatedId(symbolJson.path("lastUpdateId").asLong());
        return symbolToBeSaved;
    }
}

package com.example.binance.service;

import com.example.binance.model.Kline;
import com.example.binance.model.Symbol;
import com.example.binance.repository.KlineRepository;
import com.example.binance.repository.SymbolRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class KlineService {
    private KlineRepository klineRepository;


    private RestTemplate restTemplate;
    private ObjectMapper objectMapper;
    private SymbolRepository symbolRepository;

    //de pus URL-ul cu symbol ca parametru

    private static final String BASE_URL = "https://api.binance.com/api/v3/klines";


    @Autowired
    public KlineService(SymbolRepository symbolRepository, RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.symbolRepository = symbolRepository;
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;

    }


    public Kline addKline(String symbol) throws JsonProcessingException {
        String url = UriComponentsBuilder
                .fromUriString(BASE_URL)
                .queryParam("symbol", symbol)
                .queryParam("interval", "1m")
                .queryParam("limit", 1)
                .toUriString();

        // vom primi ca raspuns un json si sa imi returnez
        String response = restTemplate.getForObject(BASE_URL, String.class);

        JsonNode root = objectMapper.readTree(response);
        return mapFromJsonToKline(root);
    }

    public Kline mapFromJsonToKline(JsonNode root) {
        Kline kline = new Kline();
        //caut simbol dupa nume
        Symbol symbol = symbolRepository.findBySymbolName(root.get("symbolName").asText()).orElseThrow(() -> new RuntimeException("symbol don't found"));
        if (symbol != null) {
            kline.setSymbol(symbol);
            JsonNode klineArrayNode = root.get("klineArrayNode").get(0);
            //iau din root primul element al array-ului = klinearraynode
            for (int i = 0; i < 5; i++) {
                //iau elementul de la fiecare pozitie din klinearraynode si il atribui atributului corespunzator din kline pe care il voi salva

                kline.setKlineOpenTime(klineArrayNode.get("klineOpenTime").asLong());
                kline.setOpenPrice(klineArrayNode.get("openPrice").asText());
                kline.setHighPrice(klineArrayNode.get("highPrice").asText());
                kline.setLowPrice(klineArrayNode.get("lowPrice").asText());
                //leg kline de simbol
                kline.setSymbol(symbol);
                //salvez kline
                klineRepository.save(kline);

            }
        }
        return kline;


    }
}

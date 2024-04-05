package com.example.binance.service;

import com.example.binance.model.Kline;
import com.example.binance.model.Symbol;
import com.example.binance.repository.KlineRepository;
import com.example.binance.repository.SymbolRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
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
    public KlineService(KlineRepository klineRepository,SymbolRepository symbolRepository, RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.klineRepository = klineRepository;
        this.symbolRepository = symbolRepository;
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;

    }

    @Transactional
    public Kline addKline(String symbol) throws JsonProcessingException {
        String url = UriComponentsBuilder
                .fromUriString(BASE_URL)
                .queryParam("symbol", symbol)
                .queryParam("interval", "1m")
                .queryParam("limit", 1)
                .toUriString();

        // vom primi ca raspuns un json si sa imi returnez
        String response = restTemplate.getForObject(url, String.class);

        JsonNode root = objectMapper.readTree(response);
        return mapFromJsonToKline(root, symbol);

    }

    @Transactional
    public Kline mapFromJsonToKline(JsonNode root, String symbolName) {
        Symbol symbol = symbolRepository.findBySymbolName(symbolName).orElseThrow(() -> new RuntimeException("symbol don't found"));
        //iau din root primul element al array-ului = klinearraynode
        JsonNode klinearraynode = root.get(0);
        Kline kline = new Kline();

        kline.setKlineOpenTime(klinearraynode.get(0).asLong());     //iau elementul de la fiecare pozitie din klinearraynode si il atribui atributului corespunzator din kline pe care il voi salva
        kline.setOpenPrice(klinearraynode.get(1).asText());
        kline.setHighPrice(klinearraynode.get(2).asText());
        kline.setLowPrice(klinearraynode.get(3).asText());

        kline.setSymbol(symbol);
        symbol.getKlineList().add(kline);

        return klineRepository.save(kline);
    }

}







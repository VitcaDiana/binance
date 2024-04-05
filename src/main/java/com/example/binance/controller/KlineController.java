package com.example.binance.controller;

import com.example.binance.model.Kline;
import com.example.binance.model.Symbol;
import com.example.binance.repository.SymbolRepository;
import com.example.binance.service.KlineService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Controller
@RequestMapping("/kline")
public class KlineController {
    KlineService klineService;


    @Autowired
    public KlineController(KlineService klineService) {
        this.klineService = klineService;
    }

    @PostMapping("/add")
    public ResponseEntity<Kline> addKline(@RequestParam String symbol) {
        try {
            return ResponseEntity.ok(klineService.addKline(symbol));
        } catch (JsonProcessingException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }

    }
}

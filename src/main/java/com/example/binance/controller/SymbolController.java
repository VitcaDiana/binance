package com.example.binance.controller;

import com.example.binance.model.Symbol;
import com.example.binance.service.SymbolService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Controller
@RequestMapping("/symbol")
public class SymbolController {

    private SymbolService symbolService;

    @Autowired
    public SymbolController(SymbolService symbolService) {
        this.symbolService = symbolService;
    }

    @PostMapping("/addAll")
    public ResponseEntity<List<Symbol>> addAllSymbols(){
        try {
            return ResponseEntity.ok(symbolService.addAllSymbols());
        } catch (JsonProcessingException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }

    }


}

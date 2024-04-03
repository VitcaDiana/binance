package com.example.binance.repository;

import com.example.binance.model.Symbol;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SymbolRepository extends JpaRepository<Symbol,Long> {

    Optional<Symbol> findBySymbolName(String name);
}

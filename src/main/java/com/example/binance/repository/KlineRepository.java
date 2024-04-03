package com.example.binance.repository;

import com.example.binance.model.Kline;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KlineRepository extends JpaRepository<Kline, Long> {
}

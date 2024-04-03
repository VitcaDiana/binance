package com.example.binance.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
public class Kline {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long klineOpenTime;
    @Column
    private String openPrice;
    @Column
    private String highPrice;
    @Column
    private String lowPrice;

    public Kline() {
    }

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name="symbol_id")
    private Symbol symbol;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getKlineOpenTime() {
        return klineOpenTime;
    }

    public void setKlineOpenTime(Long klineOpenTime) {
        this.klineOpenTime = klineOpenTime;
    }

    public String getOpenPrice() {
        return openPrice;
    }

    public void setOpenPrice(String openPrice) {
        this.openPrice = openPrice;
    }

    public String getHighPrice() {
        return highPrice;
    }

    public void setHighPrice(String highPrice) {
        this.highPrice = highPrice;
    }

    public String getLowPrice() {
        return lowPrice;
    }

    public void setLowPrice(String lowPrice) {
        this.lowPrice = lowPrice;
    }

    public Symbol getSymbol() {
        return symbol;
    }

    public void setSymbol(Symbol symbol) {
        this.symbol = symbol;
    }
}

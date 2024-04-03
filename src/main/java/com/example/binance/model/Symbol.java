package com.example.binance.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Symbol {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column
    private String symbolName;


    @Column
    private String bidPrice;

    @Column
    private String bidQty;
    @Column
    private String askPrice;
    @Column
    private String askQty;
    @Column
    private Long time;
    @Column
    private Long lastUpdatedId;

    @OneToMany(mappedBy="symbol", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Kline> klineList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSymbolName() {
        return symbolName;
    }

    public void setSymbolName(String symbol) {
        this.symbolName = symbol;
    }

    public String getBidPrice() {
        return bidPrice;
    }

    public void setBidPrice(String bidPrice) {
        this.bidPrice = bidPrice;
    }

    public String getBidQty() {
        return bidQty;
    }

    public void setBidQty(String bidQty) {
        this.bidQty = bidQty;
    }

    public String getAskPrice() {
        return askPrice;
    }

    public void setAskPrice(String askPrice) {
        this.askPrice = askPrice;
    }

    public String getAskQty() {
        return askQty;
    }

    public void setAskQty(String askQty) {
        this.askQty = askQty;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public Long getLastUpdatedId() {
        return lastUpdatedId;
    }

    public void setLastUpdatedId(Long lastUpdatedId) {
        this.lastUpdatedId = lastUpdatedId;
    }

    public List<Kline> getKlineList() {
        return klineList;
    }

    public void setKlineList(List<Kline> klineList) {
        this.klineList = klineList;
    }
}

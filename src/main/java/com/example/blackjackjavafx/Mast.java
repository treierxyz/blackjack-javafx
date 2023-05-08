package com.example.blackjackjavafx;

public enum Mast {
    ÄRTU("♥"),
    RUUTU("♦"),
    RISTI("♣"),
    POTI("♠");

    private String sümbol;

    Mast(String sümbol) {
        this.sümbol = sümbol;
    }

    public String getSümbol() {
        return sümbol;
    }
}

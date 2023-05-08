package com.example.blackjackjavafx;

public enum Vaade {
    PEAMENÜÜ("peamenüü.fxml"),
    ABI("abi.fxml"),

    MÄNG("mäng.fxml"),
    MÄNGIJATENIMED("mängijate-nimed.fxml");

    private String failinimi;

    Vaade(String failinimi) {
        this.failinimi = failinimi;
    }

    public String getFailinimi() {
        return failinimi;
    }
}

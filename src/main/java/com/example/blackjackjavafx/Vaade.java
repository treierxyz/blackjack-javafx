package com.example.blackjackjavafx;

public enum Vaade {
    MÄNGIJAD("mängijad.fxml"),
    MÄNG("mäng.fxml"),
    MÄNGIJANIMED("mängija-nimed.fxml");

    private String failinimi;

    Vaade(String failinimi) {
        this.failinimi = failinimi;
    }

    public String getFailinimi() {
        return failinimi;
    }
}

package xyz.treier.blackjackjavafx;

public enum Vaade {
    PEAMENÜÜ("peamenüü.fxml"),
    ABI("abi.fxml"),
    MÄNG("mäng.fxml"),
    MÄNGIJATENIMED("mängijate-nimed.fxml"),
    LÕPP("lõpp.fxml");

    private final String failinimi;

    Vaade(String failinimi) {
        this.failinimi = failinimi;
    }

    public String getFailinimi() {
        return failinimi;
    }
}

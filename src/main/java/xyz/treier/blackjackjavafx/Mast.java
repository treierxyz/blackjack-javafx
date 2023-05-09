package xyz.treier.blackjackjavafx;

public enum Mast {
    ÄRTU("♥"),
    RUUTU("♦"),
    RISTI("♣"),
    POTI("♠");

    private final String sümbol;

    Mast(String sümbol) {
        this.sümbol = sümbol;
    }

    public String getSümbol() {
        return sümbol;
    }
}

package xyz.treier.blackjackjavafx;

/**
 * Kaardi mastid.
 */
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

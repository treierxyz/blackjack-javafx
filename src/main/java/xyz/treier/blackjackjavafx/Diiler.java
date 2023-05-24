package xyz.treier.blackjackjavafx;

public class Diiler extends Mängija {

    /**
     * Diiler, kes on mängija liik
     * Diileril on ainult üks käsi ja lõpmatult krediiti
     */
    public Diiler() {
        super("Diiler", Integer.MAX_VALUE);
    }

    public Käsi getKäsi() {
        return super.getKäed().get(0);
    }
}

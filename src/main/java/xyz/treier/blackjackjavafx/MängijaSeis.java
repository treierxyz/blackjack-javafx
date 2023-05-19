package xyz.treier.blackjackjavafx;

/**
 * Mängija seisu järgi määratakse mängija läbipaistvus ekraanil.
 */
public enum MängijaSeis {
    INIT(1.0, "#F8F8F7"),
    VÄLJAS(0.3, "#FF3333"),
    PANUS_VALMIS(1.0, "#F8F8F7"),
    MÄNGIB(1.0, "#F8F8F7"),
    OOTAB(0.5, "#F8F8F7"),
    STAND(0.1, "#F8F8F7"),
    BUST(0.5, "#FF3333");

    private final double läbipaistvus;
    private final String värv;

    MängijaSeis(double läbipaistvus, String värv) {
        this.läbipaistvus = läbipaistvus;
        this.värv = värv;
    }

    public double getLäbipaistvus() {
        return läbipaistvus;
    }

    public String getVärv() {
        return värv;
    }
}

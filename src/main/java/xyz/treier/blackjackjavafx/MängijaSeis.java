package xyz.treier.blackjackjavafx;

/**
 * Mängija seisu järgi määratakse mängija läbipaistvus ekraanil.
 */
public enum MängijaSeis {
    INIT(1.0),
    VÄLJAS(0.3),
    PANUS_VALMIS(1.0),
    MÄNGIB(1.0),
    OOTAB(0.5),
    STAND(0.5),
    BUST(0.3);

    private final double läbipaistvus;

    MängijaSeis(double läbipaistvus) {
        this.läbipaistvus = läbipaistvus;
    }

    public double getLäbipaistvus() {
        return läbipaistvus;
    }
}

package xyz.treier.blackjackjavafx;

public enum KäsiSeis {
    BUST(0.3),
    OOTAB(0.5),
    MÄNGIB(1.0),
    STAND(0.5);

    private final double läbipaistvus;

    KäsiSeis(double läbipaistvus) {
        this.läbipaistvus = läbipaistvus;
    }

    public double getLabipaistvus() {
        return läbipaistvus;
    }
}

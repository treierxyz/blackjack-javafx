package com.example.blackjackjavafx;

public enum Väärtus {
    ÄSS(1,"A"),
    KAKS(2,"2"),
    KOLM(3,"3"),
    NELI(4,"4"),
    VIIS(5,"5"),
    KUUS(6,"6"),
    SEITSE(7,"7"),
    KAHEKSA(8,"8"),
    ÜHEKSA(9,"9"),
    KÜMME(10,"10"),
    POISS(10,"J"),
    EMAND(10,"Q"),
    KUNINGAS(10,"K");

    private int väärtus;
    private String lühend;

    Väärtus(int väärtus, String lühend) {
        this.väärtus = väärtus;
        this.lühend = lühend;
    }

    /**
     * Annab kaardi arvulise väärtuse Blackjack mängu reeglite järgi
     *
     * @return kaardi arvuline väärtus
     */
    public int getVäärtus() {
        return väärtus;
    }

    public String getLühend() {
        return lühend;
    }
}

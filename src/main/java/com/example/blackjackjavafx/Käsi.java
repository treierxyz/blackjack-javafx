package com.example.blackjackjavafx;
import java.util.ArrayList;
import java.util.List;

public class Käsi {
    private List<Kaart> kaardid;

    /**
     * Uus käsi
     */
    public Käsi() {
        this.kaardid = new ArrayList<>();
    }

    /**
     * Lisab kaartide hulka uue kaardi
     * @param kaart lisatav kaart
     */
    public void lisaKaart(Kaart kaart) {
        this.kaardid.add(kaart);
    }

    /**
     * Teeb kaartide listi tühjaks
     */
    public void tühjendaKäsi() {
        this.kaardid.clear();
    }

    /**
     * Arvutab kaartide summa blackjacki reeglite järgi
     * @return kaartide summa
     */
    public int summa() {
        int summa = 0;
        for (Kaart kaart : kaardid) {
            summa += kaart.getVaartusArv();
        }
        if (kaardid.contains(new Kaart("","A")) && (summa+10 <= 21)) { // kui kaart on äss ja kaartide summa+10 ei ületaks 21te
            summa += 10;
        }
        return summa;
    }

    /**
     * Tagastab käe kaardid
     * @return kaartide list
     */
    public List<Kaart> getKaardid(){
        return this.kaardid;
    }

    /**
     * Tagastab käe kaardid lihtsasti loetaval kujul
     * @return kaardid sõnena
     */
    @Override
    public String toString() {
        return kaardid.toString().replace("[","").replace("]","");
    }
}

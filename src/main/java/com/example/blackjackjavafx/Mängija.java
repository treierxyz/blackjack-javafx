package com.example.blackjackjavafx;
import java.util.ArrayList;
import java.util.List;

public class Mängija implements Comparable<Mängija> {
    private String nimi;
    private int krediit;
    private int panus;
    private Käsi käsi;
    private String seis;
    private static List<String> debugNimed = new ArrayList<>(List.of("Artur","Peeter","Joonas","Kaarel","Johanna","Liina","Mia","Lisete"));

    /**
     * Mängija, kelle nime saab määrata
     * @param nimi mängija nimi
     * @param krediit mängija krediit
     */
    public Mängija(String nimi, int krediit) {
        this.nimi = nimi;
        this.krediit = krediit;
        this.käsi = new Käsi();
        this.seis = "mängib";
    }

    /**
     * Mängija kellele antakse suvaline nimi
     * @param krediit mängija krediit
     */
    public Mängija(int krediit) {
        this.nimi = debugNimed.get((int) (Math.random()* debugNimed.size()));
        debugNimed.remove(nimi);
        this.krediit = krediit;
        this.käsi = new Käsi();
        this.seis = "mängib";
    }

    /**
     * Tagastab mängija nime
     * @return mängija nimi
     */
    public String getNimi() {
        return nimi;
    }

    /**
     * Tagastab mängija krediidi
     * @return mängija krediit
     */
    public int getKrediit() {
        return krediit;
    }

    /**
     * Lisab mängijale krediiti
     * @param krediit lisatav krediidi kogus
     */
    public void lisaKrediit(int krediit){
        this.krediit += krediit;
    }

    /**
     * Tagastab mängija käe
     * @return mängija käsi
     */
    public Käsi getKäsi() {
        return käsi;
    }

    /**
     * Tagastab mängija seisu (stand, bust, blackjack)
     * @return mängija seis
     */
    public String getSeis() {
        return seis;
    }

    /**
     * Tagastab mängija panuse
     * @return mängija panus
     */
    public int getPanus() {
        return this.panus;
    }

    /**
     * Väärtustab mängija panuse
     */
    public void setPanus(int panus) {
        this.panus = panus;
    }

    /**
     * Määrab mängija seisu (stand, bust, blackjack)
     */
    public void setSeis(String seis) {
        this.seis = seis;
    }

    /**
     * Tagastab mängija nime ja krediidi lihtsalt loetaval kujul
     * @return mängija nimi ja krediit sõnena
     */
    @Override
    public String toString() {
        return nimi+", krediit: "+ krediit;
    }

    /**
     * Võrdleb teise mängijaga krediidi kogust ja tagastab,
     * kas mängijal on võrreldavast rohkem, vähem või võrdselt krediiti
     *
     * @param mängija võrreldav mängija.
     * @return -1, kui mängijal on vähem krediiti kui võrreldaval.
     *          0, kui krediiti on võrdselt.
     *          1, kui mängijal on rohkem krediiti kui võrreldaval.
     */
    @Override
    public int compareTo(Mängija mängija) {
        return Integer.compare(krediit, mängija.krediit);
    }
}

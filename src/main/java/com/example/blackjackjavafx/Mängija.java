package com.example.blackjackjavafx;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

public class Mängija implements Comparable<Mängija> {
    private String nimi;
    private SimpleIntegerProperty krediit = new SimpleIntegerProperty();
    private SimpleIntegerProperty panus = new SimpleIntegerProperty();
    private Käsi käsi;
    private MängijaSeis seis;
    private HBox mängijaHbox = new HBox();
    private static List<String> debugNimed = new ArrayList<>(List.of("Artur","Peeter","Joonas","Kaarel","Johanna","Liina","Mia","Lisete"));

    /**
     * Mängija, kelle nime saab määrata
     * @param nimi mängija nimi
     * @param krediit mängija krediit
     */
    public Mängija(String nimi, int krediit) {
        this.nimi = nimi;
        this.krediit.set(krediit);
        this.käsi = new Käsi();
        this.seis = MängijaSeis.INIT;
    }

    /**
     * Mängija kellele antakse suvaline nimi
     * @param krediit mängija krediit
     */
    public Mängija(int krediit) {
        this.nimi = debugNimed.get((int) (Math.random()* debugNimed.size()));
        debugNimed.remove(nimi);
        this.krediit.set(krediit);
        this.käsi = new Käsi();
        this.seis = MängijaSeis.INIT;
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
        return krediit.get();
    }

    /**
     * Lisab mängijale krediiti
     * @param krediit lisatav krediidi kogus
     */
    public void lisaKrediit(int krediit){
        this.krediit.set(getKrediit()+krediit);
    }

    public IntegerProperty krediitProperty() {
        return krediit;
    }

    public void strikeThroughNimi() {
        VBox vBox = (VBox)mängijaHbox.getParent();

        for (Node child : vBox.getChildren()) {
            if (child.getClass() == Text.class) {
                ((Text) child).setStrikethrough(true);
            }
        }
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
    public MängijaSeis getSeis() {
        return seis;
    }

    /**
     * Tagastab mängija panuse
     * @return mängija panus
     */
    public int getPanus() {
        return this.panus.get();
    }

    /**
     * Väärtustab mängija panuse
     */
    public void setPanus(int panus) {
        this.panus.set(panus);
    }

    public IntegerProperty panusProperty() {
        return panus;
    }

    /**
     * Määrab mängija seisu (stand, bust, blackjack)
     */
    public void setSeis(MängijaSeis seis) {
        this.seis = seis;
    }

    public HBox getMängijaHbox() {
        return mängijaHbox;
    }

    public void setMängijaHbox(HBox mängijaHbox) {
        this.mängijaHbox = mängijaHbox;
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
        return Integer.compare(krediit.get(), mängija.krediit.get());
    }
}

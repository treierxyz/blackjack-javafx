package com.example.blackjackjavafx;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Kaardipakk {
    private List<Kaart> kaardid;

    /**
     * Uus kaardipakk
     * @param pakid mitu kaardipakki kokku panna
     */
    public Kaardipakk(int pakid) {
        this.kaardid = new ArrayList<>();
        uusPakk(pakid);
    }

    /**
     * Loob mängukaardid.
     */
    public void uusPakk(int pakid) {
        List<String> mastid = List.of("♦", "♣", "♥", "♠");
        List<String> vaartused = List.of("2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A");

        this.kaardid.clear();
        for (int i = 0; i < pakid; i++)
            for (String mast : mastid)
                for (String vaartus : vaartused)
                    this.kaardid.add(new Kaart(mast, vaartus));
    }

    /**
     * Segab kaardipaki.
     */
    public void sega() {
        Collections.shuffle(this.kaardid);
    }

    /**
     * @return pakis olevate kaartide arv
     */
    public int kaarteAlles(){
        return this.kaardid.size();
    }

    /**
     * Tagastab suvalise kaardi allesolevatest kaartidest.
     * @return suvaline kaart pakist
     */
    public Kaart suvaline() {
        if (this.kaardid.isEmpty())
            return null;

        Random rng = new Random();
        Kaart suvaline = this.kaardid.get(rng.nextInt(this.kaardid.size()));
        this.kaardid.remove(suvaline);

        return suvaline;
    }
}

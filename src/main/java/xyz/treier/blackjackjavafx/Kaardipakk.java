package xyz.treier.blackjackjavafx;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Kaardipakk {
    private final List<Kaart> kaardid;

    /**
     * Uus kaardipakk.
     * @param pakid mitu kaardipakki kokku panna.
     */
    public Kaardipakk(int pakid) {
        this.kaardid = new ArrayList<>();
        uusPakk(pakid);
    }

    /**
     * Loob mängukaardid.
     */
    public void uusPakk(int pakid) {
        this.kaardid.clear();
        for (int i = 0; i < pakid; i++)
            for (Mast mast : Mast.values())
                for (Väärtus väärtus : Väärtus.values())
                    this.kaardid.add(new Kaart(mast,väärtus));
    }

    /**
     * Segab kaardipakis olevad kaardid.
     */
    public void sega() {
        Collections.shuffle(this.kaardid);
    }

    /**
     * Tagastab suvalise kaardi allesolevatest kaartidest.
     * @return suvaline kaart pakist.
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

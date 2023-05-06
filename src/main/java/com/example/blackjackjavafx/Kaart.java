package com.example.blackjackjavafx;
import java.util.Map;
import java.util.Objects;

public class Kaart {
    private String mast;
    private String vaartus;

    /**
     * Uus mängukaart
     *
     * @param mast kaardi mast
     * @param vaartus kaardi väärtus
     */
    public Kaart(String mast, String vaartus) {
        this.mast = mast;
        this.vaartus = vaartus;
    }

    /**
     * Tagastab kaardi väärtuse ja masti lihtsasti loetaval kujul.
     *
     * @return kaardi väärtus ja mast sõnena
     */
    @Override
    public String toString(){
        return this.vaartus + this.mast;
    }

    /**
     * Leiab kaardi arvulise väärtuse blackjack mängu reeglite järgi
     * @return kaardi arvuline väärtus
     */
    public int getVaartusArv(){
        int vaartus;

        // Pildikaartide väärtused
        Map<String, Integer> map = Map.of(
                "J", 10,
                "Q", 10,
                "K", 10,
                "A", 1
        );

        // Proovib sõnest täisarvu saada, pildikaardi korral vaatab mapist väärtuse
        try {
            vaartus = Integer.parseInt(this.vaartus);
        } catch (Exception e) {
            vaartus = map.get(this.vaartus);
        }

        return vaartus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Kaart kaart = (Kaart) o;
        return vaartus.equals(kaart.vaartus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mast, vaartus);
    }
}

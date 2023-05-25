package xyz.treier.blackjackjavafx;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.Objects;

public class Kaart {
    private final Mast mast;
    private final Väärtus väärtus;

    /**
     * Uus mängukaart
     *
     * @param mast    kaardi mast
     * @param väärtus kaardi väärtus
     */
    public Kaart(Mast mast, Väärtus väärtus) {
        this.mast = mast;
        this.väärtus = väärtus;
    }

    /**
     * Tagastab kaardi väärtuse ja masti lihtsasti loetaval kujul.
     *
     * @return kaardi väärtus ja mast sõnena
     */
    @Override
    public String toString() {
        return this.väärtus.getLühend() + this.mast.getSümbol();
    }

    /**
     * Kaardi Label. Teeb ärtu ja ruutu mastid punaseks, teised mustaks.
     * @return Kaardi värvitud Label.
     */
    public Label kaartLabel() {
        Label kaart = new Label();

        Label väärtus = new Label(this.väärtus.getLühend());
        Text mast = new Text(this.mast.getSümbol());

        // Ärtu ja ruutu mastid punaseks
        if (this.mast.equals(Mast.ÄRTU) || this.mast.equals(Mast.RUUTU)) {
            mast.setFill(Color.RED);
        }
        kaart.setGraphic(new HBox(väärtus,mast));
        return kaart;
    }

    /**
     * Leiab kaardi arvulise väärtuse blackjack mängu reeglite järgi.
     * @return kaardi arvuline väärtus.
     */
    public int getVaartusArv() {
        return this.väärtus.getVäärtus();
    }

    /**
     * Võimaldab võrrelda kaarte väärtuse järgi.
     *
     * @param o Suvaline klassi isend
     * @return Tõeväärtus, kas on parameetriks antud kaardi väärtus on võrdne selle isendi kaardiga.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Kaart kaart = (Kaart) o;
        return väärtus.equals(kaart.väärtus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mast, väärtus);
    }
}

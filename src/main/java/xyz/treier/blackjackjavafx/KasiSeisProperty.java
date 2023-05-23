package xyz.treier.blackjackjavafx;

import javafx.beans.property.ObjectPropertyBase;

public class KasiSeisProperty extends ObjectPropertyBase<KäsiSeis> {
    public KasiSeisProperty() {
    }

    @Override
    public Object getBean() {
        return this;
    }

    @Override
    public String getName() {
        return null;
    }
}

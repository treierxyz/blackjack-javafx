package xyz.treier.blackjackjavafx;

import javafx.beans.property.ObjectPropertyBase;

/**
 * Võimaldab muuta mängija läbipaistvust ekraanil tema seisu järgi.
 */
public class MängijaSeisProperty extends ObjectPropertyBase<MängijaSeis> {

    public MängijaSeisProperty() {
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

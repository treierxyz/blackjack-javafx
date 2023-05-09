package xyz.treier.blackjackjavafx;

import javafx.beans.property.ObjectPropertyBase;

public class MängijaSeisProperty extends ObjectPropertyBase<MängijaSeis> {

    public MängijaSeisProperty() {
    }

    public MängijaSeisProperty(MängijaSeis initialValue) {
        super(initialValue);
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

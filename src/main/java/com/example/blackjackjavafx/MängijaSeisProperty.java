package com.example.blackjackjavafx;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ObjectPropertyBase;
import javafx.beans.property.SimpleObjectProperty;

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

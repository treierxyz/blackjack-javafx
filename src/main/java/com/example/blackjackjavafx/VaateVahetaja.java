package com.example.blackjackjavafx;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

public class VaateVahetaja {

    private static Scene stseen;

    public static void setStseen(Scene stseen) {
        VaateVahetaja.stseen = stseen;
    }

    public static Scene getStseen() {
        return stseen;
    }

    public static void vaheta(Vaade vaade) {
        if (stseen == null) {
            System.out.println("Stseen valimata");
            return;
        }
        try {
            Parent juur = FXMLLoader.load(VaateVahetaja.class.getResource(vaade.getFailinimi()));
            stseen.setRoot(juur);
        } catch (IOException e){
            e.printStackTrace();
        }

    }
}

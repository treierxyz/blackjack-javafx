package com.example.blackjackjavafx;

import com.example.blackjackjavafx.kontrollerid.MängijaNimedKontroller;
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

    public static <T> T vaheta(Vaade vaade) {
        if (stseen == null) {
            System.out.println("Stseen valimata");
            return null;
        }
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(VaateVahetaja.class.getResource(vaade.getFailinimi()));
            Parent juur = fxmlLoader.load();
            T kontroller = fxmlLoader.getController();
            stseen.setRoot(juur);
            return kontroller;
        } catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }
}

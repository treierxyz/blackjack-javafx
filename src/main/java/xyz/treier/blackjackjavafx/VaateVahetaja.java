package xyz.treier.blackjackjavafx;

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

    /**
     * Vahetab mängu ekraani vaadete vahel.
     *
     * @param vaade Millisele vaatele vahetada.
     * @return Uue vaate kontroller.
     */
    public static <T> T vaheta(Vaade vaade) {
        if (stseen == null) {
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

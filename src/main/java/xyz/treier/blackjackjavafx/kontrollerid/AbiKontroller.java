package xyz.treier.blackjackjavafx.kontrollerid;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import xyz.treier.blackjackjavafx.Vaade;
import xyz.treier.blackjackjavafx.VaateVahetaja;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Hyperlink;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.IOException;
import java.net.URI;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.nio.file.Files;
import java.nio.file.Path;

public class AbiKontroller {
    @FXML
    private WebView markdownVaade;

    public void tagasi() {
        VaateVahetaja.vaheta(Vaade.PEAMENÜÜ);
    }

    public void avaLink(ActionEvent event) {
        String link = ((Hyperlink) event.getTarget()).getText();
        System.out.println("Ava link: "+link);

        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            try {
                Desktop.getDesktop().browse(new URI(link));
            } catch (Exception e) {
                System.out.println("Lingi avamine ebaõnnestus:");
                e.printStackTrace();
            }
        }
    }

    public void kopeeriLink(MouseEvent event) {
        if (event.getButton() == MouseButton.SECONDARY) {
            String link = ((Hyperlink) event.getSource()).getText();
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(link),null);
        }
    }

    public void initialize() throws IOException {
        String mdFail = "/media/NaxosA/Documents/UT/OOP/blackjack-javafx/src/main/resources/xyz/treier/blackjackjavafx/abi.md";
        Path mdPath = Path.of(mdFail);
        String mdSisu = Files.readString(mdPath);

        Parser parser = Parser.builder().build();
        HtmlRenderer renderer = HtmlRenderer.builder().build();

        String html = renderer.render(parser.parse(mdSisu));
        markdownVaade.getEngine().loadContent(html);
    }

}

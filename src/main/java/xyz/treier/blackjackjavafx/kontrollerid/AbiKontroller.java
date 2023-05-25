package xyz.treier.blackjackjavafx.kontrollerid;

import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import org.commonmark.Extension;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.commonmark.ext.gfm.tables.TablesExtension;
import xyz.treier.blackjackjavafx.Vaade;
import xyz.treier.blackjackjavafx.VaateVahetaja;
import javafx.fxml.FXML;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;

public class AbiKontroller {
    @FXML
    private WebView markdownVaade;

    public void tagasi() {
        VaateVahetaja.vaheta(Vaade.PEAMENÜÜ);
    }

    public void initialize() throws IOException {
        URL mdUrl = VaateVahetaja.class.getResource("abi.md");
        URL cssUrl = VaateVahetaja.class.getResource("abi.css");
        String mdSisu = new Scanner(mdUrl.openStream(), StandardCharsets.UTF_8).useDelimiter("\\A").next();

        List<Extension> laiendused = List.of(TablesExtension.create());
        Parser parser = Parser.builder().extensions(laiendused).build();
        HtmlRenderer renderer = HtmlRenderer.builder().extensions(laiendused).build();

        String html = renderer.render(parser.parse(mdSisu));
        WebEngine webEngine = markdownVaade.getEngine();
        webEngine.setUserStyleSheetLocation(cssUrl.toString());
        webEngine.loadContent(html);
    }

}

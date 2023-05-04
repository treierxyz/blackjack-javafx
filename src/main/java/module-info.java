module com.example.blackjackjavafx {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.blackjackjavafx to javafx.fxml;
    exports com.example.blackjackjavafx;
}
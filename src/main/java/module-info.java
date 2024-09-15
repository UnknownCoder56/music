module com.uniqueapps.music {
    requires javafx.fxml;
    requires atlantafx.base;
    requires java.desktop;
    requires java.xml.crypto;


    opens com.uniqueapps.music to javafx.fxml;
    exports com.uniqueapps.music;
}
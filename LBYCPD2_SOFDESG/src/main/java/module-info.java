module org.example.lbycpd2_sofdesg {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.lbycpd2_sofdesg to javafx.fxml;
    exports org.example.lbycpd2_sofdesg;
}
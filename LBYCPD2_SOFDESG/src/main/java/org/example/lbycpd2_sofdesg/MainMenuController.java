package org.example.lbycpd2_sofdesg;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainMenuController {
    // switch scene function
    private void switchScene(ActionEvent event, String fxmlFile) throws IOException
    {
        Parent root = FXMLLoader.load(getClass().getResource(fxmlFile));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    // overview scene
    @FXML
    void onOverviewClick(ActionEvent event) throws IOException
    {
        switchScene(event, "overview.fxml");
    }

    // update stocks scene
    @FXML
    void onUpdateStockClick(ActionEvent event) throws IOException
    {
        switchScene(event, "update_stocks.fxml");
    }

    // manage exmployees scene
    @FXML
    void onManageEmployeesClick(ActionEvent event) throws IOException
    {
        switchScene(event, "manage_employees.fxml");
    }

    // order from suppliers scene
    @FXML
    void onOrderSuppliersClick(ActionEvent event) throws IOException
    {
        switchScene(event, "order_suppliers.fxml");
    }

    // on edit stocks clicked
    @FXML
    void onEditStocksClick(ActionEvent event) throws IOException
    {
        switchScene(event, "edit_stocks.fxml");
    }

}
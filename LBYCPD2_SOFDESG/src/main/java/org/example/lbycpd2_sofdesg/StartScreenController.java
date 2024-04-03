package org.example.lbycpd2_sofdesg;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class StartScreenController {
    @FXML
    private TextField porkAndShrimpField;
    @FXML
    private TextField japaneseField;
    @FXML
    private TextField beefField;
    @FXML
    private TextField tunaField;
    @FXML
    private TextField chickenField;
    @FXML
    private TextField sharksfinField;
    @FXML
    private TextField bottledChiliField;
    @FXML
    private TextField cupField;
    @FXML
    private TextField employeeTextField;
    @FXML
    private Button addEmployeeButton;
    @FXML
    private Text errorMessage;

    private boolean validateNumericInput() {
        if (porkAndShrimpField.getText().isEmpty() || japaneseField.getText().isEmpty() ||
                beefField.getText().isEmpty() || tunaField.getText().isEmpty() ||
                chickenField.getText().isEmpty() || sharksfinField.getText().isEmpty() ||
                bottledChiliField.getText().isEmpty() || cupField.getText().isEmpty()) {

            Alert emptyAlert = new Alert(Alert.AlertType.WARNING);
            emptyAlert.setTitle("Warning");
            emptyAlert.setHeaderText(null);
            emptyAlert.setContentText("Please fill in all the fields with positive numerical values.");
            emptyAlert.showAndWait();

            return false;
        }

        try {
            double porkAndShrimp = Double.parseDouble(porkAndShrimpField.getText());
            double japanese = Double.parseDouble(japaneseField.getText());
            double beef = Double.parseDouble(beefField.getText());
            double tuna = Double.parseDouble(tunaField.getText());
            double chicken = Double.parseDouble(chickenField.getText());
            double sharksfin = Double.parseDouble(sharksfinField.getText());
            double bottledChili = Double.parseDouble(bottledChiliField.getText());
            double cup = Double.parseDouble(cupField.getText());

            // checks if any value is negative
            if (porkAndShrimp < 0 || japanese < 0 || beef < 0 || tuna < 0 || chicken < 0 || sharksfin < 0 || bottledChili < 0 || cup < 0) {
                errorMessage.setText("Values cannot be negative.");
                return false;
            }

            return true;
        } catch (NumberFormatException e) {
            errorMessage.setText("Please enter positive numerical values.");
            return false;
        }
    }

    @FXML
    void onAddEmployeeButtonClick(ActionEvent event) {
        String employeeName = employeeTextField.getText().trim();
        if (!employeeName.isEmpty()) {
            try {
                String userHome = System.getProperty("user.home");
                String filePath = userHome + File.separator + "Documents" + File.separator + "employees.csv";
                BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true));
                writer.write(employeeName);
                writer.newLine();
                writer.close();
                employeeTextField.clear(); // clears the TextField after adding the employee

                // show success alert
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Success");
                successAlert.setHeaderText(null);
                successAlert.setContentText("Employee added successfully.");
                successAlert.showAndWait();
            } catch (IOException e) {
                // show error alert
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Error");
                errorAlert.setHeaderText(null);
                errorAlert.setContentText("Error adding employee.");
                errorAlert.showAndWait();
            }
        } else {
            // show warning alert for empty input
            Alert warningAlert = new Alert(Alert.AlertType.WARNING);
            warningAlert.setTitle("Warning");
            warningAlert.setHeaderText(null);
            warningAlert.setContentText("Please enter an employee name.");
            warningAlert.showAndWait();
        }
    }

    @FXML
    void onFinishButtonClick(ActionEvent event) throws IOException {
        if (validateNumericInput()) {
            // Update the inventory.csv file
            updateInventoryFile();
            // Switch to the main menu
            switchScene(event, "main_menu.fxml");
        }
    }

    private void updateInventoryFile() {
        try {
            String userHome = System.getProperty("user.home");
            String filePath = userHome + File.separator + "Documents" + File.separator + "inventory.csv";
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));

            // Write the header
            writer.write("Pork and Shrimp, Condition, Quantity,Japanese, Condition, Quantity,Sharksfin, Condition, Quantity,Chicken, Condition, Quantity, Beef, Condition, Quantity, Tuna, Condition, Quantity,Bottled Chili, Condition, Quantity,Cup, Condition, Quantity");
            writer.newLine();

            // Write the inventory values in the correct order
            writer.write(String.format("%s, %s, %s,%s, %s, %s,%s, %s, %s,%s, %s, %s,%s, %s, %s,%s, %s, %s,%s, %s, %s,%s, %s, %s",
                    "Pork and Shrimp", getCondition(porkAndShrimpField.getText(), 200), porkAndShrimpField.getText(),
                    "Japanese", getCondition(japaneseField.getText(), 200), japaneseField.getText(),
                    "Sharksfin", getCondition(sharksfinField.getText(), 200), sharksfinField.getText(),
                    "Chicken", getCondition(chickenField.getText(), 200), chickenField.getText(),
                    "Beef", getCondition(beefField.getText(), 200), beefField.getText(),
                    "Tuna", getCondition(tunaField.getText(), 200), tunaField.getText(),
                    "Bottled Chili", getCondition(bottledChiliField.getText(), 30), bottledChiliField.getText(),
                    "Cup", getCondition(cupField.getText(), 200), cupField.getText()));
            writer.newLine();

            writer.close();
        } catch (IOException e) {
            errorMessage.setText("Error updating inventory.");
        }
    }

    private String getCondition(String quantityStr, int lowThreshold) {
        try {
            int quantity = Integer.parseInt(quantityStr);
            if (quantity < lowThreshold) {
                return "Low";
            } else {
                return "High";
            }
        } catch (NumberFormatException e) {
            return "Error";
        }
    }

    // switch scene function
    private void switchScene(ActionEvent event, String fxmlFile) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(fxmlFile));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}

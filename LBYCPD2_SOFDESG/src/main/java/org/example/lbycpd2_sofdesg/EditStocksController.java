package org.example.lbycpd2_sofdesg;

import java.io.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditStocksController {
    @FXML
    private Button updateButton;
    @FXML
    private TextField porkAndShrimpFieldUpdate;
    @FXML
    private TextField japaneseFieldUpdate;
    @FXML
    private TextField sharksfinFieldUpdate;
    @FXML
    private TextField chickenFieldUpdate;
    @FXML
    private TextField beefFieldUpdate;
    @FXML
    private TextField bottledChiliFieldUpdate;
    @FXML
    private TextField cupFieldUpdate;
    @FXML
    private TextField tunaFieldUpdate;
    @FXML
    private TextField porkShrimpQuantity;
    @FXML
    private TextField japaneseQuantity;
    @FXML
    private TextField sharksfinQuantity;
    @FXML
    private TextField chickenQuantity;
    @FXML
    private TextField beefQuantity;
    @FXML
    private TextField tunaQuantity;
    @FXML
    private TextField bottledChiliQuantity;
    @FXML
    private TextField cupQuantity;
    @FXML
    private TextField tunaCondition;
    @FXML
    private TextField bottledChiliCondition;
    @FXML
    private TextField cupCondition;
    @FXML
    private TextField porkShrimpCondition;
    @FXML
    private TextField japaneseCondition;
    @FXML
    private TextField sharksfinCondition;
    @FXML
    private TextField chickenCondition;
    @FXML
    private TextField beefCondition;

    @FXML
    private void initialize() {
        readAndUpdateStocks();
    }

    @FXML
    private void updateStocks(ActionEvent event) {
        String userHome = System.getProperty("user.home");
        String filePath = userHome + File.separator + "Documents" + File.separator + "inventory.csv";

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String header = reader.readLine(); // Read the header
            String currentLine = reader.readLine(); // Read the second line
            StringBuilder newContent = new StringBuilder(header + "\n"); // Start with the header

            if (currentLine != null) {
                String[] values = currentLine.split(",");
                if (values.length == 24) {
                    // update quantities and conditions based on the TextFields
                    values[2] = porkAndShrimpFieldUpdate.getText().isEmpty() ? values[2] : porkAndShrimpFieldUpdate.getText().trim();
                    values[5] = japaneseFieldUpdate.getText().isEmpty() ? values[5] : japaneseFieldUpdate.getText().trim();
                    values[8] = sharksfinFieldUpdate.getText().isEmpty() ? values[8] : sharksfinFieldUpdate.getText().trim();
                    values[11] = chickenFieldUpdate.getText().isEmpty() ? values[11] : chickenFieldUpdate.getText().trim();
                    values[14] = beefFieldUpdate.getText().isEmpty() ? values[14] : beefFieldUpdate.getText().trim();
                    values[17] = tunaFieldUpdate.getText().isEmpty() ? values[17] : tunaFieldUpdate.getText().trim();
                    values[20] = bottledChiliFieldUpdate.getText().isEmpty() ? values[20] : bottledChiliFieldUpdate.getText().trim();
                    values[23] = cupFieldUpdate.getText().isEmpty() ? values[23] : cupFieldUpdate.getText().trim();

                    // updates conditions based on the thresholds
                    values[1] = Integer.parseInt(values[2].trim()) < 200 ? "low" : "high";
                    values[4] = Integer.parseInt(values[5].trim()) < 200 ? "low" : "high";
                    values[7] = Integer.parseInt(values[8].trim()) < 200 ? "low" : "high";
                    values[10] = Integer.parseInt(values[11].trim()) < 200 ? "low" : "high";
                    values[13] = Integer.parseInt(values[14].trim()) < 200 ? "low" : "high";
                    values[16] = Integer.parseInt(values[17].trim()) < 200 ? "low" : "high";
                    values[19] = Integer.parseInt(values[20].trim()) < 30 ? "low" : "high";
                    values[22] = Integer.parseInt(values[23].trim()) < 200 ? "low" : "high";

                    // reconstruct the updated line
                    String newLine = String.join(",", values);
                    newContent.append(newLine).append("\n");
                }
            }

            // write the updated content back to the file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                writer.write(newContent.toString());
            }

            // show a success alert
            showAlert("Success", "Inventory updated successfully!");

            // update the stock overview in the TextFields
            readAndUpdateStocks();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void readAndUpdateStocks() {
        String userHome = System.getProperty("user.home");
        String filePath = userHome + File.separator + "Documents" + File.separator + "inventory.csv";

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String currentLine;
            String lastLine = null;
            while ((currentLine = reader.readLine()) != null) {
                if (!currentLine.trim().isEmpty()) {
                    lastLine = currentLine;
                }
            }

            if (lastLine != null) {
                String[] values = lastLine.split(",");
                if (values.length == 24) {
                    porkShrimpQuantity.setText(values[2].trim());
                    porkShrimpCondition.setText(values[1].trim());
                    japaneseQuantity.setText(values[5].trim());
                    japaneseCondition.setText(values[4].trim());
                    sharksfinQuantity.setText(values[8].trim());
                    sharksfinCondition.setText(values[7].trim());
                    chickenQuantity.setText(values[11].trim());
                    chickenCondition.setText(values[10].trim());
                    beefQuantity.setText(values[14].trim());
                    beefCondition.setText(values[13].trim());
                    tunaQuantity.setText(values[17].trim());
                    tunaCondition.setText(values[16].trim());
                    bottledChiliQuantity.setText(values[20].trim());
                    bottledChiliCondition.setText(values[19].trim());
                    cupQuantity.setText(values[23].trim());
                    cupCondition.setText(values[22].trim());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void backToMenu(ActionEvent event)
    {
        try
        {
            Parent root = FXMLLoader.load(getClass().getResource("main_menu.fxml"));
            Scene scene = new Scene(root);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // switches scene to the stage
            stage.setScene(scene);
            stage.show();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
}

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
import javafx.stage.Stage;

import java.io.*;

public class OrderSuppliersController {
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
    private TextField tunaCondition;
    @FXML
    private TextField bottledChiliCondition;
    @FXML
    private TextField cupCondition;
    // order
    @FXML
    private Button orderButton;
    @FXML
    private TextField porkAndShrimpFieldOrder;
    @FXML
    private TextField japaneseFieldOrder;
    @FXML
    private TextField sharksfinFieldOrder;
    @FXML
    private TextField chickenFieldOrder;
    @FXML
    private TextField beefFieldOrder;
    @FXML
    private TextField bottledChiliFieldOrder;

    @FXML
    private TextField cupFieldOrder;
    @FXML
    private TextField tunaFieldOrder;

    @FXML
    private void initialize() {
        readAndUpdateStocks();
    }

    @FXML
    private void order(ActionEvent event) {
        String userHome = System.getProperty("user.home");
        String filePath = userHome + File.separator + "Documents" + File.separator + "inventory.csv";

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath));
             BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {

            // reads the header line
            String headerLine = reader.readLine();
            String[] headers = headerLine.split(",");
            if (headers.length != 24) {
                return;
            }

            // finds the indices of the columns for each item
            int porkShrimpIndex = -1;
            int japaneseIndex = -1;
            int sharksfinIndex = -1;
            int chickenIndex = -1;
            int beefIndex = -1;
            int tunaIndex = -1;
            int bottledChiliIndex = -1;
            int cupIndex = -1;

            for (int i = 0; i < headers.length; i++) {
                switch (headers[i].trim()) {
                    case "Pork and Shrimp":
                        porkShrimpIndex = i + 2; // Quantity index
                        break;
                    case "Japanese":
                        japaneseIndex = i + 2; // Quantity index
                        break;
                    case "Sharksfin":
                        sharksfinIndex = i + 2; // Quantity index
                        break;
                    case "Chicken":
                        chickenIndex = i + 2; // Quantity index
                        break;
                    case "Beef":
                        beefIndex = i + 2; // Quantity index
                        break;
                    case "Tuna":
                        tunaIndex = i + 2; // Quantity index
                        break;
                    case "Bottled Chili":
                        bottledChiliIndex = i + 2; // Quantity index
                        break;
                    case "Cup":
                        cupIndex = i + 2; // Quantity index
                        break;
                }
            }

            // updates the quantities based on the user input
            if (porkShrimpIndex != -1 && japaneseIndex != -1 && sharksfinIndex != -1 &&
                    chickenIndex != -1 && beefIndex != -1 && tunaIndex != -1 &&
                    bottledChiliIndex != -1 && cupIndex != -1) {
                String line = reader.readLine(); // Skip the header for now
                String[] values = line.split(",");
                int porkShrimpStock = Integer.parseInt(values[porkShrimpIndex].trim()) + 1;
                int japaneseStock = Integer.parseInt(values[japaneseIndex].trim()) + 1;
                int sharksfinStock = Integer.parseInt(values[sharksfinIndex].trim()) + 1;
                int chickenStock = Integer.parseInt(values[chickenIndex].trim()) + 1;
                int beefStock = Integer.parseInt(values[beefIndex].trim()) + 1;
                int tunaStock = Integer.parseInt(values[tunaIndex].trim()) + 1;
                int bottledChiliStock = Integer.parseInt(values[bottledChiliIndex].trim()) + 1;
                int cupStock = Integer.parseInt(values[cupIndex].trim()) + 1;

                // writes the updated quantities back to the CSV file
                writer.write(headerLine + "\n"); // Write the header back
                writer.write("Pork and Shrimp,Condition," + porkShrimpStock + "," +
                        "Japanese,Condition," + japaneseStock + "," +
                        "Sharksfin,Condition," + sharksfinStock + "," +
                        "Chicken,Condition," + chickenStock + "," +
                        "Beef,Condition," + beefStock + "," +
                        "Tuna,Condition," + tunaStock + "," +
                        "Bottled Chili,Condition," + bottledChiliStock + "," +
                        "Cup,Condition," + cupStock + "\n");

                // shows a success message
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Success");
                successAlert.setHeaderText("Order Placed");
                successAlert.setContentText("Your order has been successfully placed.");
                successAlert.showAndWait();

                // clears the order fields
                porkAndShrimpFieldOrder.clear();
                japaneseFieldOrder.clear();
                sharksfinFieldOrder.clear();
                chickenFieldOrder.clear();
                beefFieldOrder.clear();
                tunaFieldOrder.clear();
                bottledChiliFieldOrder.clear();
                cupFieldOrder.clear();

                // updates the displayed inventory quantities
                readAndUpdateStocks();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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
    private void backToMenu(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("main_menu.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

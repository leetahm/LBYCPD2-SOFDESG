package org.example.lbycpd2_sofdesg;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UpdateStocksController {
    @FXML
    private TextField porkAndShrimpField;
    @FXML
    private TextField porkAndShrimpConditionField;
    @FXML
    private TextField japaneseField;
    @FXML
    private TextField japaneseConditionField;
    @FXML
    private TextField sharksfinField;
    @FXML
    private TextField sharksfinConditionField;
    @FXML
    private TextField chickenField;
    @FXML
    private TextField chickenConditionField;
    @FXML
    private TextField beefField;
    @FXML
    private TextField beefConditionField;
    @FXML
    private TextField tunaField;
    @FXML
    private TextField tunaConditionField;
    @FXML
    private TextField bottledChiliField;
    @FXML
    private TextField bottledChiliConditionField;
    @FXML
    private TextField cupField;
    @FXML
    private TextField cupConditionField;
    @FXML
    private DatePicker datePicker;
    @FXML
    private MenuButton personOnShift;
    @FXML
    private Button updateStocksButton;

    // input textfields
    @FXML
    private TextField porkAndShrimpBase;
    @FXML
    private TextField porkAndShrimpDiscounted;
    @FXML
    private TextField japaneseBase;
    @FXML
    private TextField japaneseDiscounted;
    @FXML
    private TextField sharksfinBase;
    @FXML
    private TextField sharksfinDiscounted;
    @FXML
    private TextField chickenBase;
    @FXML
    private TextField chickenDiscounted;
    @FXML
    private TextField beefBase;
    @FXML
    private TextField beefDiscounted;
    @FXML
    private TextField tunaBase;
    @FXML
    private TextField tunaDiscounted;
    @FXML
    private TextField bottledChiliBase;
    @FXML
    private TextField bottledChiliDiscounted;
    @FXML
    private TextField cupBase;
    @FXML
    private TextField cupDiscounted;



    // function to update the shift.csv file
    @FXML
    private void updateShiftFile(ActionEvent event) throws IOException {
        // checks if any of the required fields are empty
        LocalDate date = datePicker.getValue();
        if (date == null || personOnShift.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Missing input");
            alert.setContentText("Please double check your inputs.");
            alert.showAndWait();
            return;
        }

        // fills empty fields with "0"
        fillEmptyFieldsWithZero(
                porkAndShrimpBase, porkAndShrimpDiscounted, japaneseBase, japaneseDiscounted,
                sharksfinBase, sharksfinDiscounted, chickenBase, chickenDiscounted,
                beefBase, beefDiscounted, tunaBase, tunaDiscounted,
                bottledChiliBase, bottledChiliDiscounted, cupBase, cupDiscounted
        );

        // reads current inventory from inventory.csv
        String inventoryFilePath = System.getProperty("user.home") + File.separator + "Documents" + File.separator + "inventory.csv";
        List<Integer> currentQuantities = readCurrentInventory(inventoryFilePath);

        // subtracts values from editable text fields
        currentQuantities.set(0, currentQuantities.get(0) - Integer.parseInt(porkAndShrimpBase.getText()));
        currentQuantities.set(1, currentQuantities.get(1) - Integer.parseInt(japaneseBase.getText()));
        currentQuantities.set(2, currentQuantities.get(2) - Integer.parseInt(sharksfinBase.getText()));
        currentQuantities.set(3, currentQuantities.get(3) - Integer.parseInt(chickenBase.getText()));
        currentQuantities.set(4, currentQuantities.get(4) - Integer.parseInt(beefBase.getText()));
        currentQuantities.set(5, currentQuantities.get(5) - Integer.parseInt(tunaBase.getText()));
        currentQuantities.set(6, currentQuantities.get(6) - Integer.parseInt(bottledChiliBase.getText()));
        currentQuantities.set(7, currentQuantities.get(7) - Integer.parseInt(cupBase.getText()));

        // write updated inventory back to inventory.csv
        writeUpdatedInventory(inventoryFilePath, currentQuantities);

        // add a new row to shift.csv
        String newRow = String.join(", ",
                date.format(DateTimeFormatter.ofPattern("MMMM")), String.valueOf(date.getDayOfMonth()), String.valueOf(date.getYear()), "1",
                personOnShift.getText(),
                porkAndShrimpBase.getText(), "Condition", japaneseBase.getText(), "Condition",
                sharksfinBase.getText(), "Condition", chickenBase.getText(), "Condition",
                beefBase.getText(), "Condition", tunaBase.getText(), "Condition",
                bottledChiliBase.getText(), "Condition", cupBase.getText(), "Condition",
                porkAndShrimpDiscounted.getText(), "Condition", japaneseDiscounted.getText(), "Condition",
                sharksfinDiscounted.getText(), "Condition", chickenDiscounted.getText(), "Condition",
                beefDiscounted.getText(), "Condition", tunaDiscounted.getText(), "Condition",
                bottledChiliDiscounted.getText(), "Condition", cupDiscounted.getText(), "Condition"
        );

        String shiftFilePath = System.getProperty("user.home") + File.separator + "Documents" + File.separator + "shift.csv";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(shiftFilePath, true))) {
            writer.newLine();
            writer.write(newRow);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // show success message
        Alert successAlert = new Alert(AlertType.INFORMATION);
        successAlert.setTitle("Success");
        successAlert.setHeaderText("Stocks Updated");
        successAlert.setContentText("Stocks have been successfully updated.");
        successAlert.showAndWait();

        try {
            String filePath = System.getProperty("user.home") + "/Documents/inventory.csv";
            List<String> stockData = readStockDataFromCSV(filePath);
            populateTextFields(stockData);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void fillEmptyFieldsWithZero(TextField... textFields) {
        for (TextField textField : textFields) {
            if (textField.getText().isEmpty()) {
                textField.setText("0");
            }
        }
    }

    private boolean anyTextFieldEmpty(TextField... textFields) {
        return Arrays.stream(textFields).anyMatch(tf -> tf.getText().isEmpty());
    }

    private List<Integer> readCurrentInventory(String filePath) throws IOException {
        List<Integer> quantities = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            reader.readLine(); // Skip the header
            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                quantities.add(Integer.parseInt(values[2].trim())); // Pork and Shrimp Quantity
                quantities.add(Integer.parseInt(values[5].trim())); // Japanese Quantity
                quantities.add(Integer.parseInt(values[8].trim())); // Sharksfin Quantity
                quantities.add(Integer.parseInt(values[11].trim())); // Chicken Quantity
                quantities.add(Integer.parseInt(values[14].trim())); // Beef Quantity
                quantities.add(Integer.parseInt(values[17].trim())); // Tuna Quantity
                quantities.add(Integer.parseInt(values[20].trim())); // Bottled Chili Quantity
                quantities.add(Integer.parseInt(values[23].trim())); // Cup Quantity
            }
        }
        return quantities;
    }

    private void writeUpdatedInventory(String filePath, List<Integer> quantities) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            // writes the header
            writer.write("Pork and Shrimp, Condition, Quantity,Japanese, Condition, Quantity,Sharksfin, Condition, Quantity,Chicken, Condition, Quantity, Beef, Condition, Quantity, Tuna, Condition, Quantity,Bottled Chili, Condition, Quantity,Cup, Condition, Quantity");
            writer.newLine();

            // writes the updated inventory data
            writer.write(String.format("%s, %s, %d,%s, %s, %d,%s, %s, %d,%s, %s, %d,%s, %s, %d,%s, %s, %d,%s, %s, %d,%s, %s, %d",
                    "Pork and Shrimp", getCondition(quantities.get(0), 200), quantities.get(0),
                    "Japanese", getCondition(quantities.get(1), 200), quantities.get(1),
                    "Sharksfin", getCondition(quantities.get(2), 200), quantities.get(2),
                    "Chicken", getCondition(quantities.get(3), 200), quantities.get(3),
                    "Beef", getCondition(quantities.get(4), 200), quantities.get(4),
                    "Tuna", getCondition(quantities.get(5), 200), quantities.get(5),
                    "Bottled Chili", getCondition(quantities.get(6), 30), quantities.get(6),
                    "Cup", getCondition(quantities.get(7), 200), quantities.get(7)));
        }
    }

    private String getCondition(int quantity, int threshold) {
        return quantity < threshold ? "Low" : "High";
    }

    public void initialize() {
        try {
            // load data from CSV and populate text fields
            String filePath = System.getProperty("user.home") + "/Documents/inventory.csv";
            List<String> stockData = readStockDataFromCSV(filePath);
            populateTextFields(stockData);

            // loads employees from CSV and populate menunutton
            String employeesFilePath = System.getProperty("user.home") + "/Documents/employees.csv";
            List<String> employeeNames = readEmployeeNamesFromCSV(employeesFilePath);
            populatePersonOnShiftMenuButton(employeeNames);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<String> readEmployeeNamesFromCSV(String filePath) throws IOException {
        List<String> employeeNames = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length > 0) {
                    employeeNames.add(values[0]);
                }
            }
        }
        return employeeNames;
    }

    private void populatePersonOnShiftMenuButton(List<String> employeeNames) {
        for (String name : employeeNames) {
            MenuItem menuItem = new MenuItem(name);
            menuItem.setOnAction(event -> personOnShift.setText(name));
            personOnShift.getItems().add(menuItem);
        }
    }

    private List<String> readStockDataFromCSV(String filePath) throws IOException {
        String lastLine = null;
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                lastLine = line;
            }
        }
        return lastLine != null ? Arrays.asList(lastLine.split(",")) : null;
    }


    private void populateTextFields(List<String> stockData) {
        if (stockData != null && stockData.size() >= 24) {
            porkAndShrimpField.setText(stockData.get(2));
            porkAndShrimpConditionField.setText(stockData.get(1));
            japaneseField.setText(stockData.get(5));
            japaneseConditionField.setText(stockData.get(4));
            sharksfinField.setText(stockData.get(8));
            sharksfinConditionField.setText(stockData.get(7));
            chickenField.setText(stockData.get(11));
            chickenConditionField.setText(stockData.get(10));
            beefField.setText(stockData.get(14));
            beefConditionField.setText(stockData.get(13));
            tunaField.setText(stockData.get(17));
            tunaConditionField.setText(stockData.get(16));
            bottledChiliField.setText(stockData.get(20));
            bottledChiliConditionField.setText(stockData.get(19));
            cupField.setText(stockData.get(23));
            cupConditionField.setText(stockData.get(22));
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




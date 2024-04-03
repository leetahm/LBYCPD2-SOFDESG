package org.example.lbycpd2_sofdesg;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;

public class ManageEmployeesController {
    @FXML
    private MenuButton employeeMenuButton;
    @FXML
    private MenuButton selectMonthButton;
    @FXML
    private MenuButton selectDayButton;
    @FXML
    private MenuButton selectYearButton;
    @FXML
    private TextField employeeNameField;
    @FXML
    private Button addEmployeeButton;

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
    @FXML
    private TextField totalSalesOnSelectedShift;

    @FXML
    public void initialize() {
        populateEmployeeMenuButton();
        addEmployeeButton.setOnAction(this::addEmployee);
    }

    private void updateTextFields(String employeeName, String month, String day, String year) {
        String filePath = System.getProperty("user.home") + "/Documents/shift.csv";
        boolean entryFound = false;
        double totalSales = 0;

        // Initialize variables to accumulate quantities and sales
        int porkAndShrimpTotalBase = 0, porkAndShrimpTotalDiscounted = 0;
        int japaneseTotalBase = 0, japaneseTotalDiscounted = 0;
        int sharksfinTotalBase = 0, sharksfinTotalDiscounted = 0;
        int chickenTotalBase = 0, chickenTotalDiscounted = 0;
        int beefTotalBase = 0, beefTotalDiscounted = 0;
        int tunaTotalBase = 0, tunaTotalDiscounted = 0;
        int bottledChiliTotalBase = 0, bottledChiliTotalDiscounted = 0;
        int cupTotalBase = 0, cupTotalDiscounted = 0;

        // Prices
        double pricePorkAndShrimp = 11.75, priceJapanese = 14.0, priceBeef = 12.5;
        double priceTuna = 12.5, priceChicken = 11.75, priceSharksfin = 12.5;
        double priceCup = 15.0, priceBottledChili = 130.0;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            br.readLine(); // skips the header
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 33 && parts[4].trim().equals(employeeName) &&
                        parts[0].trim().equals(month) && parts[1].trim().equals(day) && parts[2].trim().equals(year)) {
                    entryFound = true;

                    // Accumulate quantities
                    if (!parts[5].trim().isEmpty()) porkAndShrimpTotalBase += Integer.parseInt(parts[5].trim());
                    if (!parts[21].trim().isEmpty()) porkAndShrimpTotalDiscounted += Integer.parseInt(parts[21].trim());
                    if (!parts[7].trim().isEmpty()) japaneseTotalBase += Integer.parseInt(parts[7].trim());
                    if (!parts[23].trim().isEmpty()) japaneseTotalDiscounted += Integer.parseInt(parts[23].trim());
                    if (!parts[9].trim().isEmpty()) sharksfinTotalBase += Integer.parseInt(parts[9].trim());
                    if (!parts[25].trim().isEmpty()) sharksfinTotalDiscounted += Integer.parseInt(parts[25].trim());
                    if (!parts[11].trim().isEmpty()) chickenTotalBase += Integer.parseInt(parts[11].trim());
                    if (!parts[27].trim().isEmpty()) chickenTotalDiscounted += Integer.parseInt(parts[27].trim());
                    if (!parts[13].trim().isEmpty()) beefTotalBase += Integer.parseInt(parts[13].trim());
                    if (!parts[29].trim().isEmpty()) beefTotalDiscounted += Integer.parseInt(parts[29].trim());
                    if (!parts[15].trim().isEmpty()) tunaTotalBase += Integer.parseInt(parts[15].trim());
                    if (!parts[31].trim().isEmpty()) tunaTotalDiscounted += Integer.parseInt(parts[31].trim());
                    if (!parts[17].trim().isEmpty()) bottledChiliTotalBase += Integer.parseInt(parts[17].trim());
                    if (!parts[33].trim().isEmpty()) bottledChiliTotalDiscounted += Integer.parseInt(parts[33].trim());
                    if (!parts[19].trim().isEmpty()) cupTotalBase += Integer.parseInt(parts[19].trim());
                    if (!parts[35].trim().isEmpty()) cupTotalDiscounted += Integer.parseInt(parts[35].trim());

                    // Calculate total sales
                    totalSales += (porkAndShrimpTotalBase + porkAndShrimpTotalDiscounted) * pricePorkAndShrimp +
                            (japaneseTotalBase + japaneseTotalDiscounted) * priceJapanese +
                            (sharksfinTotalBase + sharksfinTotalDiscounted) * priceSharksfin +
                            (chickenTotalBase + chickenTotalDiscounted) * priceChicken +
                            (beefTotalBase + beefTotalDiscounted) * priceBeef +
                            (tunaTotalBase + tunaTotalDiscounted) * priceTuna +
                            (bottledChiliTotalBase + bottledChiliTotalDiscounted) * priceBottledChili +
                            (cupTotalBase + cupTotalDiscounted) * priceCup;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (entryFound) {
            // Set the total sales on the TextField
            totalSalesOnSelectedShift.setText(String.format("%.2f", totalSales));

            // Set the text fields with accumulated quantities
            porkAndShrimpBase.setText(String.valueOf(porkAndShrimpTotalBase));
            porkAndShrimpDiscounted.setText(String.valueOf(porkAndShrimpTotalDiscounted));
            japaneseBase.setText(String.valueOf(japaneseTotalBase));
            japaneseDiscounted.setText(String.valueOf(japaneseTotalDiscounted));
            sharksfinBase.setText(String.valueOf(sharksfinTotalBase));
            sharksfinDiscounted.setText(String.valueOf(sharksfinTotalDiscounted));
            chickenBase.setText(String.valueOf(chickenTotalBase));
            chickenDiscounted.setText(String.valueOf(chickenTotalDiscounted));
            beefBase.setText(String.valueOf(beefTotalBase));
            beefDiscounted.setText(String.valueOf(beefTotalDiscounted));
            tunaBase.setText(String.valueOf(tunaTotalBase));
            tunaDiscounted.setText(String.valueOf(tunaTotalDiscounted));
            bottledChiliBase.setText(String.valueOf(bottledChiliTotalBase));
            bottledChiliDiscounted.setText(String.valueOf(bottledChiliTotalDiscounted));
            cupBase.setText(String.valueOf(cupTotalBase));
            cupDiscounted.setText(String.valueOf(cupTotalDiscounted));

            // Populate the text fields with specific shift data
            populateTextFields(employeeName, month, day, year);
        } else {
            // If no matching entry is found, clear the text fields
            clearTextFields();
        }
    }
    private void clearTextFields() {
        porkAndShrimpBase.clear();
        porkAndShrimpDiscounted.clear();
        japaneseBase.clear();
        japaneseDiscounted.clear();
        sharksfinBase.clear();
        sharksfinDiscounted.clear();
        chickenBase.clear();
        chickenDiscounted.clear();
        beefBase.clear();
        beefDiscounted.clear();
        tunaBase.clear();
        tunaDiscounted.clear();
        bottledChiliBase.clear();
        bottledChiliDiscounted.clear();
        cupBase.clear();
        cupDiscounted.clear();
        totalSalesOnSelectedShift.clear();
    }



    private void populateTextFields(String employeeName, String month, String day, String year) {
        String filePath = System.getProperty("user.home") + "/Documents/shift.csv";

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine(); // Skip the header
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 36 && parts[4].trim().equals(employeeName) &&
                        parts[0].trim().equals(month) && parts[1].trim().equals(day) && parts[2].trim().equals(year)) {

                    porkAndShrimpBase.setText(parts[5].trim());
                    porkAndShrimpDiscounted.setText(parts[21].trim());
                    japaneseBase.setText(parts[7].trim());
                    japaneseDiscounted.setText(parts[23].trim());
                    sharksfinBase.setText(parts[9].trim());
                    sharksfinDiscounted.setText(parts[25].trim());
                    chickenBase.setText(parts[11].trim());
                    chickenDiscounted.setText(parts[27].trim());
                    beefBase.setText(parts[13].trim());
                    beefDiscounted.setText(parts[29].trim());
                    tunaBase.setText(parts[15].trim());
                    tunaDiscounted.setText(parts[31].trim());
                    bottledChiliBase.setText(parts[17].trim());
                    bottledChiliDiscounted.setText(parts[33].trim());
                    cupBase.setText(parts[19].trim());
                    cupDiscounted.setText(parts[35].trim());
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // adds the emplyee
    @FXML
    private void addEmployee(ActionEvent event) {
        String employeeName = employeeNameField.getText().trim();
        if (!employeeName.isEmpty()) {
            String filePath = System.getProperty("user.home") + "/Documents/employees.csv";
            try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(filePath, true)))) {
                out.println(employeeName);

                // Show a success alert
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Success");
                successAlert.setHeaderText("Employee Added");
                successAlert.setContentText("Employee " + employeeName + " has been successfully added.");
                successAlert.showAndWait();

            } catch (IOException e) {
                e.printStackTrace();
            }
            populateEmployeeMenuButton();
            employeeNameField.clear();
        }
    }

    private void populateEmployeeMenuButton() {
        String filePath = System.getProperty("user.home") + "/Documents/employees.csv";
        Set<String> employeeNames = new HashSet<>(); // to avoid duplicate names
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String employeeName = line.trim();
                if (!employeeName.isEmpty()) {
                    employeeNames.add(employeeName);
                    System.out.println("Added employee: " + employeeName);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Total employees found: " + employeeNames.size());

        employeeMenuButton.getItems().clear(); // clears existing items
        for (String name : employeeNames) {
            MenuItem menuItem = new MenuItem(name);
            menuItem.setOnAction(event -> {
                employeeMenuButton.setText(name);
                populateShiftDateOptions(name);
            });
            employeeMenuButton.getItems().add(menuItem);
        }
    }

    private void populateShiftDateOptions(String employeeName) {
        String filePath = System.getProperty("user.home") + "/Documents/shift.csv";
        Set<String> months = new HashSet<>();
        Map<String, Set<String>> monthToDays = new HashMap<>();
        Map<String, Set<String>> monthToYears = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            br.readLine(); // skips the header
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length > 4 && parts[4].trim().equals(employeeName)) {
                    String month = parts[0].trim();
                    String day = parts[1].trim();
                    String year = parts[2].trim();

                    // Debugging print statements
                    System.out.println("Found shift for employee: " + employeeName + ", month: " + month + ", day: " + day + ", year: " + year);

                    months.add(month);
                    monthToDays.computeIfAbsent(month, k -> new HashSet<>()).add(day);
                    monthToYears.computeIfAbsent(month, k -> new HashSet<>()).add(year);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        populateMenuButton(selectMonthButton, months, (selectedMonth) -> {
            populateMenuButton(selectDayButton, monthToDays.getOrDefault(selectedMonth, Collections.emptySet()), (selectedDay) -> {
                updateTextFields(employeeName, selectedMonth, selectedDay, selectYearButton.getText());
            });
            populateMenuButton(selectYearButton, monthToYears.getOrDefault(selectedMonth, Collections.emptySet()), (selectedYear) -> {
                updateTextFields(employeeName, selectedMonth, selectDayButton.getText(), selectedYear);
            });
            selectDayButton.setText("Select Day");
            selectYearButton.setText("Select Year");
        });
    }

    private void populateMenuButton(MenuButton menuButton, Set<String> options, MenuButtonEventHandler eventHandler) {
        menuButton.getItems().clear();
        for (String option : options) {
            MenuItem menuItem = new MenuItem(option);
            menuItem.setOnAction(event -> {
                menuButton.setText(option);
                eventHandler.handle(option);
            });
            menuButton.getItems().add(menuItem);
        }
    }

    @FunctionalInterface
    interface MenuButtonEventHandler {
        void handle(String option);
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

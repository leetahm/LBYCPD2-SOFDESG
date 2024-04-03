package org.example.lbycpd2_sofdesg;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.Month;
import java.util.*;


public class OverviewController {

    private String selectedMonth = null;
    private String selectedQuarter = null;
    private String selectedYear = null;
    private String selectedDayMonth = null;
    private String selectedDay = null;
    private String selectedDayYear = null;



    @FXML
    private MenuButton selectedDayMonthButton;
    @FXML
    private MenuButton selectedDayMenuButton;
    @FXML
    private MenuButton selectedDayYearButton;
    @FXML
    private TextField dailySales;
    @FXML
    private MenuButton monthMenuButton;
    @FXML
    private MenuButton quarterMenuButton;
    @FXML
    private MenuButton yearMenuButton;
    @FXML
    private MenuButton monthYearMenuButton;
    @FXML
    private MenuButton quarterYearMenuButton;
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
    private TextField monthlySales;
    @FXML
    private TextField quarterlySales;
    @FXML
    private TextField yearlySales;

    private String selectedMonthYear = null;
    private String selectedQuarterYear = null;


    @FXML
    private void initialize()
    {
        readAndUpdateStocks();
        populateMonthMenu();
        populateQuarterMenu();
        populateYearMenus();
        populateDayMonthMenu();
        populateDayMenu();
    }

    private void populateDayMonthMenu() {
        Set<String> months = new TreeSet<>();
        String userHome = System.getProperty("user.home");
        String filePath = userHome + File.separator + "Documents" + File.separator + "shift.csv";

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length > 0) {
                    months.add(values[0].trim());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (String month : months) {
            MenuItem menuItem = new MenuItem(month);
            menuItem.setOnAction(event -> {
                selectedDayMonthButton.setText(month);
                selectedDayMonth = month;
                populateDayMenu();
            });
            selectedDayMonthButton.getItems().add(menuItem);
        }
    }


    // calculates sales on selected day function
    private void calculateDailySales() {
        if (selectedDay == null || selectedDayMonth == null || selectedDayYear == null) {
            return;
        }

        double totalSales = 0;
        String userHome = System.getProperty("user.home");
        String filePath = userHome + File.separator + "Documents" + File.separator + "shift.csv";

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length > 3 && values[0].equalsIgnoreCase(selectedDayMonth) && values[1].trim().equalsIgnoreCase(selectedDay) && values[2].trim().equalsIgnoreCase(selectedDayYear)) {
                    int porkShrimpSales = parseIntOrZero(values[5].trim());
                    int japaneseSales = parseIntOrZero(values[7].trim());
                    int sharksfinSales = parseIntOrZero(values[9].trim());
                    int chickenSales = parseIntOrZero(values[11].trim());
                    int beefSales = parseIntOrZero(values[13].trim());
                    int tunaSales = parseIntOrZero(values[15].trim());
                    int bottledChiliSales = parseIntOrZero(values[17].trim());
                    int cupSales = parseIntOrZero(values[19].trim());

                    totalSales += (porkShrimpSales * 11.75) +
                            (japaneseSales * 14) +
                            (sharksfinSales * 12.5) +
                            (chickenSales * 11.75) +
                            (beefSales * 12.5) +
                            (tunaSales * 12.5) +
                            (bottledChiliSales * 130) +
                            (cupSales * 15);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // display total sales
        dailySales.setText(String.format("%.2f pesos", totalSales));
    }

    private void populateDayMenu() {
        if (selectedDayMonth == null || selectedDayYear == null) {
            return;
        }

        Set<String> days = new TreeSet<>();
        String userHome = System.getProperty("user.home");
        String filePath = userHome + File.separator + "Documents" + File.separator + "shift.csv";

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length > 3 && values[0].equalsIgnoreCase(selectedDayMonth) && values[2].trim().equalsIgnoreCase(selectedDayYear)) {
                    days.add(values[1].trim());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        selectedDayMenuButton.getItems().clear(); // clears existing items
        for (String day : days) {
            MenuItem menuItem = new MenuItem(day);
            menuItem.setOnAction(event -> {
                selectedDayMenuButton.setText(day);
                selectedDay = day;
                calculateDailySales();
            });
            selectedDayMenuButton.getItems().add(menuItem);
        }
    }

    // calculates the sales per month
    private void calculateSales() {
        if (selectedMonth == null || selectedMonthYear == null) {
            return;
        }

        double totalSales = 0;
        String userHome = System.getProperty("user.home");
        String filePath = userHome + File.separator + "Documents" + File.separator + "shift.csv";

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length > 3 && values[0].equalsIgnoreCase(selectedMonth) && values[2].trim().equalsIgnoreCase(selectedMonthYear)) {
                    int porkShrimpSales = parseIntOrZero(values[5].trim());
                    int japaneseSales = parseIntOrZero(values[7].trim());
                    int sharksfinSales = parseIntOrZero(values[9].trim());
                    int chickenSales = parseIntOrZero(values[11].trim());
                    int beefSales = parseIntOrZero(values[13].trim());
                    int tunaSales = parseIntOrZero(values[15].trim());
                    int bottledChiliSales = parseIntOrZero(values[17].trim());
                    int cupSales = parseIntOrZero(values[19].trim());

                    totalSales += (porkShrimpSales * 11.75) +
                            (japaneseSales * 14) +
                            (sharksfinSales * 12.5) +
                            (chickenSales * 11.75) +
                            (beefSales * 12.5) +
                            (tunaSales * 12.5) +
                            (bottledChiliSales * 130) +
                            (cupSales * 15);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // display total sales
        monthlySales.setText(String.format("%.2f pesos", totalSales));
    }

    private int parseIntOrZero(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private void calculateQuarterlySales() {
        if (selectedQuarter == null || selectedQuarterYear == null) {
            return;
        }

        double totalSales = 0;
        String userHome = System.getProperty("user.home");
        String filePath = userHome + File.separator + "Documents" + File.separator + "shift.csv";

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length > 3 && values[2].trim().equalsIgnoreCase(selectedQuarterYear) && isMonthInQuarter(values[0].trim(), selectedQuarter)) {
                    int porkShrimpSales = parseIntOrZero(values[5].trim());
                    int japaneseSales = parseIntOrZero(values[7].trim());
                    int sharksfinSales = parseIntOrZero(values[9].trim());
                    int chickenSales = parseIntOrZero(values[11].trim());
                    int beefSales = parseIntOrZero(values[13].trim());
                    int tunaSales = parseIntOrZero(values[15].trim());
                    int bottledChiliSales = parseIntOrZero(values[17].trim());
                    int cupSales = parseIntOrZero(values[19].trim());

                    totalSales += (porkShrimpSales * 11.75) +
                            (japaneseSales * 14) +
                            (sharksfinSales * 12.5) +
                            (chickenSales * 11.75) +
                            (beefSales * 12.5) +
                            (tunaSales * 12.5) +
                            (bottledChiliSales * 130) +
                            (cupSales * 15);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Display the total sales
        quarterlySales.setText(String.format("%.2f pesos", totalSales));
    }


    private void calculateYearlySales() {
        if (selectedYear == null) {
            return;
        }

        double totalSales = 0;
        String userHome = System.getProperty("user.home");
        String filePath = userHome + File.separator + "Documents" + File.separator + "shift.csv";

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length > 3 && values[2].trim().equalsIgnoreCase(selectedYear)) {
                    int porkShrimpSales = parseIntOrZero(values[5].trim());
                    int japaneseSales = parseIntOrZero(values[7].trim());
                    int sharksfinSales = parseIntOrZero(values[9].trim());
                    int chickenSales = parseIntOrZero(values[11].trim());
                    int beefSales = parseIntOrZero(values[13].trim());
                    int tunaSales = parseIntOrZero(values[15].trim());
                    int bottledChiliSales = parseIntOrZero(values[17].trim());
                    int cupSales = parseIntOrZero(values[19].trim());

                    totalSales += (porkShrimpSales * 11.75) +
                            (japaneseSales * 14) +
                            (sharksfinSales * 12.5) +
                            (chickenSales * 11.75) +
                            (beefSales * 12.5) +
                            (tunaSales * 12.5) +
                            (bottledChiliSales * 130) +
                            (cupSales * 15);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Display total sales
        yearlySales.setText(String.format("%.2f pesos", totalSales));
    }

    private boolean isMonthInQuarter(String month, String quarter) {
        switch (quarter) {
            case "Q1":
                return month.equals("January") || month.equals("February") || month.equals("March");
            case "Q2":
                return month.equals("April") || month.equals("May") || month.equals("June");
            case "Q3":
                return month.equals("July") || month.equals("August") || month.equals("September");
            case "Q4":
                return month.equals("October") || month.equals("November") || month.equals("December");
            default:
                return false;
        }
    }

    // will read the shifts csv
    //  populates menu button with the months
    private void populateMonthMenu() {
        Set<String> months = new TreeSet<>();
        String userHome = System.getProperty("user.home");
        String filePath = userHome + File.separator + "Documents" + File.separator + "shift.csv";

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length > 0) {
                    months.add(values[0].trim());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (String month : months) {
            MenuItem menuItem = new MenuItem(month);
            menuItem.setOnAction(event -> {
                monthMenuButton.setText(month);
                selectedMonth = month;
                populateYearMenus();
                calculateSales();
            });
            monthMenuButton.getItems().add(menuItem);
        }
    }


    private int getMonthIndex(String month) {
        switch (month) {
            case "January":
                return 0;
            case "February":
                return 1;
            case "March":
                return 2;
            case "April":
                return 3;
            case "May":
                return 4;
            case "June":
                return 5;
            case "July":
                return 6;
            case "August":
                return 7;
            case "September":
                return 8;
            case "October":
                return 9;
            case "November":
                return 10;
            case "December":
                return 11;
            default:
                return -1; // Handle invalid month
        }
    }

    // will populate quarter
    private void populateQuarterMenu() {
        Set<String> quartersSet = new HashSet<>();
        String userHome = System.getProperty("user.home");
        String filePath = userHome + File.separator + "Documents" + File.separator + "shift.csv";

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length > 3) {
                    String month = values[0].trim();
                    String quarter = getQuarterFromMonth(month);
                    if (quarter != null) {
                        quartersSet.add(quarter);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        quarterMenuButton.getItems().clear(); // Clear existing items
        for (String quarter : quartersSet) {
            MenuItem menuItem = new MenuItem(quarter);
            menuItem.setOnAction(event -> {
                quarterMenuButton.setText(quarter);
                selectedQuarter = quarter;
                calculateQuarterlySales();
            });
            quarterMenuButton.getItems().add(menuItem);
        }
    }

    private String getQuarterFromMonth(String month) {
        switch (month) {
            case "January":
            case "February":
            case "March":
                return "Q1";
            case "April":
            case "May":
            case "June":
                return "Q2";
            case "July":
            case "August":
            case "September":
                return "Q3";
            case "October":
            case "November":
            case "December":
                return "Q4";
            default:
                return null;
        }
    }


    // will populate year
    private void populateYearMenus() {
        Set<String> years = new TreeSet<>(); // sorts the years
        String userHome = System.getProperty("user.home");
        String filePath = userHome + File.separator + "Documents" + File.separator + "shift.csv";

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length > 2 && (selectedMonth == null || values[0].trim().equalsIgnoreCase(selectedMonth))) {
                    years.add(values[2].trim());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (String year : years) {
            MenuItem monthMenuItem = new MenuItem(year);
            monthMenuItem.setOnAction(event -> {
                monthYearMenuButton.setText(year);
                selectedMonthYear = year;
                populateDayMenu();
                calculateSales(); // calculate monthly sales
            });
            monthYearMenuButton.getItems().add(monthMenuItem);

            MenuItem quarterMenuItem = new MenuItem(year);
            quarterMenuItem.setOnAction(event -> {
                quarterYearMenuButton.setText(year);
                selectedQuarterYear = year;
                calculateQuarterlySales(); // calculate quarterly sales
            });
            quarterYearMenuButton.getItems().add(quarterMenuItem);

            MenuItem yearMenuItem = new MenuItem(year);
            yearMenuItem.setOnAction(event -> {
                yearMenuButton.setText(year);
                selectedYear = year;
                calculateYearlySales(); // calculate yearly sales
            });
            yearMenuButton.getItems().add(yearMenuItem);

            // For day's year selection
            MenuItem dayYearMenuItem = new MenuItem(year);
            dayYearMenuItem.setOnAction(event -> {
                selectedDayYearButton.setText(year);
                selectedDayYear = year;
                populateDayMenu();
                calculateDailySales(); // calculate daily sales
            });
            selectedDayYearButton.getItems().add(dayYearMenuItem);
        }
    }



    private void disableMonthsBeforeStart(String year) {
        for (MenuItem item : monthMenuButton.getItems()) {
            item.setDisable(false);
        }
    }


    // will read the iunventory csv
    private void readAndUpdateStocks() {
        String userHome = System.getProperty("user.home");
        String filePath = userHome + File.separator + "Documents" + File.separator + "inventory.csv";

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String currentLine;
            String lastLine = null;
            // reader.readLine(); // Skip the header line
            while ((currentLine = reader.readLine()) != null) {
                if (!currentLine.trim().isEmpty()) { // Check if the line is not blank
                    lastLine = currentLine; // Update lastLine only if it's not blank
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
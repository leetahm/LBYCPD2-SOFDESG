package org.example.lbycpd2_sofdesg;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;

public class InventoryManager extends Application {
    // function to check and make csv file
    private void createFile(String fileName, String header) {
        String userHome = System.getProperty("user.home");
        String filePath = userHome + File.separator + "Documents" + File.separator + fileName;

        File file = new File(filePath);
        if (!file.exists()) {
            try {
                file.createNewFile();
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                    writer.write(header);
                    writer.newLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private boolean hasAtLeastTwoRows(String fileName) {
        String userHome = System.getProperty("user.home");
        String filePath = userHome + File.separator + "Documents" + File.separator + fileName;

        File file = new File(filePath);
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                // Read the header row
                reader.readLine();
                // Check if there's a second row with content
                String secondRow = reader.readLine();
                return secondRow != null && !secondRow.trim().isEmpty();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }


    @Override
    public void start(Stage stage) throws Exception {
        // creates shifts
        String shiftHeader = "Month, Day, Year, Shift Number, Employee Name, Pork and Shrimp, Condition, Japanese, Condition, Sharksfin, Condition, Chicken, Condition, Beef, Condition, Tuna, Condition, Bottled Chili, Condition, Cup, Condition,Pork and Shrimp (discounted), Condition, Japanese (discounted), Condition, Sharksfin (discounted), Condition, Chicken (discounted), Condition, Beef (discounted), Condition,Tuna (discounted), Condition,Bottled Chili (discounted), Condition,Cup (discounted), Condition";
        createFile("shift.csv", shiftHeader);

        // creates inventory
        String stockHeader = "Pork and Shrimp, Condition, Quantity,Japanese, Condition, Quantity,Sharksfin, Condition, Quantity,Chicken, Condition, Quantity, Beef, Condition, Quantity, Tuna, Condition, Quantity,Bottled Chili, Condition, Quantity,Cup, Condition, Quantity";
        createFile("inventory.csv", stockHeader);

        // creates employees
        String employeeHeader = "Name";
        createFile("employees.csv", employeeHeader);

        // Check if all files have at least two rows
        boolean allFilesReady = hasAtLeastTwoRows("inventory.csv");

        // Load the appropriate scene based on the file checks
        String fxmlFile = allFilesReady ? "main_menu.fxml" : "start_screen.fxml";
        FXMLLoader fxmlLoader = new FXMLLoader(InventoryManager.class.getResource(fxmlFile));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 800);
        stage.setTitle("Inventory Manager");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}

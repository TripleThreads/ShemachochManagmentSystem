package triplethreads.shemachoch.Pages;


import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;


public class Controller {
    Stage stage = Main.stage;
    Scene customersScene, homeScene, helpScene, itemsScene, sellScene, reportScene;

    Locale locale;
    ResourceBundle bundle;

    Label home, sell, items, customers, report, help;
    static String CURRENT_LANG = "English";
    JFXComboBox dropdownButton;
    Parent homeParent;

    public void sellMenu() throws IOException {
        homeParent = FXMLLoader.load(getClass().getResource("UI/SellUI.fxml"));
        sellScene = new Scene(homeParent);

        Stage stage = Main.stage;
        stage.setScene(sellScene);
        stage.show();
        setSceneItems(sellScene);
    }


    public void homeMenu() throws IOException {
        homeParent = FXMLLoader.load(getClass().getResource("UI/HomeUI.fxml"));
        homeScene = new Scene(homeParent);

        stage = Main.stage;
        stage.setScene(homeScene);
        stage.show();

        dropdownButton = (JFXComboBox) homeScene.lookup("#combo_box");
        dropdownButton.getItems().addAll("English", "Amharic");

        setSceneItems(homeScene);
    }


    public void customersMenu() throws IOException {
        homeParent = FXMLLoader.load(getClass().getResource("UI/CustomersUI.fxml"));
        customersScene = new Scene(homeParent);

        stage = Main.stage;
        stage.setScene(customersScene);
        stage.show();

        setSceneItems(customersScene);
    }

    public void helpMenu() throws IOException {
        homeParent = FXMLLoader.load(getClass().getResource("UI/HelpUI.fxml"));
        helpScene = new Scene(homeParent);

        stage = Main.stage;
        stage.setScene(helpScene);
        stage.show();
    }

    public void itemsMenu() throws IOException {
        homeParent = FXMLLoader.load(getClass().getResource("UI/ItemsUI.fxml"));
        itemsScene = new Scene(homeParent);

        createItemsTable();
        stage = Main.stage;
        stage.setScene(itemsScene);
        stage.show();

        setSceneItems(itemsScene);
    }

    public void reportMenu() throws IOException {
        homeParent = FXMLLoader.load(getClass().getResource("UI/ReportUI.fxml"));
        reportScene = new Scene(homeParent);

        stage = Main.stage;
        stage.setScene(reportScene);
        stage.show();

        setSceneItems(reportScene);
    }

    public void setSceneItems(Scene scene) {
        home = (Label) scene.lookup("#home_menu");
        sell = (Label) scene.lookup("#sell_menu");
        items = (Label) scene.lookup("#items_menu");
        customers = (Label) scene.lookup("#customers_menu");
        report = (Label) scene.lookup("#report_menu");
        help = (Label) scene.lookup("#help_menu");
    }


    public void createItemsTable() {
        JFXTreeTableView jfxTreeTableView = (JFXTreeTableView) itemsScene.lookup("#items_table");
        JFXTreeTableColumn<String, String> name = new JFXTreeTableColumn<>("Item name");
        name.setMaxWidth(200);
        JFXTreeTableColumn<String, String> type = new JFXTreeTableColumn<>("item Type");
        type.setMaxWidth(200);
        JFXTreeTableColumn<String, String> price = new JFXTreeTableColumn<>("item price");
        price.setMaxWidth(200);

        jfxTreeTableView.getColumns().addAll(name, type, price);

    }

    public void setLanguage() {
        if (homeParent == null) {
            homeParent = Main.homeUI;
        }
        JFXComboBox dropdownButton = (JFXComboBox) homeParent.lookup("#combo_box");
        CURRENT_LANG = dropdownButton.getValue().toString();
        changeLanguage();
    }

    public void changeLanguage() {
        String locale_string = "en";
        if (CURRENT_LANG.equals("Amharic")) {
            locale_string = "amh";
        } else if (CURRENT_LANG.equals("English")) {
            locale_string = "en";
        }

        locale = new Locale(locale_string);
        bundle = ResourceBundle.getBundle("triplethreads.shemachoch.Pages.lang", locale);


        Main.home.setText(bundle.getString("home_menu"));
        Main.sell.setText(bundle.getString("sell_menu"));
        Main.items.setText(bundle.getString("items_menu"));
        Main.customers.setText(bundle.getString("customers_menu"));
        Main.report.setText(bundle.getString("report_menu"));
        Main.help.setText(bundle.getString("help_menu"));
        Main.help.setText(bundle.getString("help_menu"));

    }

}

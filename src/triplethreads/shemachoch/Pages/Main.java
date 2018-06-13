package triplethreads.shemachoch.Pages;

import com.jfoenix.controls.JFXComboBox;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class Main extends Application {
    static Parent homeUI;
    static Stage stage;
    static JFXComboBox dropdownButton;
    static Label label;
    static Label home, sell, items, customers, report, help;
    static boolean fromMain = true;
    boolean logged_in = false;
    @Override
    public void start(Stage primaryStage) throws Exception {

        if (logged_in) {
            homeUI = FXMLLoader.load(getClass().getResource("UI/HomeUI.fxml"));
        } else {
            homeUI = FXMLLoader.load(getClass().getResource("UI/LoginUI.fxml"));
        }
        stage = primaryStage;
        primaryStage.setTitle("Shemt");
        Scene scene = new Scene(homeUI, 1024, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
        if (!logged_in) {
            return;
        }
        dropdownButton = (JFXComboBox) homeUI.lookup("#combo_box");
        dropdownButton.getItems().addAll("English", "Amharic");

        label = (Label) homeUI.lookup("#home_label");


        //let us initialize the menu items
        home = (Label) homeUI.lookup("#home_menu");
        sell = (Label) homeUI.lookup("#sell_menu");
        items = (Label) homeUI.lookup("#items_menu");
        customers = (Label) homeUI.lookup("#customers_menu");
        report = (Label) homeUI.lookup("#report_menu");
        help = (Label) homeUI.lookup("#help_menu");

        Controller.fromMain();
        Controller.changeLanguage();
    }


    public static void main(String[] args) {
        launch(args);
    }
}

package triplethreads.shemachoch.Pages;


import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import triplethreads.shemachoch.EntityClasses.Seller;

import java.io.*;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.function.Predicate;

/**
 * @author segni
 * the UI is built on javafx. one Stage is used for all scenes
 * but each menu has its own scene i.e FXML file. This is pretty similar to tiater where scene changes but the stage
 * will remain the same.
 *
 * homeMenu method listens for home button is clicked from main menu.
 * sellMenu method listens for sell button is clicked from main menu.
 * itemsMenu method listens for items button is clicked from main menu.
 * customersMenu method listens for customer button is clicked from main menu.
 * reportMenu method listens for report button is clicked from main menu.
 * helpMenu method listens for help button is clicked from main menu.
 *
 * setSceneItems is used to get elements by their id from the scene
 * setLanguage is called when the language is changed [using the dropdown from UI]
 * writeLanguage is used to write user language prefrence on configuration file
 * readLanguage is used to read from configuratiion file and set the UI
 * changeLanguage changes the labels on the UI
 */
public class Controller {
    public Stage stage = Main.stage;
    static Scene customersScene, homeScene, helpScene, addItemsScene,
            itemsScene, sellScene, reportScene, addCustomerScene;
    static Locale locale;
    static ResourceBundle bundle;
    static Label home, sell, items, customers, report, help, logo;
    static String CURRENT_LANG = "English";
    static JFXComboBox dropdownButton;
    static Parent homeParent;
    static JFXTextField searchInput;
    static Pane verifyCustomerPane, specifyAmountPane;
    @FXML
    static StackPane sellParentStackPane;
    static JFXProgressBar progressBar;
    static HBox currentButton;

    //the following methods are created to listen the change of menu
    public void sellMenu() throws IOException {
        homeParent = FXMLLoader.load(getClass().getResource("UI/SellUI.fxml"));
        sellScene = new Scene(homeParent);
        Stage stage = Main.stage;
        stage.setScene(sellScene);
        stage.show();
        verifyCustomerPane = (Pane) homeParent.lookup("#verifyBuyerPaneSellMenu");
        specifyAmountPane = (Pane) homeParent.lookup("#verifyBuyerPaneSellMenu");
        progressBar = (JFXProgressBar) homeParent.lookup("#progressBar");
        sellParentStackPane = (StackPane) homeParent.lookup("#sellParentStackPane");
        currentButton = (HBox) homeParent.lookup("#sellMenu");
        currentButton.setStyle("-fx-background-color :  #202332;");
        setSceneItems(sellScene);
    }


    public void homeMenu() throws IOException {
        Main.fromMain = false;
        homeParent = FXMLLoader.load(getClass().getResource("UI/HomeUI.fxml"));
        homeScene = new Scene(homeParent);

        stage = Main.stage;
        stage.setScene(homeScene);
        stage.show();

        dropdownButton = (JFXComboBox) homeScene.lookup("#combo_box");
        currentButton = (HBox) homeParent.lookup("#homeMenu");
        currentButton.setStyle("-fx-background-color :  #202332;");
        dropdownButton.getItems().addAll("English", "Amharic");
        setSceneItems(homeScene);
    }


    public void customersMenu() throws IOException {
        homeParent = FXMLLoader.load(getClass().getResource("UI/CustomersUI.fxml"));
        customersScene = new Scene(homeParent);

        currentButton = (HBox) homeParent.lookup("#customersMenu");
        currentButton.setStyle("-fx-background-color :  #202332;");

        initialize_table();
        stage = Main.stage;
        stage.setScene(customersScene);
        stage.show();


        searchInput = (JFXTextField) homeParent.lookup("#searchField");
        searchInput.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                treeView.setPredicate(new Predicate<TreeItem<User>>() {
                    @Override
                    public boolean test(TreeItem<User> userTreeItem) {
                        boolean flag = userTreeItem.getValue().department.getValue().toLowerCase().contains(newValue.toLowerCase()) || userTreeItem.getValue().department.getValue().toLowerCase().contains(newValue.toLowerCase());
                        return flag;
                    }
                });
            }
        });

        setSceneItems(customersScene);
    }

    public void helpMenu() throws IOException {
        homeParent = FXMLLoader.load(getClass().getResource("UI/HelpUI.fxml"));
        helpScene = new Scene(homeParent);

        currentButton = (HBox) homeParent.lookup("#helpMenu");
        currentButton.setStyle("-fx-background-color :  #202332;");

        stage = Main.stage;
        stage.setScene(helpScene);
        stage.show();
    }

    public void itemsMenu() throws IOException {
        homeParent = FXMLLoader.load(getClass().getResource("UI/ItemsUI.fxml"));
        itemsScene = new Scene(homeParent);

        currentButton = (HBox) homeParent.lookup("#itemsMenu");
        currentButton.setStyle("-fx-background-color :  #202332;");

        initialize_table();
        stage = Main.stage;
        stage.setScene(itemsScene);
        stage.show();

        searchInput = (JFXTextField) homeParent.lookup("#searchField");
        searchInput.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                treeView.setPredicate(new Predicate<TreeItem<User>>() {
                    @Override
                    public boolean test(TreeItem<User> userTreeItem) {
                        boolean flag = userTreeItem.getValue().department.getValue().toLowerCase().contains(newValue.toLowerCase()) || userTreeItem.getValue().department.getValue().toLowerCase().contains(newValue.toLowerCase());
                        return flag;
                    }
                });
            }
        });

        setSceneItems(itemsScene);
    }

    public void reportMenu() throws IOException {
        homeParent = FXMLLoader.load(getClass().getResource("UI/ReportUI.fxml"));
        reportScene = new Scene(homeParent);

        currentButton = (HBox) homeParent.lookup("#reportMenu");
        currentButton.setStyle("-fx-background-color :  #202332;");

        stage = Main.stage;
        stage.setScene(reportScene);
        stage.show();

        initialize_table();
        setSceneItems(reportScene);
    }

    public void addCustomer() throws IOException {
        homeParent = FXMLLoader.load(getClass().getResource("UI/AddCustomerUI.fxml"));
        addCustomerScene = new Scene(homeParent);

        stage = Main.stage;
        stage.setScene(addCustomerScene);
        stage.show();

        setSceneItems(addCustomerScene);
    }

    public void addItems() throws IOException {
        homeParent = FXMLLoader.load(getClass().getResource("UI/AddItemUI.fxml"));
        addItemsScene = new Scene(homeParent);

        stage = Main.stage;
        stage.setScene(addItemsScene);
        stage.show();

        setSceneItems(addItemsScene);
    }

    public void loginButton() throws IOException {
        homeMenu();
    }

    static int current_node = 0;

    public void nextButtonSellMenu() {
        ObservableList<Node> children = sellParentStackPane.getChildren();

        if (children.size() > 1) {
            //
            Node topNode = children.get(children.size() - 1);
            topNode.toBack();
            progressBar.setProgress((double) current_node++ / children.size());
            if (current_node > 3) {
                current_node = 1;
            }
        }
    }


    public void setSceneItems(Scene scene) throws IOException {
        // -- main menus -- //
        home = (Label) scene.lookup("#home_menu");
        sell = (Label) scene.lookup("#sell_menu");
        items = (Label) scene.lookup("#items_menu");
        customers = (Label) scene.lookup("#customers_menu");
        report = (Label) scene.lookup("#report_menu");
        help = (Label) scene.lookup("#help_menu");
        logo = (Label) scene.lookup("#logo_label");
        changeLanguage();
    }


    public static void fromMain() {
        home = Main.home;
        sell = Main.sell;
        items = Main.items;
        customers = Main.customers;
        report = Main.report;
        help = Main.help;
    }


    //handle language preference

    public void setLanguage() throws Exception {
        if (Main.fromMain) {
            CURRENT_LANG = Main.dropdownButton.getValue().toString();
            fromMain();
            writeLanguage();
            changeLanguage();
        } else {
            CURRENT_LANG = dropdownButton.getValue().toString();
            writeLanguage();
            setSceneItems(homeScene);
        }
    }

    public static void changeLanguage() throws IOException {
        locale = new Locale(readLanguage());
        bundle = ResourceBundle.getBundle("triplethreads.shemachoch.Pages.lang", locale);

        home.setText(readUTF(bundle.getString("home_menu")));
        sell.setText(readUTF(bundle.getString("sell_menu")));
        items.setText(readUTF(bundle.getString("items_menu")));
        customers.setText(readUTF(bundle.getString("customers_menu")));
        report.setText(readUTF(bundle.getString("report_menu")));
        help.setText(readUTF(bundle.getString("help_menu")));
        logo.setText(readUTF(bundle.getString("logo")));


        if (!Main.fromMain) {
            if (readLanguage().equals("en")) {
                dropdownButton.setValue("English");
            } else if (readLanguage().equals("amh")) {
                dropdownButton.setValue("Amharic");
            }
        }
    }
    public static String readUTF(String key) throws UnsupportedEncodingException {
        return new String (key.getBytes("ISO-8859-1"),"UTF-8");


    }

    public static String readLanguage() throws IOException {
        Properties prop = new Properties();
        String propFileName = "config.properties";
        InputStream inputStream = new FileInputStream(propFileName);
        prop.load(inputStream);
        return prop.getProperty("language");
    }

    public void writeLanguage() {
        String locale_string = "en";
        if (CURRENT_LANG.equals("Amharic")) {
            locale_string = "amh";
        } else if (CURRENT_LANG.equals("English")) {
            locale_string = "en";
        }
        Properties properties = new Properties();
        try {
            properties.setProperty("language", locale_string);
            properties.store(new FileOutputStream("config.properties"), null);
        } catch (IOException e) {
            System.out.println("Error while creating config");
        }
    }

    //handle report menus
    public void generateReportByMonth() {
        JFXComboBox comboBox = (JFXComboBox) homeParent.lookup("#monthPicker");
        System.out.println(comboBox.getValue());
    }

    public void generateReportByDate() {
        JFXDatePicker datePicker = (JFXDatePicker) homeParent.lookup("#datePicker");
        System.out.println(datePicker.getValue());
    }

    public void exportToPDF() {


    }


    //table view
    @FXML
    private JFXTreeTableView<User> treeView;


    public void initialize_table() {
        try {
            treeView = (JFXTreeTableView<User>) homeParent.lookup("#treeView");
        } catch (NullPointerException e) {
            treeView = (JFXTreeTableView<User>) homeParent.lookup("#treeView");
        }
        JFXTreeTableColumn<User, String> item = new JFXTreeTableColumn<>("Item");
        item.setPrefWidth(160);
        item.setCellValueFactory(param -> param.getValue().getValue().department);

        JFXTreeTableColumn<User, String> amount = new JFXTreeTableColumn<>("Amount");
        amount.setPrefWidth(160);
        amount.setCellValueFactory(param -> param.getValue().getValue().age);

        JFXTreeTableColumn<User, String> price_per_unit = new JFXTreeTableColumn<>("Price per unit");
        price_per_unit.setPrefWidth(160);
        price_per_unit.setCellValueFactory(param -> param.getValue().getValue().userName);

        JFXTreeTableColumn<User, String> expire_date = new JFXTreeTableColumn<>("Expire Date");
        expire_date.setPrefWidth(180);
        expire_date.setCellValueFactory(param -> param.getValue().getValue().userName);

        ObservableList<User> users = FXCollections.observableArrayList();
        users.add(new User("Sugar", "23", "12"));
        users.add(new User("Popular Soap", "22", "Employee 1"));
        users.add(new User("Hayat Oil", "22", "Employee 2"));
        users.add(new User("Sales Department", "25", "Employee 4"));
        users.add(new User("Sales Department", "25", "Employee 5"));
        users.add(new User("IT Department", "42", "ID 2"));
        users.add(new User("HR Department", "22", "HR 1"));
        users.add(new User("HR Department", "22", "HR 2"));

        final TreeItem<User> root = new RecursiveTreeItem<>(users, RecursiveTreeObject::getChildren);
        treeView.getColumns().setAll(item, amount, price_per_unit, expire_date);
        treeView.setRoot(root);
        treeView.setRoot(root);
        treeView.setShowRoot(false);

    }


    class User extends RecursiveTreeObject<User> {

        StringProperty userName;
        StringProperty age;
        StringProperty department;

        public User(String department, String age, String userName) {
            this.department = new SimpleStringProperty(department);
            this.userName = new SimpleStringProperty(userName);
            this.age = new SimpleStringProperty(age);
        }

    }

public void validateCustumer(TextField Name , TextField lastName , ComboBox FamilyNO , TextField laocation) {

    if (String.valueOf(Name).equals(" ") || String.valueOf(lastName).equals(" ")
            || String.valueOf(FamilyNO).equals("") || String.valueOf(laocation).equals("")) {
        System.out.println("please fill the space");

    } else if (String.valueOf(Name).matches("\\d")) {

        System.out.println("The value of name should contian only letters");
    } else if (String.valueOf(lastName).matches("\\d")) {

        System.out.println("The value of last name should only contian letters");
    } else if (String.valueOf(FamilyNO).contains("0-9")) {
        System.out.println("The value of FamilyNo should only contian numbers");


    } else {

        Seller customer = new Seller();
        String credential[] = {String.valueOf(Name), String.valueOf(lastName), String.valueOf(FamilyNO), String.valueOf(laocation)};
        customer.AddCustomer(credential);
    }


}}

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
import triplethreads.shemachoch.EntityClasses.CreateDatabaseTables;
import triplethreads.shemachoch.EntityClasses.Items;
import triplethreads.shemachoch.EntityClasses.Register;
import triplethreads.shemachoch.EntityClasses.Seller;


import java.io.*;
import java.util.*;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author segni
 * the UI is built on javafx. one Stage is used for all scenes
 * but each menu has its own scene i.e FXML file. This is pretty similar to tiater where scene changes but the stage
 * will remain the same.
 * <p>
 * homeMenu method listens for home button is clicked from main menu.
 * sellMenu method listens for sell button is clicked from main menu.
 * itemsMenu method listens for items button is clicked from main menu.
 * customersMenu method listens for customer button is clicked from main menu.
 * reportMenu method listens for report button is clicked from main menu.
 * helpMenu method listens for help button is clicked from main menu.
 * <p>
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

    //the following methods are created to listen the change of menu * * * * * * * *
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
                treeView.setPredicate(new Predicate<TreeItem<ItemAdapter>>() {
                    @Override
                    public boolean test(TreeItem<ItemAdapter> userTreeItem) {
                        boolean flag = userTreeItem.getValue().itemName.getValue().toLowerCase().contains(newValue.toLowerCase()) || userTreeItem.getValue().itemName.getValue().toLowerCase().contains(newValue.toLowerCase());
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
                treeView.setPredicate(userTreeItem -> {
                    boolean flag = userTreeItem.getValue().itemName.getValue().toLowerCase().contains(newValue.toLowerCase()) || userTreeItem.getValue().itemName.getValue().toLowerCase().contains(newValue.toLowerCase());
                    return flag;
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

    //* * * * * * * * *  *

    public void loginButton() throws IOException {
        homeMenu();
    }

    public void addItemButton() {
        TextField name = (TextField) homeParent.lookup("#item_name_textField");
        TextField brand = (TextField) homeParent.lookup("#brand_textField");
        TextField quantity = (TextField) homeParent.lookup("#quantity_textField");
        TextField price = (TextField) homeParent.lookup("#price_textField");
        validateItems(name, brand, quantity, price);
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


    // handle language preference * * * * * * *

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
        return new String(key.getBytes("ISO-8859-1"), "UTF-8");
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
    // * * * * * * * * * * end of setting language

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
    private JFXTreeTableView<ItemAdapter> treeView;


    public void initialize_table() {

        treeView = (JFXTreeTableView<ItemAdapter>) homeParent.lookup("#treeView");
        JFXTreeTableColumn<ItemAdapter, String> item_id = new JFXTreeTableColumn<>("Item ID");
        item_id.setPrefWidth(160);
        item_id.setCellValueFactory(param -> param.getValue().getValue().itemId);

        JFXTreeTableColumn<ItemAdapter, String> item_name = new JFXTreeTableColumn<>("Name");
        item_name.setPrefWidth(160);
        item_name.setCellValueFactory(param -> param.getValue().getValue().itemName);

        JFXTreeTableColumn<ItemAdapter, String> brand = new JFXTreeTableColumn<>("Brand");
        brand.setPrefWidth(160);
        brand.setCellValueFactory(param -> param.getValue().getValue().itemBrand);

        JFXTreeTableColumn<ItemAdapter, String> item_price = new JFXTreeTableColumn<>("Item Price");
        item_price.setPrefWidth(180);
        item_price.setCellValueFactory(param -> param.getValue().getValue().itemPrice);

        JFXTreeTableColumn<ItemAdapter, String> item_amount = new JFXTreeTableColumn<>("Item amount");
        item_amount.setPrefWidth(180);
        item_amount.setCellValueFactory(param -> param.getValue().getValue().itemPrice);

        ObservableList<ItemAdapter> items_list = FXCollections.observableArrayList();

        List<Items> itemsList = new Items().getListOfAllItems();
        for (int i = 0; i < itemsList.size(); i++){
            items_list.add(new ItemAdapter(itemsList.get(i)));
        }

        final TreeItem<ItemAdapter> root = new RecursiveTreeItem<>(items_list, RecursiveTreeObject::getChildren);
        treeView.getColumns().setAll(item_id, item_name, brand, item_price, item_amount);
        treeView.setRoot(root);
        treeView.setRoot(root);
        treeView.setShowRoot(false);

    }

    class ItemAdapter extends RecursiveTreeObject<ItemAdapter> {
        StringProperty itemId;
        StringProperty itemName;
        StringProperty itemPrice;
        StringProperty itemAmount;
        StringProperty itemBrand;
        public ItemAdapter(Items items) {
            this.itemAmount = new SimpleStringProperty(items.getAmount()+"");
            this.itemName = new SimpleStringProperty(items.getName());
            this.itemBrand = new SimpleStringProperty(items.getBrand());
            this.itemId = new SimpleStringProperty(items.getItemId());
            itemPrice = new SimpleStringProperty(items.getPrice()+"");
        }
    }


    //validation of fields * * * *

    public void validateCustumer(TextField Name, TextField lastName, ComboBox FamilyNO, TextField laocation) {

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


    }


    public boolean validateItems(TextField name, TextField brand, TextField amount, TextField price) {

        if (name.getText().equals("") || brand.getText().equals("") || amount.getText().equals("") || price.getText().equals("")) {
            return false;
        } else if (amount.getText().contains("[a-zA-Z]+")) {
            return false;
        } else if (price.getText().contains("[a-zA-Z]+")) {
            return false;
        } else if (name.getText().matches(".*\\d.*")) {
            return false;
        } else {
            String arr[] = {name.getText(), brand.getText(), amount.getText(), price.getText()};
            new CreateDatabaseTables();
            Items items = new Items();
            items.addItems(arr);
            return true;
        }
    }

    public boolean validateRegistration(TextField Fname, TextField Lname, TextField Password, TextField location, TextField id) {
        if (String.valueOf(Fname).equals(null) || String.valueOf(Lname).equals(null) || String.valueOf(Password).equals(null) || String.valueOf(location).equals(null) || String.valueOf(id).equals(null)) {
            return false;
        } else if (String.valueOf(Fname).matches(".*\\d*.")) {
            return false;
        } else if (String.valueOf(Lname).matches(".*\\d*.")) {
            return false;
        } else if (String.valueOf(Password).length() < 6) {

        } else {
            Pattern letter = Pattern.compile("[a-zA-z]]");
            Pattern digit = Pattern.compile("[0-9]");
            Pattern specialChars = Pattern.compile("[!@#$%^&*()_+=|<>?{}\\[\\]~-]");

            Matcher hasLetter = letter.matcher(String.valueOf(Password));
            Matcher hasDigit = digit.matcher(String.valueOf(Password));
            Matcher hasSpecialChar = specialChars.matcher(String.valueOf(Password));
            if (hasLetter.find() == false && hasDigit.find() == false || hasSpecialChar.find() == false) {
                return false;
            } else {
                String s[] = {String.valueOf(Fname) + " " + String.valueOf(Lname), String.valueOf(Password), String.valueOf(location)};
                new Register().Register(s);
                return true;
            }
        }
        return false;
    }

    ///* * * * * end validation
    public static void writeLog(String log) {
        Date d = new Date();
        @SuppressWarnings("deprecation")
        String currentDate = d.getDate() + "/" + d.getMonth() + "/" + d.getYear();
        try (FileWriter fw = new FileWriter("Crash Report.txt", true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.println("Log: " + currentDate + "  " + log);
        } catch (IOException e) {
            writeLog(e.getMessage());
        }
    }


    public static String createTempFile(String temp_file_name, String content) {
        String abs_path = null;
        try {
            FileWriter fileWriter = new FileWriter("login.tmp");
            //create a temp file
            fileWriter.write(content);
            fileWriter.close();
        } catch (IOException e) {

            e.printStackTrace();

        }
        return abs_path;
    }

    public static String readTempFiles(String temp) {
        String string = "mm";
        try {
            BufferedReader br = new BufferedReader(new FileReader(temp));
            string = br.readLine();
            br.close();
        } catch (IOException e) {
            writeLog(e.getLocalizedMessage());
        }
        return string;
    }

}

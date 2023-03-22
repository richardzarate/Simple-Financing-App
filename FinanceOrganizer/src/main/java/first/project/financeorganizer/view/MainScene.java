package first.project.financeorganizer.view;

import first.project.financeorganizer.controller.Controller;
import first.project.financeorganizer.model.Account;
import first.project.financeorganizer.model.MainAccount;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.text.NumberFormat;

public class MainScene extends Scene {
    public static final int WIDTH = 1000;
    public static final int HEIGHT = 800;

    private ImageView financeIV = new ImageView();

    private ComboBox<String> lettersCB;

    private Slider minAccountValueSlider = new Slider(0, 1000000, 0);

    private ListView<MainAccount> mainAccountLV = new ListView<>();

    private Button addButton = new Button("Add Account");
    private Button removeButton = new Button("Remove Account");
    private Button viewAccountButton = new Button("View Account");

    private Controller controller = Controller.getInstance();

    private ObservableList<MainAccount> AccountsList;

    private MainAccount selectedAccount;

    private static NumberFormat currency = NumberFormat.getCurrencyInstance();

    private Label minAccountValueLabel = new Label(currency.format(0));

    public MainScene(){
        super(new GridPane(), WIDTH, HEIGHT);
        financeIV.setImage(new Image("Finance.png"));
        financeIV.setFitWidth(WIDTH-20);


        AccountsList = controller.getAllMainAccounts();
        mainAccountLV.setItems(AccountsList);
        mainAccountLV.setPrefWidth(WIDTH);
        // DONE: Add Listener to AccountsList
        mainAccountLV.getSelectionModel().selectedItemProperty().addListener((obsValue, oldVal, newVal) -> selectAccount(newVal));

        lettersCB = new ComboBox<>(controller.getAlphabetList());
        lettersCB.getSelectionModel().select(0);
        lettersCB.setOnAction(e -> filter());

        minAccountValueSlider.setShowTickMarks(true);
        minAccountValueSlider.setShowTickLabels(true);
        minAccountValueSlider.setSnapToTicks(true);
        minAccountValueSlider.setMajorTickUnit(100000.0f);
        minAccountValueSlider.setMinorTickCount(10);
        minAccountValueSlider.setOnMouseDragged(e -> filter());

        GridPane pane = new GridPane();
        pane.setHgap(10.0);
        pane.setVgap(5);
        pane.setPadding(new Insets(10));
        // add(element, columnIndex, rowIndex, columnSpan, rowSpan)
        pane.add(financeIV, 0 , 0, 2, 1);

        pane.add(new Label("Welcome to Finance Organizer!"), 0, 1);
        pane.add(new Label("Filter by:"), 0, 2);
        pane.add(new Label("Initial Letter of Name:"), 0, 3);
        pane.add(lettersCB, 1, 3);

        pane.add(new Label("Minimum Account Value:"), 0, 4);
        pane.add(minAccountValueLabel, 1, 4);
        pane.add(minAccountValueSlider, 0, 5, 2, 1);
        minAccountValueSlider.valueProperty().addListener((obsVal, oldVal, newVal) -> updateMinAccountValue(newVal));

        //pane.add(addButton, 0, 6)
        HBox addRemoveButtons = new HBox();
        addRemoveButtons.setSpacing(10);
        addRemoveButtons.setAlignment(Pos.CENTER_LEFT);
        addRemoveButtons.getChildren().add(addButton);
        addRemoveButtons.getChildren().add(removeButton);
        pane.add(addRemoveButtons, 0, 6);
        addButton.setOnAction(e -> addAccount());
        removeButton.setDisable(true);
        removeButton.setOnAction(e -> removeAccount());



        HBox viewAccountBox = new HBox();
        viewAccountBox.setAlignment(Pos.CENTER_RIGHT);
        viewAccountBox.getChildren().add(viewAccountButton);
        pane.add(viewAccountBox, 1, 6);
        viewAccountButton.setOnAction(e -> viewAccount());

        pane.add(mainAccountLV, 0, 7, 2, 2);

        this.setRoot(pane);

    }

    private void selectAccount(MainAccount newVal) {
        selectedAccount = newVal;
        removeButton.setDisable(selectedAccount == null);
    }

    private void updateMinAccountValue(Number newVal) {
        minAccountValueLabel.setText(currency.format(newVal.doubleValue()));
        filter();
    }

    private void filter(){
        String letter = lettersCB.getSelectionModel().getSelectedItem();
        double minAccountValue = minAccountValueSlider.getValue();
        AccountsList = controller.filter(letter, minAccountValue);
        mainAccountLV.setItems(AccountsList);
    }


    private void addAccount(){
        ViewNavigator.loadScene("Add Account", new AddScene(this));
    }

    private void removeAccount(){
        if(selectedAccount == null){
            return;
        }

        AccountsList.remove(selectedAccount);
        controller.removeMainAccount(selectedAccount);
        mainAccountLV.getSelectionModel().select(-1);
    }

    private void viewAccount(){
        ViewNavigator.loadScene("View Account", new ViewAccountScene(this, selectedAccount));
    }


}

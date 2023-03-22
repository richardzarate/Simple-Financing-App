package first.project.financeorganizer.view;

import first.project.financeorganizer.controller.Controller;
import first.project.financeorganizer.model.Account;
import first.project.financeorganizer.model.Expenses;
import first.project.financeorganizer.model.MainAccount;
import first.project.financeorganizer.model.Savings;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.text.NumberFormat;
import java.time.Month;

public class ViewAccountScene extends Scene {
    private Scene previousScene;

    public static final int WIDTH = 1000;
    public static final int HEIGHT = 800;

    private ComboBox<String> accountTypeCB;
    private static ComboBox<String> YearCB = new ComboBox<>();
    private static ComboBox<String> MonthCB = new ComboBox<>();


    private Label filterByLabel = new Label("Filter by:");
    private Label yearLabel = new Label("Year:");
    private Label monthLabel = new Label("Month:");
    private Label RecordLabel = new Label();

    private Button addButton = new Button();
    private Button removeButton = new Button();
    private Label minMonthlyLabel = new Label();
    private Slider minMonthlySlider = new Slider(0, 1000000, 0);

    private ObservableList<Account> AccountsList;
    private ListView<Account> AccountsLV = new ListView<>();

    private HBox addRemoveButtons;

    private static NumberFormat currency = NumberFormat.getCurrencyInstance();

    private Label minMonthlySliderLabel = new Label(currency.format(0));

    //private Double[] accountData;
    private static Label accountBalance = new Label();
    private static Label accountSavings = new Label();
    private static Label accountExpenses = new Label();
    private Label accountBalanceLabel = new Label("Account Balance:");
    private Label accountSavingsLabel = new Label("Account Total Savings:");
    private Label accountExpensesLabel = new Label("Account Total Expenses:");


    private Account selectedExpSav;
    private Button goBackButton = new Button("Go Back");
    private ObservableList<Account> savingsList;
    private ObservableList<Account> expensesList;

    private ObservableList<Account> mFilteredList;
    private String currentSelection = "";
    private static Controller controller = Controller.getInstance();


    public ViewAccountScene(Scene previousScene, MainAccount selectedAccount){
        super(new GridPane(), WIDTH, HEIGHT);
        this.previousScene = previousScene;
        controller.updateAccountData(selectedAccount);
        //accountData = getAccountData(selectedAccount);

        GridPane pane = new GridPane();
        pane.setHgap(10.0);
        pane.setVgap(5);
        pane.setPadding(new Insets(5));

        pane.add(new Label("Welcome " + selectedAccount.getAccountName() + "!"), 0, 0);
        pane.add(new Label("View:"), 0, 1);

        accountTypeCB = new ComboBox<>(controller.getAccountType());
        pane.add(accountTypeCB, 1, 1);

        AccountsList = controller.getExpensesRecord(selectedAccount);
        AccountsLV.setItems(AccountsList);
        AccountsLV.setPrefWidth(WIDTH);

        accountBalance.setText(currency.format(controller.getBalance()));
        accountSavings.setText(currency.format(controller.getSavings()));
        accountExpenses.setText(currency.format(controller.getExpenses()));

        pane.add(goBackButton, 3, 0);
        goBackButton.setOnAction(e -> goBackToPrevScene());

        pane.add(accountBalanceLabel, 2, 1);
        pane.add(accountBalance, 3, 1);

        pane.add(accountSavingsLabel, 2, 2);
        pane.add(accountSavings, 3, 2);

        pane.add(accountExpensesLabel, 2, 3);
        pane.add(accountExpenses, 3, 3);


        pane.add(filterByLabel, 0, 2);
        filterByLabel.setVisible(false);

        //YearCB = new ComboBox<>();

        //MonthCB = new ComboBox<>();

        pane.add(YearCB, 1, 3);
        pane.add(yearLabel, 0, 3);
        YearCB.setVisible(false);
        yearLabel.setVisible(false);

        pane.add(MonthCB, 1, 4);
        pane.add(monthLabel, 0, 4);
        MonthCB.setVisible(false);
        monthLabel.setVisible(false);

        pane.add(minMonthlyLabel, 0, 5);
        pane.add(minMonthlySliderLabel, 1, 5);
        minMonthlyLabel.setVisible(false);
        minMonthlySliderLabel.setVisible(false);

        /*pane.add(minMonthlyLabel, 0, 5);
        minMonthlyLabel.setVisible(false);*/

        pane.add(minMonthlySlider, 0, 6, 4, 1);
        minMonthlySlider.setShowTickMarks(true);
        minMonthlySlider.setShowTickLabels(true);
        minMonthlySlider.setSnapToTicks(true);
        minMonthlySlider.setMajorTickUnit(100000.0f);
        minMonthlySlider.setMinorTickCount(10);
        minMonthlySlider.valueProperty().addListener((obsVal, oldVal, newVal) -> updateMinMonthlyLabel(newVal));
        minMonthlySlider.setVisible(false);

        YearCB.setOnAction(e -> filter());
        MonthCB.setOnAction(e -> filter());
        minMonthlySlider.setOnMouseDragged(e -> filter());



        pane.add(RecordLabel, 0, 7);
        addRemoveButtons = new HBox();
        addRemoveButtons.setSpacing(10);
        addRemoveButtons.setAlignment(Pos.CENTER_LEFT);
        addRemoveButtons.getChildren().add(addButton);
        addRemoveButtons.getChildren().add(removeButton);
        pane.add(addRemoveButtons, 1, 7);
        addRemoveButtons.setVisible(false);

        pane.add(AccountsLV, 0, 8, 4, 1);
        AccountsLV.setVisible(false);


        // DONE: Change setOnAction to Listener
        accountTypeCB.getSelectionModel().selectedItemProperty().addListener((obsVal, oldVal, newVal) -> selectedAccountType(newVal, pane, selectedAccount));










        this.setRoot(pane);
    }

    private void goBackToPrevScene() {
        ViewNavigator.loadScene("Finance Organizer", previousScene);
    }

    private void updateMinMonthlyLabel(Number newVal) {
        minMonthlySliderLabel.setText(currency.format(newVal.doubleValue()));

    }

    private void selectedAccountType(String newVal, GridPane pane, MainAccount selectedAccount) {

        currentSelection = newVal;
        //System.out.println(currentSelection);
        //savingsList = controller.getSavingsRecord(selectedAccount);


        if(newVal.equals("Expenses")) {
            expensesList = controller.getAllExpensesList(selectedAccount);
            filterByLabel.setVisible(true);
            yearLabel.setVisible(true);
            monthLabel.setVisible(true);
            minMonthlySlider.setVisible(true);
            minMonthlySliderLabel.setVisible(true);



            minMonthlyLabel.setText("Minimum Monthly Expenses:");
            minMonthlyLabel.setVisible(true);

            YearCB.setItems(controller.getExpensesYear(selectedAccount));
            YearCB.setVisible(true);


            MonthCB.setItems(controller.getExpensesMonth(selectedAccount));
            MonthCB.setVisible(true);


            addButton.setText("Add Monthly Expenses");
            removeButton.setText("Remove Monthly Expenses");
            addRemoveButtons.setVisible(true);
            removeButton.setDisable(true);
            addButton.setOnAction(e -> addExpense(selectedAccount));
            removeButton.setOnAction(e -> removeExpense(selectedAccount));

            RecordLabel.setText("Expenses Record:");
            RecordLabel.setVisible(true);

            AccountsLV.setItems(expensesList);
            AccountsLV.setVisible(true);
            AccountsLV.getSelectionModel().selectedItemProperty().addListener((obsVal, oldVal, newValue) -> selectAccount(newValue));
        }

        else if(newVal.equals("Savings")){
            savingsList = controller.getAllSavingsList(selectedAccount);
            filterByLabel.setVisible(true);
            yearLabel.setVisible(true);
            monthLabel.setVisible(true);
            minMonthlySlider.setVisible(true);
            minMonthlySliderLabel.setVisible(true);

            minMonthlyLabel.setText("Minimum Monthly Savings:");
            minMonthlyLabel.setVisible(true);


            YearCB.setItems(controller.getSavingsYear(selectedAccount));
            YearCB.setVisible(true);


            MonthCB.setItems(controller.getSavingsMonth(selectedAccount));
            MonthCB.setVisible(true);


            addButton.setText("Add Monthly Savings");
            removeButton.setText("Remove Monthly Savings");
            addRemoveButtons.setVisible(true);
            removeButton.setDisable(true);
            addButton.setOnAction(e -> addSavings(selectedAccount));
            removeButton.setOnAction(e -> removeSavings(selectedAccount));
            RecordLabel.setText("Savings Record:");
            RecordLabel.setVisible(true);

            AccountsLV.setItems(savingsList);
            AccountsLV.setVisible(true);
            AccountsLV.getSelectionModel().selectedItemProperty().addListener((obsVal, oldVal, newValue) -> selectAccount(newValue));

        }
        else{
            filterByLabel.setVisible(false);
            yearLabel.setVisible(false);
            monthLabel.setVisible(false);
            minMonthlySliderLabel.setVisible(false);
            minMonthlySlider.setVisible(false);

            minMonthlyLabel.setVisible(false);


            YearCB.setVisible(false);
            MonthCB.setVisible(false);

            addRemoveButtons.setVisible(false);

            RecordLabel.setVisible(false);
            AccountsLV.setVisible(false);


            //expensesRecordLabel.setVisible(false);


        }
    }

    private void removeSavings(MainAccount selectedAccount) {
        if(selectedAccount == null){
            return;
        }
        controller.removeMonthlySaving(selectedAccount, (Savings) selectedExpSav);
        setAccountBalance(controller.getBalance());
        setAccountSavings(controller.getSavings());
        setAccountExpenses(controller.getExpenses());
    }

    private void addSavings(MainAccount selectedAccount) {
        ViewNavigator.loadScene("Add Savings", new AddSavingsScene(this, selectedAccount));
    }

    private void selectAccount(Account newValue) {
        selectedExpSav = newValue;
        removeButton.setDisable(selectedExpSav == null);

    }

    private void removeExpense(MainAccount selectedAccount) {
        if(selectedExpSav == null){
            return;
        }
        controller.removeMonthlyExpense(selectedAccount, (Expenses) selectedExpSav);
        setAccountBalance(controller.getBalance());
        setAccountExpenses(controller.getExpenses());
        setAccountSavings(controller.getSavings());
    }

    private void addExpense(MainAccount selectedAccount) {
        ViewNavigator.loadScene("Add Expense", new AddExpenseScene(this, selectedAccount));




    }

    public static void setAccountBalance(Double element){
        accountBalance.setText(currency.format(element));
    }

    public static void setAccountExpenses(Double element){
        accountExpenses.setText(currency.format(element));
    }

    public static void setAccountSavings(Double element){
        accountSavings.setText(currency.format(element));
    }

    public static void setExpensesYearCB(MainAccount selectedAccount){
        YearCB.setItems(controller.getExpensesYear(selectedAccount));
    }
    public static void setExpensesMonthCB(MainAccount selectedAccount){
        MonthCB.setItems(controller.getExpensesMonth(selectedAccount));
    }

    public void setSavingsYearCB(MainAccount selectedAccount){
        YearCB.setItems(controller.getSavingsYear(selectedAccount));
    }

    public void setSavingsMonthCB(MainAccount selectedAccount){
        MonthCB.setItems(controller.getSavingsMonth(selectedAccount));
    }

    public Double[] getAccountData(MainAccount selectedAccount){
        updateAccountData(selectedAccount);
        return controller.getAccountData(selectedAccount);
    }

    public void updateAccountData(MainAccount selectedAccount){
        controller.updateAccountData(selectedAccount);
    }

    public void filter(){
        System.out.println("filtering");
        String year = YearCB.getSelectionModel().getSelectedItem();
        String month = MonthCB.getSelectionModel().getSelectedItem();
        double minTotal = minMonthlySlider.getValue();
        if(currentSelection.equals("Savings")) {
            mFilteredList = controller.filterAccountsList(savingsList, year, month, minTotal);
        }
        else if(currentSelection.equals("Expenses")){
            mFilteredList = controller.filterAccountsList(expensesList, year, month, minTotal);
        }
        AccountsLV.setItems(mFilteredList);

    }
}

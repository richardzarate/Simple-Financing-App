package first.project.financeorganizer.view;

import first.project.financeorganizer.controller.Controller;
import first.project.financeorganizer.model.Expenses;
import first.project.financeorganizer.model.MainAccount;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

public class AddExpenseScene extends Scene {
    private Scene previousScene;

    public static final int WIDTH = 600;
    public static final int HEIGHT = 600;

    private Controller controller = Controller.getInstance();

    private TextField yearTF = new TextField();
    private TextField monthTF = new TextField();
    private TextField utilitiesTF = new TextField();
    private TextField housingTF = new TextField();
    private TextField groceryTF = new TextField();
    private TextField gasTF = new TextField();
    private TextField otherTF = new TextField();

    private Label instructionsLabel = new Label("Please enter:");
    private Label yearLabel = new Label("Year(YYYY):");
    private Label monthLabel= new Label("Month(MM):");
    private Label utilitiesLabel= new Label("Utilities: $");
    private Label housingLabel= new Label("Housing: $");
    private Label groceryLabel= new Label("Grocery: $");
    private Label gasLabel= new Label("Gas: $");
    private Label otherLabel= new Label("Other: $");

    private Label yearErrorLabel = new Label("Year required. (YYYY)");
    private Label monthErrorLabel = new Label("Month required. (MM)");
    private Label utilitiesErrorLabel = new Label("Utilities required.");
    private Label housingErrorLabel = new Label("Housing required.");
    private Label groceryErrorLabel = new Label("Grocery required.");
    private Label gasErrorLabel = new Label("Gas required.");
    private Label otherErrorLabel = new Label("Other");

    private Button saveButton = new Button("Save");
    private Button cancelButton = new Button("Cancel");
    //private Button addButton = new Button("Add Expense");

    public AddExpenseScene(Scene previousScene, MainAccount selectedAccount){
        super(new GridPane(), WIDTH, HEIGHT);
        this.previousScene = previousScene;

        GridPane pane = new GridPane();
        pane.setHgap(10.0);
        pane.setVgap(5);
        pane.setPadding(new Insets(5));

        pane.add(instructionsLabel, 0, 0);
        pane.add(yearLabel, 0, 1);
        pane.add(yearTF, 1, 1);
        pane.add(yearErrorLabel, 2, 1);
        yearErrorLabel.setTextFill(Color.RED);
        yearErrorLabel.setVisible(false);

        pane.add(monthLabel, 0, 2);
        pane.add(monthTF, 1, 2);
        pane.add(monthErrorLabel, 2, 2);
        monthErrorLabel.setTextFill(Color.RED);
        monthErrorLabel.setVisible(false);

        pane.add(utilitiesLabel, 0, 3);
        pane.add(utilitiesTF, 1, 3);
        pane.add(utilitiesErrorLabel, 2, 3);
        utilitiesErrorLabel.setTextFill(Color.RED);
        utilitiesErrorLabel.setVisible(false);


        pane.add(housingLabel, 0, 4);
        pane.add(housingTF, 1, 4);
        pane.add(housingErrorLabel, 2, 4);
        housingErrorLabel.setTextFill(Color.RED);
        housingErrorLabel.setVisible(false);


        pane.add(groceryLabel, 0, 5);
        pane.add(groceryTF, 1, 5);
        pane.add(groceryErrorLabel, 2, 5);
        groceryErrorLabel.setTextFill(Color.RED);
        groceryErrorLabel.setVisible(false);

        pane.add(gasLabel, 0, 6);
        pane.add(gasTF, 1, 6);
        pane.add(gasErrorLabel, 2, 6);
        gasErrorLabel.setTextFill(Color.RED);
        gasErrorLabel.setVisible(false);

        pane.add(otherLabel, 0, 7);
        pane.add(otherTF, 1, 7);
        pane.add(otherErrorLabel, 2, 7);
        otherErrorLabel.setTextFill(Color.RED);
        otherErrorLabel.setVisible(false);
        //pane.add(addButton, 2, 7);

        HBox saveCancelButtons = new HBox();
        saveCancelButtons.setSpacing(10);
        saveCancelButtons.setAlignment(Pos.CENTER_LEFT);
        saveCancelButtons.getChildren().add(saveButton);
        saveCancelButtons.getChildren().add(cancelButton);
        pane.add(saveCancelButtons, 0, 8);
        saveButton.setOnAction(e -> save(selectedAccount));
        cancelButton.setOnAction(e -> goBackToPrevScene());


        this.setRoot(pane);
    }

    private void save(MainAccount selectedAccount) {
        String year, month, utilities, housing, grocery, gas, other;
        Double u = 0.0, h = 0.0, gr = 0.0, ga = 0.0, o = 0.0;
        year = yearTF.getText();
        month = monthTF.getText();
        utilities = utilitiesTF.getText();
        housing = housingTF.getText();
        grocery = groceryTF.getText();
        gas = gasTF.getText();
        other = otherTF.getText();
        yearErrorLabel.setVisible(false);
        monthErrorLabel.setVisible(false);
        if(year.length() != 4){
            yearErrorLabel.setVisible(true);
        }

        if(month.length() != 2){
            monthErrorLabel.setVisible(true);
        }
        /*
        yearErrorLabel.setVisible(year.isEmpty());
        monthErrorLabel.setVisible(month.isEmpty());*/
        utilitiesErrorLabel.setVisible(utilities.isEmpty());
        housingErrorLabel.setVisible(housing.isEmpty());
        groceryErrorLabel.setVisible(grocery.isEmpty());
        gasErrorLabel.setVisible(gas.isEmpty());
        otherErrorLabel.setVisible(other.isEmpty());

        try{
            u = Double.parseDouble(utilities);
        }
        catch(Exception e){
            utilitiesErrorLabel.setVisible(true);
        }
        try{
            h = Double.parseDouble(housing);
        }
        catch(Exception e){
            housingErrorLabel.setVisible(true);
        }
        try{
            gr = Double.parseDouble(grocery);
        }
        catch(Exception e){
            groceryErrorLabel.setVisible(true);
        }
        try{
            ga = Double.parseDouble(gas);
        }
        catch(Exception e){
            gasErrorLabel.setVisible(true);
        }
        try{
            o = Double.parseDouble(other);
        }
        catch(Exception e){
            otherErrorLabel.setVisible(true);
        }
        if(yearErrorLabel.isVisible() || monthErrorLabel.isVisible() ||
                utilitiesErrorLabel.isVisible() || housingErrorLabel.isVisible() ||
                groceryErrorLabel.isVisible() || gasErrorLabel.isVisible() ||
                otherErrorLabel.isVisible()){
            return;
        }

        else{

            controller.addMonthlyExpense(selectedAccount, new Expenses(month, year, u, h, gr, ga, o));
            ViewAccountScene.setAccountBalance(controller.getBalance());
            ViewAccountScene.setAccountExpenses(controller.getExpenses());
            ViewAccountScene.setAccountSavings(controller.getSavings());
            ViewAccountScene.setExpensesYearCB(selectedAccount);
            ViewAccountScene.setExpensesMonthCB(selectedAccount);

        }
        //controller.updateAccountData(selectedAccount);
        goBackToPrevScene();

    }

    private void goBackToPrevScene() {
        ViewNavigator.loadScene("Finance Organizer", previousScene);
    }


}

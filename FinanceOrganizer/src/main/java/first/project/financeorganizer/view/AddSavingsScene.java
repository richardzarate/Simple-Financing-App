package first.project.financeorganizer.view;

import first.project.financeorganizer.controller.Controller;
import first.project.financeorganizer.model.MainAccount;
import first.project.financeorganizer.model.Savings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

public class AddSavingsScene extends Scene {
    private Scene previousScene;

    public static final int WIDTH = 600;
    public static final int HEIGHT = 150;

    private Controller controller = Controller.getInstance();

    private Label yearLabel = new Label("Year:");
    private Label monthLabel = new Label("Month:");

    private Label savingsLabel = new Label("Money earned for this month: $");
    private Label yearErrorLabel = new Label("Year is required.(YYYY)");
    private Label monthErrorLabel = new Label("Month is required. (MM)");
    private Label savingsErrorLabel = new Label("Savings is required.");
    private TextField yearTF = new TextField();
    private TextField monthTF = new TextField();
    private TextField savingsTF = new TextField();

    private Button saveButton = new Button("Save");
    private Button cancelButton = new Button("Cancel");

    public AddSavingsScene(Scene previousScene, MainAccount selectedAccount){
        super(new GridPane(), WIDTH, HEIGHT);
        this.previousScene = previousScene;

        GridPane pane = new GridPane();
        pane.setHgap(10.0);
        pane.setVgap(5);
        pane.setPadding(new Insets(5));

        pane.add(new Label("Please enter:"), 0, 0);
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

        pane.add(savingsLabel, 0, 3);
        pane.add(savingsTF, 1, 3);
        pane.add(savingsErrorLabel, 2, 3);
        savingsErrorLabel.setTextFill(Color.RED);
        savingsErrorLabel.setVisible(false);

        HBox saveCancelButtons = new HBox();
        saveCancelButtons.setSpacing(10);
        saveCancelButtons.setAlignment(Pos.CENTER_RIGHT);
        saveCancelButtons.getChildren().add(saveButton);
        saveCancelButtons.getChildren().add(cancelButton);
        pane.add(saveCancelButtons, 1, 4);
        saveButton.setOnAction(e -> save(selectedAccount));
        cancelButton.setOnAction(e -> goBackToPrevScene());
        this.setRoot(pane);
    }

    private void goBackToPrevScene() {
        ViewNavigator.loadScene("Finance Organizer", previousScene);
    }

    private void save(MainAccount selectedAccount) {
        String year, month, savings;
        Double s = 0.0;

        year = yearTF.getText();
        month = monthTF.getText();
        savings = savingsTF.getText();
        yearErrorLabel.setVisible(false);
        monthErrorLabel.setVisible(false);
        savingsErrorLabel.setVisible(false);

        if(year.length() != 4){
            yearErrorLabel.setVisible(true);
        }
        if(month.length() != 2){
            monthErrorLabel.setVisible(true);
        }
        try{
            s = Double.parseDouble(savings);
        }
        catch (Exception e){
            savingsErrorLabel.setVisible(true);
        }

        if(yearErrorLabel.isVisible() || monthErrorLabel.isVisible() || savingsErrorLabel.isVisible()){
            return;
        }
        else{
            controller.addMonthlySaving(selectedAccount, new Savings(s, month, year));
            ViewAccountScene.setAccountBalance(controller.getBalance());
            ViewAccountScene.setAccountSavings(controller.getSavings());
            ViewAccountScene.setAccountExpenses(controller.getExpenses());
        }

        goBackToPrevScene();


    }
}

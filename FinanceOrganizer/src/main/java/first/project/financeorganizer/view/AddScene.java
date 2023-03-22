package first.project.financeorganizer.view;

import first.project.financeorganizer.controller.Controller;
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

import java.io.IOException;

public class AddScene extends Scene {
    private Scene previousScene;

    public static final int WIDTH = 300;
    public static final int HEIGHT = 100;

    private Controller controller = Controller.getInstance();

    private TextField firstNameTF = new TextField();
    private TextField lastNameTF = new TextField();

    private Label firstNameErrorLabel = new Label("First Name is required.");
    private Label lastNameErrorLabel = new Label("Last Name is required.");

    private Button saveButton = new Button("Save");
    private Button cancelButton = new Button("Cancel");

    public AddScene(Scene previousScene){
        super(new GridPane(), WIDTH, HEIGHT);
        this.previousScene = previousScene;

        GridPane pane = new GridPane();
        pane.setHgap(10.0);
        pane.setVgap(5);
        pane.setPadding(new Insets(5));

        pane.add(new Label("First Name: "), 0, 0);
        pane.add(firstNameTF, 1, 0);
        pane.add(firstNameErrorLabel, 2, 0);
        firstNameErrorLabel.setTextFill(Color.RED);
        firstNameErrorLabel.setVisible(false);


        pane.add(new Label("Last Name: "), 0, 1);
        pane.add(lastNameTF, 1, 1);
        pane.add(lastNameErrorLabel, 2, 1);
        lastNameErrorLabel.setTextFill(Color.RED);
        lastNameErrorLabel.setVisible(false);

        HBox saveCancelButtons = new HBox();
        saveCancelButtons.setSpacing(10);
        saveCancelButtons.setAlignment(Pos.CENTER_RIGHT);
        saveCancelButtons.getChildren().add(saveButton);
        saveCancelButtons.getChildren().add(cancelButton);
        pane.add(saveCancelButtons, 1, 2);

        saveButton.setOnAction(e -> save());
        cancelButton.setOnAction(e -> goBackToPrevScene());
        this.setRoot(pane);
    }

    private void save(){
        String firstName, lastName;

        firstName = firstNameTF.getText();
        lastName = lastNameTF.getText();

        firstNameErrorLabel.setVisible(firstName.isEmpty());
        lastNameErrorLabel.setVisible(lastName.isEmpty());

        if(firstNameErrorLabel.isVisible() || lastNameErrorLabel.isVisible()){
            return;
        }
        else{
            String name = firstName + " " + lastName;
            controller.addMainAccount(new MainAccount(name));
        }

        goBackToPrevScene();

    }
    private void goBackToPrevScene(){
        ViewNavigator.loadScene("Finance Organizer", previousScene);
    }
}

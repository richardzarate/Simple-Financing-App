package first.project.financeorganizer.view;

import first.project.financeorganizer.controller.Controller;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class View extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        

        ViewNavigator.setStage(primaryStage);
        ViewNavigator.loadScene("Finance Organizer", new MainScene());
    }

    @Override
    public void stop() throws Exception{
        Controller.getInstance().saveData();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
module com.example.financeorganizer {
    requires javafx.controls;
    requires javafx.fxml;


    opens first.project.financeorganizer to javafx.fxml;
    //exports first.project.financeorganizer;
    exports first.project.financeorganizer.view;
    opens first.project.financeorganizer.view to javafx.fxml;
}
package first.project.financeorganizer.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;

public class Model {
    private static final String BINARY_FILE = "FinanceOrganizer.dat";
    private static final String CSV_FILE = "FinanceOrganizer.csv";

    public static boolean binaryFileHasData(){
        File binaryFile = new File(BINARY_FILE);
        return (binaryFile.exists() && binaryFile.length() > 5L);
    }

    public static ObservableList<MainAccount> populateListFromBinaryFile(){
        ObservableList<MainAccount> allMainAccounts = FXCollections.observableArrayList();
        File binaryFile = new File(BINARY_FILE);
        if(binaryFileHasData()){
            try{
                ObjectInputStream fileReader = new ObjectInputStream(new FileInputStream(binaryFile));
                MainAccount[] temp = (MainAccount[]) fileReader.readObject();
                allMainAccounts.addAll(temp);
                fileReader.close();
            }
            catch(Exception e){
                System.err.println("Error opening file: " + BINARY_FILE + " for reading.\n" +
                        "Caused by: " + e.getMessage());
            }
        }
        return allMainAccounts;
    }

    public static boolean writeDataToBinaryFile(ObservableList<MainAccount> allMainAccountList){
        File binaryFile = new File(BINARY_FILE);
        try{
            ObjectOutputStream fileWriter = new ObjectOutputStream(new FileOutputStream(binaryFile));
            MainAccount[] temp = new MainAccount[allMainAccountList.size()];
            allMainAccountList.toArray(temp);
            fileWriter.writeObject(temp);
            fileWriter.close();
            return true;
        }
        catch(Exception e){
            System.err.println("Error writing binary file: " + BINARY_FILE + "\n" + e.getMessage());
            return false;
        }
    }
}

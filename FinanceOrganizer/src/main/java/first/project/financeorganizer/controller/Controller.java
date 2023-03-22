package first.project.financeorganizer.controller;

import first.project.financeorganizer.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Controller {
    private static Controller theInstance;
    private ObservableList<MainAccount> mAllMainAccountsList;
    private ObservableList<MainAccount> mFilteredMainAccountsList;

    private ObservableList<String> mAlphabetList;
    private ObservableList<String> mAccountType;
    private ObservableList<String> mExpensesYear;
    private ObservableList<String> mExpensesMonth;

    private ObservableList<String> mSavingsYear;
    private ObservableList<String> mSavingsMonth;

    private ObservableList<Account> mAllExpensesList;
    private ObservableList<Account> mFilteredExpensesList;
    private ObservableList<Account> mAllSavingsList;
    private ObservableList<Account> mFilteredSavingsList;

    private Double mBalance, mSavings, mExpenses;

    private Controller(){}

    public static Controller getInstance(){
        if(theInstance == null){
            theInstance = new Controller();
            theInstance.mAllMainAccountsList = Model.populateListFromBinaryFile();
            FXCollections.sort(theInstance.mAllMainAccountsList);

            theInstance.mAlphabetList = theInstance.initializeAlphabetList();
            FXCollections.sort(theInstance.mAlphabetList);

            theInstance.mAccountType = theInstance.initializeAccountType();
            FXCollections.sort(theInstance.mAccountType);

            theInstance.mExpensesYear = theInstance.initializeExpensesYear();
            FXCollections.sort(theInstance.mExpensesYear);

            theInstance.mExpensesMonth = theInstance.initializeExpensesMonth();
            FXCollections.sort(theInstance.mExpensesMonth);

            theInstance.mSavingsYear = theInstance.initializeSavingsYear();
            FXCollections.sort(theInstance.mSavingsYear);

            theInstance.mSavingsMonth = theInstance.initializeSavingsMonth();
            FXCollections.sort(theInstance.mSavingsMonth);

            theInstance.mFilteredMainAccountsList = FXCollections.observableArrayList();

            theInstance.mAllExpensesList = FXCollections.observableArrayList();
            theInstance.mAllSavingsList = FXCollections.observableArrayList();

            theInstance.mFilteredExpensesList = FXCollections.observableArrayList();
            theInstance.mFilteredSavingsList = FXCollections.observableArrayList();

            theInstance.mBalance = 0.0;
            theInstance.mSavings = 0.0;
            theInstance.mExpenses = 0.0;
        }

        return theInstance;
    }

    public ObservableList<MainAccount> getAllMainAccounts(){
        return mAllMainAccountsList;
    }

    public ObservableList<Account> getAllExpensesList(MainAccount selectedAccount){

        theInstance.loadAccountData(selectedAccount);
        return mAllExpensesList;}

    /*public ObservableList<Account> getAllSavingsList(MainAccount selectedAccount){
        theInstance.loadAccountData(selectedAccount);
        return mAllSavingsList;
    }*/

    public ObservableList<Account> getAllSavingsList(MainAccount selectedAccount){
        theInstance.loadAccountData(selectedAccount);
        return mAllSavingsList;}

    public ObservableList<Account> filterAccountsList(ObservableList<Account> accountList, String year, String month, Double total){
        ObservableList<Account> filteredList = accountList;
        if(("".equals(year) || year == null) && ("".equals(month) || month == null) && total == 0.0){
            return accountList;
        }
        else {
            for (Account account : accountList) {
                if (("".equals(account.getYear()) || !account.getYear().equals(year)) && ("".equals(account.getMonth()) || !account.getMonth().equals(month)) && account.getTotal() < total) {
                    filteredList.remove(account);
                }
            }
            return filteredList;
        }

    }

    public void addExpense(Expenses expense){mAllExpensesList.add(expense);}
    public void removeExpense(Expenses expense){mAllExpensesList.remove(expense);}
    public void addSavings(Savings saving){mAllSavingsList.add(saving);}
    public void removeSavings(Savings saving){
        mAllSavingsList.remove(saving);
    }

    public void loadAccountData(MainAccount selectedAccount){
        mAllSavingsList.clear();
        mAllExpensesList.clear();
        for(Expenses expense : selectedAccount.getExpensesRecord()){
            mAllExpensesList.add(expense);
        }
        for(Savings saving : selectedAccount.getSavingsRecord()){
            mAllSavingsList.add(saving);
        }
    }
    public void addMainAccount(MainAccount mainAccount){
        mAllMainAccountsList.add(mainAccount);
    }
    public void removeMainAccount(MainAccount mainAccount) {
        mAllMainAccountsList.remove(mainAccount);
    }
    public void addMonthlySaving(MainAccount selectedAccount, Savings saving){
        selectedAccount.addMonthlySavings(saving);
        theInstance.addSavings(saving);
        theInstance.updateAccountData(selectedAccount);
    }

    public void removeMonthlySaving(MainAccount selectedAccount, Savings saving){
        selectedAccount.removeMonthlySavings(saving);
        theInstance.removeSavings(saving);
        theInstance.updateAccountData(selectedAccount);
    }
    public void addMonthlyExpense(MainAccount selectedAccount, Expenses expense){

        selectedAccount.addMonthlyExpenses(expense);
        theInstance.addExpense(expense);
        theInstance.updateAccountData(selectedAccount);
    }

    // DONE: add removeMonthlyExpense Function
    public void removeMonthlyExpense(MainAccount selectedAccount, Expenses expense){
        selectedAccount.removeMonthlyExpenses(expense);
        theInstance.removeExpense(expense);
        theInstance.updateAccountData(selectedAccount);
    }

    public Double getBalance() {
        return mBalance;
    }

    public Double getSavings() {
        return mSavings;
    }

    public Double getExpenses() {
        return mExpenses;
    }

    public ObservableList<MainAccount> filter(String letter, double total){
        // TODO: Instead of clearing, try copying full list then delete if it doesn't equal parameter
        mFilteredMainAccountsList.clear();
        for(MainAccount account : mAllMainAccountsList){
            if(("".equals(letter) || letter.equals(Character.toString(account.getAccountName().charAt(0)))) && account.getTotal() >= total){
                mFilteredMainAccountsList.add(account);
            }
        }
        return mFilteredMainAccountsList;

    }

    public ObservableList<String> initializeAlphabetList(){
        String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        ObservableList<String> alphabetList = FXCollections.observableArrayList();
        alphabetList.add("");
        for(int i = 0; i < letters.length(); i++){
            alphabetList.add(Character.toString(letters.charAt(i)));
        }
        return alphabetList;
    }

    public ObservableList<String> initializeAccountType(){
        ObservableList<String> accountType = FXCollections.observableArrayList();
        accountType.add("");
        accountType.add("Savings");
        accountType.add("Expenses");
        return accountType;
    }

    public ObservableList<String> initializeExpensesYear(){
        ObservableList<String> expensesYear = FXCollections.observableArrayList();
        expensesYear.add("");
        for(MainAccount account : mAllMainAccountsList){
            for(Expenses expense : account.getExpensesRecord()){
                expensesYear.add(expense.getYear());
            }
        }
        return expensesYear;
    }

    public ObservableList<String> initializeExpensesMonth(){
        ObservableList<String> expensesMonth = FXCollections.observableArrayList();
        expensesMonth.add("");
        for(MainAccount account : mAllMainAccountsList){
            for(Expenses expense : account.getExpensesRecord()){
                expensesMonth.add(expense.getMonth());
            }
        }
        return expensesMonth;
    }

    public ObservableList<String> initializeSavingsYear(){
        ObservableList<String> savingsYear = FXCollections.observableArrayList();
        savingsYear.add("");
        for(MainAccount account : mAllMainAccountsList){
            for(Savings saving : account.getSavingsRecord()){
                savingsYear.add(saving.getYear());
            }
        }
        return savingsYear;
    }

    public ObservableList<String> initializeSavingsMonth(){
        ObservableList<String> savingsMonth = FXCollections.observableArrayList();
        savingsMonth.add("");
        for(MainAccount account : mAllMainAccountsList){
            for(Savings saving : account.getSavingsRecord()){
                savingsMonth.add(saving.getMonth());
            }
        }
        return savingsMonth;
    }

    public ObservableList<String> getAlphabetList() {
        return mAlphabetList;
    }

    public ObservableList<String> getAccountType() {
        return mAccountType;
    }

    public ObservableList<String> getExpensesYear(MainAccount selectedAccount) {
        updateAccountData(selectedAccount);
        FXCollections.sort(mExpensesYear);
        return mExpensesYear;
    }

    public ObservableList<String> getExpensesMonth(MainAccount selectedAccount) {
        updateAccountData(selectedAccount);
        FXCollections.sort(mExpensesMonth);
        return mExpensesMonth;
    }

    public ObservableList<String> getSavingsYear(MainAccount selectedAccount) {
        updateAccountData(selectedAccount);
        FXCollections.sort(mSavingsYear);
        return mSavingsYear;
    }

    public ObservableList<String> getSavingsMonth(MainAccount selectedAccount) {
        updateAccountData(selectedAccount);
        FXCollections.sort(mSavingsMonth);
        return mSavingsMonth;
    }

    public ObservableList<Account> getExpensesRecord(MainAccount selectedAccount){
        ObservableList<Account> expensesRecord = FXCollections.observableArrayList();
        for(Expenses expense : selectedAccount.getExpensesRecord()){
            expensesRecord.add(expense);
        }
        return expensesRecord;
    }

    public ObservableList<Account> getSavingsRecord(MainAccount selectedAccount){
        ObservableList<Account> savingsRecord = FXCollections.observableArrayList();
        for(Savings saving : selectedAccount.getSavingsRecord()){
            savingsRecord.add(saving);
        }
        return savingsRecord;
    }


    public Double[] getAccountData(MainAccount selectedAccount){
        Double[] accountData = new Double[3];
        //selectedAccount.updateAccountData();
        accountData[0] = selectedAccount.getTotal();
        accountData[1] = 0.0;
        accountData[2] = 0.0;
        for(Expenses expense : selectedAccount.getExpensesRecord()){
            accountData[1] += expense.getTotal();
        }

        for(Savings saving : selectedAccount.getSavingsRecord()){
            accountData[2] += saving.getTotal();
        }

        return accountData;
    }

    public void updateAccountData(MainAccount selectedAccount){
        mExpenses = 0.0;
        mSavings = 0.0;
        mExpensesYear.clear();
        mExpensesMonth.clear();
        mSavingsYear.clear();
        mSavingsMonth.clear();
        mExpensesYear.add("");
        mExpensesMonth.add("");
        mSavingsYear.add("");
        mSavingsMonth.add("");
        for(Expenses expense : selectedAccount.getExpensesRecord()){
            mExpenses += expense.getTotal();
            if(!mExpensesYear.contains(expense.getYear())) {
                mExpensesYear.add(expense.getYear());

            }

            if (!mExpensesMonth.contains(expense.getMonth())) {
                mExpensesMonth.add(expense.getMonth());
            }
        }
        for(Savings saving : selectedAccount.getSavingsRecord()){
            mSavings += saving.getTotal();

            if(!mSavingsYear.contains(saving.getYear())) {
                mSavingsYear.add(saving.getYear());

            }

            if (!mSavingsMonth.contains(saving.getMonth())) {
                mSavingsMonth.add(saving.getMonth());
            }

        }
        mBalance = mSavings - mExpenses;
    }

    public void saveData(){
        Model.writeDataToBinaryFile(mAllMainAccountsList);
    }
}

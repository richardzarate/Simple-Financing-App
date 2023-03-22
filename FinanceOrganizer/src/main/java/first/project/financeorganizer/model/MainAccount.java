package first.project.financeorganizer.model;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.ArrayList;

public class MainAccount implements Serializable, Comparable<MainAccount> {
    private ArrayList<Expenses> mExpensesRecord = new ArrayList<>();
    private ArrayList<Savings> mSavingsRecord = new ArrayList<>();

    private double mTotal;

    private String mAccountName;

    private static NumberFormat currency = NumberFormat.getCurrencyInstance();

    public MainAccount(String accountName) {
        mAccountName = accountName;
        mTotal = 0;
    }

    public ArrayList<Expenses> getExpensesRecord() {
        return mExpensesRecord;
    }


    public ArrayList<Savings> getSavingsRecord() {
        return mSavingsRecord;
    }


    public String getAccountName() {
        return mAccountName;
    }

    public void setAccountName(String accountName) {
        mAccountName = accountName;
    }

    public double getTotal() {
        return mTotal;
    }

    public void setTotal(double total) {
        mTotal = total;
    }

    public void addMonthlyExpenses(Expenses monthlyExpenses){
        mExpensesRecord.add(monthlyExpenses);
        mTotal -= monthlyExpenses.getTotal();
    }

    public void removeMonthlyExpenses(Expenses monthlyExpenses){
        mExpensesRecord.remove(monthlyExpenses);
        mTotal += monthlyExpenses.getTotal();
    }

    public void addMonthlySavings(Savings monthlySavings){
        mSavingsRecord.add(monthlySavings);
        mTotal += monthlySavings.getTotal();
    }

    public void removeMonthlySavings(Savings monthlySavings){
        mSavingsRecord.remove(monthlySavings);
        mTotal -= monthlySavings.getTotal();
    }

    public void updateAccountData(){
        double savingsTotal = 0.0, expensesTotal = 0.0;
        for(Savings saving : mSavingsRecord){
            savingsTotal += saving.getTotal();
        }
        for(Expenses expense : mExpensesRecord){
            expensesTotal -= expense.getTotal();
        }

        mTotal = savingsTotal - expensesTotal;
    }

    public void updateTotal(){
        for(Savings saving : mSavingsRecord){
            mTotal += saving.getTotal();
        }
        for(Expenses expense : mExpensesRecord){
            mTotal -= expense.getTotal();
        }
    }

    public double getExpensesTotal(){
        if(mExpensesRecord.size() == 0){
            return 0.0;
        }
        double total = 0;
        for(Expenses expense : mExpensesRecord){
            total += expense.getTotal();
        }
        return total;
    }

    public double getSavingsTotal(){
        if (mSavingsRecord.size() == 0){
            return 0.0;
        }
        double total = 0;
        for(Savings saving : mSavingsRecord){
            total += saving.getTotal();
        }
        return total;
    }

    @Override
    public int compareTo(MainAccount other) {
        int nameComp = mAccountName.compareTo(other.mAccountName);
        if(nameComp != 0){
            return nameComp;
        }
        double totalComp = this.mTotal - other.mTotal;

        return (int) totalComp;
    }

    @Override
    public String toString() {
        return "MainAccount[" +
                "Account Name: " + mAccountName +
                ", Account Balance: " + currency.format(mTotal) +
                ", Total Savings: " + currency.format(getSavingsTotal()) +
                ", Total Expenses: " + currency.format(getExpensesTotal()) + "]";
    }
}

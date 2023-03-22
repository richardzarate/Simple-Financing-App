package first.project.financeorganizer.model;

import java.text.NumberFormat;
import java.util.ArrayList;

public class Expenses extends Account{
    private double mUtilities, mHousing, mGroceries, mGas, mOther;


    private static NumberFormat currency = NumberFormat.getCurrencyInstance();

    public Expenses(String month, String year, double utilities, double housing, double groceries, double gas, double other) {
        super(0, month, year);
        mUtilities = utilities;
        mHousing = housing;
        mGroceries = groceries;
        mGas = gas;
        mOther = other;
        mTotal = mUtilities + mHousing + mGroceries + mGas + mOther;

    }

    public double getGas() {
        return mGas;
    }

    public void setGas(double gas) {
        mGas = gas;

    }

    public double getUtilities() {
        return mUtilities;
    }

    public void setUtilities(double utilities) {
        mUtilities = utilities;

    }

    public double getHousing() {
        return mHousing;
    }

    public void setHousing(double housing) {
        mHousing = housing;
    }

    public double getGroceries() {
        return mGroceries;
    }

    public void setGroceries(double groceries) {
        mGroceries = groceries;

    }

    public Double getOther() {
        return mOther;
    }

    public void setOther(double other) {
        mOther = other;
    }

    /*
    public void addToOther(double expense){
        mOther.add(expense);
        updateTotal();
    }

    public void removeFromOther(double expense){
        mOther.remove(expense);
        updateTotal();
    }*/
    /*
    public void updateTotal(){
        mTotal = 0;
        mTotal = mUtilities + mHousing + mGroceries +mGas;
        for(double expense : mOther){
            mTotal += expense;
        }
    }*/
    /*
    public double getOtherTotal(){
        double total = 0;
        for(double expense : mOther){
            total += 0;
        }
        return total;
    }*/

    @Override
    public String toString() {
        return "Expenses[Year: " + mYear +
                ", Month: " + mMonth +
                ", Total: " + currency.format(mTotal) +
                ", Utilities: " + currency.format(mUtilities) +
                ", Housing: " + currency.format(mHousing) +
                ", Groceries: " + currency.format(mGroceries) +
                ", Gas: " + currency.format(mGas) +
                ", Other: " + currency.format(mOther) +
                "]";

    }
}

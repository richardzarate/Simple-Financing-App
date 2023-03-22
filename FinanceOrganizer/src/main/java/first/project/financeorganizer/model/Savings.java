package first.project.financeorganizer.model;

import java.io.Serializable;
import java.text.NumberFormat;

public class Savings extends Account implements Serializable, Comparable<Account> {

    private static NumberFormat currency = NumberFormat.getCurrencyInstance();


    public Savings(double total, String month, String year) {
        super(total, month, year);

    }

    @Override
    public String toString() {
        return "Savings[Year:" + mYear + ", Month:" +
                mMonth + ", Total: " + currency.format(mTotal) + "]";
    }

    public void add(double salary){
        mTotal += salary;
    }

    public void remove(double salary){
        mTotal -= salary;
    }
}
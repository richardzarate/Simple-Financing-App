package first.project.financeorganizer.model;


import java.io.Serializable;


public abstract class Account implements Serializable, Comparable<Account> {
    protected double mTotal;
    protected String mMonth, mYear;

    public Account(double total, String month, String year) {
        mTotal = total;
        mMonth = month;
        mYear = year;
    }

    public double getTotal() {
        return mTotal;
    }

    public void setTotal(double total) {
        mTotal = total;
    }

    public String getMonth() {
        return mMonth;
    }

    public void setMonth(String month) {
        mMonth = month;
    }

    public String getYear() {
        return mYear;
    }

    public void setYear(String year) {
        mYear = year;
    }

    @Override
    public int compareTo(Account other){
        int yearComp = this.mYear.compareTo(other.mYear);
        if(yearComp != 0){
            return -1 * yearComp;
        }

        int monthComp = this.mMonth.compareTo(other.mMonth);
        if(monthComp != 0){
            return -1 * monthComp;
        }

        double totalComp = this.mTotal - other.mTotal;
        return (int) totalComp;

    }


}

package com.atm;

public class OperationDTO implements iDTO {
    private Account fromAccount;
    private Account toAccount;
    private String dateTime;
    private double sum;
    private boolean isCommited;
    
    public Account getFromAccount() {
        return fromAccount;
    }
    public void setFromAccount(Account fromAccount) {
        this.fromAccount = fromAccount;
    }
    public Account getToAccount() {
        return toAccount;
    }
    public void setToAccount(Account toAccount) {
        this.toAccount = toAccount;
    }
    public String getDateTime() {
        return dateTime;
    }
    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
    public double getSum() {
        return sum;
    }
    public void setSum(double sum) {
        this.sum = sum;
    }
    public boolean isCommited() {
        return isCommited;
    }
    public void setCommited(boolean isCommited) {
        this.isCommited = isCommited;
    }
    
}

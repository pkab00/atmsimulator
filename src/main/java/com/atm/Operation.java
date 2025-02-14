package com.atm;

import java.security.InvalidParameterException;

public class Operation {
    private Account fromAccount;
    private Account toAccount;
    private String dateTime;
    private double sum;

    public Operation(User toUser, double sum) throws InvalidParameterException{
        this.fromAccount = null;
        this.toAccount = toUser.getAccount();
        this.sum = sum;

        validate(toAccount, sum);
    }

    public Operation(User toUser, User fromUser, double sum) throws InvalidParameterException{
        this.fromAccount = fromUser.getAccount();
        this.toAccount = toUser.getAccount();
        this.sum = sum;

        validate(toAccount, sum);
        validate(fromAccount, -sum);
    }

    @Override
    public String toString(){
        if(fromAccount == null){
            if(sum > 0){
                return String.format("Зачисление %f руб. на %s", sum, toAccount.getCardNumber());
            } else{
                return String.format("Списание %f руб. с %s", sum, toAccount.getCardNumber());
            }
        } else{
            return String.format("Перевод %f руб. с %s на %s", sum, fromAccount.getCardNumber(), toAccount.getCardNumber());
        }
    }

    private void validate(Account acc, double sum) throws InvalidParameterException{
        if(sum < 0){
            if(acc.getBalance() + sum < 0){
                throw new InvalidParameterException(String.format("%s: недостаточно средств на счету!",
                acc.getCardNumber()));
            }
            else if(Math.abs(sum) > acc.getWithdrawLimit()){
                throw new InvalidParameterException(String.format("%s: привышен лимит списания!",
                acc.getCardNumber()));
            }
        }
    }

    public Account getFromAccount() {
        return fromAccount;
    }

    public Account getToAccount() {
        return toAccount;
    }

    public String getDateTime() {
        return dateTime;
    }

    public double getSum() {
        return sum;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
}

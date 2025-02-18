package com.atm;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Operation {
    private Account fromAccount;
    private Account toAccount;
    private String dateTime;
    private double sum;
    private boolean isCommited;

    public class InvalidOperationException  extends Exception{
        public InvalidOperationException(String message){
            super(message);
        }
    }

    public Operation(Account toAccount, double sum) throws InvalidOperationException{
        this.fromAccount = null;
        this.toAccount = toAccount;
        this.sum = sum;
        this.isCommited = false;

        validate(toAccount, sum);
    }

    public Operation(Account toAccount, Account fromAccount, double sum) throws InvalidOperationException{
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.sum = sum;
        this.isCommited = false;

        validate(toAccount, sum);
        validate(fromAccount, -sum);
    }

    public Operation(OperationDTO DTO){
        this.fromAccount = Account.getExistingAccount((AccountDTO)CommonDAO.requestData(DTO.getFromCardNumber(),
                                                            REQUEST_TYPE.ACCOUNTS).get(0));
        this.toAccount = Account.getExistingAccount((AccountDTO)CommonDAO.requestData(DTO.getToCardNumber(),
                                                            REQUEST_TYPE.ACCOUNTS).get(0));
        this.sum = DTO.getSum();
        this.dateTime = DTO.getDateTime();
        this.isCommited = DTO.isCommited();
    }

    @Override
    public String toString(){
        if(fromAccount == null){
            if(sum > 0){
                return String.format("Зачисление %.2f руб. на %s", sum, toAccount.getCardNumber());
            } else{
                return String.format("Списание %.2f руб. с %s", sum, toAccount.getCardNumber());
            }
        } else{
            return String.format("Перевод %.2f руб. с %s на %s", sum, fromAccount.getCardNumber(), toAccount.getCardNumber());
        }
    }

    private void validate(Account acc, double sum) throws InvalidOperationException{
        if(sum < 0){
            if(acc.getBalance() + sum < 0){
                throw new InvalidOperationException(String.format("%s: недостаточно средств на счету!",
                acc.getCardNumber()));
            }
            else if(Math.abs(sum) > acc.getWithdrawLimit()){
                throw new InvalidOperationException(String.format("%s: привышен лимит списания!",
                acc.getCardNumber()));
            }
        }
    }

    public void commit(){
         if(this.isCommited) return;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss dd.MM.yyyy");
        if(fromAccount == null){
            toAccount.changeBalance(sum);
        } else{
            fromAccount.changeBalance(-sum);
            toAccount.changeBalance(sum);
        }
        this.dateTime = LocalDateTime.now().format(formatter);
        this.isCommited = true;
        CommonDAO.addOperation(dateTime, toAccount.getCardNumber(),
        (fromAccount==null) ? null : fromAccount.getCardNumber(), sum);
    }

    public static ArrayList<String> getLog(String cardNumber){
        ArrayList<String> log = new ArrayList<>();
        for(iDTO i: CommonDAO.requestData(cardNumber, REQUEST_TYPE.OPERATIONS)){
            OperationDTO op = (OperationDTO)i;
            log.add(op.toString());
        }
        return log;
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

    public boolean isCommited(){
        return isCommited;
    }
}
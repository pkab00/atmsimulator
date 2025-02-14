package com.atm;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Operation {
    private Account fromAccount;
    private Account toAccount;
    private String dateTime;
    private double sum;
    private boolean isCommited;

    private class InvalidOperationException  extends Exception{
        public InvalidOperationException(String message){
            super(message);
        }
    }

    public Operation(User toUser, double sum) throws InvalidOperationException{
        this.fromAccount = null;
        this.toAccount = toUser.getAccount();
        this.sum = sum;
        this.isCommited = false;

        validate(toAccount, sum);
    }

    public Operation(User toUser, User fromUser, double sum) throws InvalidOperationException{
        this.fromAccount = fromUser.getAccount();
        this.toAccount = toUser.getAccount();
        this.sum = sum;
        this.isCommited = false;

        validate(toAccount, sum);
        validate(fromAccount, -sum);
    }

    public Operation(OperationDTO DTO){
        this.fromAccount = DTO.getFromAccount();
        this.toAccount = DTO.getToAccount();
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

    public static void main(String[] args) {
        User me = User.getExistingUser("1234", "7171 3045 4443 3989");
        User anotherUser = User.getExistingUser("6666", "0154 0963 9160 9316");
        try{
            Operation getSomeMoney = new Operation(me, 100);
            Operation takeSomeMoney = new Operation(anotherUser, 50);
            getSomeMoney.commit();
            System.out.println(getSomeMoney);
            takeSomeMoney.commit();
            System.out.println(takeSomeMoney);
        } catch(InvalidOperationException e){
            e.printStackTrace();
        };;
        
        System.out.println(String.format("До перевода:\n%s: %.2f\n%s: %.2f",
        me.getFullName(), me.getAccount().getBalance(), anotherUser.getFullName(), anotherUser.getAccount().getBalance()));

        try{
            Operation transaction = new Operation(me, anotherUser, 50);
            transaction.commit();
            System.out.println(transaction);
        } catch(InvalidOperationException e){
            e.printStackTrace();
        }

        System.out.println(String.format("После перевода:\n%s: %.2f\n%s: %.2f",
        me.getFullName(), me.getAccount().getBalance(), anotherUser.getFullName(), anotherUser.getAccount().getBalance()));
    }
}

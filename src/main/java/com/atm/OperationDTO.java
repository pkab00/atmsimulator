package com.atm;

public class OperationDTO implements iDTO {
    private String fromCardNumber;
    private String toCardNumber;
    private String dateTime;
    private double sum;
    private boolean isCommited;
    // DTO хранит только номера карт вместо объектов Account,
    // чего вполне достаточно для логирования операций
    
    @Override
    public String toString(){
        if(fromCardNumber == null){
            if(sum > 0){
                return String.format("[%s] Зачисление на %s [%.2f руб.]", dateTime, toCardNumber, sum);
            } else{
                return String.format("[%s] Списание с %s [%.2f руб.]", dateTime, toCardNumber, sum);
            }
        } else{
            return String.format("[%s] Перевод с %s на %s [%.2f руб.]", dateTime, fromCardNumber, toCardNumber, sum);
        }
    }

    public String getFromCardNumber() {
        return fromCardNumber;
    }
    public OperationDTO setFromCardNumber(String fromCardNumber) {
        this.fromCardNumber = fromCardNumber;
        return this;
    }
    public String getToCardNumber() {
        return toCardNumber;
    }
    public OperationDTO setToCardNumber(String toCardNumber) {
        this.toCardNumber = toCardNumber;
        return this;
    }
    public String getDateTime() {
        return dateTime;
    }
    public OperationDTO setDateTime(String dateTime) {
        this.dateTime = dateTime;
        return this;
    }
    public double getSum() {
        return sum;
    }
    public OperationDTO setSum(double sum) {
        this.sum = sum;
        return this;
    }
    public boolean isCommited() {
        return isCommited;
    }
    public OperationDTO setCommited(boolean isCommited) {
        this.isCommited = isCommited;
        return this;
    }
    
}

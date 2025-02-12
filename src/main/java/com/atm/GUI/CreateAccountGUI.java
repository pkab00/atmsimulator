package com.atm.GUI;

public class CreateAccountGUI extends AutorizationGUI {
    public CreateAccountGUI(){
        super("Регистрация");
        composeUI();
    }

    @Override
    protected void composeUI() {
        addInputPanel("ФАМИЛИЯ", "IVANOV");
        addInputPanel("ИМЯ", "IVAN");
        addInputPanel("ОТЧЕСТВО", "IVANOVICH");
    }
    
}

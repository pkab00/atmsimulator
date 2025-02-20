package com.atm;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public interface iDTO extends Serializable {
    public abstract String getFileName();

    public default String getFullFileName(){
        return "src\\main\\resources\\ser\\"+getFileName();
    }

    public default void serialize(){
        String fullFileName = getFullFileName();
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(fullFileName, false))) {
            objectOutputStream.writeObject(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

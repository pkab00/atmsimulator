package com.atm.GUI;

import javax.swing.SwingUtilities;

import com.atm.CommonDAO;
import com.atm.REQUEST_TYPE;
import com.atm.UserDTO;

public class Main {
    public static void main(String[] args) {
        //SwingUtilities.invokeLater(() -> new LoginGUI());
        UserDTO DTO = (UserDTO)CommonDAO.requestData("6046 4245 7962 3355", REQUEST_TYPE.USERS).get(0);
        DTO.serialize();
        UserDTO deDTO = new UserDTO();
        deDTO = CommonDAO.deserealizeDTO(deDTO);
        System.out.println(deDTO.getFatherName());
    }
}

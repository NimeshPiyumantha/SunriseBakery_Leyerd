package com.sunRiseBekary.pos.bo.custom.Impl;

import com.sunRiseBekary.pos.bo.custom.UserBO;
import com.sunRiseBekary.pos.dao.DAOFactory;
import com.sunRiseBekary.pos.dao.custom.UserDAO;
import com.sunRiseBekary.pos.dto.LoginDTO;
import com.sunRiseBekary.pos.entity.Login;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author : Nimesh Piyumantha
 * @since : 0.1.0
 **/
public class UserBOImpl implements UserBO {
    private final UserDAO userDAO = (UserDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.USER);

    @Override
    public ArrayList<LoginDTO> getAllUsers() throws SQLException, ClassNotFoundException {
        ArrayList<Login> all = userDAO.getAllUsers();
        ArrayList<LoginDTO> allOrder = new ArrayList<>();
        for (Login users : all) {
            allOrder.add(new LoginDTO(users.getUserName(), users.getPassword()));
        }
        return allOrder;
    }
}

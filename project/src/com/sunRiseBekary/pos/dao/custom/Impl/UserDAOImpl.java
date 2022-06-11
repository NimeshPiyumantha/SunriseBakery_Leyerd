package com.sunRiseBekary.pos.dao.custom.Impl;

import com.sunRiseBekary.pos.dao.custom.UserDAO;
import com.sunRiseBekary.pos.db.DBConnection;
import com.sunRiseBekary.pos.entity.Login;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author : Nimesh Piyumantha
 * @since : 0.1.0
 **/
public class UserDAOImpl implements UserDAO {

    @Override
    public ArrayList<Login> getAllUsers() throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Login");
        ResultSet rst = stm.executeQuery();
        ArrayList<Login> users = new ArrayList<>();
        while (rst.next()) {
            users.add(new Login(
                    rst.getString(1),
                    rst.getString(2)
            ));
        }
        return users;
    }
}

package com.sunRiseBekary.pos.dao.custom;

import com.sunRiseBekary.pos.dao.SuperDAO;
import com.sunRiseBekary.pos.entity.Login;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author : Nimesh Piyumantha
 * @since : 0.1.0
 **/
public interface UserDAO extends SuperDAO {
    ArrayList<Login> getAllUsers() throws SQLException, ClassNotFoundException;
}

package com.example.taxreports.DAO;

import com.example.taxreports.bean.InspectorsBean;
import com.example.taxreports.controller.Reports.ReportServlet;
import com.example.taxreports.util.ConnectionPool;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdminDao {

    private static final Logger log = Logger.getLogger(AdminDao.class);
    private static final String UPDATE_INSPECTOR = "update inspectors set Fname =?, Lname =? where user_id =? ";
    private static final String INSERT_INSPECTORS = "insert into inspectors (Fname, Lname,user_id) values (?,?,?) ";
    private static final String DELETE_INSPECTORS = "delete from inspectors where user_id =? ";
    private static final String SELECT_ALL_INSPECTORS ="select user_id, FName,LName from inspectors";


    public void editInspector(int id, String fName, String sName){

        try (Connection con= ConnectionPool.getInstance().getConnection();
             PreparedStatement stm = con.prepareStatement(UPDATE_INSPECTOR)){
            stm.setString(1, fName);
            stm.setString(2,sName);
            stm.setInt(3,id);
            stm.executeUpdate();
        } catch (SQLException e) {
            log.error(e);
            throw new RuntimeException("Sorry, cannot edit such user");
        }
    }
    public void createInspector(int userId, String fName,String lName){

        try (Connection con= ConnectionPool.getInstance().getConnection();
             PreparedStatement stm = con.prepareStatement(INSERT_INSPECTORS)){
            stm.setString(1, fName);
            stm.setString(2,lName);
            stm.setInt(3,userId);
            stm.executeUpdate();
        } catch (SQLException e) {
            log.error(e);
            throw new RuntimeException("Sorry, cannot register such user");
        }
    }
    public void deleteInspector(int id){

        UserDAO userDAO = new UserDAO();
        userDAO.deleteUser(id);

        try (Connection con= ConnectionPool.getInstance().getConnection();
            PreparedStatement  stm = con.prepareStatement(DELETE_INSPECTORS)){

            stm.setInt(1,id);
            stm.executeUpdate();

        } catch (SQLException e) {
            log.error(e);
            throw new RuntimeException("Sorry, cannot find such user");
        }

    }

    public static List<InspectorsBean> getInspectorsList(){

        List<InspectorsBean> list = new ArrayList<>();
        int userID;
        String fName;
        String lName;

        try(Connection con = ConnectionPool.getInstance().getConnection(); Statement stm = con.createStatement()) {
            con.setAutoCommit(false);
            ResultSet rs = stm.executeQuery(SELECT_ALL_INSPECTORS);
            while (rs.next()){
                userID =rs.getInt("user_id");
                fName =rs.getString("FName");
                lName= rs.getString("LName");
                list.add(new InspectorsBean(userID,fName,lName));
            }
            con.setAutoCommit(true);
        } catch (SQLException e) {
            log.error(e);
            throw new RuntimeException("Sorry, cannot load user. Try again.");
        }
        return list;
    }
}

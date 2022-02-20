package com.example.taxreports.DAO;

import com.example.taxreports.bean.IndividualBean;
import com.example.taxreports.util.ConnectionPool;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class IndividualDAO {

    private static final Logger log = Logger.getLogger(IndividualDAO.class);
    private static final  String INSERT_INDIVIDUAL =
            "insert into individual (user_id, FName,sName , LName, TIN) values (?,?,?,?,?)";
    private static final String UPDATE_INDIVIDUAL_FNAME = "update individual set FName = ? where user_id =?";
    private static final String UPDATE_INDIVIDUAL_SNAME = "update individual set sName = ? where user_id =?";
    private static final String UPDATE_INDIVIDUAL_LNAME = "update individual set LName = ? where user_id =?";
    private static final String UPDATE_INDIVIDUAL_TIN  = "update individual set TIN = ? where user_id =?";
    private static final String SELECT_INDIVIDUAL = "select FName,sName, LName, TIN from individual where user_id =?";

    public void insertInd(int userId,String fName, String sName, String lName, String tin){

        try (Connection con= ConnectionPool.getInstance().getConnection(); PreparedStatement stm = con.prepareStatement(INSERT_INDIVIDUAL)){
            stm.setInt(1, userId);
            stm.setString(2,fName);
            stm.setString(3,sName);
            stm.setString(4,lName);
            stm.setString(5,tin);
            stm.executeUpdate();
        } catch (SQLException e) {
            log.error(e);
            throw new RuntimeException("Sorry, cannot register such user");
        }
    }

    public void updateFnameInd (int id, String fName){

        try (Connection con= ConnectionPool.getInstance().getConnection(); PreparedStatement stm = con.prepareStatement(UPDATE_INDIVIDUAL_FNAME)){
            stm.setString(1,fName);
            stm.setInt(2, id);
            stm.executeUpdate();
        } catch (SQLException e) {
            log.error(e);
            throw new RuntimeException("Sorry, cannot update First name");
        }
    }
    public void updateSnameInd (int id, String sName){

        try (Connection con= ConnectionPool.getInstance().getConnection(); PreparedStatement stm = con.prepareStatement(UPDATE_INDIVIDUAL_SNAME)){
            stm.setString(1,sName);
            stm.setInt(2, id);
            stm.executeUpdate();
        } catch (SQLException e) {
            log.error(e);
            throw new RuntimeException("Sorry, cannot update Second name");
        }
    }
    public void updateLnameInd (int id, String lName){

        try (Connection con= ConnectionPool.getInstance().getConnection(); PreparedStatement stm = con.prepareStatement(UPDATE_INDIVIDUAL_LNAME)){
            stm.setString(1,lName);
            stm.setInt(2, id);
            stm.executeUpdate();
        } catch (SQLException e) {
            log.error(e);
            throw new RuntimeException("Sorry, cannot update Last name");
        }
    }
    public void updateTinInd (int id, String tin){

        try (Connection con= ConnectionPool.getInstance().getConnection(); PreparedStatement stm = con.prepareStatement(UPDATE_INDIVIDUAL_TIN)){
            stm.setString(1,tin);
            stm.setInt(2, id);
            stm.executeUpdate();
        } catch (SQLException e) {
            log.error(e);
            throw new RuntimeException("Sorry, cannot update TIN. Check your input data");
        }
    }

    public static IndividualBean userInfo(int id){

       IndividualBean infInd = null;
        try (Connection con= ConnectionPool.getInstance().getConnection(); PreparedStatement stm = con.prepareStatement(SELECT_INDIVIDUAL)){
            con.setAutoCommit(false);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
           while (rs.next()){
               String fName = rs.getString("FName");
               String sName = rs.getString("sName");
               String lName = rs.getString("LName");
               String tin = rs.getString("TIN");
               infInd =new IndividualBean(id,fName,sName,lName,tin);
           }

        } catch (SQLException e) {
            log.error(e);
            throw new RuntimeException("Sorry, cannot load information try again");
        }
        return infInd;
    }
}

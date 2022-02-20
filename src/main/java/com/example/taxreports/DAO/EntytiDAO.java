package com.example.taxreports.DAO;

import com.example.taxreports.bean.EntytiBean;
import com.example.taxreports.util.ConnectionPool;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EntytiDAO {

    private static final Logger log = Logger.getLogger(EntytiDAO.class);
    private static final String INSERT_ENTYTI = "insert into entytis (user_id, Company,OKPO) values (?,?,?)";
    private static final String UPDATE_COMPANY_NAME = "update entytis set Company = ? where user_id =?";
    private static final String UPDATE_OKPO = "update entytis set OKPO = ? where user_id =?";
    private static final String SELECT_ENTYTI = "select Company,OKPO from entytis where user_id =?";

    public void insertEntyti(int userId,String company, String okpo){

        try (Connection con= ConnectionPool.getInstance().getConnection(); PreparedStatement stm = con.prepareStatement(INSERT_ENTYTI)){
            stm.setInt(1, userId);
            stm.setString(2,company);
            stm.setString(3,okpo);
            stm.executeUpdate();
        } catch (SQLException e) {
            log.error(e);
            throw new RuntimeException("Sorry your can only edit info");
        }
    }

    public void updateCompany (int id, String company){

        try (Connection con= ConnectionPool.getInstance().getConnection(); PreparedStatement stm = con.prepareStatement(UPDATE_COMPANY_NAME)){
            stm.setString(1,company);
            stm.setInt(2, id);
            stm.executeUpdate();
        } catch (SQLException e) {
            log.error(e);
            throw new RuntimeException("Sorry cannot update Company name");
        }
    }
    public void updateOkpo (int id, String okpo){

        try (Connection con= ConnectionPool.getInstance().getConnection(); PreparedStatement stm = con.prepareStatement(UPDATE_OKPO)){
            stm.setString(1,okpo);
            stm.setInt(2, id);
            stm.executeUpdate();
        } catch (SQLException e) {
            log.error(e);
            throw new RuntimeException("Sorry  cannot update OKPO, \n" +
                    "check for correct input");
        }
    }

    public static EntytiBean userInfo(int id){

        EntytiBean infInd = null;
        try (Connection con= ConnectionPool.getInstance().getConnection(); PreparedStatement stm = con.prepareStatement(SELECT_ENTYTI)){
            con.setAutoCommit(false);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            while (rs.next()){
                String company = rs.getString("Company");
                String okpo = rs.getString("OKPO");

                infInd =new EntytiBean(company,okpo,id);
            }

        } catch (SQLException e) {
            log.error(e);
            throw new RuntimeException("Sorry user information missing");
        }
        return infInd;
    }
}

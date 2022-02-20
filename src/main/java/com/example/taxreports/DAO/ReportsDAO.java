package com.example.taxreports.DAO;

import com.example.taxreports.bean.ReportBean;
import com.example.taxreports.util.ConnectionPool;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ReportsDAO {

   private static final Logger log = Logger.getLogger(ReportsDAO.class);
   private static final String INSERT_REPORT = "Insert into report(status_id,creater,date,XML, description) "
            + " values (?,?,?,?,?) ";
   private static final String SELECT_USER_REPORTS ="select r.id, r.date,r.status_id, r.comments,r.xml,r.description,CONCAT(i.FName,' ',i.LName) as inspec "+
            "from report as r left join inspectors as i " +
            "on i.user_id= r.inspector_id "+
            "where r.creater = ?";
   private static final String DELETE_REPORT = "delete from report where id = ?";
   private static final String SELECT_REPORT_STATUS = "select status_id from report where id = ?";
   private static final String SELECT_REPORT_INSPECTOR = "select inspector_id from report where id = ?";
   private static final String UPDATE_REPORT = "Update report set status_id = ?, date = ?, xml=? where id = ?";
   private static final String SELECT_AVELAIBLE_REPORT ="select r.creater,CONCAT(ind.LName,' ',ind.FName,' ',ind.sName) as user,e.company," +
           " r.id, r.date,r.status_id, r.comments,r.xml,r.description,CONCAT(i.FName,' ',i.LName) as inspec"+
           " from report as r left join inspectors as i "+
           "on i.user_id= r.inspector_id left join individual as ind " +
           "on ind.user_id= r.creater left join entytis as e on e.user_id= r.creater" +
           " where (r.status_id = ? or r.status_id = ? or r.status_id = ? ) and (r.inspector_id is null or r.inspector_id = ?) ";
   private static final String SELECT_ARCHIV_REPORT="select r.creater,CONCAT(ind.LName,' ',ind.FName,' ',ind.sName) as user,e.company," +
            " r.id, r.date,r.status_id, r.comments,r.xml,r.description,CONCAT(i.FName,' ',i.LName) as inspec"+
            " from report as r left join inspectors as i "+
            "on i.user_id= r.inspector_id left join individual as ind " +
            "on ind.user_id= r.creater left join entytis as e on e.user_id= r.creater " +
            "where r.status_id = ? or r.status_id = ?";
   private final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ROOT);
   private Date date;
   private String comments;
   private int status;
   private String filePath;
   private String description;
   private String insp;


    public void writeToDB( String file, int userId, String description) {
        status = ReportBean.STATUS_FILED;
        date= new Date();

        try(Connection con = ConnectionPool.getInstance().getConnection(); PreparedStatement pstm = con.prepareStatement(INSERT_REPORT)) {

            pstm.setInt(1, status);
            pstm.setInt(2,userId);
            pstm.setTimestamp(3, java.sql.Timestamp.valueOf(DATE_FORMAT.format(date)));
            pstm.setString(4, file);
            pstm.setString(5,description);
            pstm.executeUpdate();
        } catch (SQLException e) {
            log.error(e);
            throw new RuntimeException("Sorry, cannot insert file");
        }

    }
    public  List<ReportBean> getUserReport (int userId) {

        List<ReportBean> list = new ArrayList<>();
        int id;

        try(Connection con = ConnectionPool.getInstance().getConnection(); PreparedStatement pstm = con.prepareStatement(SELECT_USER_REPORTS)) {
            con.setAutoCommit(false);
            pstm.setInt(1, userId);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()){
                status =rs.getInt("status_id");
                id =rs.getInt("id");
                date = DATE_FORMAT.parse(rs.getString("date"));
                comments =rs.getString("comments");
                filePath= rs.getString("XML");
                description = rs.getString("description");
                insp = rs.getString("inspec");
                list.add(new ReportBean(id,status,insp,comments,date,filePath,description));

            }
            con.setAutoCommit(true);

        } catch (SQLException | ParseException e) {
            log.error(e);
            throw new RuntimeException("Sorry, cannot load info");
        }
        return list;
    }

    public void deleteRepo(int id, String path) throws IOException {
        Files.delete(Paths.get(path));
            if( getReportStatus(id) == ReportBean.STATUS_FILED) {
                try (Connection con = ConnectionPool.getInstance().getConnection();
                     PreparedStatement stm = con.prepareStatement(DELETE_REPORT)) {
                    stm.setInt(1, id);
                    stm.executeUpdate();
                } catch (SQLException e) {
                    log.error(e);
                    throw new RuntimeException("Sorry, cannot delete report.");

                }
            } else {
                log.warn("cannot delete report");
                throw new RuntimeException("Sorry, cannot delete report check status.");
            }
    }
    public void editReport(int id, String filePath){

        status = ReportBean.STATUS_UPDATE;
        date= new Date();

        try(Connection con = ConnectionPool.getInstance().getConnection(); PreparedStatement pstm = con.prepareStatement(UPDATE_REPORT)) {
            pstm.setInt(1, status);
            pstm.setString(3,filePath);
            pstm.setTimestamp(2, java.sql.Timestamp.valueOf(DATE_FORMAT.format(date)));
            pstm.setInt(4, id);
            pstm.executeUpdate();
        }catch (SQLException e){
            log.error(e);
            throw new RuntimeException("Sorry, cannot load file");
        }

    }

    public List<ReportBean> getAvelaibleReport (int inspetorId) {

        List<ReportBean> list = new ArrayList<>();

        try(Connection con = ConnectionPool.getInstance().getConnection(); PreparedStatement pstm = con.prepareStatement(SELECT_AVELAIBLE_REPORT)) {
            con.setAutoCommit(false);
            pstm.setInt(1, ReportBean.STATUS_FILED);
            pstm.setInt(2, ReportBean.STATUS_IN_PROCESSING);
            pstm.setInt(3, ReportBean.STATUS_UPDATE);
            pstm.setInt(4, inspetorId);

            ResultSet rs = pstm.executeQuery();
            while (rs.next()){

                status = rs.getInt("status_id");
                int id = rs.getInt("id");
                date = DATE_FORMAT.parse(rs.getString("date"));
                comments =rs.getString("comments");
                filePath= rs.getString("XML");
                description = rs.getString("description");
                insp = rs.getString("inspec");
                String creater = rs.getString("creater");
                String createrName;
                if(rs.getString("user") != null){
                    createrName = rs.getString("user");
                } else {
                    createrName = rs.getString("company");
                }
                list.add(new ReportBean(id, status, creater, insp, comments, date, filePath, createrName, description));

            }

            con.setAutoCommit(true);

        } catch (SQLException | ParseException e) {
            log.error(e);
            throw new RuntimeException("Sorry, cannot load available report.");
        }
        return list;
    }

    public List<ReportBean> getArchive () {

        List<ReportBean> list = new ArrayList<>();
        int id;
        String creater;
        String createrName;

        try(Connection con = ConnectionPool.getInstance().getConnection(); PreparedStatement pstm = con.prepareStatement(SELECT_ARCHIV_REPORT)) {
            con.setAutoCommit(false);
            pstm.setInt(1, ReportBean.STATUS_ACCEPTED);
            pstm.setInt(2, ReportBean.STATUS_REJECT);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()){
                status =rs.getInt("status_id");
                id =rs.getInt("id");
                date = DATE_FORMAT.parse(rs.getString("date"));
                comments =rs.getString("comments");
                filePath= rs.getString("XML");
                creater = rs.getString("creater");
                description = rs.getString("description");
                insp = rs.getString("inspec");
                if(rs.getString("user") != null){
                    createrName = rs.getString("user");
                } else {
                    createrName = rs.getString("company");
                }
                list.add(new ReportBean(id, status, creater, insp, comments, date, filePath, createrName , description));
            }

            con.setAutoCommit(true);

        } catch (SQLException | ParseException e) {
            log.error(e);
            throw new RuntimeException("Sorry, cannot load archiv.");
        }
        return list;
    }

    public int getReportStatus(int id){
        int statusId = 0;
        try(Connection con = ConnectionPool.getInstance().getConnection();
            PreparedStatement pstm = con.prepareStatement(SELECT_REPORT_STATUS)) {
            pstm.setInt(1, id);
            ResultSet rs = pstm.executeQuery();
            if(rs.next()){
                statusId = rs.getInt("status_id");
            }

            return statusId;
        }catch (SQLException e){
            log.error(e);
            throw new RuntimeException("Sorry,no such file.");
        }
    }

    public int getInspector(int id){
        int inspectorId = 0;
        try(Connection con = ConnectionPool.getInstance().getConnection();
            PreparedStatement pstm = con.prepareStatement(SELECT_REPORT_INSPECTOR)) {
            pstm.setInt(1, id);
            ResultSet rs = pstm.executeQuery();
            if(rs.next()){
                inspectorId = rs.getInt("inspector_id");
            }
            return inspectorId;
        }catch (SQLException e){
            log.error(e);
            throw new RuntimeException("Sorry, cannot find inspector.");
        }
    }
}

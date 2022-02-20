package com.example.taxreports.DAO;

import com.example.taxreports.bean.RegisterBean;
import com.example.taxreports.bean.UserBean;
import com.example.taxreports.util.ConnectionPool;
import com.example.taxreports.util.SecurityPassword;
import org.apache.log4j.Logger;

import java.sql.*;

public class UserDAO {
   private static final Logger log = Logger.getLogger(UserDAO.class);
   private static final String DELETE_USER = "delete from users where id =? ";
   private static final String UPDATE_PASSWORD = "update users set password = ?, salt = ? where id =?";
   private static final String SELECT_ROLE_FROM_USERS_WHERE_ID = "select role from users where id = ?";
   private static final String SELECT_ID_USER_BY_LOGIN = "select id from users where login = ?";
   private static final String SELECT_SALT_FROM_USERS_WHERE_LOGIN = "select salt from users where login = ?";
   private static final String SELECT_USERS = "select id, login,password, role from users";
   private static final String INSERT_USER = "insert into users(login,password,role,salt) values (?,?,?,?)";


    public UserBean authenticateUser(RegisterBean registerBean)
    {
        String login = registerBean.getLogin(); //Assign user entered values to temporary variables.
        String password = registerBean.getPassword();
        ResultSet resultSet ;
        String roleDB;
        int idDB;
        String loginDB ;
        String passwordDB;

        try(Connection con = ConnectionPool.getInstance().getConnection();Statement statement = con.createStatement() )
        {
            resultSet = statement.executeQuery(SELECT_USERS); //the table name is users and userName,password are columns. Fetching all the records and storing in a resultSet.

            while(resultSet.next()) // Until next row is present otherwise it return false
            {
                loginDB = resultSet.getString("login"); //fetch the values present in database
                passwordDB = resultSet.getString("password");
                roleDB = resultSet.getString("role");
                idDB = resultSet.getInt("id");

                if(login.equals(loginDB) && password.equals(passwordDB)) {
                    UserBean user = new UserBean();
                    user.setRole(roleDB);
                    user.setId(idDB);
                    return user; ////If the user entered values are already present in the database, which means user has already registered so return a SUCCESS message.
                }
            }

            return null; // Return appropriate message in case of failure
        } catch (SQLException e) {
            log.error(e);
            throw new RuntimeException("Sorry, try again");
        }

    }

    public int registerUser(RegisterBean registerBean)
    {
        String role = registerBean.getRole();
        String login = registerBean.getLogin();
        String password = registerBean.getPassword();
        String salt = registerBean.getSalt();
        try(Connection con = ConnectionPool.getInstance().getConnection();
            PreparedStatement preparedStatement = con.prepareStatement(INSERT_USER, Statement.RETURN_GENERATED_KEYS))
        {
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, role);
            preparedStatement.setString(4, salt);
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                return   rs.getInt(1);
            }

        }
        catch(SQLException e) {
            log.error(e);
            throw new RuntimeException("Sorry, cannot register such user");
        }
        return 0;
    }

    public static String getUserRoleByID (int id){
        String role = null;

        try (Connection con = ConnectionPool.getInstance().getConnection();
            PreparedStatement pstm = con.prepareStatement(SELECT_ROLE_FROM_USERS_WHERE_ID)){

            pstm.setInt(1,id);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()){
                role = rs.getString("role");
            }

        } catch (SQLException e) {
            log.error(e);
            throw new RuntimeException("Sorry, cannot find user role");
        }
        return role;
    }

    public void deleteUser(int id){
        try (Connection con= ConnectionPool.getInstance().getConnection();
             PreparedStatement stm = con.prepareStatement(DELETE_USER)){

            stm.setInt(1,id);
            stm.executeUpdate();

        } catch (SQLException e) {
            log.error(e);
            throw new RuntimeException("Sorry, cannot find such user");
        }

    }
    public static void updatePassword (int id, String password){
        SecurityPassword securityPassword = new SecurityPassword();
        String salt = securityPassword.getSalt();
        String newPassword = securityPassword.getHashPassword(password + salt);
        try (Connection con= ConnectionPool.getInstance().getConnection();
             PreparedStatement stm = con.prepareStatement(UPDATE_PASSWORD)){
            stm.setString(1,newPassword);
            stm.setString(2, salt);
            stm.setInt(3, id);
            stm.executeUpdate();
        } catch (SQLException e) {
            log.error(e);
            throw new RuntimeException("Sorry cannot update password");
        }
    }

    public  String getSalByLogin (String login){
        String salt = null;
        try (Connection con= ConnectionPool.getInstance().getConnection();
             PreparedStatement stm = con.prepareStatement(SELECT_SALT_FROM_USERS_WHERE_LOGIN)){
            stm.setString(1,login);

            ResultSet rs = stm.executeQuery();
            if (rs.next()){
                salt = rs.getString("salt");
            }
        } catch (SQLException e) {
            log.error(e);
            throw new RuntimeException("Sorry cannot find information");
        }
        return salt;
    }

    public  int getIdByLogin (String login){
       int id = 0;
        try (Connection con= ConnectionPool.getInstance().getConnection();
             PreparedStatement stm = con.prepareStatement(SELECT_ID_USER_BY_LOGIN)){
            stm.setString(1,login);

            ResultSet rs = stm.executeQuery();
            if (rs.next()){
                id = rs.getInt("id");
            }

        } catch (SQLException e) {
            log.error(e);
            throw new RuntimeException("Sorry user missing");
        }
        return id;
    }
}

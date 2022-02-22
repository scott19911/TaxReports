package com.example.taxreports.controller.Authorization;


import com.example.taxreports.DAO.UserDAO;
import com.example.taxreports.bean.RegisterBean;
import com.example.taxreports.bean.UserBean;
import com.example.taxreports.util.SecurityPassword;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class RegisterServlet extends HttpServlet {
    private static final Logger log = Logger.getLogger(RegisterServlet.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String role = request.getParameter("role");
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        HttpSession session = request.getSession();
        RegisterBean registerBean = new RegisterBean();
        UserDAO userDAO = new UserDAO();
        UserBean userBean = new UserBean();
        SecurityPassword securityPassword = new SecurityPassword();
        if (userDAO.getIdByLogin(login) > 0){
            request.setAttribute("errMessage", "Oops, login is already taken");
            request.getRequestDispatcher("/Register.jsp").forward(request, response);
        }
        String salt=securityPassword.getSalt();
        registerBean.setSalt(salt);
        registerBean.setLogin(login);
        registerBean.setPassword(securityPassword.getHashPassword(password + salt));

        registerBean.setRole(role);

        // We are going to insert user data in to the database
        //and if successful, we will get id
        int userRegistered = userDAO.registerUser(registerBean);
        //write the necessary data about the user to the session
        userBean.setId(userRegistered);
        userBean.setRole(role);
        session.setAttribute("user",userBean);

        //depending on the role, go to the start pages
        if(userRegistered > 0) {
            if(role.equals("indi")) {
                response.sendRedirect("/insertInd?action=insert");
                log.info("Register user role indi");
            }
            if(role.equals("entyti")) {
                log.info("Register user role entyti");
                response.sendRedirect("/editEntyti?action=insert");
            }
        } else { //On Failure, display a meaningful message to the User.
            log.warn("Cannot register user");
            request.setAttribute("errMessage", "Try again");
            request.getRequestDispatcher("/ErrorPage.jsp").forward(request, response);
        }
    }
}
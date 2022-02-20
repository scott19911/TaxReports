package com.example.taxreports.controller.Entytis;

import com.example.taxreports.DAO.EntytiDAO;
import com.example.taxreports.DAO.IndividualDAO;
import com.example.taxreports.bean.EntytiBean;
import com.example.taxreports.bean.IndividualBean;
import com.example.taxreports.bean.UserBean;

import javax.management.relation.Role;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/accountEntyti")
public class UserInfEntyti extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        UserBean user = (UserBean) session.getAttribute("user");
        int userId;

        if(req.getParameter("id") != null){
            userId = Integer.parseInt(req.getParameter("id"));
        } else {
            userId = user.getId();
        }
        EntytiBean infEntyti = EntytiDAO.userInfo(userId);
        req.setAttribute("infEntyti", infEntyti);
        req.getRequestDispatcher("/entytiAccount.jsp").forward(req, resp);
    }


}

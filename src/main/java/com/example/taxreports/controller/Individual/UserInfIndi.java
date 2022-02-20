package com.example.taxreports.controller.Individual;


import com.example.taxreports.DAO.IndividualDAO;
import com.example.taxreports.bean.IndividualBean;
import com.example.taxreports.bean.UserBean;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/accountInd")
public class UserInfIndi extends HttpServlet {

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
       IndividualBean infInd = IndividualDAO.userInfo(userId);
       req.setAttribute("infInd", infInd);
       req.getRequestDispatcher("/indAccount.jsp").forward(req, resp);
    }
}

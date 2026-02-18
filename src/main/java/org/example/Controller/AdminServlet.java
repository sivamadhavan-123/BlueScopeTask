package org.example.Controller;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.DAO.UserDao;

import org.example.DTO.User;
import org.example.Service.ServiceLayer;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.List;


@WebServlet("/admin/alluser")
public class AdminServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws  IOException {

        HttpSession session = req.getSession(false);
        PrintWriter out = resp.getWriter();
        int totalRows= UserDao.totalRows();


        int pageSize = totalRows;
        int pageNumber = 1;

        try {
            String pagesize = req.getParameter("pagesize");
            if (pagesize != null) {
                pageSize = Integer.parseInt(pagesize);
            }
            String page = req.getParameter("page");
            if (page != null) {
                pageNumber = Integer.parseInt(page);
            }
        } catch (NumberFormatException e) {
            pageSize = totalRows;
            pageNumber = 1;
        }


        int totalPage = (int) Math.ceil((double) totalRows / pageSize);


        if (pageNumber > totalPage) {
            pageNumber = totalPage;
        }


        if (session != null && session.getAttribute("role").equals("ADMIN")) {

            List<User> user = ServiceLayer.pagenation(pageSize,pageNumber);
            resp.setContentType("application/json");


            out.println("PAGE :"+pageNumber+"/"+totalPage);
            out.println("Users List");

            out.println("[");
            for (User u : user) {
                out.print("{");
                out.println("\"Id\": " + u.getId() + ",");
                out.println("\"Name\": \"" + u.getName() + "\",");
                out.println("\"Age\": " + u.getAge() + ",");
                out.println("\"Username\": \"" + u.getUsername() + "\",");
                out.println("\"Password\": \"" + u.getPassword() + "\",");
                out.println("\"Mobile Number\": \"" + u.getMobile() + "\"");
                out.print("}");

            }
            out.println("]");

        } else if (session != null && session.getAttribute("role").equals("USER")) {
            out.println("you are not admin");

        } else {
            out.println("You are not logged in ");
        }

    }
}

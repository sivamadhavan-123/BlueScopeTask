package org.example.Controller;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.DAO.UserDao;
import org.example.DTO.User;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.io.PrintWriter;


@WebServlet(urlPatterns ={"/user/update","/user/delete"})
public class UserServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String path = req.getServletPath();

        if ("/user/update".equals(path)) {

            HttpSession session = req.getSession(false);
            PrintWriter out = resp.getWriter();
            if (session != null && "USER".equals(session.getAttribute("role"))) {


                String sessionUsername = (String) session.getAttribute("username");

                String name = req.getParameter("name");
                String mobile = req.getParameter("mobile");
                String password = req.getParameter("password");
                String username = req.getParameter("username");
                int age = Integer.parseInt(req.getParameter("age"));

                String hashPassword = BCrypt.hashpw(password, BCrypt.gensalt(12));


                User user = new User(name, age, username, hashPassword, mobile);
                boolean result = UserDao.updateUserDetail(sessionUsername, user);

                if (result) {
                    session.setAttribute("username", username);
                    out.println("Update Success");
                } else {

                    out.println("Update Failed");
                }


            } else {
                resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String path = req.getServletPath();
        if ("/user/delete".equals(path)) {

            HttpSession session = req.getSession(false);
            PrintWriter out = resp.getWriter();

            if (session != null && "USER".equals(session.getAttribute("role"))) {
                String sessionUsername = (String) session.getAttribute("username");
                boolean result = UserDao.deleteUser(sessionUsername);
                if(result){
                    out.println("Delete Success");
                    session.invalidate();
                }else{
                    out.println("Delete Failed");
                }

            }else {
                out.println("You are not logged in");
                resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }
        }


    }
}

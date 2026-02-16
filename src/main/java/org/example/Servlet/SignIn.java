package org.example.Servlet;

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


@WebServlet("/signin")
public class SignIn extends HttpServlet {


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
      String username=  req.getParameter("username");
        String password=req.getParameter("password");

        UserDao userDao = new UserDao();
        User user = UserDao.login(username);

        PrintWriter out = resp.getWriter();

        if(user!=null){
            if(BCrypt.checkpw(password,user.getPassword())){

                HttpSession session = req.getSession();
                session.setAttribute("username",user.getUsername());
                session.setAttribute("role",user.getRole());
                session.setAttribute("name",user.getName());

                out.println("sign in success");
                out.println("welcome "+user.getName());
            }else {
                out.print("Invalid password");
            }
        }else {
            out.println("User not found");
        }


    }
}

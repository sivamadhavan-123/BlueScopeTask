package org.example.Servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.DAO.UserDao;
import org.example.DTO.User;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/signup")
public class SignUp extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        PrintWriter out = resp.getWriter();
String name=req.getParameter("name");
int age=Integer.parseInt(req.getParameter("age"));
String username=req.getParameter("username");
String password=req.getParameter("password");
String mobile=req.getParameter("mobile");

     String hashPassword = BCrypt.hashpw(password,BCrypt.gensalt(12));


User user=new User(name,age,username,hashPassword,mobile);
user.setRole("USER");
     boolean ok = UserDao.insert(user);

out.println(ok?"signup success":"signup failed");


    }
}

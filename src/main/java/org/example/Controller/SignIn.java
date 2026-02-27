package org.example.Controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.DTO.LoginDto;
import org.example.Service.ServiceLayer;
import java.io.IOException;
import java.io.PrintWriter;


@WebServlet("/signin")
public class SignIn extends HttpServlet {


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");


        LoginDto user = ServiceLayer.login(username,password);

        PrintWriter out = resp.getWriter();


        if (user != null) {
                HttpSession session = req.getSession(true);
                session.setAttribute("username", user.getUsername());
                session.setAttribute("role", user.getRole());
                session.setAttribute("name", user.getName());
                out.println("sign in success");
                out.println("welcome " + user.getRole()+" - "+ user.getName());
                resp.setStatus(HttpServletResponse.SC_OK);
        } else {
            out.println("Invalid credentials");
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }


    }
}

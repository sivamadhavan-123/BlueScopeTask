package org.example.Controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.DTO.LoginDto;
import org.example.Service.ServiceLayer;
import java.io.IOException;
import java.io.PrintWriter;


@WebServlet("/signin")
public class SignIn extends HttpServlet {

private static final Logger logger= LogManager.getLogger(SignIn.class.getName());
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.trace("Entering signin doPost");

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

//                if(user.getRole().equals("ADMIN")){
//                    user.getEmail();
//                }


                logger.info("Info : signin success");
        } else {
            out.println("Invalid credentials");
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            logger.warn("Invalid credentials");
        }

logger.trace("Exiting signin doPost");
    }
}

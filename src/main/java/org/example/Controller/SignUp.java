package org.example.Controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.DAO.UserDao;
import org.example.DTO.User;
import org.mindrot.jbcrypt.BCrypt;
import java.io.IOException;
import java.io.PrintWriter;


@WebServlet("/signup")
public class SignUp extends HttpServlet {
    private static final Logger logger= LogManager.getLogger(SignUp.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        logger.trace("Trace : Entering SignUp.doPost()");

        PrintWriter out = resp.getWriter();
        String name = req.getParameter("name");
        int age = Integer.parseInt(req.getParameter("age"));
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String mobile = req.getParameter("mobile");
        String email = req.getParameter("email");

        String hashPassword = BCrypt.hashpw(password, BCrypt.gensalt(12));


        User user = new User(name, age, username, hashPassword, mobile,email);
        logger.info("Info :send user value in userDto");

        boolean ok = UserDao.insert(user);
        logger.info("Info :Insert user value in userDao");
if(ok){
    out.println("signup success");
   resp.setStatus(HttpServletResponse.SC_CREATED);
   logger.info("Info : signup success");
}else{
    out.println("signup failed");
    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    logger.info("Info : signup failed");
}

        logger.trace("Trace : Exiting SignUp.doPost()");
    }
}

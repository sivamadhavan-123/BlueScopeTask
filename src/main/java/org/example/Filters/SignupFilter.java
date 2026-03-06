package org.example.Filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.DAO.UserDao;

import java.io.IOException;
import java.io.PrintWriter;

@WebFilter(urlPatterns = {"/signup","/user"})
public class SignupFilter implements Filter {

    private static final Logger logger= LogManager.getLogger(SignupFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        logger.trace("Entering SignupFilter.doFilter");

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        final String regex_name = "^[A-Za-z]{2,50}$";
        final String regex_username = "^[A-Za-z0-9]{3,50}$";
        final String regex_password = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@!$*%?&]).{8,}$";
        final String regex_mobile = "^[6-9][0-9]{9}$";
        final String regex_email ="^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";

        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String mobile = req.getParameter("mobile");
        int age = Integer.parseInt(req.getParameter("age"));
        String name = req.getParameter("name");
        String email = req.getParameter("email");

        PrintWriter out = resp.getWriter();

        String path=req.getServletPath();

        if (name == null || !name.matches(regex_name)) {
            out.println("Invalid name");
            logger.warn("Invalid name");
            return;
        }

        if (email == null || !email.matches(regex_email)) {
            out.println("Invalid email");
            logger.warn("Invalid email");
            return;
        }


        if (mobile == null || !mobile.matches(regex_mobile)) {
            out.println("Invalid mobile number");
            logger.warn("Invalid mobile number");
            return;
        }

        if (username == null || !username.matches(regex_username)) {
            out.println("Invalid username");
            logger.warn("Invalid username");
            return;
        }
        if (password == null || !password.matches(regex_password)) {
            out.println("Invalid password");
            logger.warn("Invalid password");
            return;
        }

        if (age < 1 || age > 100) {
            out.println("Invalid age");
            logger.warn("Invalid age");
            return;
        }


        if (path.equals("/signup")) {

            String check= UserDao.existingUserCheck(username,mobile);
            if (check != null) {
                out.println(check);
                logger.info("User already exists");
            }
        }else{
            String check= UserDao.existingUser(username,mobile);
            if (check != null) {
                out.println(check);
                logger.info("Checks User already exists");
            }
        }



        chain.doFilter(request, response);
        logger.trace("Exiting SignupFilter.doFilter");


    }


}

package org.example.Filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.DAO.UserDao;

import java.io.IOException;
import java.io.PrintWriter;

@WebFilter(urlPatterns = {"/signup","/user"})
public class SignupFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        final String regex_name = "^[A-Za-z]{2,50}$";
        final String regex_username = "^[A-Za-z0-9]{3,50}$";
        final String regex_password = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@!$*%?&]).{8,}$";
        final String regex_mobile = "^[6-9][0-9]{9}$";

        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String mobile = req.getParameter("mobile");
        int age = Integer.parseInt(req.getParameter("age"));
        String name = req.getParameter("name");

        PrintWriter out = resp.getWriter();

        String path=req.getServletPath();

        if (name == null || !name.matches(regex_name)) {
            out.println("Invalid name");
            return;
        }

        if (mobile == null || !mobile.matches(regex_mobile)) {
            out.println("Invalid mobile number");
            return;
        }

        if (username == null || !username.matches(regex_username)) {
            out.println("Invalid username");
            return;
        }
        if (password == null || !password.matches(regex_password)) {
            out.println("Invalid password");
            return;
        }

        if (age < 1 || age > 100) {
            out.println("Invalid age");
            return;
        }


        if (path.equals("/signup")) {

            String check= UserDao.existingUserCheck(username,mobile);
            if (check != null) {
                out.println(check);
            }
        }else{
            String check= UserDao.existingUser(username,mobile);
            if (check != null) {
                out.println(check);
            }
        }



        chain.doFilter(request, response);


    }


}

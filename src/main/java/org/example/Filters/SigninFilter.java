package org.example.Filters;


import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;

@WebFilter("/signin")
public class SigninFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        final String regex_username = "^[A-Za-z0-9]{3,50}$";
        final String regex_password = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@!$*%?&]).{8,}$";


        HttpSession session = req.getSession(false);
        PrintWriter out = resp.getWriter();
        if (session != null) {
            out.println("logout previous signin");
            return;
        } else {

            if (username == null || !username.matches(regex_username)) {
                out.println("Invalid username");
                return;
            }
            if (password == null || !password.matches(regex_password)) {
                out.println("Invalid password");
                return;
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);


    }


}
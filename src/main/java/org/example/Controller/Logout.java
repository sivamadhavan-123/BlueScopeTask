package org.example.Controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;


@WebServlet("/logout")
public class Logout extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        PrintWriter out = response.getWriter();

        HttpSession session = request.getSession(false);


        if(session != null) {

            session.invalidate();

            response.setStatus(HttpServletResponse.SC_OK);
            out.println("You have been logged out successfully");

        }else {
          
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "You are not logged in ");

        }




    }


}

package org.example.Controller;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.DAO.UserDao;
import org.example.DTO.User;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.io.PrintWriter;


@WebServlet(urlPatterns ={"/user/update","/user/delete"})
public class UserServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(UserServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        logger.trace("Entering userServlet doPost");

        String path = req.getServletPath();

        if ("/user/update".equals(path)) {
            logger.info("User update request received");

            HttpSession session = req.getSession(false);
            PrintWriter out = resp.getWriter();
            if (session != null && "USER".equals(session.getAttribute("role"))) {
                logger.info("Checks session is not null and role is USER");


                String sessionUsername = (String) session.getAttribute("username");

                String name = req.getParameter("name");
                String mobile = req.getParameter("mobile");
                String password = req.getParameter("password");
                String username = req.getParameter("username");
                int age = Integer.parseInt(req.getParameter("age"));
                String email = req.getParameter("email");

                String hashPassword = BCrypt.hashpw(password, BCrypt.gensalt(12));

                User user = new User(name, age, username, hashPassword, mobile,email);
                boolean result = UserDao.updateUserDetail(sessionUsername, user);

                if (result) {
                    session.setAttribute("username", username);
                    out.println("Update Success");
                    logger.info("User updated successfully");
                } else {

                    out.println("Update Failed");
                    logger.info("User not updated successfully");
                }


            } else {
                resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                logger.warn("User not logged in");
            }
        }
        logger.trace("Exiting userServlet doPost");
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        logger.trace("Entering userServlet doDelete");
        String path = req.getServletPath();
        if ("/user/delete".equals(path)) {
            logger.info("User delete request received");

            HttpSession session = req.getSession(false);
            PrintWriter out = resp.getWriter();

            if (session != null && "USER".equals(session.getAttribute("role"))) {
                logger.info("Checks session is not null and role is USER");
                String sessionUsername = (String) session.getAttribute("username");
                boolean result = UserDao.deleteUser(sessionUsername);
                if(result){
                    out.println("Delete Success");
                    logger.info("User delete successfully");
                    session.invalidate();
                }else{
                    out.println("Delete Failed");
                    logger.info("User not deleted successfully");
                }

            }else {
                out.println("You are not logged in");
                resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                logger.warn("User not logged in");
            }
        }


    }
}

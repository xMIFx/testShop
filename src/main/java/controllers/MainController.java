package controllers;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Vlad on 07.02.2015.
 */

@WebServlet("/")
public class MainController extends HttpServlet {
    private static final String COOKIE_NAME = "counter";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cookie cookieFromClient = null;
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(COOKIE_NAME)) {
                    cookieFromClient = cookie;
                    break;
                }
            }
        }
        if (cookieFromClient == null) {
            resp.addCookie(new Cookie(COOKIE_NAME, "" + 1));
            req.setAttribute("countVisit", 1);
        } else {
            int visitCount = Integer.valueOf(cookieFromClient.getValue());
            resp.addCookie(new Cookie(COOKIE_NAME, "" + ++visitCount));
            req.setAttribute("countVisit", visitCount);
        }

        req.setAttribute("name", "MIF");
        req.getRequestDispatcher("pages/index.jsp").forward(req, resp);
    }
}

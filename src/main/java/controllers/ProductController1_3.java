package controllers;

import Entity.Product;
import SQLpack.TransactionManager;
import dao.ProductDao;
import inject.DependencyInjectionServlet;
import inject.Inject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Driver;
import java.sql.DriverManager;
import java.util.Enumeration;

/**
 * Created by Vlad on 07.02.2015.
 */
//@WebServlet("/productPage.do")
public class ProductController1_3 extends DependencyInjectionServlet {
    public static final String PARAM_ID = "id";
    public static final String ATTRIBUTE_MODEL_TO_VIEW = "product";
    public static final String PAGE_OK = "pages/productPage.jsp";
    public static final String PAGE_Error = "pages/errorPage.jsp";

    @Inject("txManager")
    private TransactionManager txManager;

    @Inject("productDao")
    private ProductDao productDao;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idStr = req.getParameter(PARAM_ID);
        if (idStr == null) {
            resp.sendRedirect(PAGE_Error);
        } else {
            try {
                Integer id = Integer.valueOf(idStr);

                Enumeration<Driver> enumeration = DriverManager.getDrivers();
                System.out.println(enumeration.hasMoreElements());
                while (enumeration.hasMoreElements())
                {Driver driver1 = enumeration.nextElement();
                    System.out.println("before"+driver1);}

               Product model = txManager.doInTransaction(() -> productDao.selectById(id));
                req.setAttribute(ATTRIBUTE_MODEL_TO_VIEW, model);
                req.getRequestDispatcher(PAGE_OK).forward(req, resp);

            } catch (Exception e) {
                resp.sendRedirect(PAGE_Error);

            }
        }

    }
}

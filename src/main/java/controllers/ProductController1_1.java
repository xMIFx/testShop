package controllers;

import Entity.Product;
import dao.ProductDao;
import dao.exception.DaoSystemException;
import dao.exception.NoSuchEntityException;
import dao.impl.ProductDaoGMO;
import inject.DependencyInjectionServlet;
import inject.Inject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Map;

/**
 * Created by Vlad on 07.02.2015.
 */
//@WebServlet("/productPage.do")
public class ProductController1_1 extends DependencyInjectionServlet {
    public static final String PARAM_ID = "id";
    public static final String ATTRIBUTE_MODEL_TO_VIEW = "product";
    public static final String PAGE_OK = "pages/productPage.jsp";
    public static final String PAGE_Error = "pages/errorPage.jsp";
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
                Product model = null;
                try {
                    model = productDao.selectById(id);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                req.setAttribute(ATTRIBUTE_MODEL_TO_VIEW, model);
                req.getRequestDispatcher(PAGE_OK).forward(req, resp);

            } catch (NoSuchEntityException | DaoSystemException e) {
                resp.sendRedirect(PAGE_Error);

            }
        }

    }
}

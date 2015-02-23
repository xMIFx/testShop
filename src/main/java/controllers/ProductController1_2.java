package controllers;

import Entity.Product;
import SQLpack.TransactionManager;
import SQLpack.TransactionManager1_0;
import SQLpack.UnitOfWork;
import dao.ProductDao;
import dao.exception.DaoException;
import dao.exception.DaoSystemException;
import dao.exception.NoSuchEntityException;
import inject.DependencyInjectionServlet;
import inject.Inject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.concurrent.Callable;

/**
 * Created by Vlad on 07.02.2015.
 */
@WebServlet("/productPage.do")
public class ProductController1_2 extends DependencyInjectionServlet {
    public static final String PARAM_ID = "id";
    public static final String ATTRIBUTE_MODEL_TO_VIEW = "product";
    public static final String PAGE_OK = "pages/productPage.jsp";
    public static final String PAGE_Error = "pages/errorPage.jsp";

    @Inject("txManager")
    private TransactionManager1_0 txManager;

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
                Product model = txManager.doInTransaction(new UnitOfWork<Product, DaoException>() {
                    @Override
                    public Product doInTx() throws DaoException, SQLException {
                        return productDao.selectById(id);
                    }
                });
                req.setAttribute(ATTRIBUTE_MODEL_TO_VIEW, model);
                req.getRequestDispatcher(PAGE_OK).forward(req, resp);

            } catch (Exception e) {
                resp.sendRedirect(PAGE_Error);

            }
        }

    }
}

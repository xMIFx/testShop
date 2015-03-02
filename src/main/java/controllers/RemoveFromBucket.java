package controllers;

import Entity.Product;
import SQLpack.TransactionManager;
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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import static Entity.SessionAttributes.*;

/**
 * Created by Vlad on 08.02.2015.
 */
@WebServlet("/RemoveFromBucket.do")
public class RemoveFromBucket extends DependencyInjectionServlet {
    public static final String PARAM_ID = "id";
    public static final String PAGE_ERROR = "allProductsPage.do";
    //private ProductDao productDao = ProductDaoGMO.getProductDao();
    @Inject("txManager")
    TransactionManager txManager;

    @Inject("productDao")
    private ProductDao productDao;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idStr = req.getParameter(PARAM_ID);
        try {
            int id = Integer.valueOf(idStr);
           /* String pageFrom = req
                    .getHeader("referer");
                    .substring(
                            req.getHeader("referer").lastIndexOf("/"));*/
            //Product product = productDao.selectById(id);
            Product product = txManager.doInTransaction(()->productDao.selectById(id));
            HttpSession session = req.getSession();
            Map<Product, Integer> oldBucket = (Map<Product, Integer>) session.getAttribute(PRODUCTS_IN_BUCKET);
            if (oldBucket == null || !oldBucket.containsKey(product)) {
                resp.sendRedirect(req.getHeader("referer"));
                return;
            }
            Map<Product, Integer> newBucket = new LinkedHashMap<>(oldBucket);
            if ((newBucket.get(product) - 1) == 0) {
                newBucket.remove(product);
            } else {
                newBucket.put(product, newBucket.get(product) - 1);
            }
            session.setAttribute(PRODUCTS_IN_BUCKET, Collections.unmodifiableMap(newBucket));
            resp.sendRedirect(req.getHeader("referer"));
            return;
        } catch (NoSuchEntityException | DaoSystemException ignore) {

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        resp.sendRedirect(PAGE_ERROR);
    }
}

package controllers;

import Entity.Product;
import Entity.SessionAttributes;
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
import java.util.*;


import static Entity.SessionAttributes.*;


/**
 * Created by Vlad on 08.02.2015.
 */
@WebServlet("/AddToBucket.do")
public class AddToBucketController extends DependencyInjectionServlet {
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
        if (idStr != null) {
            try {

                int id = Integer.valueOf(idStr);

               // Product product = productDao.selectById(id);
                Product product = txManager.doInTransaction(()->productDao.selectById(id));
                HttpSession session = req.getSession(true);
                Map<Product, Integer> oldBucket = (Map<Product, Integer>) session.getAttribute(PRODUCTS_IN_BUCKET);
                if (oldBucket == null) {
                    session.setAttribute(PRODUCTS_IN_BUCKET, Collections.singletonMap(product, 1));
                } else {
                    Map<Product, Integer> newBucket = new LinkedHashMap<>(oldBucket);
                    if (!oldBucket.containsKey(product)) {
                        newBucket.put(product, 1);
                    } else {
                        newBucket.put(product, (oldBucket.get(product) + 1));
                    }
                    session.setAttribute(PRODUCTS_IN_BUCKET, Collections.unmodifiableMap(newBucket));
                }


                /*String pageFrom = req
                        .getHeader("referer");
                                                .substring(
                                req.getHeader("referer").lastIndexOf("/"));*/


                resp.sendRedirect(req.getHeader("referer"));
                return;
            } catch (NoSuchEntityException | DaoSystemException ignore) {
                //NOP
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            resp.sendRedirect(PAGE_ERROR);
        }
    }
}

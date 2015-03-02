package controllers;

import Entity.Product;
import SQLpack.TransactionManager;
import SQLpack.TransactionManager1_0;
import dao.ProductDao;
import dao.exception.DaoSystemException;
import dao.exception.NoSuchEntityException;
import inject.DependencyInjectionServlet;
import inject.Inject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Vlad on 07.02.2015.
 */
@WebServlet("/allProductsPage.do")
public class AllProductController extends DependencyInjectionServlet {
    public static final String SET_ATTRIBUTE_TO_VIEW = "productList";
    public static final String PAGE_OK = "pages/allProductsPage.jsp";
    public static final String PAGE_Error = "pages/errorPage.jsp";
    //private ProductDao productDao = ProductDaoGMO.getProductDao();
   @Inject("txManager")
   private TransactionManager1_0 txManager;

    @Inject("productDao")
    private ProductDao productDao;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        AtomicInteger visitCount = (AtomicInteger) session.getAttribute("visitCount");
        if (visitCount == null) {
            visitCount = new AtomicInteger(1);
            session.setAttribute("visitCount", visitCount);
        } else {
            visitCount.getAndIncrement();
        }
        req.setAttribute("visitCount", visitCount);

        try {

            //List<Product> listProduct = productDao.selectAll();
            List<Product> listProduct = txManager.doInTransaction(()->productDao.selectAll());
            req.setAttribute(SET_ATTRIBUTE_TO_VIEW, listProduct);
            req.getRequestDispatcher(PAGE_OK).forward(req, resp);

        } catch (DaoSystemException ignore) {
            resp.sendRedirect(PAGE_Error);
            return;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

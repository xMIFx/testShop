package dao.impl;

import Entity.Product;
import dao.ProductDao;
import dao.exception.DaoSystemException;
import dao.exception.NoSuchEntityException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by Vlad on 07.02.2015.
 */
public class ProductDaoGMO implements ProductDao {
    private final Map<Integer, Product> memory = new ConcurrentHashMap<Integer, Product>();
    private static ProductDaoGMO productDaoGMO;
    public static int ID;
    private ProductDaoGMO() {
        //if (productDaoGMO != null) return;
        this.memory.put(1, new Product(1, "Bread"));
        this.memory.put(2, new Product(2, "Sugar"));
        this.memory.put(3, new Product(3, "Paper"));
        productDaoGMO = this;
        ID++;
        System.out.println(ID);
    }

    public static ProductDaoGMO getProductDao() {
        if (productDaoGMO == null) {
            new ProductDaoGMO();
        }
        return productDaoGMO;
    }

    @Override
    public String toString() {
        String result = "[";
        for (Map.Entry<Integer, Product> pair : memory.entrySet()) {
            result = result + " id = " + pair.getKey() + "; value = " + pair.getValue();
        }
        result = result + " ]";
        return result;
    }

    @Override
    public Product selectById(int id) throws DaoSystemException, NoSuchEntityException {
        if (memory.containsKey(id)) return memory.get(id);
        else throw new NoSuchEntityException("No product for id = " + id);
    }

    @Override
    public List<Product> selectAll() throws DaoSystemException {
        return new ArrayList<Product>(memory.values());
    }
}

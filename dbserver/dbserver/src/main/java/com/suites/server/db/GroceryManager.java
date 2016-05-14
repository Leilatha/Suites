package com.suites.server.db;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.io.UnsupportedEncodingException;

import com.google.common.base.Optional;

import com.suites.server.core.User;
import com.suites.server.core.Grocery;

import java.util.List;

public class GroceryManager {

    private final SuitesDAO dao;
    
    public GroceryManager(SuitesDAO dao) {
        this.dao = dao;
    }
    
    public List<Grocery> getSuiteGroceries(int suiteId) {
        return dao.getSuiteGroceries(suiteId);
    }
    
    public void addGrocery(int suiteId,
                           String name,
                           double price, 
                           int quantity) {
        dao.addGrocery(suiteId, name, price, quantity);
    }
    
    public boolean editGrocery(int id, 
                               User user, 
                               String name,
                               double price, 
                               int quantity) {
        return dao.editGrocery(id, user.getId(), name, price, quantity) > 0;
    }

    public boolean deleteGrocery(int id, User user) {
        return dao.deleteGrocery(id, user.getId()) > 0;
    }

}

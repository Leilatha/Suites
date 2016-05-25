package com.suites.server.db;

import com.suites.server.core.User;
import com.suites.server.core.PSA;
import com.suites.server.api.PSAView;

import java.util.List;

import java.sql.Timestamp;

public class PSAManager {

    private final SuitesDAO dao;
    
    public PSAManager(SuitesDAO dao) {
        this.dao = dao;
    }
    
    public List<PSAView> getSuitePSAs(int suiteId) {
        return dao.getSuitePSAs(suiteId);
    }
    
    public void addPSA(int suiteId,
                       User user,
                       String title, 
                       String description) {
        Timestamp now = new Timestamp(new java.util.Date().getTime());
        dao.addPSA(suiteId, user.getId(), title, description, now);
    }
    
    public boolean editPSA(int id, 
                           User user, 
                           String title,
                           String description) {
        return dao.editPSA(id, user.getId(), title, description) > 0;
    }

    public boolean deletePSA(int id, User user) {
        return dao.deletePSA(id, user.getId()) > 0;
    }
}

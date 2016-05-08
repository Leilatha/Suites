package com.suites.server.db;

import com.suites.server.core.User;
import com.suites.server.core.Suite;

import java.util.List;

public class SuiteManager {

    private final SuitesDAO dao;

    public SuiteManager(SuitesDAO dao) {
        this.dao = dao;
    }

    // A user makes a suite and adds themselves immediately
    public void makeSuite(User user, String suiteName) {
        int suiteid = dao.addSuite(suiteName);
        dao.addUserToSuite(user.getId(), suiteid);
    }

    public List<Suite> getUserSuites(User user) {
        return dao.getUserSuites(user.getId());
    }
}

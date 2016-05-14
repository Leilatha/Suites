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

    public boolean isUserInSuite(User user, int suiteId) {
        return dao.isUserInSuite(user.getId(), suiteId);
    }

    public void inviteUser(String email, int suiteId) {
        dao.inviteUser(email, suiteId);
    }

    public List<Suite> getUserInvites(User user) {
        return dao.getUserInvites(user.getEmail());
    }

    public boolean isUserInvited(User user, int suiteId) {
        return dao.isUserInvited(suiteId, user.getEmail());
    }

    public void addUserToSuite(User user, int suiteId) {
        dao.addUserToSuite(user.getId(), suiteId);
    }

    public List<User> getSuiteUsers(int suiteId) {
        return dao.getSuiteUsers(suiteId);
    }
}

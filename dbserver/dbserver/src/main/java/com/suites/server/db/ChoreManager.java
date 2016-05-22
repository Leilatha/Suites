package com.suites.server.db;

import com.suites.server.core.User;
import com.suites.server.core.Suite;
import com.suites.server.core.Chore;

import java.util.List;
import java.util.Iterator;

public class ChoreManager {

    private class NatIterator implements Iterator<Integer> {
        private int current;

        public NatIterator() { current = -1; }
        
        public boolean hasNext() { return true; }
        public Integer next() {
            current = current + 1;
            return current;
        }
    }

    private final SuitesDAO dao;

    public ChoreManager(SuitesDAO dao) {
        this.dao = dao;
    }

    public List<Chore> getSuiteChores(int suiteId) {
        return dao.getSuiteChores(suiteId);
    }

    public List<Chore> getSuiteUserChores(int suiteId, User user) {
        return dao.getSuiteUserChores(suiteId, user.getId());
    }

    public List<User> getChoreAssignees(int choreId, User user) {
        return dao.getChoreAssignees(choreId);
    }

    public void addChore(int suiteId,
                         String name,
                         String description,
                         List<Integer> assignments) {
        int choreId = dao.addChore(suiteId, name, description);
        dao.assignChore(assignments.iterator(), choreId, new NatIterator());
    }

    public boolean editChore(int id,
                             User user,
                             String name,
                             String description,
                             List<Integer> assignments) {
        if (dao.editChore(id, user.getId(), name, description) == 0) {
            return false;
        } else {
            dao.deleteChoreAssignments(id);
            dao.assignChore(assignments.iterator(), id, new NatIterator());
            dao.fixChore(id);
            return true;
        }
    }

    public boolean deleteChore(int id, User user) {
        dao.deleteChoreAssignments(id);
        return dao.deleteChore(id, user.getId()) > 0;
    }

    public boolean advanceChore(int id, User user) {
        return dao.advanceChore(id, user.getId()) > 0;
    }
}

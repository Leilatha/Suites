package com.suites.server.db;

import com.suites.server.core.User;
import com.suites.server.core.Suite;
import com.suites.server.core.Chore;
import com.suites.server.api.ChoreView;

import java.util.List;
import java.util.Iterator;
import java.util.Arrays;
import java.util.stream.Stream;

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

    public List<ChoreView> getSuiteChores(int suiteId) {
        return pairUserList(dao.getSuiteChores(suiteId));
    }

    public List<ChoreView> getSuiteUserChores(int suiteId, User user) {
        return pairUserList(dao.getSuiteUserChores(suiteId, user.getId()));
    }

    private List<User> getChoreAssignees(int choreId) {
        return dao.getChoreAssignees(choreId);
    }

    private List<ChoreView> pairUserList(List<Chore> cList) {

        Stream<Chore> cStream = cList.stream();
        Stream<ChoreView> cvStream = cStream.map(c -> new ChoreView(c, getChoreAssignees(c.getId())));
        ChoreView [] cvArr = cvStream.toArray(ChoreView[]::new);
        
        return Arrays.asList(cvArr);
    }

    public void addChore(int suiteId,
                         String name,
                         String description,
                         List<Integer> assignments) {
        int choreId = dao.addChore(suiteId, name, description);
        dao.assignChore(assignments, choreId, new NatIterator());
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
            dao.assignChore(assignments, id, new NatIterator());
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

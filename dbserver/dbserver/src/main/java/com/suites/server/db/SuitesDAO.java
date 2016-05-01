package com.suites.server.db;

import org.skife.jdbi.v2.sqlobject.*;

public interface SuitesDAO {

    @SqlUpdate("CREATE TABLE IF NOT EXISTS suite (id int primary key," +
               " name varchar(80))")
    void createSuiteTable();

    @SqlUpdate("CREATE TABLE IF NOT EXISTS member (id int primary key," +
               " email varchar(80)," +
               " name varchar(80))")
    void createUserTable();

    @SqlUpdate("CREATE TABLE IF NOT EXISTS suitemembership " +
               " (memberid int references member(id)," +
               " suiteid int references suite(id))")
    void createSuiteMembershipTable();
}

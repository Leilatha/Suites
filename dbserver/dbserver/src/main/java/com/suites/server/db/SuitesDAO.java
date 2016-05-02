package com.suites.server.db;

import org.skife.jdbi.v2.sqlobject.*;

public interface SuitesDAO {

    @SqlUpdate("CREATE TABLE IF NOT EXISTS Suite (Id int primary key," +
               " Name varchar(45))")
    void createSuiteTable();

    @SqlUpdate("CREATE TABLE IF NOT EXISTS Member (Id int primary key," +
               " Email varchar(80)," +
               " Name varchar(80)," +
               " Password varchar(100)," +
               " ProfilePicture varchar(255))")
    void createUserTable();

    @SqlUpdate("CREATE TABLE IF NOT EXISTS SuiteMembership " +
               " (UserId int references Member(id)," +
               " SuiteId int references Suite(id))")
    void createSuiteMembershipTable();

    @SqlUpdate("CREATE INDEX SuiteMembership_idx_1 ON SuiteMembership (UserId, SuiteId)")
    void createSuiteMembershipIndex();
}

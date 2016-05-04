package com.suites.server.db;

import com.google.common.base.Optional;

import org.skife.jdbi.v2.sqlobject.*;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import com.suites.server.core.User;

@RegisterMapper(UserMapper.class)
public interface SuitesDAO {

    @SqlUpdate("CREATE TABLE IF NOT EXISTS Suite (Id SERIAL primary key," +
               " Name varchar(45) not null)")
    void createSuiteTable();

    @SqlUpdate("CREATE TABLE IF NOT EXISTS Member (Id SERIAL primary key," +
               " Email varchar(80) not null," +
               " Name varchar(80) not null," +
               " Password varchar(100) not null," +
               " ProfilePicture varchar(255))")
    void createUserTable();

    @SqlUpdate("CREATE TABLE IF NOT EXISTS SuiteMembership " +
               " (UserId int references Member(id)," +
               " SuiteId int references Suite(id))")
    void createSuiteMembershipTable();

    @SqlUpdate("CREATE INDEX IF NOT EXISTS SuiteMembership_idx_1 ON SuiteMembership (UserId, SuiteId)")
    void createSuiteMembershipIndex();

    @SqlQuery("SELECT Id, Email, Name, ProfilePicture FROM Member"
              + " WHERE Email = :email AND Password = :passhash")
    Optional<User> authenticateUser(@Bind("email") String email, @Bind("passhash") String passhash);

    @SqlUpdate("INSERT INTO Member (Email, Name, Password)"
               + " VALUES (:email, :name, :passhash)")
    void addUser(@Bind("email") String email,
                 @Bind("name") String name,
                 @Bind("passhash") String passhash);

    
}

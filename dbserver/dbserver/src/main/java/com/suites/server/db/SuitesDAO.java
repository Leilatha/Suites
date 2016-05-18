package com.suites.server.db;

import com.google.common.base.Optional;

import org.skife.jdbi.v2.sqlobject.*;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;

import com.suites.server.core.User;
import com.suites.server.core.Suite;
import com.suites.server.core.Grocery;

import java.util.List;

@RegisterMapper(UserMapper.class)
public interface SuitesDAO {

    @SqlUpdate("CREATE TABLE IF NOT EXISTS Suite (Id SERIAL primary key," +
               " Name varchar(45) not null)")
    void createSuiteTable();

    @SqlUpdate("CREATE TABLE IF NOT EXISTS Member (Id SERIAL primary key," +
               " Email varchar(80) not null UNIQUE," +
               " Name varchar(80) not null," +
               " Password varchar(100) not null," +
               " ProfilePicture varchar(255))")
    void createUserTable();

    @SqlUpdate("CREATE TABLE IF NOT EXISTS SuiteMembership " +
               " (MemberId int references Member(id)," +
               " SuiteId int references Suite(id))")
    void createSuiteMembershipTable();

    @SqlUpdate("CREATE TABLE IF NOT EXISTS Invitation " +
               " (Email varchar(80)," +
               " SuiteId int references Suite(id))")
    void createSuiteInvitationTable();

    @SqlUpdate("CREATE TABLE IF NOT EXISTS Grocery " +
               " (Id SERIAL primary key," +
               " SuiteId int references Suite(Id)," +
               " Name varchar(80)," +
               " Price decimal(10,2)," +
               " Quantity int)")
    void createGroceryTable();


    @SqlUpdate("CREATE INDEX IF NOT EXISTS SuiteMembership_idx_1 ON SuiteMembership (MemberId, SuiteId)")
    void createSuiteMembershipIndex();

    @SqlQuery("Select Id, Email, Name, ProfilePicture FROM Member"
              + " WHERE Email = :email")
    User getUserByEmail(@Bind("email") String email);
    
    @SqlQuery("SELECT Id, Email, Name, ProfilePicture FROM Member"
              + " WHERE Email = :email AND Password = :passhash")
    User authenticateUser(@Bind("email") String email, @Bind("passhash") String passhash);

    @SqlUpdate("INSERT INTO Member (Email, Name, Password)"
               + " VALUES (:email, :name, :passhash)")
    void addUser(@Bind("email") String email,
                 @Bind("name") String name,
                @Bind("passhash") String passhash);

    @SqlQuery("INSERT INTO Suite (Name) VALUES (:name) RETURNING Id")
    int addSuite(@Bind("name") String name);

    @SqlUpdate("INSERT INTO SuiteMembership (SuiteId, MemberId) VALUES (:suiteid, :userid)")
    void addUserToSuite(@Bind("userid") int userid, @Bind("suiteid") int suiteid);

    @SqlQuery("SELECT Id, Name FROM Suite WHERE"
              + " Id IN ("
              + "SELECT SuiteId FROM SuiteMembership WHERE MemberId = :memberid)")
    @Mapper(SuiteMapper.class)
    List<Suite> getUserSuites(@Bind("memberid") int id);

    @SqlUpdate("INSERT INTO Invitation (SuiteId, Email) VALUES (:suiteid, :email)")
    void inviteUser(@Bind("email") String email, @Bind("suiteid") int suiteId);

    @SqlQuery("SELECT Id, Name FROM Suite WHERE"
              + " Id IN ("
              + "SELECT SuiteId FROM Invitation WHERE Email = :email)")
    @Mapper(SuiteMapper.class)
    List<Suite> getUserInvites(@Bind("email") String email);

    @SqlQuery("SELECT count(SuiteId) > 0 FROM Invitation"
              + " WHERE SuiteId = :suiteid AND Email = :email LIMIT 1")
    boolean isUserInvited(@Bind("suiteid") int suiteId, @Bind("email") String email);

    @SqlQuery("SELECT count(SuiteId) > 0 FROM SuiteMembership"
              + " WHERE MemberId = :userid AND SuiteId = :suiteid LIMIT 1")
    boolean isUserInSuite(@Bind("userid") int userId, @Bind("suiteid") int suiteId);

    @SqlQuery("SELECT Id, Email, Name, ProfilePicture FROM Member WHERE"
            + " Id IN ("
            + " SELECT MemberId FROM SuiteMembership WHERE SuiteId = :suiteid)")
    List<User> getSuiteUsers(@Bind("suiteid") int suiteId);

    @SqlQuery("SELECT Id, Name, Quantity, Price FROM Grocery WHERE" +
              " SuiteId = :suiteid")
    @Mapper(GroceryMapper.class)
    List<Grocery> getSuiteGroceries(@Bind("suiteid") int suiteId);

    @SqlUpdate("INSERT INTO Grocery (SuiteId, Name, Price, Quantity)" +
               " VALUES (:suiteid, :name, :price, :quantity)")
    void addGrocery(@Bind("suiteid") int suiteId,
                    @Bind("name") String name,
                    @Bind("price") double price,
                    @Bind("quantity") int quantity);

    @SqlUpdate("UPDATE Grocery " +
               "SET Name = :name, Quantity = :quantity, Price = :price " +
               "WHERE Id = :id AND " +
                     "SuiteId IN (SELECT SuiteId FROM SuiteMembership WHERE MemberId = :userid)")
    int editGrocery(@Bind("id") int id,
                    @Bind("userid") int userId,
                    @Bind("name") String name,
                    @Bind("price") double price,
                    @Bind("quantity") int quantity);

    @SqlUpdate("DELETE FROM Grocery " +
               "WHERE Id = :id AND " +
                     "SuiteId IN (SELECT SuiteId FROM SuiteMembership WHERE MemberId = :userid)")
    int deleteGrocery(@Bind("id") int id,
                      @Bind("userid") int userId);
}

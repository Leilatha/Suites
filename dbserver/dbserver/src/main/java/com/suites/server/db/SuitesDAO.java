package com.suites.server.db;

import com.google.common.base.Optional;

import org.skife.jdbi.v2.sqlobject.*;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;

import com.suites.server.core.User;
import com.suites.server.core.Suite;
import com.suites.server.core.Grocery;
import com.suites.server.core.Chore;
import com.suites.server.core.PSA;

import java.util.List;
import java.util.Iterator;

import java.sql.Timestamp;

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

    @SqlUpdate("CREATE TABLE IF NOT EXISTS Chore " +
               " (Id SERIAL primary key, " +
               " SuiteId int references Suite(Id)," +
               " Name varchar(80)," +
               " Description varchar(255)," +
               " CurrentTurn int)")
    void createChoreTable();

    @SqlUpdate("CREATE TABLE IF NOT EXISTS ChoreAssignment" +
               " (MemberId int references Member(Id)," +
               " ChoreId int references Chore(Id)," +
               " Turn int)")
    void createChoreAssignmentTable();

    @SqlUpdate("CREATE TABLE IF NOT EXISTS PSA" +
               " (Id SERIAL primary key," +
               " SuiteId int references Suite(Id)," +
               " AuthorId int references Member(Id)," +
               " Title varchar(80)," +
               " Description varchar(255)," +
               " Timestamp timestamp")
    void createPSATable();

    @SqlUpdate("CREATE INDEX IF NOT EXISTS SuiteMembership_idx_1 ON SuiteMembership (MemberId, SuiteId)")
    void createSuiteMembershipIndex();

    @SqlUpdate("CREATE OR REPLACE FUNCTION assignee_cnt(int) RETURNS bigint AS $$"
               + "SELECT count(ChoreId) FROM ChoreAssignment WHERE ChoreId = $1; $$"
               + " LANGUAGE SQL")
    void createAssigneeCountFunction();

    @SqlUpdate("CREATE OR REPLACE FUNCTION user_in_suite(int, int) RETURNS boolean AS $$"
               + "SELECT (EXISTS (SELECT MemberId FROM SuiteMembership WHERE "
               + " SuiteId = $2 AND MemberId = $1)); $$"
               + " LANGUAGE SQL")
    void createSuiteMembershipFunction();

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

    @SqlUpdate("DELETE FROM SuiteMembership WHERE SuiteId = :suiteid AND MemberId = :userid")
    void removeUserFromSuite(@Bind("userid") int userId, @Bind("suiteid") int suiteId);

    @SqlUpdate("DELETE FROM Suite WHERE " +
               "Id = :suiteid " +
               "AND NOT EXISTS (SELECT * FROM SuiteMembership WHERE SuiteId = :suiteid)")
    void removeSuiteIfEmpty(@Bind("suiteid") int suiteId);

    @SqlQuery("SELECT Id, Name FROM Suite WHERE user_in_suite(:memberid, Id)")
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

    @SqlQuery("SELECT user_in_suite(:userid, :suiteid)")
    boolean isUserInSuite(@Bind("userid") int userId, @Bind("suiteid") int suiteId);

    @SqlQuery("SELECT Id, Email, Name, ProfilePicture FROM SuiteMembership JOIN Member" +
              " ON Id = MemberId AND SuiteId = :suiteid")
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
               "WHERE Id = :id AND user_in_suite(:userid, SuiteId)")
    int editGrocery(@Bind("id") int id,
                    @Bind("userid") int userId,
                    @Bind("name") String name,
                    @Bind("price") double price,
                    @Bind("quantity") int quantity);

    @SqlUpdate("DELETE FROM Grocery " +
               "WHERE Id = :id AND user_in_suite(:userid, SuiteId)")
    int deleteGrocery(@Bind("id") int id,
                      @Bind("userid") int userId);

    @SqlQuery("SELECT Id, Name, Description, CurrentTurn FROM Chore WHERE" +
              " SuiteId = :suiteid")
    @Mapper(ChoreMapper.class)
    List<Chore> getSuiteChores(@Bind("suiteid") int suiteId);

    @SqlQuery("SELECT Id, Name, Description FROM Chore WHERE" +
              " SuiteId = :suiteid AND Id IN"+
              " (SELECT SuiteId FROM ChoreAssignment WHERE MemberId = :userid)")
    List<Chore> getSuiteUserChores(@Bind("suiteid") int suiteId, @Bind("userid") int userId);
    
    @SqlQuery("INSERT INTO Chore (SuiteId, Name, Description, currentTurn) " +
              "VALUES (:suiteid, :name, :description, 0) RETURNING Id")
    int addChore(@Bind("suiteid") int suiteId,
                 @Bind("name") String name,
                 @Bind("description") String description);

    @SqlUpdate("UPDATE Chore " +
               "SET Name = :name, Description = :description " +
               "WHERE Id = :id AND user_in_suite(:userid, SuiteId)")
    int editChore(@Bind("id") int id,
                  @Bind("userid") int userId,
                  @Bind("name") String name,
                  @Bind("description") String description);

    @SqlUpdate("DELETE FROM  Chore " +
               "WHERE Id = :id AND user_in_suite(:userid, SuiteId)")
    int deleteChore(@Bind("id") int id,
                    @Bind("userid") int userId);

    @SqlUpdate("DELETE FROM ChoreAssignment" +
               " WHERE Id = :id")
    int deleteChoreAssignments(@Bind("id") int id);

    @SqlBatch("INSERT INTO ChoreAssignment (MemberId, ChoreId, Turn)" +
              " VALUES (:memberid, :choreid, :turn)")
    void assignChore(@Bind("memberid") List<Integer> memberId,
                     @Bind("choreid") int choreId,
                     @Bind("turn") Iterator<Integer> turn);

    @SqlUpdate("UPDATE Chore " +
               "SET CurrentTurn = (CurrentTurn + 1) % assignee_cnt(:id) " +
               "WHERE Id = :id AND " +
               "user_in_suite(:userid, SuiteId)")
    int advanceChore(@Bind("id") int id, @Bind("userid") int userId);

    @SqlUpdate("UPDATE Chore " +
               "SET CurrentTurn = CurrentTurn % assignee_cnt(ChoreId) " +
               "WHERE Id = :id")
    void fixChore(@Bind("id") int id);

    @SqlQuery("SELECT Id, Email, Name, ProfilePicture FROM ChoreAssignment JOIN Member " +
              "ON Id = MemberId AND ChoreId = :choreid")
    List<User> getChoreAssignees(@Bind("choreid") int choreId);

    @SqlQuery("SELECT Id, AuthorId, Title, Description, Timestamp FROM PSA " +
              "WHERE SuiteId = :suiteid")
    @Mapper(PSAMapper.class)
    List<PSA> getSuitePSAs(@Bind("suiteid") int suiteId);

    @SqlUpdate("INSERT INTO PSA (SuiteId, AuthorId, Title, Description, Timestamp) " +
               "VALUES(:suiteid, :authorid, :title, :description, :timestamp)")
    void addPSA(@Bind("suiteid") int suiteId,
                @Bind("authorid") int authorId,
                @Bind("title") String title,
                @Bind("description") String description,
                @Bind("timestamp") Timestamp timestamp);

    @SqlUpdate("UPDATE PSA " +
               "SET Title = :title, Description = :description " +
               "WHERE Id = :id AND AuthorId = :userid")
    int editPSA(@Bind("id") int id,
                @Bind("userid") int userId,
                @Bind("title") String title,
                @Bind("description") String description);

    @SqlUpdate("DELETE FROM PSA " +
               "WHERE Id = :id AND AuthorId = :userid")
    int deletePSA(@Bind("id") int id,
                  @Bind("userid") int userId);
}

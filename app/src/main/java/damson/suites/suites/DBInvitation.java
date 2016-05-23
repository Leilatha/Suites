package damson.suites.suites;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DBInvitation {
    private final String invitee; // Email
    private final int suiteId;

    @JsonCreator
    public DBInvitation(@JsonProperty("invitee") String invitee,
                        @JsonProperty("suiteId") int suiteId) {
        this.invitee = invitee;
        this.suiteId = suiteId;
    }

    @JsonProperty("invitee")
    public String getInvitee() {
        return invitee;
    }

    @JsonProperty("suiteId")
    public int getSuiteId() {
        return suiteId;
    }
}

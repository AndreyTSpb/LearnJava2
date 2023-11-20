package Part_2.MyClassToBePersisted;

import java.io.Serial;
import java.io.Serializable;

public class MyClassToBePersisted implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    private String[] profiles;
    private String[] groups;

    public MyClassToBePersisted(String[] profiles, String[] groups){
        this.profiles = profiles;
        this.groups = groups;
    }

    public String[] getGroups() {
        return groups;
    }
    public String[] getProfiles() {
        return profiles;
    }
    public void setGroups(String[] groups) {
        this.groups = groups;
    }

    public void setProfiles(String[] profiles) {
        this.profiles = profiles;
    }

}

package id.ac.itb.ditlog.monitorandperformance;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UserPayload {
    public long idUser;
    public String jwtToken;

    public UserPayload() {
        idUser = -1;
        jwtToken = "";
    }

    public UserPayload(Long id, String t) {
        idUser = id;
        jwtToken = t;
    }
}

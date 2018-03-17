package id.ac.itb.ditlog.monitorandperformance;

public class UserPayload {
    public long idUser;
    public long roleId;
    public String jwtToken;

    public UserPayload() {
        idUser = -1;
        roleId = -1;
        jwtToken = "";
    }

    public UserPayload(Long idUser, Long roleId, String jwtToken) {
        this.idUser = idUser;
        this.roleId = roleId;
        this.jwtToken = jwtToken;
    }
}

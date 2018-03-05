package id.ac.itb.ditlog.monitorandperformance;

public class LoginWrapper{
    private LoginInfo loginInfo;
    private LoginResponse loginResponse;

    public LoginWrapper() {
        loginInfo = new LoginInfo();
        loginResponse = new LoginResponse();
    }

    public LoginWrapper(LoginInfo i, LoginResponse r) {
        loginInfo = i;
        loginResponse = r;
    }

    public LoginInfo getLoginInfo(){
        return loginInfo;
    }

    public LoginResponse getLoginResponse() {
        return loginResponse;
    }
}

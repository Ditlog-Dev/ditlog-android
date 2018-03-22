package id.ac.itb.ditlog.monitorandperformance;

public class LoginWrapper {

  private LoginInfo loginInfo;
  private UserPayload payload;

  public LoginWrapper() {
    loginInfo = new LoginInfo();
    payload = new UserPayload();
  }

  public LoginWrapper(LoginInfo i, UserPayload r) {
    loginInfo = i;
    payload = r;
  }

  public LoginInfo getLoginInfo() {
    return loginInfo;
  }

  public UserPayload getPayload() {
    return payload;
  }
}

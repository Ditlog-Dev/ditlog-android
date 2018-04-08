package id.ac.itb.ditlog.monitorandperformance;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assume.assumeTrue;

import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class LoginInstrumentedTest {
  @Rule
  public ActivityTestRule<Login> mActivityRule = new ActivityTestRule<>(Login.class);

  @Before
  public void testWebserviceConnection() throws IOException, JSONException {
    URL url = new URL(BuildConfig.WEBSERVICE_URL + "/login");

    JSONObject request = new JSONObject();
    request.put("username", "dummy_kasubdit");
    request.put("password", "123");

    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
    urlConnection.setRequestMethod("POST");
    urlConnection.setReadTimeout(1500);
    urlConnection.setConnectTimeout(1500);
    urlConnection.setDoOutput(true);
    urlConnection.setDoInput(true);
    urlConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

    DataOutputStream wr = new DataOutputStream(urlConnection.getOutputStream());
    wr.writeBytes(request.toString());
    wr.flush();
    wr.close();

    int responseStatusCode = urlConnection.getResponseCode();
    assumeTrue("Error on the webservice part, The tests will not run. the webservice response status code is " + responseStatusCode + ", expected 200 or 403",
        responseStatusCode == HttpURLConnection.HTTP_OK || responseStatusCode == HttpURLConnection.HTTP_FORBIDDEN);

  }

  @Test
  public void testLoginAttempt_correctCredential() {
    performLogin("dummy_kasubdit","123");

    Intents.init();
    intended(hasComponent(MainActivity.class.getName()));
    Intents.release();
  }

  public void performLogin(String username, String password) {
    onView(withId(R.id.username)).perform(typeText(username),closeSoftKeyboard());
    onView(withId(R.id.password)).perform(typeText(password),closeSoftKeyboard());

    onView(withId(R.id.approval_button)).perform(click());
  }
}

package com.petzila.api;

import com.petzila.api.model.Login;
import com.petzila.api.model.response.UserNotificationGetResponse;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by rsaborio on 18/12/14.
 */
public class UserNotificationGetTest {
    private String userKey;

    @Before
    public void before() throws Exception {
        Login login = new Login();
        login.email = "rsaborio@wearegap.com";
        login.password = "qwerty123";
        login.loginType = "local";
        userKey = Petzila.UserAPI.login(login).data.token;
//        Login login = new Login();
//        login.email = "john.howardpe@gmail.com";
//        login.password = "Paris13101";
//        login.loginType = "local";
//        userKey = Petzila.UserAPI.login(login).data.token;
    }

    @Test
    public void testUserNotificationGetHappyPath() throws Exception {
        UserNotificationGetResponse userNotificationGetResponse = Petzila.UserAPI.getNotifications(userKey, null, 5);
        assertNotNull(userNotificationGetResponse);
        assertEquals(userNotificationGetResponse.status, "Success");
        //@TODO make it better!
    }
}

package com.petzila.api;

import com.petzila.api.model.response.UserNotificationGetResponse;
import com.petzila.api.util.Users;
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
        userKey = Petzila.UserAPI.login(Users.get()).data.token;
    }

    @Test
    public void testUserNotificationGetHappyPath() throws Exception {
        UserNotificationGetResponse userNotificationGetResponse = Petzila.UserAPI.getNotifications(userKey, null, 5);
        assertNotNull(userNotificationGetResponse);
        assertEquals(userNotificationGetResponse.status, "Success");
        //@TODO make it better!
    }
}

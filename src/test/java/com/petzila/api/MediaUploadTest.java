package com.petzila.api;

import com.petzila.api.model.Login;
import com.petzila.api.model.Media;
import com.petzila.api.model.response.MediaUploadResponse;
import com.petzila.api.util.Utils;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by rsaborio on 02/12/14.
 */
public class MediaUploadTest {
    private String userKey;

    @Before
    public void before() {
        Login login = new Login();
        login.email = "rsaborio@wearegap.com";
        login.password = "qwerty123";
        login.loginType = "local";
        userKey = Petzila.UserAPI.login(login).data.token;
    }

    @Test
    public void testUploadHappyPath() {
        Media media = new Media();
        media.media = Utils.asFilename("/dog1.jpg");
        MediaUploadResponse response = Petzila.MediaAPI.upload(media, userKey);

        assertNotNull(response);
        assertEquals(response.status, "Success");
    }
}

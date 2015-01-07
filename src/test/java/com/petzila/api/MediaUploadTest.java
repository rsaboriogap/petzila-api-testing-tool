package com.petzila.api;

import com.petzila.api.model.Media;
import com.petzila.api.model.response.MediaUploadResponse;
import com.petzila.api.util.Users;
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
        userKey = Petzila.UserAPI.login(Users.get()).data.token;
    }

    @Test
    public void testUploadHappyPath() throws Exception {
        Media media = new Media();
        media.media = Utils.asTempFile("/dog1.jpg");
        MediaUploadResponse response = Petzila.MediaAPI.upload(media, userKey);

        assertNotNull(response);
        assertEquals(response.status, "Success");
    }
}

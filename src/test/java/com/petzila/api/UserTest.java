package com.petzila.api;

import com.petzila.api.model.SignUp;
import com.petzila.api.model.SignUpResponse;
import com.petzila.api.util.Utils;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by rylexr on 28/11/14.
 */
public class UserTest {
    @Test
    public void testSignUpHappyPath() throws Exception {
        SignUp signUp = new SignUp();
        signUp.email = "rsaborio@wearegap.com";
        signUp.username = "rsaborio";
        signUp.password = "qwerty123";
        signUp.profilePicture = Utils.asBase64("/media/work/development/projects/gap/petzila/java/post-test/src/main/resources/dog1.jpg");
        signUp.resourceType = "image/jpeg";
        signUp.signupType = "local";
        SignUpResponse response = Petzila.User.signup(signUp);
        assertNotNull(response);
        assertEquals(response.status, "Success");
    }
}

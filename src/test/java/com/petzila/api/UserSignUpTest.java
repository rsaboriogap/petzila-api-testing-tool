package com.petzila.api;

import com.petzila.api.model.SignUp;
import com.petzila.api.model.response.ErrorResponse;
import com.petzila.api.model.response.UserSignUpResponse;
import com.petzila.api.util.Utils;
import org.junit.Ignore;
import org.junit.Test;

import javax.ws.rs.BadRequestException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by rsaborio on 02/12/14.
 */
public class UserSignUpTest {
    @Ignore
    @Test
    public void testSignUpHappyPath() throws Exception {
        SignUp signUp = new SignUp();
        signUp.username = "rsaborio";
        signUp.email = "rsaborio@wearegap.com";
        signUp.password = "qwerty123";
        signUp.profilePicture = Utils.asBase64("/neo.jpg");
        signUp.resourceType = "image/jpeg";
        signUp.description = "My test account";
        signUp.signupType = "local";
        signUp.name.firstName = "Randy";
        signUp.name.lastName = "Saborio";
        signUp.location.country = "Costa Rica";
        signUp.location.city = "San José";
        signUp.location.zipCode = "1000";
        UserSignUpResponse response = Petzila.UserAPI.signup(signUp);

        assertNotNull(response);
        assertEquals(response.status, "Success");
    }

    @Test
    public void testSignUpMissingUsername() throws Exception {
        SignUp signUp = new SignUp();
        signUp.email = "rsaborio@wearegap.com";
        signUp.password = "qwerty123";
        signUp.profilePicture = Utils.asBase64("/neo.jpg");
        signUp.resourceType = "image/jpeg";
        signUp.description = "My test account";
        signUp.signupType = "local";
        signUp.name.firstName = "Randy";
        signUp.name.lastName = "Saborio";
        signUp.location.country = "Costa Rica";
        signUp.location.city = "San José";
        signUp.location.zipCode = "1000";
        try {
            Petzila.UserAPI.signup(signUp);
        } catch (BadRequestException bre) {
            assertNotNull(bre.getResponse());
            ErrorResponse response = bre.getResponse().readEntity(ErrorResponse.class);
            assertEquals("Invalid error code", 610, response.code);
            assertEquals("Invalid message", "The username is required.", response.data.message);
        }
    }
}

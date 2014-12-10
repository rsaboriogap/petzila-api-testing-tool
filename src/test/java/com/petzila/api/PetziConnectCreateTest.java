package com.petzila.api;

import com.petzila.api.model.Login;
import com.petzila.api.model.PetziConnect;
import com.petzila.api.model.response.ErrorResponse;
import com.petzila.api.model.response.PetziConnectCreateResponse;
import com.petzila.api.model.response.PetziConnectGetResponse;
import com.petzila.api.model.response.UserLoginResponse;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.BadRequestException;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by rsaborio on 09/12/14.
 */
public class PetziConnectCreateTest {
    private String userKey1;
    private String userKey2;

    @Before
    public void before() {
        Login login = new Login();
        login.email = "rsaborio@wearegap.com";
        login.password = "qwerty123";
        login.loginType = "local";
        UserLoginResponse userLoginResponse = Petzila.UserAPI.login(login);
        userKey1 = userLoginResponse.data.token;

        login.email = "lema017@gmail.com";
        login.password = "clarodeluna";
        userLoginResponse = Petzila.UserAPI.login(login);
        userKey2 = userLoginResponse.data.token;
    }

    @Test
    public void testCreatePetziConnectHappyPath() {
        String pzcId = UUID.randomUUID().toString();

        PetziConnect petziConnect = new PetziConnect();
        petziConnect.pzcName = "Test device";
        petziConnect.isDefault = false;
        PetziConnectCreateResponse response = Petzila.PetziConnectAPI.create(petziConnect, pzcId, userKey1);

        assertNotNull(response);
        assertEquals(response.status, "Success");
        assertNotNull(response.data.hbsUrl);
    }

    @Test
    public void testCreateTwoPetziConnectsWithSameIDAndSameOwner() {
        String pzcId = UUID.randomUUID().toString();

        PetziConnect petziConnect = new PetziConnect();
        petziConnect.pzcName = "Test device";
        petziConnect.isDefault = false;
        PetziConnectCreateResponse response = Petzila.PetziConnectAPI.create(petziConnect, pzcId, userKey1);

        assertNotNull(response);
        assertEquals(response.status, "Success");
        assertNotNull(response.data.hbsUrl);

        // Second create backend behavior is "re-provisioning"
        petziConnect = new PetziConnect();
        petziConnect.pzcName = "Test device 2";
        petziConnect.isDefault = false;
        response = Petzila.PetziConnectAPI.create(petziConnect, pzcId, userKey1);

        assertNotNull(response);
        assertEquals(response.status, "Success");
        assertNotNull(response.data.hbsUrl);

        PetziConnectGetResponse petziConnectGetResponse = Petzila.PetziConnectAPI.get(pzcId);
        assertNotNull(petziConnectGetResponse);
        assertEquals(petziConnectGetResponse.status, "Success");
    }

    @Test
    public void testCreateTwoPetziConnectsWithSameIDAndDifferentOwners() {
        String pzcId = UUID.randomUUID().toString();

        PetziConnect petziConnect = new PetziConnect();
        petziConnect.pzcName = "Test device";
        petziConnect.isDefault = false;
        PetziConnectCreateResponse response = Petzila.PetziConnectAPI.create(petziConnect, pzcId, userKey1);

        assertNotNull(response);
        assertEquals(response.status, "Success");
        assertNotNull(response.data.hbsUrl);

        petziConnect = new PetziConnect();
        petziConnect.pzcName = "Test device 2";
        petziConnect.isDefault = false;
        try {
            Petzila.PetziConnectAPI.create(petziConnect, pzcId, userKey2);
        } catch (BadRequestException bre) {
            ErrorResponse error = bre.getResponse().readEntity(ErrorResponse.class);
            assertEquals("Invalid expected error code", 1038, error.code);
            assertEquals("Invalid expected status", "Fail", error.status);
            assertEquals("Invalid expected message", "PetziConnect is already registered to another user.", error.data.message);
        }
    }
}

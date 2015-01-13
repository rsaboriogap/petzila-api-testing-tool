package com.petzila.api;

import com.petzila.api.model.PetziConnect;
import com.petzila.api.model.response.ErrorResponse;
import com.petzila.api.model.response.PetziConnectRegisterResponse;
import com.petzila.api.model.response.PetziConnectGetResponse;
import com.petzila.api.model.response.UserLoginResponse;
import com.petzila.api.util.Users;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.BadRequestException;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by rsaborio on 09/12/14.
 */
public class PetziConnectRegisterTest {
    private String userKey1;
    private String userKey2;

    @Before
    public void before() {
        UserLoginResponse userLoginResponse = Petzila.UserAPI.login(Users.get(0));
        userKey1 = userLoginResponse.data.token;

        userLoginResponse = Petzila.UserAPI.login(Users.get(1));
        userKey2 = userLoginResponse.data.token;
    }

    @Test
    public void testRegisterPetziConnectHappyPath() {
        String pzcId = UUID.randomUUID().toString();

        PetziConnect petziConnect = new PetziConnect();
        petziConnect.pzcName = "Test device";
        petziConnect.isDefault = false;
        PetziConnectRegisterResponse response = Petzila.PetziConnectAPI.register(petziConnect, pzcId, userKey1);

        assertNotNull(response);
        assertEquals(response.status, "Success");
        assertNotNull(response.data.hbsUrl);
    }

    @Test
    public void testRegisterTwoPetziConnectsWithSameIDAndSameOwner() {
        String pzcId = UUID.randomUUID().toString();

        PetziConnect petziConnect = new PetziConnect();
        petziConnect.pzcName = "Test device";
        petziConnect.isDefault = false;
        PetziConnectRegisterResponse response = Petzila.PetziConnectAPI.register(petziConnect, pzcId, userKey1);

        assertNotNull(response);
        assertEquals(response.status, "Success");
        assertNotNull(response.data.hbsUrl);

        // Second create backend behavior is "re-provisioning"
        petziConnect = new PetziConnect();
        petziConnect.pzcName = "Test device 2";
        petziConnect.isDefault = false;
        response = Petzila.PetziConnectAPI.register(petziConnect, pzcId, userKey1);

        assertNotNull(response);
        assertEquals(response.status, "Success");
        assertNotNull(response.data.hbsUrl);

        PetziConnectGetResponse petziConnectGetResponse = Petzila.PetziConnectAPI.get(pzcId);
        assertNotNull(petziConnectGetResponse);
        assertEquals(petziConnectGetResponse.status, "Success");
    }

    @Test
    public void testRegisterTwoPetziConnectsWithSameIDAndDifferentOwners() {
        String pzcId = UUID.randomUUID().toString();

        PetziConnect petziConnect = new PetziConnect();
        petziConnect.pzcName = "Test device";
        petziConnect.isDefault = false;
        PetziConnectRegisterResponse response = Petzila.PetziConnectAPI.register(petziConnect, pzcId, userKey1);

        assertNotNull(response);
        assertEquals(response.status, "Success");
        assertNotNull(response.data.hbsUrl);

        petziConnect = new PetziConnect();
        petziConnect.pzcName = "Test device 2";
        petziConnect.isDefault = false;
        try {
            Petzila.PetziConnectAPI.register(petziConnect, pzcId, userKey2);
        } catch (BadRequestException bre) {
            ErrorResponse error = bre.getResponse().readEntity(ErrorResponse.class);
            assertEquals("Invalid expected error code", 1038, error.code);
            assertEquals("Invalid expected status", "Fail", error.status);
            assertEquals("Invalid expected message", "PetziConnect is already registered to another user.", error.data.message);
        }
    }
}

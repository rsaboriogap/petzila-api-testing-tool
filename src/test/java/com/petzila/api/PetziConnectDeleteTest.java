package com.petzila.api;

import com.petzila.api.model.PetziConnect;
import com.petzila.api.model.response.PetziConnectRegisterResponse;
import com.petzila.api.model.response.PetziConnectDeleteResponse;
import com.petzila.api.model.response.PetziConnectGetResponse;
import com.petzila.api.model.response.UserLoginResponse;
import com.petzila.api.util.Users;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by rsaborio on 23/12/14.
 */
public class PetziConnectDeleteTest {
    private String userKey;

    @Before
    public void before() {
        UserLoginResponse userLoginResponse = Petzila.UserAPI.login(Users.random());
        userKey = userLoginResponse.data.token;
    }

    @Test
    public void testDeletePetziConnectHappyPath() {
        String pzcId = UUID.randomUUID().toString();

        PetziConnectGetResponse petziConnectGetResponse = Petzila.PetziConnectAPI.get(pzcId);
        assertNotNull(petziConnectGetResponse);
        assertEquals("Success", petziConnectGetResponse.status);
        assertEquals(false, petziConnectGetResponse.data.needProcess);

        PetziConnect petziConnect = new PetziConnect();
        petziConnect.pzcName = "Test device";
        petziConnect.isDefault = true;

        PetziConnectRegisterResponse petziConnectRegisterResponse = Petzila.PetziConnectAPI.register(petziConnect, pzcId, userKey);
        assertNotNull(petziConnectRegisterResponse);
        assertEquals("Success", petziConnectRegisterResponse.status);
        assertNotNull(petziConnectRegisterResponse.data.hbsUrl);

        petziConnectGetResponse = Petzila.PetziConnectAPI.get(pzcId);
        assertNotNull(petziConnectGetResponse);
        assertEquals("Success", petziConnectGetResponse.status);
        assertEquals(false, petziConnectGetResponse.data.needProcess);

        PetziConnectDeleteResponse petziConnectDeleteResponse = Petzila.PetziConnectAPI.delete(pzcId, userKey);
        assertNotNull(petziConnectDeleteResponse);
        assertEquals("Success", petziConnectDeleteResponse.status);
        assertEquals("OK", petziConnectDeleteResponse.data.message);

        petziConnectGetResponse = Petzila.PetziConnectAPI.get(pzcId);
        assertNotNull(petziConnectGetResponse);
        assertEquals("Success", petziConnectGetResponse.status);
        assertEquals(false, petziConnectGetResponse.data.needProcess);
    }
}

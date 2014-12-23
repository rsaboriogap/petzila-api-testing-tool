package com.petzila.api;

import com.petzila.api.model.Login;
import com.petzila.api.model.PetziConnect;
import com.petzila.api.model.response.PetziConnectCreateResponse;
import com.petzila.api.model.response.PetziConnectDeleteResponse;
import com.petzila.api.model.response.PetziConnectGetResponse;
import com.petzila.api.model.response.UserLoginResponse;
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
        Login login = new Login();
        login.email = "rsaborio@wearegap.com";
        login.password = "qwerty123";
        login.loginType = "local";
        UserLoginResponse userLoginResponse = Petzila.UserAPI.login(login);
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

        PetziConnectCreateResponse petziConnectCreateResponse = Petzila.PetziConnectAPI.create(petziConnect, pzcId, userKey);
        assertNotNull(petziConnectCreateResponse);
        assertEquals("Success", petziConnectCreateResponse.status);
        assertNotNull(petziConnectCreateResponse.data.hbsUrl);

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

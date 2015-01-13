package com.petzila.api;

import com.petzila.api.model.PetziConnect;
import com.petzila.api.model.response.PetziConnectGetResponse;
import com.petzila.api.model.response.UserLoginResponse;
import com.petzila.api.util.Users;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by rsaborio on 09/12/14.
 */
public class UserDeviceGetTest {
    private static final String PZC_ID = UUID.randomUUID().toString();

    @Before
    public void before() {
        UserLoginResponse userLoginResponse = Petzila.UserAPI.login(Users.random());
        String userKey = userLoginResponse.data.token;

        PetziConnect petziConnect = new PetziConnect();
        petziConnect.pzcName = "Test device";
        petziConnect.isDefault = false;
        Petzila.PetziConnectAPI.register(petziConnect, PZC_ID, userKey);
    }

    @Test
    public void testGetDevicesHappyPath() {
        PetziConnectGetResponse response = Petzila.PetziConnectAPI.get(PZC_ID);

        assertNotNull(response);
        assertEquals(response.status, "Success");
    }
}

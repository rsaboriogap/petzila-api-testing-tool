package com.petzila.api;

import com.petzila.api.model.Login;
import com.petzila.api.model.PetziConnect;
import com.petzila.api.model.response.PetziConnectCreateResponse;
import com.petzila.api.model.response.UserLoginResponse;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by rsaborio on 09/12/14.
 */
public class PetziConnectCreateTest {
    private static final String PZC_ID = "38:60:77:38:bd:f0";
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
    public void testCreatePetziConnectHappyPath() {
        PetziConnect petziConnect = new PetziConnect();
        petziConnect.pzcName = "Test device";
        petziConnect.isDefault = false;
        PetziConnectCreateResponse response = Petzila.PetziConnectAPI.create(petziConnect, PZC_ID, userKey);

        assertNotNull(response);
        assertEquals(response.status, "Success");
        assertNotNull(response.data.hbsUrl);
    }
}

package com.petzila.api;

import com.petzila.api.model.Login;
import com.petzila.api.model.PetziConnect;
import com.petzila.api.model.response.PetziConnectGetResponse;
import com.petzila.api.model.response.UserLoginResponse;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by rsaborio on 09/12/14.
 */
public class UserDeviceGetTest {
    private static final String PZC_ID = "38:60:77:38:bd:f0";

    @Before
    public void before() {
        Login login = new Login();
        login.email = "rsaborio@wearegap.com";
        login.password = "qwerty123";
        login.loginType = "local";
        UserLoginResponse userLoginResponse = Petzila.UserAPI.login(login);
        String userKey = userLoginResponse.data.token;

        PetziConnect petziConnect = new PetziConnect();
        petziConnect.pzcName = "Test device";
        petziConnect.isDefault = false;
        Petzila.PetziConnectAPI.create(petziConnect, PZC_ID, userKey);
    }

    @Test
    public void testGetDevicesHappyPath() {
        PetziConnectGetResponse response = Petzila.PetziConnectAPI.get(PZC_ID);

        assertNotNull(response);
        assertEquals(response.status, "Success");
    }
}

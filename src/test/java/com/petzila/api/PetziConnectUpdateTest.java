package com.petzila.api;

import com.petzila.api.model.Login;
import com.petzila.api.model.PetziConnect;
import com.petzila.api.model.response.PetziConnectCreateResponse;
import com.petzila.api.model.response.PetziConnectUpdateResponse;
import com.petzila.api.model.response.UserLoginResponse;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by rsaborio on 09/12/14.
 */
public class PetziConnectUpdateTest {
    private static final String PZC_ID = "38:60:77:38:bd:f0";
    private static final String PZC_NAME = "Test device";
    private static final String PZC_NAME_UPDATED = "Test device - updated";
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
    public void testUpdatePetziConnectHappyPath() {
        PetziConnect petziConnect = new PetziConnect();
        petziConnect.pzcName = PZC_NAME;
        petziConnect.isDefault = false;
        PetziConnectCreateResponse petziConnectCreateResponse = Petzila.PetziConnectAPI.create(petziConnect, PZC_ID, userKey);

        assertNotNull(petziConnectCreateResponse);
        assertEquals(petziConnectCreateResponse.status, "Success");
        assertNotNull(petziConnectCreateResponse.data.hbsUrl);

        petziConnect.pzcName = PZC_NAME_UPDATED;
        petziConnect.isDefault = true;
        PetziConnectUpdateResponse petziConnectUpdateResponse = Petzila.PetziConnectAPI.update(petziConnect, PZC_ID, userKey);

        assertNotNull(petziConnectUpdateResponse);
        assertEquals(petziConnectUpdateResponse.status, "Success");
        assertEquals(PZC_NAME_UPDATED, petziConnectUpdateResponse.data.pzcName);
        assertEquals(true, petziConnectUpdateResponse.data.isDefault);
    }
}

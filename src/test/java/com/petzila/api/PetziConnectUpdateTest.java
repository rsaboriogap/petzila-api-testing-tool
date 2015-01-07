package com.petzila.api;

import com.petzila.api.model.PetziConnect;
import com.petzila.api.model.response.PetziConnectRegisterResponse;
import com.petzila.api.model.response.PetziConnectUpdateResponse;
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
public class PetziConnectUpdateTest {
    private static final String PZC_ID = UUID.randomUUID().toString();
    private static final String PZC_NAME = "Test device";
    private static final String PZC_NAME_UPDATED = "Test device - updated";
    private String userKey;

    @Before
    public void before() {
        UserLoginResponse userLoginResponse = Petzila.UserAPI.login(Users.get());
        userKey = userLoginResponse.data.token;
    }

    @Test
    public void testUpdatePetziConnectHappyPath() {
        PetziConnect petziConnect = new PetziConnect();
        petziConnect.pzcName = PZC_NAME;
        petziConnect.isDefault = false;
        PetziConnectRegisterResponse petziConnectRegisterResponse = Petzila.PetziConnectAPI.register(petziConnect, PZC_ID, userKey);

        assertNotNull(petziConnectRegisterResponse);
        assertEquals(petziConnectRegisterResponse.status, "Success");
        assertNotNull(petziConnectRegisterResponse.data.hbsUrl);

        petziConnect.pzcName = PZC_NAME_UPDATED;
        petziConnect.isDefault = true;
        PetziConnectUpdateResponse petziConnectUpdateResponse = Petzila.PetziConnectAPI.update(petziConnect, PZC_ID, userKey);

        assertNotNull(petziConnectUpdateResponse);
        assertEquals(petziConnectUpdateResponse.status, "Success");
        assertEquals(PZC_NAME_UPDATED, petziConnectUpdateResponse.data.pzcName);
        assertEquals(true, petziConnectUpdateResponse.data.isDefault);
    }
}

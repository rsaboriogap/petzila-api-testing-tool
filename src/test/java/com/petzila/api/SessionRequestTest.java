package com.petzila.api;

import com.petzila.api.model.Login;
import com.petzila.api.model.PetziConnect;
import com.petzila.api.model.response.PetziConnectGetResponse;
import com.petzila.api.model.response.PetziConnectRegisterResponse;
import com.petzila.api.model.response.PetziConnectStartSessionResponse;
import com.petzila.api.model.response.UserLoginResponse;
import com.petzila.api.util.Users;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by vicente on 09/01/15.
 */
public class SessionRequestTest {

    private String userKey;
    private String pzcId;

    @Before
    public void before() {
        UserLoginResponse userLoginResponse = Petzila.UserAPI.login(Users.get());
        userKey = userLoginResponse.data.token;

        pzcId = "cbee4775-0648-4d3d-b469-b7a7df9d2725";

//        PetziConnect petziConnect = new PetziConnect();
//        petziConnect.pzcName = "Test device";
//        petziConnect.isDefault = false;
//        PetziConnectRegisterResponse response = Petzila.PetziConnectAPI.register(petziConnect, pzcId, userKey);
//
//        assertNotNull(response);
//        assertEquals(response.status, "Success");
//        assertNotNull(response.data.hbsUrl);
    }

    @Test
    @Ignore
    public void testStartSessionHappyPath() {

        //start a new session
        PetziConnectStartSessionResponse response = Petzila.PetziConnectAPI.startSession(pzcId, userKey);
        assertNotNull(response);
        assertEquals(response.status, "Success");


        //ask for the newly created session
        PetziConnectGetResponse getresponse = Petzila.PetziConnectAPI.get(pzcId);


        assertNotNull(getresponse);
        assertEquals(getresponse.status, "Success");
        //assert the newly created session needs to be proccesed
        assertEquals(getresponse.data.needProcess, true);


        //ask for the newly created session
        PetziConnectGetResponse getresponse2 = Petzila.PetziConnectAPI.get(pzcId);
        assertNotNull(getresponse2);
        assertEquals(getresponse2.status, "Success");
        //assert the newly created session dont need to be proccesed as has already being handled to the HBS
        assertEquals(getresponse2.data.needProcess, false);
    }


    @Test
    @Ignore
    public void testGetStartSessionMultipleSessionsActive() {

        //start a new session
        PetziConnectStartSessionResponse response = Petzila.PetziConnectAPI.startSession(pzcId, userKey);
        assertNotNull(response);
        assertEquals(response.status, "Success");

        //start a new session for the same petzi device
        PetziConnectStartSessionResponse response2 = Petzila.PetziConnectAPI.startSession(pzcId, userKey);
        assertNotNull(response2);
        assertEquals(response2.status, "Success");


        //ask for the newly created session
        PetziConnectGetResponse getresponse = Petzila.PetziConnectAPI.get(pzcId);


        assertNotNull(getresponse);
        assertEquals(getresponse.status, "Success");
        //assert the newly created session needs to be proccesed
        assertEquals(getresponse.data.needProcess, true);


        //ask for the newly created session
        PetziConnectGetResponse getresponse2 = Petzila.PetziConnectAPI.get(pzcId);
        assertNotNull(getresponse2);
        assertEquals(getresponse2.status, "Success");
        //assert the newly created session dont need to be proccesed as has already being handled to the HBS
        assertEquals(getresponse2.data.needProcess, false);
    }

}

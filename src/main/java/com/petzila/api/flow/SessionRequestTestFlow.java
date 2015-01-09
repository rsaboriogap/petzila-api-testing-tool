package com.petzila.api.flow;

import com.petzila.api.Petzila;
import com.petzila.api.model.Login;
import com.petzila.api.model.response.PetziConnectGetResponse;
import com.petzila.api.model.response.PetziConnectStartSessionResponse;
import com.petzila.api.model.response.UserLoginResponse;
import com.petzila.api.util.Users;

/**
 * Created by vicente on 09/01/15.
 */
public class SessionRequestTestFlow implements Flow{
    private String userKey;
    private String pzcId;

    @Override
    public String getName() {
        return "session-request-test-flow";
    }

    @Override
    public String getDescription() {
        return "creates session requests and validates success upon the operation, also validates get session request after creation returns proper data";
    }

    @Override
    public void init() {
        Login login = new Login();
        login.email = "lema017@gmail.com";
        login.password = "password";
        login.loginType = "local";

        userKey = Petzila.UserAPI.login(login).data.token;
        pzcId = "cbee4775-0648-4d3d-b469-b7a7df9d2725";


    }

    @Override
    public long run() throws Exception {
        //start a new session
        PetziConnectStartSessionResponse response = Petzila.PetziConnectAPI.startSession(pzcId, userKey);
        //ask for the newly created session
        return Petzila.PetziConnectAPI.get(pzcId).report.duration;
    }
}

package com.petzila.api.util;

import com.petzila.api.model.Login;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by rylexr on 28/11/14.
 */
public class Users {
    private static Random random = new Random();
    private static List<Login> logins = new ArrayList<>();
    static {
        Login login = new Login();
        login.email = "test-user-1@wearegap.com";
        login.password = "password";
        login.loginType = "local";
        logins.add(login);
        Login login2 = new Login();
        login2.email = "test-user-2@wearegap.com";
        login2.password = "password";
        login2.loginType = "local";
        logins.add(login2);
        Login login3 = new Login();
        login3.email = "test-user-3@wearegap.com";
        login3.password = "password";
        login3.loginType = "local";
        logins.add(login3);
    }

    public static Login get() {
        return logins.get(random.nextInt(logins.size()));
    }
}

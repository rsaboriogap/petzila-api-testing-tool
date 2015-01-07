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
        login.email = "rsaborio@wearegap.com";
        login.password = "qwerty123";
        login.loginType = "local";
        logins.add(login);
        // @TODO crear diferentes "test users"
    }

    public static Login get() {
        return logins.get(random.nextInt(logins.size()));
    }
}

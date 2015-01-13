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
    private static Login admin;
    static {
        Login login = new Login();
        login.email = "test-user-1@wearegap.com";
        login.password = "password";
        login.loginType = "local";
        logins.add(login);

        login = new Login();
        login.email = "test-user-2@wearegap.com";
        login.password = "password";
        login.loginType = "local";
        logins.add(login);

        // This is an admin user
        admin = new Login();
        admin.email = "test-user-3@wearegap.com";
        admin.password = "password";
        admin.loginType = "local";
    }

    public static Login random() {
        return logins.get(random.nextInt(logins.size())).clone();
    }

    public static Login get(int index) {
        return logins.get(index).clone();
    }

    public static Login admin() {
        return admin.clone();
    }
}

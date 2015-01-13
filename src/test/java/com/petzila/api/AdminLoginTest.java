package com.petzila.api;

import com.petzila.api.model.response.AdminLoginResponse;
import com.petzila.api.util.Users;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by rylexr on 13/01/15.
 */
public class AdminLoginTest {
    @Test
    public void testAdminLoginHappyPath() {
        AdminLoginResponse response = Petzila.AdminAPI.login(Users.admin());

        assertNotNull(response);
        assertEquals(response.status, "Success");
    }
}

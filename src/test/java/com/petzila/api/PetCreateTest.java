package com.petzila.api;

import com.petzila.api.model.Login;
import com.petzila.api.model.Pet;
import com.petzila.api.model.response.PetCreateResponse;
import com.petzila.api.util.Utils;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by rsaborio on 02/12/14.
 */
public class PetCreateTest {
    private String userKey;

    @Before
    public void before() {
        Login login = new Login();
        login.email = "rsaborio@wearegap.com";
        login.password = "qwerty123";
        login.loginType = "local";
        userKey = Petzila.UserAPI.login(login).data.token;
    }

    @Test
    public void testPetCreateHappyPath() throws Exception {
        Pet pet = new Pet();
        pet.name = "Malú";
        pet.age = "10 - 15";
        pet.species = "canine";
        pet.size = "small";
        pet.breed = "Cocker Spaniel";
        pet.gender = "female";
        pet.profilePicture = Utils.asBase64("/dog1.jpg");
        pet.resourceType = "image/jpeg";
        PetCreateResponse response = Petzila.PetAPI.create(pet, userKey);

        assertNotNull(response);
        assertEquals(response.status, "Success");
    }
}

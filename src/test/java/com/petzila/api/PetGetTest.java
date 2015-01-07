package com.petzila.api;

import com.petzila.api.model.Pet;
import com.petzila.api.model.response.PetCreateResponse;
import com.petzila.api.model.response.PetGetResponse;
import com.petzila.api.util.Users;
import com.petzila.api.util.Utils;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by rsaborio on 02/12/14.
 */
public class PetGetTest {
    private String userKey;

    @Before
    public void before() {
        userKey = Petzila.UserAPI.login(Users.get()).data.token;
    }

    @Test
    public void testPetGetHappyPath() throws Exception {
        Pet pet = new Pet();
        pet.name = "Malú";
        pet.age = "10 - 15";
        pet.species = "canine";
        pet.size = "small";
        pet.breed = "Cocker Spaniel";
        pet.gender = "female";
        pet.profilePicture = Utils.asBase64("/dog1.jpg");
        pet.resourceType = "image/jpeg";
        PetCreateResponse petCreateResponse = Petzila.PetAPI.create(pet, userKey);

        assertNotNull(petCreateResponse);
        assertEquals(petCreateResponse.status, "Success");

        PetGetResponse petGetResponse = Petzila.PetAPI.get(petCreateResponse.data.id);

        assertNotNull(petGetResponse);
        assertEquals(petGetResponse.status, "Success");
        assertEquals(petGetResponse.data.pet.name, pet.name);
        assertEquals(petGetResponse.data.pet.age, pet.age);
        assertEquals(petGetResponse.data.pet.species, pet.species);
        assertEquals(petGetResponse.data.pet.size, pet.size);
        assertEquals(petGetResponse.data.pet.breed, pet.breed);
        assertEquals(petGetResponse.data.pet.gender, pet.gender);
    }
}

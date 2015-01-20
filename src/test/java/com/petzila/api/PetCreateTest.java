package com.petzila.api;

import com.petzila.api.model.Pet;
import com.petzila.api.model.response.*;
import com.petzila.api.util.Users;
import com.petzila.api.util.Utils;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by rsaborio on 02/12/14.
 */
public class PetCreateTest {
    private static String userKey;
    private static String userId;

    @BeforeClass
    public static void before() {
        UserLoginResponse userLoginResponse = Petzila.UserAPI.login(Users.random());
        userKey=userLoginResponse.data.token;
        userId=userLoginResponse.data.id;
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

    @Test
    public void testPetCreateVerifyCounters() throws Exception {
        UserLoginResponse userLoginResponse = Petzila.UserAPI.login(Users.random());
        userKey=userLoginResponse.data.token;
        int beforeLoginPetCounter = Integer.parseInt(userLoginResponse.data.petsCount);

        //bring pets counter for user
        UserPetGetResponse userPetGetResponseBefore=Petzila.UserAPI.getPets(userKey,userId);
        int petCounterBefore=userPetGetResponseBefore.data.length;

       UserGetResponse userGetResponseBefore= Petzila.UserAPI.getUser(userKey,userId);
        int petsCounterBefore=Integer.parseInt(userGetResponseBefore.data.petsCount);

        //verify both counters are equal
        assertTrue(petCounterBefore==petsCounterBefore);
        assertTrue(petCounterBefore==beforeLoginPetCounter);


        //add new pet
        Pet pet = new Pet();
        pet.name = "test pet";
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

        //        validate proper pet creation response
        assertNotNull(petGetResponse);
        assertEquals(petGetResponse.status, "Success");
        assertEquals(petGetResponse.data.pet.name, pet.name);
        assertEquals(petGetResponse.data.pet.age, pet.age);
        assertEquals(petGetResponse.data.pet.species, pet.species);
        assertEquals(petGetResponse.data.pet.size, pet.size);
        assertEquals(petGetResponse.data.pet.breed, pet.breed);
        assertEquals(petGetResponse.data.pet.gender, pet.gender);

       // verify counter updated properly
        //bring pets counter for user
        UserPetGetResponse userPetGetResponseAfter=Petzila.UserAPI.getPets(userKey,userId);
        int petCounterAfter=userPetGetResponseAfter.data.length;

        UserGetResponse userGetResponseAfter= Petzila.UserAPI.getUser(userKey,userId);
        int petsCounterAfter=Integer.parseInt(userGetResponseAfter.data.petsCount);

        UserLoginResponse userLoginResponseBefore = Petzila.UserAPI.login(Users.random());
        int afterLoginPetCounter = Integer.parseInt(userLoginResponseBefore.data.petsCount);

        //verify both counters are equal
        assertTrue(petCounterAfter==petsCounterAfter);

        assertTrue(petCounterBefore+1==petCounterAfter);
        assertTrue(petCounterBefore+1==afterLoginPetCounter);

    }
}

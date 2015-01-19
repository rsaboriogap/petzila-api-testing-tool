package com.petzila.api;

import com.petzila.api.model.Pet;
import com.petzila.api.model.Post;
import com.petzila.api.model.response.*;
import com.petzila.api.util.Users;
import com.petzila.api.util.Utils;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static com.google.common.collect.Range.greaterThan;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by vicente on 19/01/15.
 */
public class PetFollowTest {
    private static String userKey;
    private static String userKey2;

    private static String petId;

    @BeforeClass
    public static void before() throws Exception {
        userKey = Petzila.UserAPI.login(Users.get(0)).data.token;
        userKey2 = Petzila.UserAPI.login(Users.get(1)).data.token;

        Pet pet = new Pet();
        pet.name = "test-pet-1";
        pet.age = "10 - 15";
        pet.species = "canine";
        pet.size = "small";
        pet.breed = "Cocker Spaniel";
        pet.gender = "female";
        pet.profilePicture = Utils.asBase64("/dog1.jpg");
        pet.resourceType = "image/jpeg";
        petId = Petzila.PetAPI.create(pet, userKey).data.id;

    }

    @Before
    public void beforeEachTest() throws Exception {
        PetFollowUnfollowResponse response =Petzila.PetAPI.unfollow(petId, userKey2);
        assertNotNull(response);
        assertEquals(response.status, "Success");
        assertTrue(response.data.followersCounter >=0);
    }

    @Test
    public void testFollowHappyPath() throws Exception {
        PetFollowersResponse petFollowersResponseBefore = Petzila.PetAPI.getFollowers(petId);

        PetFollowUnfollowResponse response =Petzila.PetAPI.follow(petId,userKey2);
        assertNotNull(response);
        assertEquals(response.status, "Success");
        assertTrue(response.data.followersCounter >=0);

        PetFollowersResponse petFollowersResponseAfter = Petzila.PetAPI.getFollowers(petId);
        assertTrue(petFollowersResponseBefore.data.length+1 == petFollowersResponseAfter.data.length);

    }

    @Test
    public void testFollowConcurrencyIntegrityTest() throws Exception {
        PetFollowUnfollowResponse response =Petzila.PetAPI.follow(petId,userKey2);
        assertNotNull(response);
        assertEquals(response.status, "Success");

        int followersCounter= response.data.followersCounter;
        assertTrue(response.data.followersCounter >=0);


        PetFollowUnfollowResponse response2 =Petzila.PetAPI.follow(petId,userKey2);
        assertNotNull(response2);
        assertEquals(response2.status, "Success");
        assertTrue(response2.data.followersCounter == followersCounter);

        PetFollowUnfollowResponse response3 =Petzila.PetAPI.follow(petId,userKey2);
        assertNotNull(response3);
        assertEquals(response3.status, "Success");
        assertTrue(response3.data.followersCounter == followersCounter);

        PetFollowUnfollowResponse response4 =Petzila.PetAPI.follow(petId,userKey2);
        assertNotNull(response4);
        assertEquals(response4.status, "Success");
        assertTrue(response4.data.followersCounter == followersCounter);

        PetFollowUnfollowResponse response5 =Petzila.PetAPI.follow(petId,userKey2);
        assertNotNull(response5);
        assertEquals(response5.status, "Success");
        assertTrue(response5.data.followersCounter == followersCounter);
    }
}

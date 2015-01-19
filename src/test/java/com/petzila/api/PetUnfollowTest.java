package com.petzila.api;

import com.petzila.api.model.Pet;
import com.petzila.api.model.response.PetFollowUnfollowResponse;
import com.petzila.api.model.response.PetFollowersResponse;
import com.petzila.api.util.Users;
import com.petzila.api.util.Utils;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by vicente on 19/01/15.
 */
public class PetUnfollowTest {
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
    public void beforeEachTest()throws Exception{
        PetFollowUnfollowResponse response =Petzila.PetAPI.follow(petId,userKey2);
        assertNotNull(response);
        assertEquals(response.status, "Success");
    }

    @Test
    public void testUnFollowHappyPath() throws Exception {
        PetFollowersResponse petFollowersResponseBefore = Petzila.PetAPI.getFollowers(petId);

        PetFollowUnfollowResponse response =Petzila.PetAPI.unfollow(petId, userKey2);
        assertNotNull(response);
        assertEquals(response.status, "Success");
        assertTrue(response.data.followersCounter >=0);

        PetFollowersResponse petFollowersResponseAfter= Petzila.PetAPI.getFollowers(petId);
        assertTrue(petFollowersResponseBefore.data.length-1 == petFollowersResponseAfter.data.length);
    }

    @Test
    public void testUnFollowConcurrencyIntegrityTest() throws Exception {
        PetFollowersResponse petFollowersResponseBefore = Petzila.PetAPI.getFollowers(petId);
        int followersCounter=petFollowersResponseBefore.data.length ;

        PetFollowUnfollowResponse responseUnFollow =Petzila.PetAPI.unfollow(petId, userKey2);
        assertNotNull(responseUnFollow);
        assertEquals(responseUnFollow.status, "Success");

        assertTrue(responseUnFollow.data.followersCounter >= 0);
        assertTrue(followersCounter-1==responseUnFollow.data.followersCounter);

        PetFollowUnfollowResponse responseUnFollow2 =Petzila.PetAPI.unfollow(petId, userKey2);
        assertNotNull(responseUnFollow2);
        assertEquals(responseUnFollow2.status, "Success");
        assertTrue(responseUnFollow2.data.followersCounter >= 0);
        assertTrue(followersCounter-1==responseUnFollow2.data.followersCounter);

        PetFollowUnfollowResponse responseUnFollow3 =Petzila.PetAPI.unfollow(petId, userKey2);
        assertNotNull(responseUnFollow3);
        assertEquals(responseUnFollow3.status, "Success");
        assertTrue(responseUnFollow3.data.followersCounter >= 0);
        assertTrue(followersCounter-1==responseUnFollow3.data.followersCounter);
    }
}

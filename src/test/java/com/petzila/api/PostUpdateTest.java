package com.petzila.api;

import com.petzila.api.model.Login;
import com.petzila.api.model.Pet;
import com.petzila.api.model.Post;
import com.petzila.api.model.response.*;
import com.petzila.api.util.Utils;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by rsaborio on 17/12/14.
 */
public class PostUpdateTest {
    private String userId;
    private String userKey;
    private String petId;
    private Pet pet;

    @Before
    public void before() throws Exception {
        Login login = new Login();
        login.email = "rsaborio@wearegap.com";
        login.password = "qwerty123";
        login.loginType = "local";
        UserLoginResponse response = Petzila.UserAPI.login(login);
        userId = response.data.id;
        userKey = response.data.token;

        pet = new Pet();
        pet.name = "Malú";
        pet.age = "10 - 15";
        pet.species = "canine";
        pet.size = "small";
        pet.breed = "Cocker Spaniel";
        pet.gender = "female";
        pet.profilePicture = Utils.asBase64("/dog3.jpg");
        pet.resourceType = "image/jpeg";
        petId = Petzila.PetAPI.create(pet, userKey).data.id;
    }

    @Test
    public void testPostUpdateHappyPath() throws Exception {
        Post post = new Post();
        post.petId = petId;
        post.description = "This is my little awesome dog!";
        post.replacePetProfilePicture = false;
        PostCreateResponse postCreateResponse = Petzila.PostAPI.createBinary(post, userKey, Utils.asTempFile("/dog4.jpg"));

        assertNotNull(postCreateResponse);
        assertEquals(postCreateResponse.status, "Success");

        PostGetStreamResponse postGetStreamResponse = Petzila.PostAPI.getMyPets(userId);

        assertNotNull(postGetStreamResponse);
        assertEquals(postGetStreamResponse.status, "Success");
        assertNotNull(postGetStreamResponse.data);
        assertTrue(postGetStreamResponse.data.length > 0);
        assertEquals(postCreateResponse.data.id, postGetStreamResponse.data[0].id);

        pet.name = "Malú - updated";
        PetUpdateResponse petUpdateResponse = Petzila.PetAPI.update(pet, petId, userKey);

        assertNotNull(petUpdateResponse);
        assertEquals(petUpdateResponse.status, "Success");

        Thread.sleep(2000); // backend works with update-queues so we have to wait while it finishes :/

        postGetStreamResponse = Petzila.PostAPI.getMyPets(userId);
        assertNotNull(postGetStreamResponse);
        assertEquals(postGetStreamResponse.status, "Success");
        assertEquals(pet.name, postGetStreamResponse.data[0].pet.name);
    }
}

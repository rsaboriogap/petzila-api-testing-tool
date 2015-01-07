package com.petzila.api;

import com.petzila.api.model.Pet;
import com.petzila.api.model.Post;
import com.petzila.api.model.response.PostCreateResponse;
import com.petzila.api.util.Users;
import com.petzila.api.util.Utils;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by rsaborio on 02/12/14.
 */
public class PostCreateTest {
    private String userKey;
    private String petId;

    @Before
    public void before() throws Exception {
        userKey = Petzila.UserAPI.login(Users.get()).data.token;

        Pet pet = new Pet();
        pet.name = "Malú";
        pet.age = "10 - 15";
        pet.species = "canine";
        pet.size = "small";
        pet.breed = "Cocker Spaniel";
        pet.gender = "female";
        pet.profilePicture = Utils.asBase64("/dog1.jpg");
        pet.resourceType = "image/jpeg";
        petId = Petzila.PetAPI.create(pet, userKey).data.id;
    }

    @Test
    public void testPostCreateMultipartHappyPath() throws Exception {
        Post post = new Post();
        post.petId = petId;
        post.description = "This is my awesome dog!";
        post.replacePetProfilePicture = false;
        PostCreateResponse response = Petzila.PostAPI.createBinary(post, userKey, Utils.asTempFile("/dog1.jpg"));

        assertNotNull(response);
        assertEquals(response.status, "Success");
    }
}

package com.petzila.api;

import com.petzila.api.model.Pet;
import com.petzila.api.model.Post;
import com.petzila.api.model.response.ErrorResponse;
import com.petzila.api.model.response.PetGetResponse;
import com.petzila.api.model.response.PostCreateResponse;
import com.petzila.api.util.Users;
import com.petzila.api.util.Utils;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.ws.rs.BadRequestException;

import static org.junit.Assert.*;

/**
 * Created by rsaborio on 02/12/14.
 */
public class PostCreateTest {
    private static String userKey;
    private static String petId;

    @BeforeClass
    public static void before() throws Exception {
        userKey = Petzila.UserAPI.login(Users.random()).data.token;

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

    @Test
    public void testPostCreateVerifyCounter() throws Exception {
        int beforeCounter = Petzila.PetAPI.get(petId).data.postCount;
        assertTrue(beforeCounter >= 0);

        Post post = new Post();
        post.petId = petId;
        post.description = "This is my awesome dog!";
        post.replacePetProfilePicture = false;
        post.mediaIds = "http://images.digitalmedianet.com/2005/Week_8/ns6rwb8c/story/flower4megapixel.jpg";
        PostCreateResponse response = Petzila.PostAPI.create(post, userKey);

        assertNotNull(response);
        assertEquals(response.status, "Success");

        int afterCounter = Petzila.PetAPI.get(petId).data.postCount;
        assertEquals(beforeCounter + 1, afterCounter);
    }

    @Test
    public void testPostCreateBinaryVerifyCounter() throws Exception {
        int beforeCounter = Petzila.PetAPI.get(petId).data.postCount;
        assertTrue(beforeCounter >= 0);

        Post post = new Post();
        post.petId = petId;
        post.description = "This is my awesome dog!";
        post.replacePetProfilePicture = false;
        PostCreateResponse response = Petzila.PostAPI.createBinary(post, userKey, Utils.asTempFile("/dog1.jpg"));

        assertNotNull(response);
        assertEquals(response.status, "Success");

        int afterCounter = Petzila.PetAPI.get(petId).data.postCount;
        assertEquals(beforeCounter + 1, afterCounter);
    }

    @Test
    public void testPostCreateBase64VerifyCounter() throws Exception {
        int beforeCounter = Petzila.PetAPI.get(petId).data.postCount;
        assertTrue(beforeCounter >= 0);

        Post post = new Post();
        post.petId = petId;
        post.description = "This is my awesome dog!";
        post.replacePetProfilePicture = false;
        post.content = Utils.asBase64("/dog1.jpg");
        post.resourceType = "image/jpeg";
        PostCreateResponse response = Petzila.PostAPI.createBase64(post, userKey);

        assertNotNull(response);
        assertEquals(response.status, "Success");

        int afterCounter = Petzila.PetAPI.get(petId).data.postCount;
        assertEquals(beforeCounter + 1, afterCounter);
    }
}

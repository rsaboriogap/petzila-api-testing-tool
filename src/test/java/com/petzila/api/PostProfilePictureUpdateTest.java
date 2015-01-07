package com.petzila.api;

import com.petzila.api.model.Pet;
import com.petzila.api.model.Post;
import com.petzila.api.model.response.*;
import com.petzila.api.util.Users;
import com.petzila.api.util.Utils;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.BadRequestException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by rsaborio on 02/12/14.
 */
public class PostProfilePictureUpdateTest {
    private String userKey;
    private String petId;
    private PetCreateResponse petCreateResponse;
    private PostCreateResponse postResponse1;
    private PostCreateResponse postResponse2;

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
        try {
            petCreateResponse = Petzila.PetAPI.create(pet, userKey);
            petId = petCreateResponse.data.id;
        } catch (BadRequestException bre) {
            bre.printStackTrace();
            System.err.println(bre.getResponse().readEntity(ErrorResponse.class));
        }

        Post post = new Post();
        post.petId = petId;
        post.description = "This is my awesome dog!";
        post.replacePetProfilePicture = false;
        postResponse1 = Petzila.PostAPI.createBinary(post, userKey, Utils.asTempFile("/dog1.jpg"));

        post = new Post();
        post.petId = petId;
        post.description = "Super awesome dog!";
        post.replacePetProfilePicture = false;
        postResponse2 = Petzila.PostAPI.createBinary(post, userKey, Utils.asTempFile("/dog2.jpg"));
    }

    @Test
    public void testPostProfilePictureUpdateHappyPath() {
        PetGetResponse petGetResponse = Petzila.PetAPI.get(petId);
        assertNotNull(petGetResponse);
        assertEquals(petGetResponse.status, "Success");
        assertEquals(petGetResponse.data.pet.profilePicture, petCreateResponse.data.profilePicture);

        PetUpdateResponse petUpdateResponse = Petzila.PostAPI.updateProfilePicture(postResponse1.data.id, userKey);

        assertNotNull(petUpdateResponse);
        assertEquals(petUpdateResponse.status, "Success");
        assertEquals(petUpdateResponse.data.profilePicture, postResponse1.data.postPicture);

        petGetResponse = Petzila.PetAPI.get(petId);
        assertNotNull(petGetResponse);
        assertEquals(petGetResponse.status, "Success");
        assertEquals(petGetResponse.data.pet.profilePicture, postResponse1.data.postPicture);

        petUpdateResponse = Petzila.PostAPI.updateProfilePicture(postResponse2.data.id, userKey);

        assertNotNull(petUpdateResponse);
        assertEquals(petUpdateResponse.status, "Success");
        assertEquals(petUpdateResponse.data.profilePicture, postResponse2.data.postPicture);

        petGetResponse = Petzila.PetAPI.get(petId);
        assertNotNull(petGetResponse);
        assertEquals(petGetResponse.status, "Success");
        assertEquals(petGetResponse.data.pet.profilePicture, postResponse2.data.postPicture);
    }
}

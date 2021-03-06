package com.petzila.api;

import com.petzila.api.model.Comment;
import com.petzila.api.model.Pet;
import com.petzila.api.model.Post;
import com.petzila.api.model.response.PostCommentCreateResponse;
import com.petzila.api.util.Users;
import com.petzila.api.util.Utils;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by rsaborio on 02/12/14.
 */
public class PostCommentCreateTest {
    private String userKey;
    private String postId;

    @Before
    public void before() throws Exception {
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
        String petId = Petzila.PetAPI.create(pet, userKey).data.id;

        Post post = new Post();
        post.petId = petId;
        post.description = "This is my awesome dog!";
        post.replacePetProfilePicture = false;
        postId = Petzila.PostAPI.createBinary(post, userKey, Utils.asTempFile("/dog1.jpg")).data.id;
    }

    @Test
    public void testPostCommentCreateHappyPath() {
        Comment comment = new Comment();
        comment.comment = "This is a new comment";
        comment.postId = postId;
        PostCommentCreateResponse response = Petzila.PostAPI.createComment(comment, userKey);

        assertNotNull(response);
        assertEquals(response.status, "Success");
    }
}

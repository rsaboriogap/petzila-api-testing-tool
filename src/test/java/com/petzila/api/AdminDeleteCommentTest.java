package com.petzila.api;

import com.petzila.api.model.Comment;
import com.petzila.api.model.Pet;
import com.petzila.api.model.Post;
import com.petzila.api.model.response.AdminCommentDeleteResponse;
import com.petzila.api.model.response.ErrorResponse;
import com.petzila.api.util.Users;
import com.petzila.api.util.Utils;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.BadRequestException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by rylexr on 13/01/15.
 */
public class AdminDeleteCommentTest {
    private String postId;
    private String commentId;
    private String adminKey;

    @Before
    public void before() throws Exception {
        String userKey = Petzila.UserAPI.login(Users.random()).data.token;

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

        Comment comment = new Comment();
        comment.comment = "This is a new comment";
        comment.postId = postId;
        commentId = Petzila.PostAPI.createComment(comment, userKey).data.id;

        adminKey = Petzila.AdminAPI.login(Users.admin()).data.token;
    }

    @Test
    public void testAdminDeleteCommentHappyPath() {
        AdminCommentDeleteResponse response = Petzila.AdminAPI.deleteComment(postId, commentId, adminKey);

        assertNotNull(response);
        assertEquals(response.status, "Success");
        assertEquals(response.data.message, "OK");

        try {
            Petzila.AdminAPI.deleteComment(postId, commentId, adminKey);
        } catch (BadRequestException bre) {
            ErrorResponse er = bre.getResponse().readEntity(ErrorResponse.class);

            assertNotNull(er);
            assertEquals("Fail", er.status);
            assertEquals(831, er.code);
            assertEquals("Error deleting the comment.", er.data.message);
        }
    }
}

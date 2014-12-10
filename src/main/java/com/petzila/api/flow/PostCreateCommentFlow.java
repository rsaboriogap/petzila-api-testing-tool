package com.petzila.api.flow;

import com.petzila.api.Petzila;
import com.petzila.api.model.Comment;
import com.petzila.api.model.Login;
import com.petzila.api.model.Pet;
import com.petzila.api.model.Post;
import com.petzila.api.model.response.PostCreateResponse;
import com.petzila.api.util.Utils;

/**
 * Created by rylexr on 05/12/14.
 */
public final class PostCreateCommentFlow implements Flow {
    private String userKey;
    private String petId;
    private String postId;

    @Override
    public String getName() {
        return "post-create-comment-flow";
    }

    @Override
    public String getDescription() {
        return "Creates a new comment using the /post/comment endpoint";
    }

    @Override
    public void init() {
        Login login = new Login();
        login.email = "rsaborio@wearegap.com";
        login.password = "qwerty123";
        login.loginType = "local";
        userKey = Petzila.UserAPI.login(login).data.token;

        Pet pet = new Pet();
        pet.name = "Malú";
        pet.age = "10 - 15";
        pet.species = "canine";
        pet.size = "small";
        pet.breed = "Cocker Spaniel";
        pet.gender = "female";
        try {
            pet.profilePicture = Utils.asBase64("/dog1.jpg");
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
        pet.resourceType = "image/jpeg";
        petId = Petzila.PetAPI.create(pet, userKey).data.id;

        Post post = new Post();
        post.petId = petId;
        post.description = "This is my awesome dog!";
        post.replacePetProfilePicture = false;
        try {
            postId = Petzila.PostAPI.createBinary(post, userKey, Utils.asTempFile("/dog1.jpg")).data.id;
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    @Override
    public long run() {
        Comment comment = new Comment();
        comment.comment = "This is a new comment";
        comment.postId = postId;
        return Petzila.PostAPI.createComment(comment, userKey).report.duration;
    }
}
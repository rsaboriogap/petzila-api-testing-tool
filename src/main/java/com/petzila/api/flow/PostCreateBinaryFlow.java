package com.petzila.api.flow;

import com.petzila.api.Petzila;
import com.petzila.api.model.Login;
import com.petzila.api.model.Pet;
import com.petzila.api.model.Post;
import com.petzila.api.util.Utils;

/**
 * Created by rylexr on 08/12/14.
 */
public class PostCreateBinaryFlow implements Flow {
    private String userKey;
    private String petId;

    public PostCreateBinaryFlow() throws Exception {
        Login login = new Login();
        login.email = "antaria@gmail.com";
        login.password = "password";
        login.loginType = "local";
        userKey = Petzila.UserAPI.login(login).data.token;

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

    public String getName() {
        return "post-create-binary-flow";
    }

    public String getDescription() {
        return "Creates a new post using binary approach";
    }

    public long run() {
        Post post = new Post();
        post.petId = petId;
        post.description = "This is my awesome dog!";
        post.replacePetProfilePicture = false;
        post.content = Utils.asFilename("/dog1.jpg");
        return Petzila.PostAPI.createBinary(post, userKey).report.duration;
    }
}
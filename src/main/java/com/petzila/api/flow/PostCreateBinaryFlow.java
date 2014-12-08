package com.petzila.api.flow;

import com.petzila.api.Petzila;
import com.petzila.api.model.Login;
import com.petzila.api.model.Pet;
import com.petzila.api.model.Post;
import com.petzila.api.util.Utils;

/**
 * Created by rylexr on 08/12/14.
 */
public final class PostCreateBinaryFlow implements Flow {
    private String userKey;
    private String petId;

    @Override
    public String getName() {
        return "post-create-binary-flow";
    }

    @Override
    public String getDescription() {
        return "Creates a new post using binary approach";
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
    }

    @Override
    public long run() throws Exception {
        Post post = new Post();
        post.petId = petId;
        post.description = "This is my awesome dog!";
        post.replacePetProfilePicture = false;
        return Petzila.PostAPI.createBinary(post, userKey, Utils.asTempFile("/dog1.jpg")).report.duration;
    }
}
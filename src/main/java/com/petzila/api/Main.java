package com.petzila.api;

import com.petzila.api.model.*;
import com.petzila.api.util.Utils;
import org.apache.commons.cli.*;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by rsaborio on 19/11/14.
 */
public class Main {
    private static final int DEFAULT_THREAD_COUNT = 5;
//    private static final String DEFAULT_MEDIA_FILENAME = "/media/work/development/projects/gap/petzila/java/post-test/src/main/resources/dog1.jpg";
    private static final String DEFAULT_MEDIA_FILENAME = "/media/rsaborio/development/projects/petzila/java/post-test/src/main/resources/dog1.jpg";

    private static Options options = new Options();

    static {
        options.addOption("t", true, "Number of concurrent Threads");
        options.addOption("f", true, "File to upload (e.g. dog1.jpg, dog2.jpg, ..., dog5.jpg)");
        options.addOption("e", true, "Environment to test (l=localhost, qa4=qa4.petzila.com)");
    }

    public static void main(String[] args) throws Exception {
        CommandLineParser parser = new BasicParser();
        final CommandLine cl = parser.parse(options, args);

        int threadCount = DEFAULT_THREAD_COUNT;
        String mediaFilename = DEFAULT_MEDIA_FILENAME;
        String environment = Petzila.DEFAULT_ENVIRONMENT_URL;
        if (cl.hasOption("t")) {
            threadCount = Integer.parseInt(cl.getOptionValue('t'));
        }
        if (cl.hasOption("f")) {
            mediaFilename = cl.getOptionValue('f');
        }
        if (cl.hasOption("e")) {
            environment = cl.getOptionValue('e');
        }

//        Petzila.environment = environment;

//        SignUp signUp = new SignUp();
//        signUp.username = "rsaborio";
//        signUp.email = "rsaborio@wearegap.com";
//        signUp.password = "qwerty123";
//        signUp.profilePicture = Utils.asBase64(DEFAULT_MEDIA_FILENAME);
//        signUp.resourceType = "image/jpeg";
//        signUp.description = "My test account";
//        signUp.signupType = "local";
//        signUp.name.firstName = "Randy";
//        signUp.name.lastName = "Saborio";
//        signUp.location.country = "Costa Rica";
//        signUp.location.city = "San José";
//        signUp.location.zipCode = "1000";
//        SignUpResponse signUpResponse = Petzila.User.signup(signUp);
//
//        if (true)
//            System.exit(0);


        Login login = new Login();
        login.email = "rsaborio@wearegap.com";
        login.password = "qwerty123";
        login.loginType = "local";
        final String token = Petzila.User.login(login).data.token;
        System.out.println("Token = " + token);

        Pet pet = new Pet();
        pet.name = "Malú";
        pet.age = "10 - 15";
        pet.species = "canine";
        pet.size = "small";
        pet.breed = "Cocker Spaniel";
        pet.gender = "female";
        pet.profilePicture = Utils.asBase64(DEFAULT_MEDIA_FILENAME);
        pet.resourceType = "image/jpeg";

        PetCreateResponse petCreateResponse;
        try {
            petCreateResponse = Petzila.PetAPI.create(pet, token);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

//        Media media1 = new Media();
//        media1.filename = Main.class.getResource(mediaFilename).getFile();
//        media1.filename = new File(mediaFilename).toString();
//        MediaResponse response = Petzila.upload(media1, token);
//        System.out.println(response);



        final Post post = new Post();
        post.petId = petCreateResponse.data.id;
        post.description = "This is a new test 2";
        post.replacePetProfilePicture = false;
//        post.mediaIds = response.data.mediaId;

        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        final String content = Utils.asBase64(mediaFilename);
//        final String filename = Main.class.getResource(mediaFilename).getFile();
        final String filename = new File(mediaFilename).toString();
        final byte[] media = Utils.asBinary(mediaFilename);
        post.resourceType = "image/jpeg";
        for (int i = 0; i < threadCount; i++) {
            final int id = i;
            executor.execute(new Runnable() {
                long start;

                @Override
                public void run() {
//                    start = System.currentTimeMillis();
//                    Petzila.post(post, token);
//                    long duration0 = System.currentTimeMillis() - start;
//                    System.out.println(id + " JSON - Duration ms: " + duration0);
//
//                    post.content = content;
//                    post.media = null;
//                    start = System.currentTimeMillis();
//                    Petzila.postBase64(post, token);
//                    long duration1 = System.currentTimeMillis() - start;
//                    System.out.println(id + " Base64 - Duration ms: " + duration1);

                    post.content = filename;
                    post.media = media;
                    start = System.currentTimeMillis();
                    Petzila.postBinary(post, token);
                    long duration2 = System.currentTimeMillis() - start;
                    System.out.println(id + " Binary - Duration ms: " + duration2);

//                    System.out.printf(id + " Time saved (binary vs base64) - ms: %.2f%%\n", (1.0 - (duration2 / (float) duration1)) * 100.0);
//                    System.out.printf(id + " Time saved (json vs binary) - ms: %.2f%%\n", (1.0 - (duration0 / (float) duration2)) * 100.0);
//                    System.out.printf(id + " Time saved (json vs base64) - ms: %.2f%%\n", (1.0 - (duration0 / (float) duration1)) * 100.0);
                }
            });
        }
        executor.shutdown();
        while (!executor.isTerminated()) {
        }
    }

    private static void printHelp() {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("pat", options);
        System.exit(0);
    }
}

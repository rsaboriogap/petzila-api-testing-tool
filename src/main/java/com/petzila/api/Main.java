package com.petzila.api;

import com.petzila.api.model.*;
import com.petzila.api.model.response.PetCreateResponse;
import com.petzila.api.model.response.SignUpResponse;
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

    public static void main(String[] args) throws Exception {

//        final Post post = new Post();
//        post.petId = petCreateResponse.data.id;
//        post.description = "This is a new test 2";
//        post.replacePetProfilePicture = false;
//        post.mediaIds = response.data.mediaId;

/*        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        final String content = Utils.asBase64(mediaFilename);
//        final String media = Main.class.getResource(mediaFilename).getFile();
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
                    Petzila.PostAPI.postBinary(post, token);
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
        }*/
    }}

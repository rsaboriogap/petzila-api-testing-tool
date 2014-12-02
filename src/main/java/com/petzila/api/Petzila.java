package com.petzila.api;

import com.petzila.api.model.*;
import com.petzila.api.util.SecureRestClientTrustManager;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.media.multipart.file.FileDataBodyPart;

import javax.net.ssl.SSLContext;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import java.io.File;

/**
 * Created by rsaborio on 20/11/14.
 */
public final class Petzila {
//    public static final String DEFAULT_ENVIRONMENT_URL = "http://localhost:3000/api";
//    public static final String DEFAULT_ENVIRONMENT_URL = "https://qa4.petzila.com/api";
    public static final String DEFAULT_ENVIRONMENT_URL = "https://preview.petzila.com/api";
    public static final String PETZILA_API_VERSION = "2";
    private static final String HEADER_VERSION = "version";

    private static WebTarget target;
    static {
        try {
            target = newClient().target(DEFAULT_ENVIRONMENT_URL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Client newClient() throws Exception {
        SecureRestClientTrustManager secureRestClientTrustManager = new SecureRestClientTrustManager();
        SSLContext sslContext = SSLContext.getInstance("SSL");
        sslContext.init(null, new javax.net.ssl.TrustManager[]{secureRestClientTrustManager}, null);

        return ClientBuilder.newBuilder()
                .sslContext(sslContext)
                .build()
                .register(JacksonFeature.class)
                .register(MultiPartFeature.class);
    }

    public final static class User {
        public static SignUpResponse signup(SignUp signUp) {
            return target
                    .path("/user/signup")
                    .request()
                    .header(HEADER_VERSION, PETZILA_API_VERSION)
                    .post(Entity.entity(signUp, MediaType.APPLICATION_JSON), SignUpResponse.class);
        }

        public static LoginResponse login(Login login) {
            System.out.println(target);
            return target
                    .path("/user/login")
                    .request()
                    .header(HEADER_VERSION, PETZILA_API_VERSION)
                    .post(Entity.entity(login, MediaType.APPLICATION_JSON), LoginResponse.class);
        }
    }

    public final static class PetAPI {
        public static PetCreateResponse create(Pet pet, String userKey) {
            return target
                    .path("/pet")
                    .request()
                    .header(HEADER_VERSION, PETZILA_API_VERSION)
                    .header("userKey", userKey)
                    .post(Entity.entity(pet, MediaType.APPLICATION_JSON), PetCreateResponse.class);
        }
    }

    public static PostResponse post(Post post, String userKey) {
        return target
                .path("/post")
                .request()
                .header(HEADER_VERSION, PETZILA_API_VERSION)
                .header("userKey", userKey)
                .post(Entity.entity(post, MediaType.APPLICATION_JSON), PostResponse.class);
    }

    public static PostResponse postBinary(Post post, String userKey) {
        FormDataMultiPart mp = new FormDataMultiPart();
        mp.field("petId", post.petId);
        mp.field("description", post.description);
        mp.field("replacePetProfilePicture", String.valueOf(post.replacePetProfilePicture));
        mp.bodyPart(new FileDataBodyPart("media", new File(post.content)));

        return target
                .path("/post/binary")
                .request()
                .header(HEADER_VERSION, PETZILA_API_VERSION)
                .header("userKey", userKey)
                .post(Entity.entity(mp, MediaType.MULTIPART_FORM_DATA), PostResponse.class);
    }

    public static PostResponse postBase64(Post post, String userKey) {
        return target
                .path("/post/base64")
                .request()
                .header(HEADER_VERSION, PETZILA_API_VERSION)
                .header("userKey", userKey)
                .post(Entity.entity(post, MediaType.APPLICATION_JSON), PostResponse.class);
    }

    public static MediaResponse upload(Media media, String userKey) {
        FormDataMultiPart mp = new FormDataMultiPart();
        mp.bodyPart(new FileDataBodyPart("media", new File(media.filename)));

        return target
                .path("/media/upload")
                .request()
                .header(HEADER_VERSION, PETZILA_API_VERSION)
                .header("userKey", userKey)
                .post(Entity.entity(mp, MediaType.MULTIPART_FORM_DATA), MediaResponse.class);
    }
}

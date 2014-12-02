package com.petzila.api;

import com.petzila.api.model.*;
import com.petzila.api.model.response.*;
import com.petzila.api.util.Environments;
import com.petzila.api.util.SecureRestClientTrustManager;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.media.multipart.file.FileDataBodyPart;

import javax.net.ssl.SSLContext;
import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import java.io.File;
import java.text.MessageFormat;

/**
 * Created by rsaborio on 20/11/14.
 */
public final class Petzila {
    private static final String DEFAULT_ENVIRONMENT_URL = Environments.get() + "/api";
    private static final String PETZILA_API_VERSION = "2";
    private static final String HEADER_VERSION = "version";
    private static final String HEADER_USER_KEY = "userKey";

    // HTTP Methods
    private static final String METHOD_GET = "GET";
    private static final String METHOD_POST = "POST";
    private static final String METHOD_PUT = "PUT";
    private static final String METHOD_DELETE = "DELETE";

    private static WebTarget target;

    static {
        try {
            target = newClient().target(DEFAULT_ENVIRONMENT_URL);
            System.out.println("Target API: " + target.getUri());
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
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

    private static <T extends Response, E> T call(String path, String method, String userKey, E entity, MediaType mediaType, Class<T> responseClass) {
        long start = System.currentTimeMillis();
        T response = target
                .path(path)
                .request()
                .header(HEADER_VERSION, PETZILA_API_VERSION)
                .header(HEADER_USER_KEY, userKey)
                .method(method, javax.ws.rs.client.Entity.entity(entity, mediaType), responseClass);
        long end = System.currentTimeMillis();

        System.out.println(MessageFormat.format("{0} {1}", method, path));
        if (userKey != null)
            System.out.println(MessageFormat.format("  -- User Key: {0}", userKey));
        System.out.println(MessageFormat.format("  -- Body: {0}", entity));
        System.out.println(MessageFormat.format("  -- Content Type: {0}", mediaType));
        System.out.println(MessageFormat.format("  -- Response: {0}", response));
        System.out.println(MessageFormat.format("  -- Duration ms: {0}", end - start));
        return response;
    }

    private static <T extends Response, E> T call(String path, String method, E entity, Class<T> responseClass) {
        return call(path, method, null, entity, MediaType.APPLICATION_JSON_TYPE, responseClass);
    }

    private static <T extends Response, E> T call(String path, String method, String userKey, E entity, Class<T> responseClass) {
        return call(path, method, userKey, entity, MediaType.APPLICATION_JSON_TYPE, responseClass);
    }

    public static final class UserAPI {
        public static LoginResponse login(Login login) {
            return call("/user/login", METHOD_POST, login, LoginResponse.class);
        }

        public static SignUpResponse signup(SignUp signUp) {
            return call("/user/signup", METHOD_POST, signUp, SignUpResponse.class);
        }
    }

    public static final class PetAPI {
        public static PetCreateResponse create(Pet pet, String userKey) {
            return call("/pet", METHOD_POST, userKey, pet, PetCreateResponse.class);
        }
    }

    public static final class PostAPI {
        public static PostCreateResponse post(Post post, String userKey) {
            return call("/post", METHOD_POST, userKey, post, PostCreateResponse.class);
        }

        public static PostCreateResponse postBinary(Post post, String userKey) {
            FormDataMultiPart form = new FormDataMultiPart();
            form.field("petId", post.petId);
            form.field("description", post.description);
            form.field("replacePetProfilePicture", String.valueOf(post.replacePetProfilePicture));
            form.bodyPart(new FileDataBodyPart("media", new File(post.content)));

            return Petzila.call("/post/binary", METHOD_POST, userKey, form, MediaType.MULTIPART_FORM_DATA_TYPE, PostCreateResponse.class);
        }

        public static PostCreateResponse postBase64(Post post, String userKey) {
            return call("/post/base64", METHOD_POST, userKey, post, PostCreateResponse.class);
        }
    }

    public static final class MediaAPI {
        public static MediaUploadResponse upload(Media media, String userKey) {
            FormDataMultiPart form = new FormDataMultiPart();
            form.bodyPart(new FileDataBodyPart("media", new File(media.media)));

            return Petzila.call("/media/upload", METHOD_POST, userKey, form, MediaType.MULTIPART_FORM_DATA_TYPE, MediaUploadResponse.class);
        }
    }
}

package com.petzila.api;

import com.petzila.api.model.*;
import com.petzila.api.model.response.*;
import com.petzila.api.model.response.Response;
import com.petzila.api.util.Environments;
import com.petzila.api.util.SecureRestClientTrustManager;
import com.petzila.api.util.Utils;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.media.multipart.file.FileDataBodyPart;

import javax.net.ssl.SSLContext;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.client.*;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.*;
import java.io.File;
import java.io.PrintStream;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

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

    private static <T extends Response, E> T call(String path, Map<String, ?> queryParams, String method, String userKey, E entity, MediaType mediaType, Class<T> responseClass) {
        String query = "?";
        WebTarget wb = target;
        if (queryParams != null && !queryParams.isEmpty()) {
            for (Map.Entry<String, ?> entry : queryParams.entrySet()) {
                wb = wb.queryParam(entry.getKey(), entry.getValue());
                query += entry.getKey() + "=" + entry.getValue() + "&";
            }
        }
        if (query.endsWith("&")) {
            query = query.substring(0, query.length() - 1);
        }

        Invocation.Builder builder = wb
                .path(path)
                .request()
                .header(HEADER_VERSION, PETZILA_API_VERSION);
        if (userKey != null)
            builder.header(HEADER_USER_KEY, userKey);

        long start = System.currentTimeMillis();
        javax.ws.rs.core.Response response = builder.method(method, entity != null ? Entity.entity(entity, mediaType) : null);
        long end = System.currentTimeMillis();
        T apiResponse = null;
        if (response.getStatusInfo().getStatusCode() == javax.ws.rs.core.Response.Status.OK.getStatusCode()) {
            apiResponse = response.readEntity(responseClass);
            apiResponse.report.duration = end - start;
        }

        String logLevel = Utils.getProperty("api.loglevel");
        if (logLevel.equals("basic") || logLevel.equals("full")) {
            PrintStream ps = apiResponse != null ? System.out : System.err;
            ps.println(MessageFormat.format("HTTP/{0} {1}   {2} secs\t {3} bytes  ->  {4} {5}",
                    "1.1", //@TODO obtener la version de los headers
                    response.getStatus(),
                    (end - start) / 1000f,
                    response.getLength(),
                    method,
                    path + (query.length() > 1 ? query : "")));
            if (logLevel.equals("full")) {
                if (userKey != null)
                    ps.println(MessageFormat.format("  -- User Key: {0}", userKey));
                if (entity != null)
                    ps.println(MessageFormat.format("  -- Body: {0}", entity));
                if (apiResponse != null) {
                    ps.println(MessageFormat.format("  -- Response: {0}", apiResponse));
                }
            }
        }
        if (response.getStatusInfo().getStatusCode() != javax.ws.rs.core.Response.Status.OK.getStatusCode())
            throw new BadRequestException(response);

        return apiResponse;
    }

    private static <T extends Response, E extends com.petzila.api.model.Entity> T call(String path, String method, E entity, Class<T> responseClass) {
        return call(path, null, method, null, entity, MediaType.APPLICATION_JSON_TYPE, responseClass);
    }

    private static <T extends Response, E extends com.petzila.api.model.Entity> T call(String path, String method, String userKey, E entity, Class<T> responseClass) {
        return call(path, null, method, userKey, entity, MediaType.APPLICATION_JSON_TYPE, responseClass);
    }

    private static <T extends Response> T call(String path, String method, Class<T> responseClass) {
        return call(path, null, method, null, null, MediaType.APPLICATION_JSON_TYPE, responseClass);
    }

    private static <T extends Response> T call(String path, Map<String, ?> queryParams, String method, Class<T> responseClass) {
        return call(path, queryParams, method, null, null, MediaType.APPLICATION_JSON_TYPE, responseClass);
    }

    private static <T extends Response> T call(String path, String method, String userKey, Class<T> responseClass) {
        return call(path, null, method, userKey, null, MediaType.APPLICATION_JSON_TYPE, responseClass);
    }

    private static <T extends Response> T call(String path, Map<String, ?> queryParams, String method, String userKey, Class<T> responseClass) {
        return call(path, queryParams, method, userKey, null, MediaType.APPLICATION_JSON_TYPE, responseClass);
    }

    public static final class UserAPI {
        public static UserLoginResponse login(Login login) {
            return call("/user/login", METHOD_POST, login, UserLoginResponse.class);
        }

        public static UserSignUpResponse signup(SignUp signUp) {
            return call("/user/signup", METHOD_POST, signUp, UserSignUpResponse.class);
        }

        public static UserDeviceGetResponse getDevices(String userKey, String userId) {
            return call(MessageFormat.format("/user/{0}/devices", userId), METHOD_GET, userKey, UserDeviceGetResponse.class);
        }

        public static UserNotificationGetResponse getNotifications(String userKey) {
            return getNotifications(userKey, null, null);
        }

        public static UserNotificationGetResponse getNotifications(String userKey, Integer index, Integer count) {
            Map<String, Integer> queryParams = new HashMap<>();
            if (index != null) {
                queryParams.put("index", index);
            }
            if (count != null) {
                queryParams.put("count", count);
            }
            return call("/user/notifications", queryParams, METHOD_GET, userKey, UserNotificationGetResponse.class);
        }
    }

    public static final class PetziConnectAPI {
        public static PetziConnectGetResponse get(String petziId) {
            return call(MessageFormat.format("/petziConnect/{0}", petziId), METHOD_GET, PetziConnectGetResponse.class);
        }

        public static PetziConnectCreateResponse create(PetziConnect petziConnect, String petziId, String userKey) {
            return call(MessageFormat.format("/petziConnect/registration/{0}", petziId), METHOD_POST, userKey, petziConnect, PetziConnectCreateResponse.class);
        }

        public static PetziConnectUpdateResponse update(PetziConnect petziConnect, String petziId, String userKey) {
            return call(MessageFormat.format("/petziConnect/{0}/edit", petziId), METHOD_PUT, userKey, petziConnect, PetziConnectUpdateResponse.class);
        }

        public static PetziConnectDeleteResponse delete(String petziId, String userKey) {
            return call(MessageFormat.format("/petziConnect/{0}", petziId), METHOD_DELETE, userKey, PetziConnectDeleteResponse.class);
        }
    }

    public static final class PetAPI {
        public static PetGetResponse get(String petId) {
            return call(MessageFormat.format("/pet/{0}", petId), METHOD_GET, PetGetResponse.class);
        }

        public static PetDeleteResponse delete(String petId) {
            return call(MessageFormat.format("/pet/{0}", petId), METHOD_DELETE, PetDeleteResponse.class);
        }

        public static PetCreateResponse create(Pet pet, String userKey) {
            return call("/pet", METHOD_POST, userKey, pet, PetCreateResponse.class);
        }

        public static PetUpdateResponse update(Pet pet, String petId, String userKey) {
            return call(MessageFormat.format("/pet/{0}/edit", petId), METHOD_PUT, userKey, pet, PetUpdateResponse.class);
        }

        public static PetFollowUnfollowResponse follow(String petId, String userKey) {
            return call(MessageFormat.format("/pet/{0}/follow", petId), METHOD_PUT, userKey, PetFollowUnfollowResponse.class);
        }

        public static PetFollowUnfollowResponse unfollow(String petId, String userKey) {
            return call(MessageFormat.format("/pet/{0}/unfollow", petId), METHOD_PUT, userKey, PetFollowUnfollowResponse.class);
        }

        public static PetFollowersResponse getFollowers(String petId) {
            return getFollowers(petId, null, null);
        }

        public static PetFollowersResponse getFollowers(String petId, Integer index, Integer count) {
            Map<String, Integer> queryParams = new HashMap<>();
            if (index != null) {
                queryParams.put("index", index);
            }
            if (count != null) {
                queryParams.put("count", count);
            }
            return call(MessageFormat.format("/pet/{0}/followers", petId), queryParams, METHOD_GET, PetFollowersResponse.class);
        }
    }

    public static final class PostAPI {
        public static PostGetResponse get() {
            return call("/post", METHOD_GET, PostGetResponse.class);
        }

        public static PostGetStreamResponse getStream(String stream, String userId) {
            Map<String, String> queryParams = new HashMap<>();
            queryParams.put("userid", userId);
            return call(MessageFormat.format("/post/{0}", stream), queryParams, METHOD_GET, PostGetStreamResponse.class);
        }

        public static PostGetStreamResponse getMyPets(String userId) {
            return getStream("myPets", userId);
        }

        public static PostCreateResponse create(Post post, String userKey) {
            return call("/post", METHOD_POST, userKey, post, PostCreateResponse.class);
        }

        public static PostCreateResponse createBinary(Post post, String userKey, File media) {
            FormDataMultiPart form = new FormDataMultiPart();
            form.field("petId", post.petId);
            form.field("description", post.description);
            form.field("replacePetProfilePicture", String.valueOf(post.replacePetProfilePicture));
            form.bodyPart(new FileDataBodyPart("media", media));

            return Petzila.call("/post/binary", null, METHOD_POST, userKey, form, MediaType.MULTIPART_FORM_DATA_TYPE, PostCreateResponse.class);
        }

        public static PostCreateResponse createBase64(Post post, String userKey) {
            return call("/post/base64", METHOD_POST, userKey, post, PostCreateResponse.class);
        }

        public static PostCommentCreateResponse createComment(Comment comment, String userKey) {
            return call("/post/comment", METHOD_POST, userKey, comment, PostCommentCreateResponse.class);
        }

        public static PetUpdateResponse updateProfilePicture(String postId, String userKey) {
            return call(MessageFormat.format("/post/{0}/profile", postId), METHOD_PUT, userKey, Empty.EMPTY, PetUpdateResponse.class);
        }
    }

    public static final class MediaAPI {
        public static MediaUploadResponse upload(Media media, String userKey) {
            FormDataMultiPart form = new FormDataMultiPart();
            form.bodyPart(new FileDataBodyPart("media", media.media));

            return Petzila.call("/media/upload", null, METHOD_POST, userKey, form, MediaType.MULTIPART_FORM_DATA_TYPE, MediaUploadResponse.class);
        }
    }
}

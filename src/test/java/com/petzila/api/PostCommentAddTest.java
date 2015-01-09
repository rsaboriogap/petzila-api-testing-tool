package com.petzila.api;

import com.petzila.api.model.Comment;
import com.petzila.api.model.Login;
import com.petzila.api.model.Pet;
import com.petzila.api.model.Post;
import com.petzila.api.model.response.ErrorResponse;
import com.petzila.api.model.response.PostCommentCreateResponse;
import com.petzila.api.util.Utils;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.BadRequestException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by rsaborio on 02/12/14.
 */
public class PostCommentAddTest {
    private String userKey;
    private String postId;

    @Before
    public void before() throws Exception {
        Login login = new Login();
        login.email = "tester@wearegap.com";
        login.password = "password";
        login.loginType = "local";
        userKey = Petzila.UserAPI.login(login).data.token;
        postId = "54ac35f6161ff3f92f41a67a";
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

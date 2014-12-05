package com.petzila.api.flow;

import com.petzila.api.Petzila;
import com.petzila.api.model.Comment;

/**
 * Created by rylexr on 05/12/14.
 */
public final class PostCreateCommentFlow implements Flow {
    private final String userKey = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyIjoiNTQ3OGI2OWMzZjk4NjEwNzAxOWNiNDdhIiwiZXhwaXJhdGlvbkRhdGUiOiIyMDE0LTEyLTA1VDIxOjE1OjM4LjQ0NFoifQ.E2Pk1VRLCjCxxrhnaAeUQGv3zYjTi32xUoeafldnXRg";
    private final String postId = "5481e2d292e085d70a761516";

    @Override
    public String getName() {
        return "post-create-comment-flow";
    }

    @Override
    public String getDescription() {
        return "Creates a new comment using the /post/comment endpoint";
    }

    @Override
    public long run() {
        Comment comment = new Comment();
        comment.comment = "This is a new comment";
        comment.postId = postId;
        return Petzila.PostAPI.createComment(comment, userKey).report.duration;
    }
}
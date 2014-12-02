package com.petzila.api.model;

import com.google.common.base.MoreObjects;

/**
 * Created by rsaborio on 02/12/14.
 */
public final class Comment extends Entity {
    public String comment;
    public String postId;

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("comment", comment)
                .add("postId", postId)
                .toString();
    }
}

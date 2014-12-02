package com.petzila.api.model;

import com.google.common.base.MoreObjects;

/**
 * Created by rsaborio on 25/11/14.
 */
public class Media extends Entity {
    public String media;

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("media", media)
                .toString();
    }
}

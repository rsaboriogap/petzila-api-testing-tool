package com.petzila.api.model;

import com.google.common.base.MoreObjects;

import java.io.File;

/**
 * Created by rsaborio on 25/11/14.
 */
public final class Media extends Entity {
    public File media;

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("media", media)
                .toString();
    }
}

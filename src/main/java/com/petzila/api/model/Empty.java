package com.petzila.api.model;

import com.google.common.base.MoreObjects;

/**
 * Created by rsaborio on 16/12/14.
 *
 * Dummy class for empty body in HTTP calls.
 */
public final class Empty extends Entity {
    public static final Empty EMPTY = new Empty();
    public String empty;

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("empty", empty)
                .toString();
    }
}

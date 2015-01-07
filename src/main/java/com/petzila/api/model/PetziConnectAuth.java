package com.petzila.api.model;

import com.google.common.base.MoreObjects;

/**
 * Created by rsaborio on 09/12/14.
 */
public final class PetziConnectAuth extends Entity {
    public String pzcId;
    public String cNonce;
    public String hash;

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("pzcId", pzcId)
                .add("cNonce", cNonce)
                .add("hash", hash)
                .toString();
    }
}

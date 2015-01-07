package com.petzila.api.model;

import com.google.common.base.MoreObjects;

/**
 * Created by rsaborio on 09/12/14.
 */
public final class PetziConnectRegister extends Entity {
    public String pzcName;
    public String env;

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("pzcName", pzcName)
                .add("env", env)
                .toString();
    }
}

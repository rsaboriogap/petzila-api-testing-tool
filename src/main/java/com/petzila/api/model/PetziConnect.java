package com.petzila.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

/**
 * Created by rsaborio on 09/12/14.
 */
public final class PetziConnect extends Entity {
    public String pzcName;
    @JsonProperty("default")
    public boolean isDefault;

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("pzcName", pzcName)
                .add("default", isDefault)
                .toString();
    }
}

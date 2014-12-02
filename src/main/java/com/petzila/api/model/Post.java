package com.petzila.api.model;

import com.google.common.base.MoreObjects;

/**
 * Created by rsaborio on 19/11/14.
 */
public class Post extends Entity {
    public String petId;
    public String description;
    public String content;
    public String resourceType;
    public boolean replacePetProfilePicture;
    public byte[] media; // this name must be in sync with server side code
    public String mediaIds;

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("petId", petId)
                .add("description", description)
                .add("content", content)
                .add("resourceType", resourceType)
                .add("replacePetProfilePicture", replacePetProfilePicture)
                .add("media", media)
                .add("mediaIds", mediaIds)
                .toString();
    }
}

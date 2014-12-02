package com.petzila.api.model;

/**
 * Created by rsaborio on 19/11/14.
 */
public class Post {
    public String petId;
    public String description;
    public String content;
    public String resourceType;
    public boolean replacePetProfilePicture;
    public byte[] media; // this name must be in sync with server side code
    public String mediaIds;
}

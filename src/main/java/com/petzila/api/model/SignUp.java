package com.petzila.api.model;

/**
 * Created by rylexr on 28/11/14.
 */
public class SignUp {
    public String email;
    public String password;
    public String username;
    public String profilePicture;
    public String resourceType;
    public String description;
    public Name name = new Name();
    public Location location = new Location();
    public String signupType;
    public String socialNetworkID;
    public String facebookToken;
    public String facebookTokenType;

    public static class Name {
        public String firstName;
        public String lastName;
    }

    public static class Location {
        public String country;
        public String city;
        public String zipCode;
    }
}

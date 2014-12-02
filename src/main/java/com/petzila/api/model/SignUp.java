package com.petzila.api.model;

import com.google.common.base.MoreObjects;

/**
 * Created by rylexr on 28/11/14.
 */
public final class SignUp extends Entity {
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

        @Override
        public String toString() {
            return MoreObjects.toStringHelper(this)
                    .add("firstName", firstName)
                    .add("lastName", lastName)
                    .toString();
        }
    }

    public static class Location {
        public String country;
        public String city;
        public String zipCode;

        @Override
        public String toString() {
            return MoreObjects.toStringHelper(this)
                    .add("country", country)
                    .add("city", city)
                    .add("zipCode", zipCode)
                    .toString();
        }
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("email", email)
                .add("password", password)
                .add("username", username)
                .add("profilePicture", profilePicture)
                .add("resourceType", resourceType)
                .add("description", description)
                .add("name", name)
                .add("location", location)
                .add("signupType", signupType)
                .add("socialNetworkID", socialNetworkID)
                .add("facebookToken", facebookToken)
                .add("facebookTokenType", facebookTokenType)
                .toString();
    }
}

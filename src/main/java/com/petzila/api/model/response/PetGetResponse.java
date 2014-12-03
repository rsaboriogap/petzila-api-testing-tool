package com.petzila.api.model.response;

import com.google.common.base.MoreObjects;

/**
 * Created by rsaborio on 20/11/14.
 */
public final class PetGetResponse extends Response {
    public Data data;

    public static class Data {
        public Pet pet;
        public Owner owner;
        public int followers;
        public int postCount;
        public boolean followed;

        public static class Pet {
            public String id;
            public String name;
            public String description;
            public String age;
            public String species;
            public String other;
            public String breed;
            public String size;
            public String gender;
            public String website;
            public String food;
            public String profilePicture;
            public String updatedAt;
            public String createdAt;
            public String owner;

            @Override
            public String toString() {
                return MoreObjects.toStringHelper(this)
                        .add("id", id)
                        .add("name", name)
                        .add("description", description)
                        .add("age", age)
                        .add("species", species)
                        .add("other", other)
                        .add("breed", breed)
                        .add("size", size)
                        .add("gender", gender)
                        .add("website", website)
                        .add("food", food)
                        .add("profilePicture", profilePicture)
                        .add("updatedAt", updatedAt)
                        .add("createdAt", createdAt)
                        .add("owner", owner)
                        .toString();
            }
        }

        public static class Owner {
            public String id;
            public String firstName;
            public String lastName;
            public String profilePicture;
            public String updatedAt;
            public String username;

            @Override
            public String toString() {
                return MoreObjects.toStringHelper(this)
                        .add("id", id)
                        .add("firstName", firstName)
                        .add("lastName", lastName)
                        .add("profilePicture", profilePicture)
                        .add("updatedAt", updatedAt)
                        .add("username", username)
                        .toString();
            }
        }

        @Override
        public String toString() {
            return MoreObjects.toStringHelper(this)
                    .add("pet", pet)
                    .add("owner", owner)
                    .add("followers", followers)
                    .add("postCount", postCount)
                    .add("followed", followed)
                    .toString();
        }
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("status", status)
                .add("data", data)
                .toString();
    }
}
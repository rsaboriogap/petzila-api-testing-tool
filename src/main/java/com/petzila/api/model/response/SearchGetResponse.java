package com.petzila.api.model.response;

import com.google.common.base.MoreObjects;

import java.util.List;

/**
 * Created by rsaborio on 20/11/14.
 */
public final class SearchGetResponse extends Response {
    public Data data;

    public static class Data {
        public List<PetR> pets;
        public List<UserR> users;

        public static class PetR {
            public String id;
            public Pet pet;
            public Owner owner;

            public static class Pet {
                public String id;
                public String breed;
                public String gender;
                public String profilePicture;
                public String updatedAt;
                public String createdAt;
                public String name;

                @Override
                public String toString() {
                    return MoreObjects.toStringHelper(this)
                            .add("id", id)
                            .add("breed", breed)
                            .add("gender", gender)
                            .add("profilePicture", profilePicture)
                            .add("updatedAt", updatedAt)
                            .add("createdAt", createdAt)
                            .add("name", name)
                            .toString();
                }
            }

            public static class Owner {
                public String id;
                public String username;
                public String firstname;
                public String lastname;

                @Override
                public String toString() {
                    return MoreObjects.toStringHelper(this)
                            .add("id", id)
                            .add("username", username)
                            .add("firstname", firstname)
                            .add("lastname", lastname)
                            .toString();
                }
            }

            @Override
            public String toString() {
                return MoreObjects.toStringHelper(this)
                        .add("id", id)
                        .add("pet", pet)
                        .add("owner", owner)
                        .toString();
            }
        }

        public static class UserR {
            public String id;
            public String username;
            public String firstname;
            public String lastname;
            public String profilePicture;
            public String createdAt;
            public String updatedAt;

            @Override
            public String toString() {
                return MoreObjects.toStringHelper(this)
                        .add("id", id)
                        .add("username", username)
                        .add("firstname", firstname)
                        .add("lastname", lastname)
                        .add("profilePicture", profilePicture)
                        .add("createdAt", createdAt)
                        .add("updatedAt", updatedAt)
                        .toString();
            }
        }

        @Override
        public String toString() {
            return MoreObjects.toStringHelper(this)
                    .add("pets", pets)
                    .add("users", users)
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

package com.petzila.api.model;

import com.google.common.base.MoreObjects;

/**
 * Created by rylexr on 01/12/14.
 */
public class Pet extends Entity {
    public String name;
    public String description;
    public String age;
    public String species;
    public String other;
    public String breed;
    public String size;
    public String gender;
    public String food;
    public String website;
    public String profilePicture;
    public String resourceType;

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("name", name)
                .add("description", description)
                .add("age", age)
                .add("species", species)
                .add("other", other)
                .add("breed", breed)
                .add("size", size)
                .add("gender", gender)
                .add("food", food)
                .add("website", website)
                .add("profilePicture", profilePicture)
                .add("resourceType", resourceType)
                .toString();
    }
}

package de.fu_berlin.agdb.crepe.rest.controller;

import de.fu_berlin.agdb.crepe.json.algebra.JSONProfile;
import de.fu_berlin.agdb.crepe.json.util.SimpleLocation;

/**
 * @author Simon Kalt
 */
public class ProfileRequest {
    private String userId;
    private String name;
    private SimpleLocation location;
    private int id;
    private JSONProfile profile;
    private String appId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SimpleLocation getLocation() {
        return location;
    }

    public void setLocation(SimpleLocation location) {
        this.location = location;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public JSONProfile getProfile() {
        return profile;
    }

    public void setProfile(JSONProfile profile) {
        this.profile = profile;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }
}

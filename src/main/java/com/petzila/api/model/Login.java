package com.petzila.api.model;

import com.google.common.base.MoreObjects;

/**
 * Created by rsaborio on 19/11/14.
 */
public class Login extends Entity {
    public String email;
    public String password;
    public String loginType;
    public String socialNetworkID;
    public String facebookToken;
    public String facebookTokenType;

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("email", email)
                .add("password", password)
                .add("loginType", loginType)
                .add("socialNetworkID", socialNetworkID)
                .add("facebookToken", facebookToken)
                .add("facebookTokenType", facebookTokenType)
                .toString();
    }
}

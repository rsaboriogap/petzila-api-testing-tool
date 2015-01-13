package com.petzila.api.model;

import com.google.common.base.MoreObjects;

/**
 * Created by rsaborio on 19/11/14.
 */
public final class Login extends Entity implements Cloneable {
    public String email;
    public String password;
    public String loginType;
    public String socialNetworkID;
    public String facebookToken;
    public String facebookTokenType;

    @Override
    public Login clone() {
        Login clone = new Login();
        clone.email = email;
        clone.password = password;
        clone.loginType = loginType;
        clone.socialNetworkID = socialNetworkID;
        clone.facebookToken = facebookToken;
        clone.facebookTokenType = facebookTokenType;
        return clone;
    }

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

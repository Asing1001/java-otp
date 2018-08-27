package com.odde.securetoken;

public class ProfileDao implements Profile {
    public String getPassword(String account) {
        return Context.getPassword(account);
    }
}

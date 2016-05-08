package com.suites.server.api;

import com.suites.server.db.UserManager;
import com.suites.server.db.UserException;
import com.suites.server.core.User;

import com.google.common.base.Optional;

import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.basic.BasicCredentials;

import java.security.MessageDigest;
import java.sql.SQLException;

public class DBAuthenticator implements Authenticator<BasicCredentials, User> {
    UserManager um;

    public DBAuthenticator(UserManager manager) {
        um = manager;
    }
    
    @Override
    public Optional<User> authenticate(BasicCredentials creds)
        throws AuthenticationException {
        try {
            return um.authenticateUser(creds.getUsername(), creds.getPassword());
        } catch (UserException e) {
            throw new AuthenticationException(e.getMessage());
        }
    }
}

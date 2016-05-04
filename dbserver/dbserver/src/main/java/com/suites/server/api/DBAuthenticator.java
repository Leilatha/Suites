package com.suites.server.api;

import com.suites.server.db.SuitesDAO;
import com.suites.server.core.User;

import com.google.common.base.Optional;

import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.basic.BasicCredentials;

import java.security.MessageDigest;
import java.sql.SQLException;

public class DBAuthenticator implements Authenticator<BasicCredentials, User> {
    SuitesDAO dao;

    public DBAuthenticator(SuitesDAO dao) {
        this.dao = dao;
    }
    
    @Override
    public Optional<User> authenticate(BasicCredentials creds) throws AuthenticationException {
        String hash;
 
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(creds.getPassword().getBytes("UTF-8"));
            byte[] digest = md.digest();
            hash = String.format("%064x", new java.math.BigInteger(1, digest));
        } catch (Exception e) {
            throw new AuthenticationException("Failed to hash password");
        }


        try {
            return dao.authenticateUser(creds.getUsername(), hash);
        } catch (Exception e) {
            throw new AuthenticationException(e.getMessage());
        }
    }
}

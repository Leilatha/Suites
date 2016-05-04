package com.suites.server.db;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.io.UnsupportedEncodingException;

import com.google.common.base.Optional;

import com.suites.server.core.User;

public class UserManager {

    SuitesDAO dao;

    public UserManager(SuitesDAO dao) {
        this.dao = dao;
    }

    
    public static String hashPassword(String pass)
        throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(pass.getBytes("UTF-8"));
        byte[] digest = md.digest();
        return String.format("%064x", new java.math.BigInteger(1, digest));
    }

    public Optional<User> authenticateUser(String email, String password)
        throws UserException {
        String passhash;
        try {
            passhash = hashPassword(password);
        } catch (Exception e) {
            throw new UserException("Failed to hash password: " + e.getMessage());
        }

        return dao.authenticateUser(email, passhash);
    }

    public void registerUser(String email, String name, String password)
        throws UserException{
        String passhash;
        try {
            passhash = hashPassword(password);
        } catch (Exception e) {
            throw new UserException("Failed to hash password: " + e.getMessage());
        }

        dao.addUser(email, name, passhash);
    }
}

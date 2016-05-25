package com.suites.server.db;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.io.UnsupportedEncodingException;

import com.google.common.base.Optional;

import com.suites.server.core.User;

public class UserManager {

    private final SuitesDAO dao;

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

        User user = dao.authenticateUser(email, passhash);
        return Optional.fromNullable(user);
    }

    public void registerUser(String email, String name, String password)
        throws UserException {
        String passhash;
        try {
            passhash = hashPassword(password);
        } catch (Exception e) {
            throw new UserException("Failed to hash password: " + e.getMessage());
        }

        User existing = dao.getUserByEmail(email);

        if (existing != null) {
            throw new UserException("An account with that email already exists.");
        }

        dao.addUser(email, name, passhash);
    }

    public User getUserByEmail(String email) {
        return dao.getUserByEmail(email);
    }

    public void editUser(User user, String email, String name, String password)
        throws UserException {

        int id = user.getId();
        String oldEmail = user.getEmail();

        String passhash;
        try {
            passhash = hashPassword(password);
        } catch (Exception e) {
            throw new UserException("Failed to hash password: " + e.getMessage());
        }

        User clashing = dao.getUserByEmail(email);

        if (clashing != null && clashing.getId() != id) {
            throw new UserException("Another account has that email.");
        }

        dao.editUser(id, email, name, passhash);

        dao.switchInvitations(oldEmail, email);
    }
}

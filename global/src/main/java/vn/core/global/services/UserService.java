package vn.core.global.services;

import vn.core.global.entities.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers(int page);
}

package vn.core.global.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.core.global.dao.UserDao;
import vn.core.global.entities.User;
import vn.core.global.services.UserService;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public List<User> getAllUsers(int page) {
        return userDao.getAllUsers(page);
    }
}

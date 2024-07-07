package vn.core.global.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.core.global.configuration.DatabaseConfig;
import vn.core.global.dao.UserDao;
import vn.core.global.entities.User;
import vn.core.global.services.UserService;

import java.util.List;

@RestController
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(DatabaseConfig.class);

    @Autowired
    UserService userService;

    @GetMapping("/users")
    public List<User> getUsers() {
        return userService.getAllUsers();
    }
}

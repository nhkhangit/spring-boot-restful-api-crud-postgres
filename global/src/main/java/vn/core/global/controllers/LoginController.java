package vn.core.global.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.core.global.entities.User;

@RestController
@RequestMapping("/api/login/")
public class LoginController {
    private final vn.core.global.dao.UserDao UserDao;
    public LoginController(vn.core.global.dao.UserDao UserDao) {
        this.UserDao = UserDao;
    }

    @PostMapping
    public ResponseEntity<User> loginUser(@RequestBody User user) {
        if (user.getEmail().isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        User authorizedUser = new User();
        authorizedUser = UserDao.getUserByEmail(user.getEmail());
        if (authorizedUser == null)  {
            return ResponseEntity.notFound().build();
        } else if (authorizedUser.getEmail().equals(user.getEmail()) & (authorizedUser.getPassword().equals(user.getPassword())) ) {
            user.setUserId(authorizedUser.getUserId());
            user.setEmail(authorizedUser.getEmail());
            user.setGender(authorizedUser.getGender());
            user.setCreatedAt(authorizedUser.getCreatedAt());
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.notFound().build();
    }
}

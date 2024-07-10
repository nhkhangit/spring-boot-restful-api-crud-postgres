package vn.core.global.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.core.global.entities.User;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final vn.core.global.dao.UserDao UserDao;

    public UserController(vn.core.global.dao.UserDao UserDao) {
        this.UserDao = UserDao;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return UserDao.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable int id) {
        User User = UserDao.getUserById(id);
        if (User != null) {
            return ResponseEntity.ok(User);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody User User) {
        if (UserDao.createUser(User)) {
            return ResponseEntity.ok("{\"message\": \"Create user success\"}");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"message\": \"Fail to create user\"}");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable int id, @RequestBody User User) {
        User.setUserId(id);
        if (UserDao.updateUser(User)) {
            return ResponseEntity.ok("{\"message\": \"User updated successfully\"}");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"message\": \"Failed to update user\"}");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable int id) {
        if (UserDao.deleteUser(id)) {
            return ResponseEntity.ok("{\"message\": \"User deleted successfully\"}");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"message\": \"Failed to delete user\"}");
        }
    }
}

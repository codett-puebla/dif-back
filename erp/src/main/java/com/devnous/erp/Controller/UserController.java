package com.devnous.erp.Controller;

import com.devnous.erp.Constants.Endpoints;
import com.devnous.erp.Entity.User;
import com.devnous.erp.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Endpoints.USER)
public class UserController {

    @Autowired
    @Qualifier("userService")
    UserService userService;

    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> users = userService.readAllActiveUser();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable("id") int id){
        return new ResponseEntity<User>(userService.readUser(id), HttpStatus.OK);
    }

    @PostMapping("/new")
    public ResponseEntity<String> insertUser(@RequestBody User user) {
        userService.createUser(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(path = "", consumes = "application/json")
    public ResponseEntity<String> updateUser(@RequestBody User user) {
        userService.updateUser(user);
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deleteSoftUser(@PathVariable("id") int id) {
        userService.softDeleteUser(id);
        return new ResponseEntity<String>(HttpStatus.OK);
    }

}

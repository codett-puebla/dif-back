package com.devnous.erp.Controller;

import com.devnous.erp.Constants.Endpoints;
import com.devnous.erp.Entity.User;
import com.devnous.erp.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


}

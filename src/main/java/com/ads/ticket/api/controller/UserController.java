package com.ads.ticket.api.controller;

import com.ads.ticket.api.data.entity.User;
import com.ads.ticket.api.data.manager.UserManager;
import com.ads.ticket.api.exception.UserNameAlreadyTakenException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@Api(value = "users", description = "A set of endpoints for managing users")
@RestController
@RequestMapping("/users")
public class UserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    private UserManager userManager;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    public UserController(UserManager userManager) {
        this.userManager = userManager;
    }
    
    @ApiOperation(value = "createUser", notes = "Create a new user")
    @RequestMapping(value = "/sign-up", method = RequestMethod.PUT)
    public User createUser(@RequestBody User model) throws UserNameAlreadyTakenException {
        model.setPassword(bCryptPasswordEncoder.encode(model.getPassword()));
        User createUser = userManager.createUser(model);
        return createUser;
    }
    
//    @PreAuthorize("hasRole('INTEGRATION_TEST')")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteUser(@PathVariable String id) {
        userManager.deleteUserById(id);
    }
    
    @ApiOperation(value = "getUserById", notes = "Return User object for a given User")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public User getUserById(@PathVariable("id") String id) {

        return userManager.getUserById(id);
    }
    
    @ApiOperation(value = "getAllUsers", notes = "Return all user object")
    @RequestMapping(value = "users", method = RequestMethod.GET)
    public List<User> getAllUsers() {
        return userManager.getAllUsers();
    }
    
    @ApiOperation(value = "getUserByUsername", notes = "Return User object for a given User")
    @RequestMapping(value = "/loadUser", method = RequestMethod.GET)
    public User getUserByUsername(@RequestParam("username") String id) {

        return userManager.getUserByUsername(id);
    }
    
    
}

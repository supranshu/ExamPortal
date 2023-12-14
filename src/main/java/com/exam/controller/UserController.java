package com.exam.controller;


import com.exam.model.Role;
import com.exam.model.User;
import com.exam.model.UserRole;
import com.exam.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class UserController {

    @Autowired
    private UserService userService;





    //creating user
    @PostMapping("/")
    public User createUser(@RequestBody User user) throws Exception {
        user.setProfile("default.jpg");
        Set<UserRole> roles=new HashSet<>();
        Role role=new Role();
        role.setRoleId(45);
        role.setRoleName("NORMAL");

        UserRole userRole=new UserRole();
        userRole.setUser(user);
        userRole.setRole(role);
        roles.add(userRole);

        return this.userService.createUser(user,roles);
    }

    @GetMapping("/{username}")
    public User getUser(@PathVariable("username") String username){

        User user=this.userService.getUser(username);
        Set<UserRole> roles=user.getUserRoles();
        if(roles.contains("ADMIN"))
        {

        }else {

        }

        return this.userService.getUser(username);
    }

    //delete user
    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable("userId") long userId){
        this.userService.deleteUser(userId);
    }

    //update user

}

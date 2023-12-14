package com.exam.controller;

import com.exam.model.JwtRequest;
import com.exam.model.JwtResponse;
import com.exam.service.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.SecureRandom;
import java.util.Base64;

@RestController
public class AuthenticateController {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    private void authenticate(String username,String password) throws Exception{
        try{


            System.out.println("Valid User");
            UserDetails userDetails=this.userDetailsService.loadUserByUsername(username);
            if(userDetails.getPassword().equals(password)){

            }
            else {
                throw new Exception("Invalid Password");
            }


        }catch (DisabledException e){
            throw new Exception("USER DISABLED: "+e.getMessage());
        }catch (BadCredentialsException e)
        {
            throw new Exception("BAD CREDENTIALS");
        }


    }
    @PostMapping("/generate-token")
    public ResponseEntity<?> generateToken(@RequestBody JwtRequest request) throws Exception{
        try {

            authenticate(request.getUsername(), request.getPassword());

        }
        catch (UsernameNotFoundException e){
            e.printStackTrace();
            throw new Exception("User Not Found");
        }
        SecureRandom secureRandom = new SecureRandom();

        // Generate random bytes
        byte[] randomBytes = new byte[32];
        secureRandom.nextBytes(randomBytes);

        // Encode the random bytes to a string representation
        String token = Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
        System.out.println(token);

        return ResponseEntity.ok(new JwtResponse(token));

    }

}

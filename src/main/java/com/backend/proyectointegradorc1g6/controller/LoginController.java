package com.backend.proyectointegradorc1g6.controller;

import com.backend.proyectointegradorc1g6.entity.Login;
import com.backend.proyectointegradorc1g6.service.ILoginService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/logins")
@CrossOrigin
public class LoginController {
    private ILoginService loginService;

    public LoginController(ILoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/login")
    public ResponseEntity<Login> iniciarSession(@RequestBody  Login login) {
        return new ResponseEntity<>(loginService.iniciarSession(login), HttpStatus.ACCEPTED);
    }
}

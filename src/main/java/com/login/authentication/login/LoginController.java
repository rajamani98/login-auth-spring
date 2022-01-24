package com.login.authentication.login;

import lombok.AllArgsConstructor;

import java.util.*;

import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Produces;


@CrossOrigin(origins = "http://localhost:3000/")
@RestController
@RequestMapping(path = "api/v1")
@AllArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @PostMapping(value = "/login")
    public boolean login(@RequestBody LoginRequest request, HttpServletRequest httpServletRequest) {
        HttpSession httpSession = httpServletRequest.getSession();
        httpSession.setAttribute("emailId", request.getEmailId());
        if (loginService.loginUser(request.getEmailId()))
            return true;
        else
            return false;
    }

    @PostMapping(value = "/verify")
    @Produces("application/json")
    public ResponseEntity<Map<String, Boolean>> verifyOtp(@RequestBody String otpObj, HttpServletRequest httpServletRequest) {
        String emailId = (String) httpServletRequest.getSession().getAttribute("emailId");
        JSONObject json = new JSONObject(otpObj);
        System.out.println("Session id : " + httpServletRequest.getSession().getId());
        System.out.println("otp from fe" + json.getString("otp"));
        Map<String, Boolean> body = new HashMap<>();
        body.put("status", true);
        if (loginService.verifyOtp(json.getString("otp"), emailId))
            return new ResponseEntity<>(body, HttpStatus.OK);
        else
            return new ResponseEntity<>(body, HttpStatus.UNAUTHORIZED);
    }

    @PostMapping(value = "/logout")
    public ResponseEntity<Map<String, Boolean>> destroySession(HttpServletRequest request) {
        request.getSession().invalidate();
        Map<String, Boolean> body = new HashMap<>();
        body.put("status", true);
        return new ResponseEntity<>(body, HttpStatus.OK);
    }
}

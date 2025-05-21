package com.ofektom.med.controller;


import com.ofektom.med.dto.request.AddPatientAndStaffDto;
import com.ofektom.med.dto.request.LoginDto;
import com.ofektom.med.dto.request.SignupDto;
import com.ofektom.med.model.User;
import com.ofektom.med.service.UserService;
import com.ofektom.med.serviceImpl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = {"http://localhost:5173", "https://airway-ng.netlify.app"}, allowCredentials = "true")
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final UserService userService;
    @Autowired
    public AuthController(ApplicationEventPublisher publisher, UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<?> register(@Valid @RequestBody SignupDto signupDto){
        return userService.registerUser(signupDto);
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('SUPER_ADMIN', 'ADMIN')")
    public ResponseEntity<?> addPatientAndStaff(@Valid @RequestBody AddPatientAndStaffDto addPatientAndStaffDto){
        return userService.addStaffAndPatient(addPatientAndStaffDto);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDto loginDto){
        return userService.loginUser(loginDto);
    }

    @GetMapping("/validate-token")
    public ResponseEntity<?> validateToken(HttpServletRequest request) {
        return userService.validateToken(request);
    }



//    @GetMapping("/verifyRegistration")
//    public ResponseEntity<String> verifyRegistration(@RequestParam("token") String token){
//        String result = userService.validateVerificationToken(token);
//        if (result.equalsIgnoreCase("valid")){
//            return new ResponseEntity<>( "User Verified Successfully",HttpStatus.OK);
//        }
//        return new ResponseEntity<>("User not Verified", HttpStatus.OK);
//    }

//    @PostMapping("/login")
//    public ResponseEntity<String> login(@RequestBody LoginDto loginDto){
//String result = userService.logInUser(loginDto);
//        return new ResponseEntity<>(result, HttpStatus.OK);
//    }


//    @PostMapping("/changePassword")
//    public ResponseEntity <String> changePassword(@RequestBody ChangePasswordDto passwordDto) {
//        return new ResponseEntity<>(userService.changeUserPassword(passwordDto), HttpStatus.OK);
//    }
//
//    @PostMapping("/forgot-password")
//    public ResponseEntity<String> forgotPassword(@RequestBody EmailSenderDto passwordDto, HttpServletRequest request){
//        userService.forgotPassword(passwordDto, request);
//        return new ResponseEntity<>("Forgot password email successfully sent", HttpStatus.OK);
//
//    }
//
//    @PostMapping("/reset-password/{token}")
//    public ResponseEntity<String> resetPassword(@PathVariable String token, @RequestBody ResetPasswordDto passwordDto) {
//        return userService.resetPassword(token, passwordDto);
//    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request){
        String result = userService.logoutUser(request);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


}

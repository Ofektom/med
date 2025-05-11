package com.ofektom.med.serviceImpl;


import com.ofektom.med.dto.request.AddPatientAndStaffDto;
import com.ofektom.med.dto.request.LoginDto;
import com.ofektom.med.dto.request.SignupDto;
import com.ofektom.med.dto.response.AddressResponse;
import com.ofektom.med.dto.response.ApiResponse;
import com.ofektom.med.dto.response.ResponseData;
import com.ofektom.med.dto.response.UserResponse;
import com.ofektom.med.enums.Role;
import com.ofektom.med.exception.*;
import com.ofektom.med.model.Address;
import com.ofektom.med.model.User;
import com.ofektom.med.repositroy.UserRepository;
import com.ofektom.med.service.UserService;
import com.ofektom.med.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, JwtUtils jwtUtils, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtUtils = jwtUtils;
        this.passwordEncoder = passwordEncoder;

    }

    @Override
    public UserDetails loadUserByUsername(String userInput) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(userInput)
                .orElseGet(() -> userRepository.findByPhoneNumber(userInput).orElse(null));
        if (user == null) {
            throw new NotFoundException("User not found with provided email or phone");
        }
        return user;
    }

    @Override
    public ResponseEntity<?> registerUser(SignupDto signupDto) {
        if (userRepository.existsByEmail(signupDto.getEmail())) {
            throw new ConflictException("Email is already taken, try Logging In or Signup with another email" );
        }

        if (userRepository.existsByPhoneNumber(signupDto.getPhoneNumber())) {
            throw new BadRequestException("Phone number is already taken");
        }

        if (signupDto.getUserRole().equals("ROLE_SUPER_ADMIN")) {
            boolean superAdminExists = userRepository.existsByUserRole(Role.ROLE_SUPER_ADMIN);
            if (superAdminExists) {
                throw new ConflictException("A Super Admin already exists. Only one Super Admin is allowed.");
            }
        }

        User user = new User();

        if (!validatePassword(signupDto.getPassword())) {
            throw new ConflictException("Password does not meet the required criteria");
        }

        user.setPassword(passwordEncoder.encode(signupDto.getPassword()));
        user.setFirstName(signupDto.getFirstName());
        user.setLastName(signupDto.getLastName());
        user.setPhoneNumber(signupDto.getPhoneNumber());
        user.setEmail(signupDto.getEmail());
        user.setEnabled(true);
        user.setMembershipNo(generateMemberShip("EMR"));

        Role userRole;
        try {
            userRole = Role.valueOf(signupDto.getUserRole());
        } catch (IllegalArgumentException e) {
            throw new ConflictException("Invalid role provided");
        }
        user.setUserRole(userRole);

        // Set optional fields
        user.setEducation(signupDto.getEducation());
        user.setDesignation(signupDto.getDesignation());
        user.setDepartment(signupDto.getDepartment());
        user.setPostalCode(signupDto.getPostalCode());
        user.setDateOfBirth(signupDto.getDateOfBirth());
        user.setGender(signupDto.getGender());
        user.setAbout(signupDto.getAbout());
        user.setStatus(signupDto.getStatus());
        user.setAge(signupDto.getAge());
        user.setMaritalStatus(signupDto.getMaritalStatus());
        user.setProfilePicture(signupDto.getProfilePicture());
        user.setBloodGroup(signupDto.getBloodGroup());
        user.setBloodPressure(signupDto.getBloodPressure());
        user.setSugar(signupDto.getSugar());
        user.setInjuryCondition(signupDto.getInjuryCondition());
        user.setAllergies(signupDto.getAllergies());

        // Address association, ensure Address entity exists and is set
        Address address = new Address();
        address.setCountry(signupDto.getCountry());
        address.setState(signupDto.getState());
        address.setCity(signupDto.getCity());
        address.setStreetAddress(signupDto.getStreetAddress());
        user.setAddress(address);

        user = userRepository.save(user);

        UserResponse userResponse = getUserResponse(user);
        ResponseData data = new ResponseData(userResponse);
        return new ResponseEntity<>(new ApiResponse<>(HttpStatus.OK.value(), "Registration Successful!", null, data), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> addStaffAndPatient(AddPatientAndStaffDto signupDto) {
        if (userRepository.existsByEmail(signupDto.getEmail())) {
            throw new ConflictException("Email is already taken, try Logging In or Signup with another email" );
        }

        if (userRepository.existsByPhoneNumber(signupDto.getPhoneNumber())) {
            throw new BadRequestException("Phone number is already taken");
        }

        Set<String> allowedRoles = Set.of("ROLE_STAFF", "ROLE_PATIENT", "ROLE_NURSE");
        if (!allowedRoles.contains(signupDto.getUserRole())) {
            throw new BadRequestException("You cannot add this user.");
        }

        User user = new User();

        user.setFirstName(signupDto.getFirstName());
        user.setLastName(signupDto.getLastName());
        user.setPhoneNumber(signupDto.getPhoneNumber());
        user.setEmail(signupDto.getEmail());
        user.setEnabled(true);
        user.setMembershipNo(generateMemberShip("EMR"));

        Role userRole;
        try {
            userRole = Role.valueOf(signupDto.getUserRole());
        } catch (IllegalArgumentException e) {
            throw new ConflictException("Invalid role provided");
        }
        user.setUserRole(userRole);


        user.setDateOfBirth(signupDto.getDateOfBirth());
        user.setGender(signupDto.getGender());
        user.setAge(signupDto.getAge());
        user.setMaritalStatus(signupDto.getMaritalStatus());
        user.setProfilePicture(signupDto.getProfilePicture());
        user.setBloodGroup(signupDto.getBloodGroup());
        user.setBloodPressure(signupDto.getBloodPressure());
        user.setSugar(signupDto.getSugar());
        user.setInjuryCondition(signupDto.getInjuryCondition());
        user.setAllergies(signupDto.getAllergies());

        // Address association, ensure Address entity exists and is set
        Address address = new Address();
        address.setStreetAddress(signupDto.getStreetAddress());
        user.setAddress(address);

        user = userRepository.save(user);

        UserResponse userResponse = getUserResponse(user);
        ResponseData data = new ResponseData(userResponse);
        return new ResponseEntity<>(new ApiResponse<>(HttpStatus.OK.value(), user.getUserRole() + " Successfully Added!", null, data), HttpStatus.OK);

    }

    @Override
    public ResponseEntity<ApiResponse<ResponseData>> loginUser(LoginDto loginDto) {
        UserDetails userDetails = loadUserByUsername(loginDto.getEmailOrPhone());
        User user = (User) userDetails;
        boolean isValidPassword =
                passwordEncoder.matches(loginDto.getPassword(), userDetails.getPassword());
        if (!isValidPassword) {
            throw new BadRequestException("Invalid login details");
        }
        String token = jwtUtils.createJwt.apply(userDetails);
        UserResponse userResponse = getUserResponse(user);
        ResponseData data = new ResponseData(userResponse);
        return new ResponseEntity<>(new ApiResponse<>(HttpStatus.OK.value(), "Login Successful!", token, data), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getUserById(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User not found with ID: " + userId));
        UserResponse userResponse = getUserResponse(user);
        ResponseData data = new ResponseData(userResponse);
        return new ResponseEntity<>(new ApiResponse<>(HttpStatus.OK.value(), "User fetched successfully", null, data), HttpStatus.OK);
    }

    @Override
    public List<UserResponse> getAllUsersByRoleAsList(String role) {
        List<User> users;
        if (role == null || role.trim().isEmpty()) {
            users = userRepository.findAll();
        } else {
            users = userRepository.findByUserRole(Role.valueOf(role.toUpperCase()));
        }
        return users.stream().map(this::getUserResponse).collect(Collectors.toList());
    }

    @Override
    public Page<UserResponse> getAllUsersByRoleAsPage(String role, Pageable pageable) {
        Page<User> usersPage;
        if (role == null || role.trim().isEmpty()) {
            usersPage = userRepository.findAll(pageable);
        } else {
            usersPage = userRepository.findByUserRole(Role.valueOf(role.toUpperCase()), pageable);
        }
        return usersPage.map(this::getUserResponse);
    }

    private UserResponse getUserResponse(User user) {
        Address address = user.getAddress();

        AddressResponse addressResponse = new AddressResponse();
        addressResponse.setCountry(address.getCountry());
        addressResponse.setState(address.getState());
        addressResponse.setCity(address.getCity());
        addressResponse.setStreetAddress(address.getStreetAddress());

        return new UserResponse(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getMembershipNo(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getGender(),
                user.getStatus(),
                user.getProfilePicture(),
                user.getEducation(),
                user.getDesignation(),
                user.getDepartment(),
                user.getMaritalStatus(),
                user.getBloodGroup(),
                user.getBloodPressure(),
                user.getSugar(),
                user.getInjuryCondition(),
                user.getAllergies(),
                addressResponse,
                user.getUserRole().name(),
                user.getLastLogin() != null ? user.getLastLogin().toString() : null,
                user.getLoginDuration(),
                user.getSpeciality(),
                user.getAbout(),
                user.getCreatedAt()
        );
    }

    public String generateMemberShip (String prefix) {
        Random random = new Random();
        int suffixLength = 6;
        StringBuilder suffixBuilder = new StringBuilder();
        for (int i = 0; i < suffixLength; i++) {
            suffixBuilder.append(random.nextInt(10));
        }
        return prefix + suffixBuilder.toString();
    }

    public boolean validatePassword(String password){
        String capitalLetterPattern = "(?=.*[A-Z])";
        String lowercaseLetterPattern = "(?=.*[a-z])";
        String digitPattern = "(?=.*\\d)";
        String symbolPattern = "(?=.*[@#$%^&+=])";
        String lengthPattern = ".{8,}";

        String regex = capitalLetterPattern + lowercaseLetterPattern + digitPattern + symbolPattern + lengthPattern;

        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(password);

        return matcher.matches();
    }


    public boolean checkIfValidOldPassword(User user, String oldPassword) {
        return validatePassword(oldPassword) && passwordEncoder.matches(oldPassword, user.getPassword());
    }

//    public void changePassword(User user, String newPassword) {
//        user.setPassword(passwordEncoder.encode(newPassword));
//        userRepository.save(user);
//    }
//
//    @Override
//    public String changeUserPassword(ChangePasswordDto passwordDto) {
//        User user = userRepository.findUserByEmail(passwordDto.getEmail());
//        if (user == null) {
//            return "User not found";
//        }
//
////        THIS IS COMMENTED OUT BECAUSE THE ADMIN PASSWORD IS 1234
//
////        if (!validatePassword(passwordDto.getOldPassword())) {
////            return "Invalid Old Password. Password must meet the required criteria: at least 1 uppercase letter, 1 lowercase letter, 1 digit, 1 special character (@#$%^&+=), and minimum length of 8 characters";
////        }
//
//        if (passwordDto.getOldPassword().equals(passwordDto.getNewPassword())) {
//            return "New password must be different from the old password";
//        }
//
//        if (!validatePassword(passwordDto.getNewPassword())) {
//            return "New password does not meet the required criteria: at least 1 uppercase letter, 1 lowercase letter, 1 digit, 1 special character (@#$%^&+=), and minimum length of 8 characters";
//        }
//
//        if (!passwordEncoder.matches(passwordDto.getOldPassword(),user.getPassword())){
//            return "Password does not match";
//         } else {
//        return "Password Changed Successfully ";
//    }
//
//    }
//
//
//
//    public User findUserByEmail(String username) {
//        return userRepository.findByEmail(username).orElseThrow(() -> new RuntimeException("Username Not Found" + username));
//    }
//    @Override
//    public void createPasswordResetTokenForUser(User user, String token) {
//        PasswordResetToken newlyCreatedPasswordResetToken = new PasswordResetToken(user, token);
//        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByUserId(user.getId());
//        if(passwordResetToken != null){
//            passwordResetTokenRepository.delete(passwordResetToken);
//        }
//        passwordResetTokenRepository.save(newlyCreatedPasswordResetToken);
//    }
//
//    private String validatePasswordResetToken(String token) {
//        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken(token);
//        if (passwordResetToken == null) {
//            return "invalid";
//        }
//        Calendar cal = Calendar.getInstance();
//        if (passwordResetToken.getExpirationTime().getTime()
//                - cal.getTime().getTime() <= 0) {
//            passwordResetTokenRepository.delete(passwordResetToken);
//            return "expired";
//        }
//        return "valid";
//    }
//
//
//    @Override
//    public void forgotPassword(EmailSenderDto passwordDto, HttpServletRequest request) {
//        User user = findUserByEmail(passwordDto.getEmail());
//        if (user == null) {
//            throw new UsernameNotFoundException("User with email " + passwordDto.getEmail() + " not found");
//        }
//            String token = UUID.randomUUID().toString();
//            createPasswordResetTokenForUser(user, token);
//            emailService.passwordResetTokenMail(user, emailService.applicationUrl(request), token);
//    }
//
//
//    private Optional<User> getUserByPasswordReset(String token) {
//        return Optional.ofNullable(passwordResetTokenRepository.findByToken(token).getUser());
//    }
//
//    private void changePassword(User user, String newPassword, String newConfirmPassword) {
//
//        if (newPassword.equals(newConfirmPassword)) {
//            user.setPassword(passwordEncoder.encode(newPassword));
//            user.setConfirmPassword(passwordEncoder.encode(newConfirmPassword));
//            userRepository.save(user);
//        } else {
//            throw new PasswordsDontMatchException("Passwords do not Match!");
//        }
//    }
//
//    @Override
//    public ResponseEntity<String> resetPassword(String token, ResetPasswordDto passwordDto) {
//        String result = validatePasswordResetToken(token);
//        if (!result.equalsIgnoreCase("valid")) {
//            throw new InvalidTokenException("Invalid Token");
//        }
//        Optional<User> user = getUserByPasswordReset(token);
//        if (user.isPresent()) {
//            changePassword(user.get(), passwordDto.getPassword(), passwordDto.getConfirmPassword());
//            return new ResponseEntity<>("Password Reset Successful", HttpStatus.OK);
//        } else {
//            throw new InvalidTokenException("Invalid Token");
//        }
//    }


//    public void saveVerificationTokenForUser(User user, String token) {
//        VerificationToken verificationToken = new VerificationToken(user, token);
//        verificationTokenRepository.save(verificationToken);
//
//    }
//    public String validateVerificationToken(String token) {
//        VerificationToken verificationToken = verificationTokenRepository.findByToken(token);
//        if (verificationToken == null){
//            return "invalid";
//        }
//        User user = verificationToken.getUser();
//        Calendar cal = Calendar.getInstance();
//        if ((verificationToken.getExpirationTime().getTime()
//                - cal.getTime().getTime()) <=0) {
//            verificationTokenRepository.delete(verificationToken);
//            return "expired";
//        }
//        user.setIsEnabled(true);
//        userRepository.save(user);
//        return "valid";
//    }
//
//    public VerificationToken generateNewVerificationToken(String oldToken) {
//        VerificationToken verificationToken = verificationTokenRepository.findByToken(oldToken);
//        verificationToken.setToken(UUID.randomUUID().toString());
//        verificationTokenRepository.save(verificationToken);
//        return verificationToken;
//    }


//    @Override
//    public User editUser(UserDto userEditDto, Long userId) {
//        Long loggedInUserId = getUserIdFromAuthenticationContext();
//        log.debug("Editing user with ID: {}", loggedInUserId);
//
//        User userMakingEdit = this.userRepository.findById(loggedInUserId)
//                .orElseThrow(() -> new UserNotFoundException("User with ID: "+loggedInUserId+ " Not Found"));
//
//        if (!loggedInUserId.equals(userId)) {
//            throw new UserNotEligibleException("You are not eligible to edit this user");
//        }
//
//        // Update user's information
//        userMakingEdit.setFirstName(userEditDto.getFirstName());
//        userMakingEdit.setLastName(userEditDto.getLastName());
//        userMakingEdit.setCountry(userEditDto.getCountry());
//        userMakingEdit.setPhoneNumber(userEditDto.getPhoneNumber());
//        userMakingEdit.setGender(userEditDto.getGender());
//        userMakingEdit.setDateOfBirth(userEditDto.getDateOfBirth());
//        return userRepository.save(userMakingEdit);
//    }

    private Long getUserIdFromAuthenticationContext() {
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return loggedInUser.getId();
    }

    @Override
    public String logoutUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        SecurityContextHolder.getContext().setAuthentication(null);
        SecurityContextHolder.clearContext();
        return "User logged out Successfully";
    }
}

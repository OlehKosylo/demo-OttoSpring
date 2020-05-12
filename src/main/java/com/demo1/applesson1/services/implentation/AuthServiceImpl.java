package com.demo1.applesson1.services.implentation;

import com.demo1.applesson1.dto.JwtAuthenticationResponse;
import com.demo1.applesson1.dto.LoginRequest;
import com.demo1.applesson1.dto.UserRequest;
import com.demo1.applesson1.dto.UserResponse;
import com.demo1.applesson1.models.Role;
import com.demo1.applesson1.models.User;
import com.demo1.applesson1.repository.UserRepository;
import com.demo1.applesson1.security.JwtTokenProvider;
import com.demo1.applesson1.security.UserPrincipal;
import com.demo1.applesson1.services.AuthService;
import com.demo1.applesson1.services.PaymentService2;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;
    private final PaymentService2 paymentService2;

    @Override
    public UserResponse registerUser(UserRequest userRequest) {

        if (userRepository.existsByUsername(userRequest.getUsername())) {
            throw new RuntimeException("Username [username: " + userRequest.getUsername() + "] is already taken");
        }


        User user = User.builder()
                .username(userRequest.getUsername())
                .password(passwordEncoder.encode(userRequest.getPassword()))
                .name_surname(userRequest.getName_surname())
                .age(userRequest.getAge())
                .sex(userRequest.getSex())
                .mail(userRequest.getMail())
                .roles(Collections.singletonList(Role.ROLE_USER))
                .build();


        log.info("Successfully registered user with [username: {}]", user.getUsername());

        String id = paymentService2.createCustomer(user);
        System.out.println(id + " id id id id id ");
        user.setStripeCustomerId(id);

        User save = userRepository.save(user);

        return UserResponse.builder()
                .username(save.getUsername())
                .id(save.getId())
                .name_surname(save.getName_surname())
                .age(save.getAge())
                .sex(save.getSex())
                .mail(save.getMail())
                .build();
    }

    @Override
    public JwtAuthenticationResponse loginUser(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        log.info("User with [username: {}] has logged in", userPrincipal.getUsername());

        return new JwtAuthenticationResponse(jwt);
    }
}

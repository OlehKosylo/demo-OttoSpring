package com.demo1.applesson1.services.implentation;

import com.demo1.applesson1.dto.*;
import com.demo1.applesson1.models.User;
import com.demo1.applesson1.repository.UserRepository;
import com.demo1.applesson1.security.JwtTokenProvider;
import com.demo1.applesson1.security.UserPrincipal;
import com.demo1.applesson1.services.AuthService;
import com.demo1.applesson1.services.MailSenderService;
import com.demo1.applesson1.services.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;
    private final PaymentService paymentService;
    private final MailSenderService mailSenderService;

    @Override
    public void registerUser(UserRequest userRequest) {

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
                .statusMailActivate(UUID.randomUUID().toString())
                .build();


        log.info("Successfully registered user with [username: {}]", user.getUsername());

        String id = paymentService.createCustomer(user);
        user.setStripeCustomerId(id);

        //Sending mail part
        if (!StringUtils.isEmpty(user.getMail())) {
            String message = String.format(
                    "Hello, %s \n" +
                            "Welcome to Otto. \n" +
                            "Please, visit next link: http://localhost:4200/activate?token=%s",
                    user.getName_surname(),
                    user.getStatusMailActivate()
            );

            try {
                mailSenderService.send(user.getMail(), "Activation code", message);
                User userSave = userRepository.save(user);
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        }
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

        Boolean statusMail = checkStatusUserMail(loginRequest.getUsername());

        if (!statusMail) {
            throw new RuntimeException(String.format("Please, activate your mail."));
        }

        return new JwtAuthenticationResponse(jwt);

    }

    @Override
    public void activateAccount(String token) {
        User userOfDB = userRepository.findByStatusMailActivate(token).orElseThrow(() -> new RuntimeException(String.format("User with activatedToken: %s not found!", token)));

        User user = User.builder()
                .id(userOfDB.getId())
                .username(userOfDB.getUsername())
                .password(userOfDB.getPassword())
                .name_surname(userOfDB.getName_surname())
                .age(userOfDB.getAge())
                .sex(userOfDB.getSex())
                .mail(userOfDB.getMail())
                .stripeCustomerId(userOfDB.getStripeCustomerId())
                .statusMailActivate("Activated")
                .build();

        userRepository.save(user);
    }

    private Boolean checkStatusUserMail(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException(String.format("User with username: %s not found!", username)));

        String statusMailActivate = user.getStatusMailActivate();
        if (statusMailActivate.equals("Activated")) {
            return true;
        } else {
            return false;
        }
    }
}

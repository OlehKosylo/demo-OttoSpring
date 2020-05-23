package com.demo1.applesson1.services.implentation;

import com.demo1.applesson1.dto.*;
import com.demo1.applesson1.models.Course;
import com.demo1.applesson1.models.User;
import com.demo1.applesson1.repository.CourseRepository;
import com.demo1.applesson1.repository.UserRepository;
import com.demo1.applesson1.services.PaymentService;
import com.demo1.applesson1.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final PasswordEncoder passwordEncoder;
    private final PaymentService paymentService;

    @Override
    public UserResponse getUserInfoForMainPage(int id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException(String.format("User with id: %s not found!", id)));

        return UserResponse.builder()
                .name_surname(user.getName_surname())
                .lvl(user.getLvl())
                .stripe_card_id(user.getStripeCardId())
                .photoURL(user.getPhotoURL())
                .build();
    }

    @Override
    public UserResponse getUserInfoForProfilePage(int id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException(String.format("User with id: %s not found!", id)));

        if (user.getStripeCardId() != null ) {
            CardResponse card = paymentService.retrieveCard(id);

            return UserResponse.builder()
                    .name_surname(user.getName_surname())
                    .age(user.getAge())
                    .username(user.getUsername())
                    .id(user.getId())
                    .lvl(user.getLvl())
                    .sex(user.getSex())
                    .stripe_card_id(user.getStripeCardId())
                    .stripe_customer_id(user.getStripeCustomerId())
                    .token_stripe(user.getTokenStripe())
                    .photoURL(user.getPhotoURL())
                    .card(card)
                    .build();
        } else {
            return UserResponse.builder()
                    .name_surname(user.getName_surname())
                    .age(user.getAge())
                    .username(user.getUsername())
                    .id(user.getId())
                    .lvl(user.getLvl())
                    .sex(user.getSex())
                    .stripe_card_id(user.getStripeCardId())
                    .stripe_customer_id(user.getStripeCustomerId())
                    .token_stripe(user.getTokenStripe())
                    .photoURL(user.getPhotoURL())
                    .build();
        }
    }


    @Override
    public UserResponse editInfoUser(UserRequest user) {

        int userId = user.getId();
        User userOfDB = userRepository.findById(userId).orElseThrow(() -> new RuntimeException(String.format("User with id: %s not found!", userId)));

        User newUser;

        if (user.getPassword() != null && user.getPassword().length() >= 8) {
            newUser = User.builder()
                    .id(user.getId())
                    .name_surname(user.getName_surname())
                    .age(user.getAge())
                    .sex(user.getSex())
                    .username(userOfDB.getUsername())
                    .password(passwordEncoder.encode(user.getPassword()))
                    .lvl(userOfDB.getLvl())
                    .stripeCardId(userOfDB.getStripeCardId())
                    .stripeCustomerId(userOfDB.getStripeCustomerId())
                    .tokenStripe(userOfDB.getTokenStripe())
                    .photoURL(userOfDB.getPhotoURL())
                    .mail(userOfDB.getMail())
                    .statusMailActivate(userOfDB.getStatusMailActivate())
                    .build();
        } else {
            newUser = User.builder()
                    .id(user.getId())
                    .name_surname(user.getName_surname())
                    .age(user.getAge())
                    .sex(user.getSex())
                    .username(userOfDB.getUsername())
                    .password(userOfDB.getPassword())
                    .lvl(userOfDB.getLvl())
                    .stripeCardId(userOfDB.getStripeCardId())
                    .stripeCustomerId(userOfDB.getStripeCustomerId())
                    .tokenStripe(userOfDB.getTokenStripe())
                    .photoURL(userOfDB.getPhotoURL())
                    .mail(userOfDB.getMail())
                    .statusMailActivate(userOfDB.getStatusMailActivate())
                    .build();
        }

        User save = userRepository.save(newUser);

        return UserResponse.builder()
                .name_surname(user.getName_surname())
                .age(user.getAge())
                .id(user.getId())
                .photoURL(user.getPhotoURL())
                .sex(user.getSex())
                .build();
    }

    @Override
    public UserResponse editUserPhoto(UserRequest editUserInfoResponse) {
        int userId = editUserInfoResponse.getId();
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException(String.format("User with id: %s not found!", userId)));

        User newUser;

        newUser = User.builder()
                .id(user.getId())
                .name_surname(user.getName_surname())
                .age(user.getAge())
                .sex(user.getSex())
                .username(user.getUsername())
                .password(user.getPassword())
                .lvl(user.getLvl())
                .stripeCardId(user.getStripeCardId())
                .stripeCustomerId(user.getStripeCustomerId())
                .tokenStripe(user.getTokenStripe())
                .mail(user.getMail())
                .photoURL(editUserInfoResponse.getPhotoURL())
                .statusMailActivate(user.getStatusMailActivate())
                .build();

        userRepository.save(newUser);

        return UserResponse.builder()
                .id(newUser.getId())
                .name_surname(newUser.getName_surname())
                .age(newUser.getAge())
                .sex(newUser.getSex())
                .username(newUser.getUsername())
                .lvl(newUser.getLvl())
                .stripe_card_id(newUser.getStripeCardId())
                .stripe_customer_id(newUser.getStripeCustomerId())
                .token_stripe(newUser.getTokenStripe())
                .mail(newUser.getMail())
                .photoURL(newUser.getPhotoURL())
                .build();
    }

    @Override
    public CourseResponse uploadCourse(CourseRequest courseRequest) {

        if (courseRepository.existsByTitle(courseRequest.getTitle())) {
            throw new RuntimeException("Username [username: " + courseRequest.getTitle() + "] is already taken");
        }

        int userId = courseRequest.getUserId();
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException(String.format("User with id: %s not found!", userId)));


        Course course = Course.builder()
                .title(courseRequest.getTitle())
                .description(courseRequest.getDescription())
                .price(courseRequest.getPrice())
                .genre(courseRequest.getGenre())
                .linkOnVideo(courseRequest.getDownloadURL())
                .user(user)
                .statusForCheckIfUserHasThisCourse(0)
                .users(Collections.singletonList(user))
                .build();

        Course saveCourse = courseRepository.save(course);

        return CourseResponse.builder()
                .title(course.getTitle())
                .description(course.getDescription())
                .price(course.getPrice())
                .genre(course.getGenre())
                .downloadURL(course.getLinkOnVideo())
                .build();
    }

    @Override
    public void setBoughtCourse(int userId, long courseId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException(String.format("User with id: %s not found!", userId)));

        List<Course> courses = user.getCourses();

        Course course = courseRepository.findById(courseId).orElseThrow(() -> new RuntimeException(String.format("Course with id: %s not found!", courseId)));
        courses.add(course);
        user.setCourses(courses);

        userRepository.save(user);
    }
}

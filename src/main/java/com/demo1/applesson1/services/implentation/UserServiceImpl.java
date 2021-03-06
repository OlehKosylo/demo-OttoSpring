package com.demo1.applesson1.services.implentation;

import com.demo1.applesson1.dto.Request.CourseRequest;
import com.demo1.applesson1.dto.Request.UserRequest;
import com.demo1.applesson1.dto.Response.CardResponse;
import com.demo1.applesson1.dto.Response.CourseResponse;
import com.demo1.applesson1.dto.Response.UserResponse;
import com.demo1.applesson1.models.Course;
import com.demo1.applesson1.models.User;
import com.demo1.applesson1.repository.CourseRepository;
import com.demo1.applesson1.repository.UserRepository;
import com.demo1.applesson1.services.MailSenderService;
import com.demo1.applesson1.services.PaymentService;
import com.demo1.applesson1.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final PasswordEncoder passwordEncoder;
    private final PaymentService paymentService;
    private final MailSenderService mailSenderService;

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

        CardResponse card = null;
        if (user.getStripeCardId() != null) {
            card = paymentService.retrieveCard(id);
        }

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
                .mail(user.getMail())
                .card(card)
                .build();
    }


    @Override
    public UserResponse editInfoUser(UserRequest user) {

        int userId = user.getId();
        User userOfDB = userRepository.findById(userId).orElseThrow(() -> new RuntimeException(String.format("User with id: %s not found!", userId)));

        User newUser = User.builder()
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

        if (user.getPassword() != null && user.getPassword().length() >= 8) {
            newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        User save = userRepository.save(newUser);

        return UserResponse.builder()
                .name_surname(save.getName_surname())
                .age(save.getAge())
                .id(save.getId())
                .photoURL(save.getPhotoURL())
                .sex(save.getSex())
                .mail(save.getMail())
                .username(save.getUsername())
                .stripe_card_id(save.getStripeCardId())
                .stripe_customer_id(save.getStripeCustomerId())
                .token_stripe(save.getTokenStripe())
                .build();
    }

    @Override
    public UserResponse editUserPhoto(UserRequest editUserInfoResponse) {

        int userId = editUserInfoResponse.getId();
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException(String.format("User with id: %s not found!", userId)));

        user.setPhotoURL(editUserInfoResponse.getPhotoURL());

        userRepository.save(user);

        return UserResponse.builder()
                .id(user.getId())
                .name_surname(user.getName_surname())
                .age(user.getAge())
                .sex(user.getSex())
                .username(user.getUsername())
                .lvl(user.getLvl())
                .stripe_card_id(user.getStripeCardId())
                .stripe_customer_id(user.getStripeCustomerId())
                .token_stripe(user.getTokenStripe())
                .mail(user.getMail())
                .photoURL(user.getPhotoURL())
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

        Integer priceCourse = course.getPrice();
        int upgradeLvl = countPercentForLvl(priceCourse);
        user.setLvl(user.getLvl() + upgradeLvl);

        userRepository.save(user);
    }

    private int countPercentForLvl(int priceCourse) {
        int percent = (priceCourse * 10) / 1000;

        return percent;
    }
}

package me.potato.lec0126_study.services;

import lombok.RequiredArgsConstructor;
import me.potato.lec0126_study.services.exceptions.*;
import me.potato.lec0126_study.stores.UserRepository;
import me.potato.lec0126_study.stores.entities.User;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User create(User user) {
        validateName(user.getName());
        validatePassword(user.getPassword());
        String encodedPassword = encodePassword(user.getPassword());
        user.setPassword(encodedPassword);
        validateEmail(user.getEmail());
        return userRepository.save(user);
    }

    public String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    private void validateName(String name) {
        if(!checkNameLength(name)) {
            throw new NameValidationException("10002", "Name length must be greater than than 5 and less than 50");
        }
    }

    private void validatePassword(String password) {
        if(!checkPasswordLength(password)) {
            throw new PasswordValidationException("10001", "Password length must be greater than than 5 and less than 50");
        }
    }

    private void validateEmail(String email) {
        if(!checkEmailLength(email)) {
            throw new EmailValidationException("10001","Email length must be greater than than 5 and less than 50");
        }

        if(userRepository.existsByEmail(email)) {
            throw new EmailAlreadyExistException("10000", "Email already exists");
        }
    }

    private boolean checkNameLength(String name) {
        return name.length() >= 5 && name.length() <= 50;
    }

    private boolean checkPasswordLength(String password) {
        return password.length() >= 5 && password.length() <= 50;
    }

    private boolean checkEmailLength(String email) {
        return email.length() >=5 && email.length() <= 50;
    }

    public User update(Long id, User user) {
        Optional<User> existingUser = userRepository.findById(id);
        //optional 이라는 하나의 배열을 가지고 있는 배열에 들어있는 User를 미리 꺼내서 내용을 바꿔주고 싶을 때 map을 사용함 (모나딕이라고함) 처리해서 반환함
        return existingUser.map(u -> {
            if(user.getName() != null) {
                validateName((user.getName()));
                u.setName(user.getName());
            }

            if(user.getEmail() != null) {
                validateEmail(user.getEmail());
                u.setEmail(user.getEmail());
            }
            return userRepository.save(u);
        }).orElseThrow(() -> new UserNotExistException("10002", "User not found"));
        //컨슈머 서플라이어 등 공부해보기 function의 종류
    }

    public void deleteById(Long id) {
        userRepository
                .findById(id)
                .ifPresentOrElse(
                        userRepository::delete
                        , () -> {throw  new UserNotExistException("10002", "User not found");}
                );
    }

    public User getById(Long id) {
        return userRepository.findById(id).orElseThrow(() ->new UserNotExistException("10002", "User not found"));
    }

    public Iterable<User> getAll() {
        return userRepository.findAll();
    }

    public boolean checkPassword(String email, String password) {

        User user = findUserByEmail(email).get();

        if(findUserByEmail(email).isEmpty() || !passwordEncoder.matches(password, user.getPassword())) {
            throw new PasswordNotMatchedException("10009","password not matched");
        }


        return findUserByEmail(email).isPresent();

    }

    private Optional<User> findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }


}

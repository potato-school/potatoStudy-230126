package me.potato.lec0126_study.controller;

import lombok.RequiredArgsConstructor;
import me.potato.lec0126_study.controller.dtos.CreateUserRequest;
import me.potato.lec0126_study.controller.dtos.ErrorResponse;
import me.potato.lec0126_study.controller.dtos.UpdateRequest;
import me.potato.lec0126_study.controller.dtos.UserResponse;
import me.potato.lec0126_study.services.UserService;
import me.potato.lec0126_study.services.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("users")
public class UserController {

    private final UserService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<UserResponse> create(@RequestBody Mono<CreateUserRequest> createUserRequest){
        return createUserRequest
                .log()
                .filter(CreateUserRequest::isPasswordMatched)
                .switchIfEmpty(Mono.error(new PasswordValidationException("10004","password not matched")))
                .map(EntityDtoUtil::toEntity)
                .map(service::create)
                .map(EntityDtoUtil::toUserDto);
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<UserResponse> update(@PathVariable Long id, @RequestBody Mono<UpdateRequest> updateRequest) {
        return updateRequest
                .log()
                .map(EntityDtoUtil::toEntity)
                .map(user -> service.update(id,user))
                .map(EntityDtoUtil::toUserDto);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void delete(@PathVariable Long id) {
        service.deleteById(id);
    }

    @GetMapping("{id}")
    public Mono<UserResponse> getUser(@PathVariable Long id){
        return Mono.just(service.getById(id))
                .map(EntityDtoUtil::toUserDto);
    }

    @GetMapping
    public Flux<UserResponse> getAllUser() {
        return Flux
                .fromIterable(service.getAll())
                .map(EntityDtoUtil::toUserDto);
    }

    @ExceptionHandler(EmailAlreadyExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Mono<ErrorResponse> handleAlreadyEmailExist(CommonException e) {
        return Mono.just(EntityDtoUtil.toErrorDto(e));
    }

    @ExceptionHandler(EmailValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Mono<ErrorResponse> handleEmailLengthException(CommonException e) {
        return Mono.just(EntityDtoUtil.toErrorDto(e));
    }

    @ExceptionHandler(UserNotExistException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Mono<ErrorResponse> handleUserNotExist(CommonException e) {
        return Mono.just(EntityDtoUtil.toErrorDto(e));
    }

}

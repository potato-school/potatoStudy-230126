package me.potato.lec0126_study.controller;

import lombok.RequiredArgsConstructor;
import me.potato.lec0126_study.controller.dtos.ErrorResponse;
import me.potato.lec0126_study.services.UserService;
import me.potato.lec0126_study.services.exceptions.CommonException;
import me.potato.lec0126_study.services.exceptions.PasswordNotMatchedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("password-check")
public class CheckPasswordController {

    private final UserService service;

    @PostMapping("{email}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public boolean checkPassword(@PathVariable String email, @RequestBody Map<String, String> passwordMap) {
        return service.checkPassword(email, passwordMap.get("password"));
    }

    @ExceptionHandler(PasswordNotMatchedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Mono<ErrorResponse> handlePasswordNotMatched(CommonException e) {
        return Mono.just(EntityDtoUtil.toErrorDto(e));
    }

}

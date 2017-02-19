package ru.javawebinar.topjava.web.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.util.exception.ErrorInfo;
import ru.javawebinar.topjava.web.ExceptionInfoHandler;

import javax.servlet.http.HttpServletRequest;

/**
 * User: gkislin
 * Date: 23.09.2014
 */
@ControllerAdvice(basePackages = "ru.javawebinar.topjava.web.user")
@Order(Ordered.HIGHEST_PRECEDENCE)
public class UserExceptionInfoHandler extends ExceptionInfoHandler {

    @Autowired
    private MessageSource messageSource;

    @ResponseStatus(value = HttpStatus.CONFLICT)  // 409
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseBody
    @Order(Ordered.HIGHEST_PRECEDENCE + 1)
    public ErrorInfo conflict(HttpServletRequest req, DataIntegrityViolationException e) {
        return logAndGetErrorInfo(req,
                new Exception(
                        messageSource.getMessage("users.email.duplicate", null, LocaleContextHolder.getLocale())
                ), false);
    }
}

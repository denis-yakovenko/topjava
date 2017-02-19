package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.javawebinar.topjava.util.exception.ErrorInfo;
import ru.javawebinar.topjava.web.ExceptionInfoHandler;

import javax.servlet.http.HttpServletRequest;

/**
 * User: gkislin
 * Date: 23.09.2014
 */
@ControllerAdvice(basePackages = "ru.javawebinar.topjava.web.meal")
@Order(Ordered.HIGHEST_PRECEDENCE)
public class MealExceptionInfoHandler extends ExceptionInfoHandler {

    @Autowired
    private MessageSource messageSource;

    @ResponseStatus(value = HttpStatus.CONFLICT)  // 409
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseBody
    @Order(Ordered.HIGHEST_PRECEDENCE + 1)
    public ErrorInfo conflict(HttpServletRequest req, DataIntegrityViolationException e) {
        return logAndGetErrorInfo(req,
                new Exception(
                        messageSource.getMessage("meals.datetime.duplicate", null, LocaleContextHolder.getLocale())
                ), false);
    }
}

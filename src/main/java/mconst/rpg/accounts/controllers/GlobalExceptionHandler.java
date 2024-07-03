package mconst.rpg.accounts.controllers;

import mconst.rpg.accounts.models.exceptions.ConflictException;
import mconst.rpg.accounts.models.exceptions.ExceptionBody;
import mconst.rpg.accounts.models.exceptions.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ResponseBody
    @ExceptionHandler(value = { NotFoundException.class })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionBody onNotFoundException(NotFoundException exception) {
        return exception.getBody();
    }

    @ResponseBody
    @ExceptionHandler(value = { ConflictException.class })
    @ResponseStatus(HttpStatus.CONFLICT)
    public ExceptionBody onConflictException(ConflictException exception) {
        return exception.getBody();
    }
}

package com.lorenaperez.Copper.exception;

import com.lorenaperez.Copper.constants.Messages;
import com.lorenaperez.Copper.dto.GlobalResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomizedResponseEntityExceptionHandler.class);

    @ExceptionHandler(CopperCurrencyException.class)
    public final ResponseEntity<GlobalResponseDTO> handleNotFoundException(CopperCurrencyException exception) {
        LOGGER.error("Invalid Currency Exception, Message: {}", exception.getMessage());
        return new ResponseEntity<>(GlobalResponseDTO.builder().description(Messages.INVALID_CURRENCY).build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CopperPriorityException.class)
    public final ResponseEntity<GlobalResponseDTO> handleNotFoundException(CopperPriorityException exception) {
        LOGGER.error("Invalid Priority Exception, Message: {}", exception.getMessage());
        return new ResponseEntity<>(GlobalResponseDTO.builder().description(Messages.INVALID_PRIORITY).build(), HttpStatus.BAD_REQUEST);
    }
}

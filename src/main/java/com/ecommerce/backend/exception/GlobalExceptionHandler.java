package com.ecommerce.backend.exception;

import com.ecommerce.backend.dto.ApiMessageDto;
import com.ecommerce.backend.form.ErrorForm;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Arrays;
import java.util.List;

@ControllerAdvice
@RestController
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    final ObjectMapper mapper = new ObjectMapper();

    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<ApiMessageDto<String>> globalExceptionHandler(NotFoundException ex) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        apiMessageDto.setCode("ERROR-404");
        apiMessageDto.setResult(false);
        apiMessageDto.setMessage(ex.getMessage());
        return new ResponseEntity<>(apiMessageDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({AlreadyExistsException.class})
    public ResponseEntity<ApiMessageDto<String>> alreadyExistsExceptionHandler(AlreadyExistsException ex) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        apiMessageDto.setResult(false);
        apiMessageDto.setMessage(ex.getMessage());
        return new ResponseEntity<>(apiMessageDto, HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ApiMessageDto<List<ErrorForm>> exceptionHandler(Exception ex) {
        log.error("error: " + ex.getMessage(), ex);
        ApiMessageDto<List<ErrorForm>> apiMessageDto = new ApiMessageDto<>();
        apiMessageDto.setCode("ERROR-400");
        apiMessageDto.setResult(false);
        if(ex instanceof MyBindingException){
            try {
                List<ErrorForm> errorForms = Arrays.asList(mapper.readValue(ex.getMessage(), ErrorForm[].class));
                apiMessageDto.setData(errorForms);
                apiMessageDto.setMessage("Invalid form");
            }catch (Exception e){
                log.error(e.getMessage());
            }
        }else{
            apiMessageDto.setMessage("[Ex2]: " + ex.getMessage());
        }
        return apiMessageDto;
    }

    @ExceptionHandler({BadRequestException.class})
    public ResponseEntity<ApiMessageDto<String>> requestExceptionHandler(BadRequestException ex) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        apiMessageDto.setCode("ERROR-400");
        apiMessageDto.setResult(false);
        apiMessageDto.setMessage(ex.getMessage());
        return new ResponseEntity<>(apiMessageDto, HttpStatus.BAD_REQUEST);
    }
}

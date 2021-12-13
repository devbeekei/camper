package com.ss.camper.common.exception;

import com.ss.camper.common.payload.ApiResponse;
import com.ss.camper.common.payload.ApiResponseType;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 잘못된 URI로 요청했을때 발생
     */
    @ExceptionHandler({NoHandlerFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiResponse HandlerExecutionChain(NoHandlerFoundException e) {
        return ApiResponse.error(ApiResponseType.NOT_FOUND);
    }

    /**
     * 잘못된 Method로 요청했을때 발생
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse MethodArgumentNotValidException(HttpRequestMethodNotSupportedException e) {
        return ApiResponse.error(ApiResponseType.REQUEST_METHOD_NOT_SUPPORT);
    }

    /**
     * Controller Request 유효성 검증 실패
     * HttpMessageNotReadableException : Request가 null일때 발생
     * MethodArgumentNotValidException : Request가 @Valid 조건에 맞지 않을때 발생
     * MissingServletRequestParameterException : Request Parameter가  조건에 맞지 않을때 발생
     */
    @ExceptionHandler({HttpMessageNotReadableException.class, MethodArgumentNotValidException.class, MissingServletRequestParameterException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse NotValidException(Exception e) {
        return ApiResponse.error(ApiResponseType.REQUEST_NOT_VALID);
    }

    /**
     * 시퀀서 검사 실패 또는 트랜잭션 중단
     */
    @ExceptionHandler(ConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiResponse ConflictException(ConflictException e) {
        return ApiResponse.error(e.getApiResponseType());
    }

}
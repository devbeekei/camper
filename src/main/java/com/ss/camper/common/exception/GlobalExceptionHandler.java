package com.ss.camper.common.exception;

import com.ss.camper.common.payload.DefaultApiResponse;
import com.ss.camper.common.payload.ApiResponseType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 잘못된 URI로 요청했을때 발생
     */
    @ExceptionHandler({NoHandlerFoundException.class})
    public ResponseEntity<DefaultApiResponse> HandlerExecutionChain(NoHandlerFoundException e) {
        final DefaultApiResponse response = DefaultApiResponse.error(ApiResponseType.NOT_FOUND);
        return new ResponseEntity<>(response, ApiResponseType.NOT_FOUND.getStatus());
    }

    /**
     * 잘못된 Method로 요청했을때 발생
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<DefaultApiResponse> MethodArgumentNotValidException(HttpRequestMethodNotSupportedException e) {
        final DefaultApiResponse response = DefaultApiResponse.error(ApiResponseType.REQUEST_METHOD_NOT_SUPPORT);
        return new ResponseEntity<>(response, ApiResponseType.REQUEST_METHOD_NOT_SUPPORT.getStatus());
    }

    /**
     * Controller Request 유효성 검증 실패
     * HttpMessageNotReadableException : Request가 null일때 발생
     * MethodArgumentNotValidException : Request가 @Valid 조건에 맞지 않을때 발생
     * MissingServletRequestParameterException : Request Parameter가  조건에 맞지 않을때 발생
     */
    @ExceptionHandler({HttpMessageNotReadableException.class, MethodArgumentNotValidException.class, MissingServletRequestParameterException.class})
    public ResponseEntity<DefaultApiResponse> NotValidException(Exception e) {
        final DefaultApiResponse response = DefaultApiResponse.error(ApiResponseType.REQUEST_NOT_VALID);
        return new ResponseEntity<>(response, ApiResponseType.REQUEST_NOT_VALID.getStatus());
    }

    /**
     * 시퀀서 검사 실패 또는 트랜잭션 중단
     */
    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<DefaultApiResponse> ConflictException(ConflictException e) {
        final DefaultApiResponse response = DefaultApiResponse.error(e.getApiResponseType());
        return new ResponseEntity<>(response, e.getApiResponseType().getStatus());
    }

}
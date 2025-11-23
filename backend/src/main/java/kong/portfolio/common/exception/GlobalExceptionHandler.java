package kong.portfolio.common.exception;

import kong.portfolio.common.response.ResponseDto;
import kong.portfolio.common.status.StatusCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * 모든 예외를 처리하는 핸들러
     * INTERNAL_SERVER_ERROR 상태 코드와 메시지를 ResponseEntity로 반환
     */
    @ExceptionHandler({Exception.class})
    public ResponseEntity<ResponseDto> handleAllException(Exception ex) {
        log.error("[handleAllException] ex", ex);
        return handleExceptionInternal(StatusCode.INTERNAL_SERVER_ERROR);
    }

    /**
     * RestApiException을 처리하는 핸들러
     * 커스텀 에러 코드와 데이터를 ResponseEntity로 반환
     */
    @ExceptionHandler(RestApiException.class)
    public ResponseEntity<ResponseDto> handleRestApiException(RestApiException ex) {
        log.warn("[handleRestApiException] code: {}, message: {}",
                ex.getStatusCode().getServiceStatus(), ex.getMessage());
        return customHandleExceptionInternal(ex.getStatusCode(), ex.getData());
    }

    /**
     * 기본 예외 응답 생성
     */
    private ResponseEntity<ResponseDto> handleExceptionInternal(StatusCode errorCode) {
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(new ResponseDto(errorCode, null));
    }

    /**
     * 커스텀 데이터를 포함한 예외 응답 생성
     */
    private ResponseEntity<ResponseDto> customHandleExceptionInternal(StatusCode errorCode, Object data) {
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(new ResponseDto(errorCode, data));
    }
}
package kong.portfolio.common.status;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum StatusCode {

    // 성공
    SUCCESS(HttpStatus.OK, 200, "정상적으로 요청이 완료되었습니다."),
    CREATED(HttpStatus.CREATED, 201, "정상적으로 생성되었습니다."),

    // 클라이언트 에러 (400번대)
    BAD_REQUEST(HttpStatus.BAD_REQUEST, 400, "잘못된 요청입니다."),
    UNAUTHORIZED_REQUEST(HttpStatus.UNAUTHORIZED, 401, "인증되지 않은 사용자입니다."),
    FORBIDDEN_ACCESS(HttpStatus.FORBIDDEN, 403, "권한이 없는 사용자입니다."),
    NOT_FOUND(HttpStatus.NOT_FOUND, 404, "요청 정보를 찾을 수 없습니다."),

    // 데이터 검증 에러
    VALUE_CANT_NULL(HttpStatus.BAD_REQUEST, 405, "필수 값을 입력해야 합니다."),
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, 406, "입력 값이 올바르지 않습니다."),

    // 중복 에러
    DUPLICATE_USERNAME(HttpStatus.BAD_REQUEST, 410, "중복된 사용자명입니다."),
    DUPLICATE_EMAIL(HttpStatus.BAD_REQUEST, 411, "중복된 이메일입니다."),
    DUPLICATE_PROFILE(HttpStatus.BAD_REQUEST, 412, "프로필이 이미 존재합니다."),
    DUPLICATE_PROJECT(HttpStatus.BAD_REQUEST, 413, "중복된 프로젝트입니다."),
    DUPLICATE_SKILL(HttpStatus.BAD_REQUEST, 414, "중복된 기술스택입니다."),

    // 인증/인가 에러
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, 420, "유효하지 않은 토큰입니다."),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, 421, "만료된 토큰입니다."),
    INVALID_CREDENTIALS(HttpStatus.UNAUTHORIZED, 422, "아이디/비밀번호가 일치하지 않습니다."),

    // 파일 관련 에러
    FILE_UPLOAD_FAILED(HttpStatus.BAD_REQUEST, 430, "파일 업로드에 실패했습니다."),
    FILE_DELETE_FAILED(HttpStatus.BAD_REQUEST, 431, "파일 삭제에 실패했습니다."),
    INVALID_FILE_TYPE(HttpStatus.BAD_REQUEST, 432, "지원하지 않는 파일 형식입니다."),
    FILE_SIZE_EXCEEDED(HttpStatus.BAD_REQUEST, 433, "파일 크기가 제한을 초과했습니다."),

    // 서버 에러 (500번대)
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 500, "서버에서 처리 중 에러가 발생했습니다."),
    DATABASE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 501, "데이터베이스 처리 중 에러가 발생했습니다."),
    EXTERNAL_API_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 502, "외부 API 호출 중 에러가 발생했습니다."),

    // 컨텐츠 없음
    NO_CONTENT(HttpStatus.OK, 204, "요청한 정보가 없습니다.");

    private final HttpStatus httpStatus;
    private final int serviceStatus;
    private final String message;
}
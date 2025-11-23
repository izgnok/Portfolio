package kong.portfolio.common.status;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * API 응답 상태 코드 정의
 */
@Getter
@RequiredArgsConstructor
public enum StatusCode {

    // ============================================
    // Common - 공통 (200번대)
    // ============================================
    SUCCESS(HttpStatus.OK, 200, "요청이 성공했습니다"),
    CREATED(HttpStatus.CREATED, 201, "리소스가 생성되었습니다"),
    UPDATED(HttpStatus.OK, 202, "리소스가 수정되었습니다"),
    DELETED(HttpStatus.OK, 203, "리소스가 삭제되었습니다"),

    // ============================================
    // Error - 공통 에러 (400번대)
    // ============================================
    BAD_REQUEST(HttpStatus.BAD_REQUEST, 400, "잘못된 요청입니다"),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, 401, "인증이 필요합니다"),
    FORBIDDEN(HttpStatus.FORBIDDEN, 403, "접근 권한이 없습니다"),
    NOT_FOUND(HttpStatus.NOT_FOUND, 404, "리소스를 찾을 수 없습니다"),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, 405, "허용되지 않은 메서드입니다"),
    CONFLICT(HttpStatus.CONFLICT, 409, "리소스 충돌이 발생했습니다"),
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, 410, "유효하지 않은 입력값입니다"),

    // ============================================
    // Error - 서버 에러 (500번대)
    // ============================================
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 500, "서버 내부 오류가 발생했습니다"),
    SERVICE_UNAVAILABLE(HttpStatus.SERVICE_UNAVAILABLE, 503, "서비스를 사용할 수 없습니다"),

    // ============================================
    // Profile - 프로필 관련 (1000번대)
    // ============================================
    PROFILE_NOT_FOUND(HttpStatus.NOT_FOUND, 1001, "프로필을 찾을 수 없습니다"),
    PROFILE_ALREADY_EXISTS(HttpStatus.CONFLICT, 1002, "프로필이 이미 존재합니다"),
    PROFILE_UPDATE_SUCCESS(HttpStatus.OK, 200, "프로필 수정 성공"),
    PROFILE_IMAGE_UPLOAD_SUCCESS(HttpStatus.OK, 200, "프로필 이미지 업로드 성공"),
    PROFILE_IMAGE_DELETE_SUCCESS(HttpStatus.OK, 200, "프로필 이미지 삭제 성공"),

    // ============================================
    // Keyword - 키워드 관련 (1100번대)
    // ============================================
    KEYWORD_NOT_FOUND(HttpStatus.NOT_FOUND, 1101, "키워드를 찾을 수 없습니다"),
    KEYWORD_CREATE_SUCCESS(HttpStatus.CREATED, 201, "키워드 생성 성공"),
    KEYWORD_UPDATE_SUCCESS(HttpStatus.OK, 200, "키워드 수정 성공"),
    KEYWORD_DELETE_SUCCESS(HttpStatus.OK, 200, "키워드 삭제 성공"),
    KEYWORD_ORDER_UPDATE_SUCCESS(HttpStatus.OK, 200, "키워드 순서 변경 성공"),

    // ============================================
    // Education - 학력 관련 (1200번대)
    // ============================================
    EDUCATION_NOT_FOUND(HttpStatus.NOT_FOUND, 1201, "학력 정보를 찾을 수 없습니다"),
    EDUCATION_CREATE_SUCCESS(HttpStatus.CREATED, 201, "학력 정보 생성 성공"),
    EDUCATION_UPDATE_SUCCESS(HttpStatus.OK, 200, "학력 정보 수정 성공"),
    EDUCATION_DELETE_SUCCESS(HttpStatus.OK, 200, "학력 정보 삭제 성공"),
    EDUCATION_ORDER_UPDATE_SUCCESS(HttpStatus.OK, 200, "학력 순서 변경 성공"),

    // ============================================
    // Certificate - 자격증 관련 (1300번대)
    // ============================================
    CERTIFICATE_NOT_FOUND(HttpStatus.NOT_FOUND, 1301, "자격증 정보를 찾을 수 없습니다"),
    CERTIFICATE_CREATE_SUCCESS(HttpStatus.CREATED, 201, "자격증 정보 생성 성공"),
    CERTIFICATE_UPDATE_SUCCESS(HttpStatus.OK, 200, "자격증 정보 수정 성공"),
    CERTIFICATE_DELETE_SUCCESS(HttpStatus.OK, 200, "자격증 정보 삭제 성공"),
    CERTIFICATE_ORDER_UPDATE_SUCCESS(HttpStatus.OK, 200, "자격증 순서 변경 성공"),

    // ============================================
    // Award - 수상 관련 (1400번대)
    // ============================================
    AWARD_NOT_FOUND(HttpStatus.NOT_FOUND, 1401, "수상 정보를 찾을 수 없습니다"),
    AWARD_CREATE_SUCCESS(HttpStatus.CREATED, 201, "수상 정보 생성 성공"),
    AWARD_UPDATE_SUCCESS(HttpStatus.OK, 200, "수상 정보 수정 성공"),
    AWARD_DELETE_SUCCESS(HttpStatus.OK, 200, "수상 정보 삭제 성공"),
    AWARD_ORDER_UPDATE_SUCCESS(HttpStatus.OK, 200, "수상 순서 변경 성공"),

    // ============================================
    // Skill - 기술스택 관련 (1500번대)
    // ============================================
    SKILL_NOT_FOUND(HttpStatus.NOT_FOUND, 1501, "기술스택을 찾을 수 없습니다"),
    SKILL_CREATE_SUCCESS(HttpStatus.CREATED, 201, "기술스택 생성 성공"),
    SKILL_UPDATE_SUCCESS(HttpStatus.OK, 200, "기술스택 수정 성공"),
    SKILL_DELETE_SUCCESS(HttpStatus.OK, 200, "기술스택 삭제 성공"),
    SKILL_LEVEL_UPDATE_SUCCESS(HttpStatus.OK, 200, "기술스택 레벨 변경 성공"),

    // ============================================
    // Project - 프로젝트 관련 (2000번대)
    // ============================================
    PROJECT_NOT_FOUND(HttpStatus.NOT_FOUND, 2001, "프로젝트를 찾을 수 없습니다"),
    PROJECT_CREATE_SUCCESS(HttpStatus.CREATED, 201, "프로젝트 생성 성공"),
    PROJECT_UPDATE_SUCCESS(HttpStatus.OK, 200, "프로젝트 수정 성공"),
    PROJECT_DELETE_SUCCESS(HttpStatus.OK, 200, "프로젝트 삭제 성공"),
    PROJECT_ORDER_UPDATE_SUCCESS(HttpStatus.OK, 200, "프로젝트 순서 변경 성공"),

    // ProjectDetail
    PROJECT_DETAIL_NOT_FOUND(HttpStatus.NOT_FOUND, 2101, "프로젝트 상세 정보를 찾을 수 없습니다"),
    PROJECT_DETAIL_UPDATE_SUCCESS(HttpStatus.OK, 200, "프로젝트 상세 정보 수정 성공"),

    // ProjectImage
    PROJECT_IMAGE_NOT_FOUND(HttpStatus.NOT_FOUND, 2201, "프로젝트 이미지를 찾을 수 없습니다"),
    PROJECT_IMAGE_UPLOAD_SUCCESS(HttpStatus.CREATED, 201, "프로젝트 이미지 업로드 성공"),
    PROJECT_IMAGE_DELETE_SUCCESS(HttpStatus.OK, 200, "프로젝트 이미지 삭제 성공"),
    PROJECT_IMAGE_ORDER_UPDATE_SUCCESS(HttpStatus.OK, 200, "프로젝트 이미지 순서 변경 성공"),

    // ProjectTechStack
    PROJECT_TECH_STACK_NOT_FOUND(HttpStatus.NOT_FOUND, 2202, "프로젝트 기술스택을 찾을 수 없습니다"),
    PROJECT_TECH_STACK_ALREADY_EXISTS(HttpStatus.CONFLICT, 2203, "이미 추가된 기술스택입니다"),
    PROJECT_TECH_STACK_ADD_SUCCESS(HttpStatus.CREATED, 201, "기술스택 추가 성공"),
    PROJECT_TECH_STACK_REMOVE_SUCCESS(HttpStatus.OK, 200, "기술스택 제거 성공"),

    // Achievement
    ACHIEVEMENT_NOT_FOUND(HttpStatus.NOT_FOUND, 2301, "성과를 찾을 수 없습니다"),
    ACHIEVEMENT_CREATE_SUCCESS(HttpStatus.CREATED, 201, "성과 생성 성공"),
    ACHIEVEMENT_UPDATE_SUCCESS(HttpStatus.OK, 200, "성과 수정 성공"),
    ACHIEVEMENT_DELETE_SUCCESS(HttpStatus.OK, 200, "성과 삭제 성공"),
    ACHIEVEMENT_ORDER_UPDATE_SUCCESS(HttpStatus.OK, 200, "성과 순서 변경 성공"),

    // ============================================
    // User - 사용자 관련 (3000번대)
    // ============================================
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, 3001, "사용자를 찾을 수 없습니다"),
    USER_ALREADY_EXISTS(HttpStatus.CONFLICT, 3002, "이미 존재하는 사용자입니다"),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, 3003, "비밀번호가 일치하지 않습니다"),
    PASSWORD_CHANGE_SUCCESS(HttpStatus.OK, 200, "비밀번호 변경 성공"),
    USER_CREATE_SUCCESS(HttpStatus.CREATED, 201, "사용자 생성 성공"),
    USER_UPDATE_SUCCESS(HttpStatus.OK, 200, "사용자 정보 수정 성공"),
    USER_DELETE_SUCCESS(HttpStatus.OK, 200, "사용자 삭제 성공"),

    // ============================================
    // VisitorLog - 방문자 통계 관련 (3500번대)
    // ============================================
    VISITOR_LOG_CREATE_SUCCESS(HttpStatus.CREATED, 201, "방문자 로그 생성 성공"),
    VISITOR_STATS_RETRIEVED(HttpStatus.OK, 200, "방문자 통계 조회 성공"),

    // ============================================
    // File - 파일 관련 (4000번대)
    // ============================================
    FILE_UPLOAD_SUCCESS(HttpStatus.OK, 200, "파일 업로드 성공"),
    FILE_UPLOAD_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, 4001, "파일 업로드 실패"),
    FILE_DELETE_SUCCESS(HttpStatus.OK, 200, "파일 삭제 성공"),
    FILE_DELETE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, 4002, "파일 삭제 실패"),
    FILE_DOWNLOAD_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, 4003, "파일 다운로드 실패"),
    FILE_NOT_FOUND(HttpStatus.NOT_FOUND, 4004, "파일을 찾을 수 없습니다"),
    INVALID_FILE_NAME(HttpStatus.BAD_REQUEST, 4005, "유효하지 않은 파일명입니다"),
    FILE_SIZE_EXCEEDED(HttpStatus.BAD_REQUEST, 4006, "파일 크기가 제한을 초과했습니다"),
    INVALID_FILE_TYPE(HttpStatus.BAD_REQUEST, 4007, "지원하지 않는 파일 형식입니다"),
    FILE_STORAGE_INIT_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, 4008, "파일 저장소 초기화 실패");

    private final HttpStatus httpStatus;
    private final int serviceStatus;
    private final String message;
}

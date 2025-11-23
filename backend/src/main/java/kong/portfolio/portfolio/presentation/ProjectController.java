package kong.portfolio.portfolio.presentation;

import kong.portfolio.common.response.ResponseDto;
import kong.portfolio.common.status.StatusCode;
import kong.portfolio.portfolio.application.ProjectService;
import kong.portfolio.portfolio.dto.ProjectRequest;
import kong.portfolio.portfolio.dto.ProjectDetailResponse;
import kong.portfolio.portfolio.dto.ProjectListResponse;
import kong.portfolio.portfolio.dto.AwardedProjectResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    /**
     * 프로젝트 목록 조회 (프로젝트명, 이미지1개, 팀인원, 기간, 수상여부, 수상명)
     */
    @GetMapping
    public ResponseEntity<ResponseDto> getProjects() {
        List<ProjectListResponse> projects = projectService.getProjects();
        return ResponseDto.response(StatusCode.SUCCESS, projects);
    }

    /**
     * 수상작만 조회 (hasAward = true인 프로젝트)
     */
    @GetMapping("/awarded")
    public ResponseEntity<ResponseDto> getAwardedProjects() {
        List<AwardedProjectResponse> projects = projectService.getAwardedProjects();
        return ResponseDto.response(StatusCode.SUCCESS, projects);
    }

    /**
     * 프로젝트 상세 조회 (모든 정보)
     */
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto> getProject(@PathVariable Long id) {
        ProjectDetailResponse project = projectService.getProject(id);
        return ResponseDto.response(StatusCode.SUCCESS, project);
    }

    /**
     * 프로젝트 추가 (모든 정보 한번에)
     */
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseDto> createProject(
            @RequestPart("project") ProjectRequest request,
            @RequestPart(value = "projectImages", required = false) List<MultipartFile> projectImages,
            @RequestPart(value = "architectureImage", required = false) MultipartFile architectureImage) {
        ProjectDetailResponse project = projectService.createProject(request, projectImages, architectureImage);
        return ResponseDto.response(StatusCode.SUCCESS, project);
    }

    /**
     * 프로젝트 수정 (모든 정보 한번에)
     */
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseDto> updateProject(
            @PathVariable Long id,
            @RequestPart("project") ProjectRequest request,
            @RequestPart(value = "projectImages", required = false) List<MultipartFile> projectImages,
            @RequestPart(value = "architectureImage", required = false) MultipartFile architectureImage) {
        ProjectDetailResponse project = projectService.updateProject(id, request, projectImages, architectureImage);
        return ResponseDto.response(StatusCode.SUCCESS, project);
    }

    /**
     * 프로젝트 삭제
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto> deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
        return ResponseDto.response(StatusCode.SUCCESS, "프로젝트 삭제 완료");
    }
}

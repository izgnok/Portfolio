package kong.portfolio.portfolio.presentation;

import kong.portfolio.common.response.ResponseDto;
import kong.portfolio.common.status.StatusCode;
import kong.portfolio.portfolio.application.ProjectService;
import kong.portfolio.portfolio.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @GetMapping
    public ResponseEntity<ResponseDto> getAllProjects() {
        List<ProjectResponse> response = projectService.getAllProjects();
        return ResponseDto.response(StatusCode.SUCCESS, response);
    }

    @GetMapping("/{projectSeq}")
    public ResponseEntity<ResponseDto> getProjectDetail(@PathVariable Long projectSeq) {
        ProjectFullResponse response = projectService.getProjectDetail(projectSeq);
        return ResponseDto.response(StatusCode.SUCCESS, response);
    }

    @PostMapping
    public ResponseEntity<ResponseDto> createProject(@Valid @RequestBody ProjectRequest request) {
        ProjectResponse response = projectService.createProject(request);
        return ResponseDto.response(StatusCode.CREATED, response);
    }

    @PutMapping("/{projectSeq}")
    public ResponseEntity<ResponseDto> updateProject(
            @PathVariable Long projectSeq,
            @Valid @RequestBody ProjectRequest request) {
        ProjectResponse response = projectService.updateProject(projectSeq, request);
        return ResponseDto.response(StatusCode.SUCCESS, response);
    }

    @PutMapping("/{projectSeq}/detail")
    public ResponseEntity<ResponseDto> updateProjectDetail(
            @PathVariable Long projectSeq,
            @Valid @RequestBody ProjectDetailRequest request) {
        projectService.updateProjectDetail(projectSeq, request);
        return ResponseDto.response(StatusCode.SUCCESS, null);
    }

    @PostMapping("/{projectSeq}/images")
    public ResponseEntity<ResponseDto> addProjectImage(
            @PathVariable Long projectSeq,
            @RequestParam String imageUrl) {
        projectService.addProjectImage(projectSeq, imageUrl);
        return ResponseDto.response(StatusCode.CREATED, null);
    }

    @DeleteMapping("/images/{projectImageSeq}")
    public ResponseEntity<ResponseDto> deleteProjectImage(@PathVariable Long projectImageSeq) {
        projectService.deleteProjectImage(projectImageSeq);
        return ResponseDto.response(StatusCode.SUCCESS, null);
    }

    @PostMapping("/{projectSeq}/tech-stacks/{skillSeq}")
    public ResponseEntity<ResponseDto> addProjectTechStack(
            @PathVariable Long projectSeq,
            @PathVariable Long skillSeq) {
        projectService.addProjectTechStack(projectSeq, skillSeq);
        return ResponseDto.response(StatusCode.CREATED, null);
    }

    @DeleteMapping("/{projectSeq}/tech-stacks/{skillSeq}")
    public ResponseEntity<ResponseDto> removeProjectTechStack(
            @PathVariable Long projectSeq,
            @PathVariable Long skillSeq) {
        projectService.removeProjectTechStack(projectSeq, skillSeq);
        return ResponseDto.response(StatusCode.SUCCESS, null);
    }

    @PostMapping("/{projectSeq}/achievements")
    public ResponseEntity<ResponseDto> addAchievement(
            @PathVariable Long projectSeq,
            @Valid @RequestBody AchievementRequest request) {
        projectService.addAchievement(projectSeq, request);
        return ResponseDto.response(StatusCode.CREATED, null);
    }

    @PutMapping("/achievements/{achievementSeq}")
    public ResponseEntity<ResponseDto> updateAchievement(
            @PathVariable Long achievementSeq,
            @Valid @RequestBody AchievementRequest request) {
        projectService.updateAchievement(achievementSeq, request);
        return ResponseDto.response(StatusCode.SUCCESS, null);
    }

    @DeleteMapping("/achievements/{achievementSeq}")
    public ResponseEntity<ResponseDto> deleteAchievement(@PathVariable Long achievementSeq) {
        projectService.deleteAchievement(achievementSeq);
        return ResponseDto.response(StatusCode.SUCCESS, null);
    }

    @PatchMapping("/{projectSeq}/order")
    public ResponseEntity<ResponseDto> updateProjectOrder(
            @PathVariable Long projectSeq,
            @RequestParam Integer newOrder) {
        projectService.updateProjectOrder(projectSeq, newOrder);
        return ResponseDto.response(StatusCode.SUCCESS, null);
    }

    @DeleteMapping("/{projectSeq}")
    public ResponseEntity<ResponseDto> deleteProject(@PathVariable Long projectSeq) {
        projectService.deleteProject(projectSeq);
        return ResponseDto.response(StatusCode.SUCCESS, null);
    }
}

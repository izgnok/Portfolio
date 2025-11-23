package kong.portfolio.portfolio.application;

import kong.portfolio.common.exception.RestApiException;
import kong.portfolio.common.status.StatusCode;
import kong.portfolio.portfolio.dto.*;
import kong.portfolio.portfolio.entity.*;
import kong.portfolio.portfolio.infrastructure.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 프로젝트 서비스
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectDetailRepository projectDetailRepository;
    private final ProjectImageRepository projectImageRepository;
    private final ProjectTechStackRepository projectTechStackRepository;
    private final AchievementRepository achievementRepository;
    private final SkillRepository skillRepository;

    /**
     * 모든 프로젝트 조회
     */
    public List<ProjectResponse> getAllProjects() {
        return projectRepository.findAllByOrderByDisplayOrderAsc().stream()
                .map(ProjectResponse::from)
                .collect(Collectors.toList());
    }

    /**
     * 프로젝트 상세 조회 (모든 연관 데이터 포함)
     */
    public ProjectFullResponse getProjectDetail(Long projectSeq) {
        Project project = projectRepository.findById(projectSeq)
                .orElseThrow(() -> new RestApiException(StatusCode.PROJECT_NOT_FOUND));

        // 상세 정보
        ProjectDetail detail = projectDetailRepository.findByProject_ProjectSeq(projectSeq)
                .orElse(null);

        // 이미지 목록
        List<ProjectImage> images = projectImageRepository
                .findAllByProject_ProjectSeqOrderByDisplayOrderAsc(projectSeq);

        // 기술스택 목록
        List<ProjectTechStack> techStacks = projectTechStackRepository
                .findAllByProjectSeqWithSkill(projectSeq);

        // 성과 목록
        List<Achievement> achievements = achievementRepository
                .findAllByProject_ProjectSeqOrderByDisplayOrderAsc(projectSeq);

        return ProjectFullResponse.from(project, detail, images, techStacks, achievements);
    }

    /**
     * 프로젝트 생성
     */
    @Transactional
    public ProjectResponse createProject(ProjectRequest request) {
        // 다음 순서 계산
        int nextOrder = (int) projectRepository.count();

        Project project = Project.builder()
                .title(request.getTitle())
                .subtitle(request.getSubtitle())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .teamSize(request.getTeamSize())
                .projectType(request.getProjectType())
                .award(request.getAward())
                .githubUrl(request.getGithubUrl())
                .demoUrl(request.getDemoUrl())
                .icon(request.getIcon())
                .displayOrder(nextOrder)
                .build();

        Project savedProject = projectRepository.save(project);
        log.info("프로젝트 생성 완료: {}", savedProject.getProjectSeq());
        
        return ProjectResponse.from(savedProject);
    }

    /**
     * 프로젝트 기본 정보 수정
     */
    @Transactional
    public ProjectResponse updateProject(Long projectSeq, ProjectRequest request) {
        Project project = projectRepository.findById(projectSeq)
                .orElseThrow(() -> new RestApiException(StatusCode.PROJECT_NOT_FOUND));

        project.updateProject(
                request.getTitle(),
                request.getSubtitle(),
                request.getStartDate(),
                request.getEndDate(),
                request.getTeamSize(),
                request.getProjectType(),
                request.getAward(),
                request.getGithubUrl(),
                request.getDemoUrl(),
                request.getIcon()
        );

        log.info("프로젝트 수정 완료: {}", projectSeq);
        return ProjectResponse.from(project);
    }

    /**
     * 프로젝트 상세 정보 설정/수정
     */
    @Transactional
    public void updateProjectDetail(Long projectSeq, ProjectDetailRequest request) {
        Project project = projectRepository.findById(projectSeq)
                .orElseThrow(() -> new RestApiException(StatusCode.PROJECT_NOT_FOUND));

        ProjectDetail detail = projectDetailRepository.findByProject_ProjectSeq(projectSeq)
                .orElse(null);

        if (detail == null) {
            // 신규 생성
            detail = ProjectDetail.builder()
                    .project(project)
                    .summary(request.getSummary())
                    .coreValues(request.getCoreValues())
                    .mainFeatures(request.getMainFeatures())
                    .myRole(request.getMyRole())
                    .build();
            
            project.setProjectDetail(detail);
            projectDetailRepository.save(detail);
            log.info("프로젝트 상세 정보 생성 완료: {}", projectSeq);
        } else {
            // 기존 수정
            detail.updateProjectDetail(
                    request.getSummary(),
                    request.getCoreValues(),
                    request.getMainFeatures(),
                    request.getMyRole()
            );
            log.info("프로젝트 상세 정보 수정 완료: {}", projectSeq);
        }
    }

    /**
     * 프로젝트 이미지 추가
     */
    @Transactional
    public void addProjectImage(Long projectSeq, String imageUrl) {
        Project project = projectRepository.findById(projectSeq)
                .orElseThrow(() -> new RestApiException(StatusCode.PROJECT_NOT_FOUND));

        // 다음 순서 계산
        int nextOrder = (int) projectImageRepository.countByProject_ProjectSeq(projectSeq);

        ProjectImage projectImage = ProjectImage.builder()
                .project(project)
                .imageUrl(imageUrl)
                .displayOrder(nextOrder)
                .build();

        project.addProjectImage(projectImage);
        projectImageRepository.save(projectImage);
        
        log.info("프로젝트 이미지 추가 완료: {} - {}", projectSeq, imageUrl);
    }

    /**
     * 프로젝트 이미지 삭제
     */
    @Transactional
    public void deleteProjectImage(Long projectImageSeq) {
        ProjectImage projectImage = projectImageRepository.findById(projectImageSeq)
                .orElseThrow(() -> new RestApiException(StatusCode.PROJECT_IMAGE_NOT_FOUND));

        Project project = projectImage.getProject();
        project.removeProjectImage(projectImage);
        projectImageRepository.delete(projectImage);
        
        log.info("프로젝트 이미지 삭제 완료: {}", projectImageSeq);
    }

    /**
     * 프로젝트에 기술스택 추가
     */
    @Transactional
    public void addProjectTechStack(Long projectSeq, Long skillSeq) {
        Project project = projectRepository.findById(projectSeq)
                .orElseThrow(() -> new RestApiException(StatusCode.PROJECT_NOT_FOUND));

        Skill skill = skillRepository.findById(skillSeq)
                .orElseThrow(() -> new RestApiException(StatusCode.SKILL_NOT_FOUND));

//        // 이미 연결되어 있는지 확인
//        if (projectTechStackRepository.existsByProject_ProjectSeqAndSkill_SkillSeq(projectSeq, skillSeq)) {
//            throw new RestApiException(StatusCode.PROJECT_TECH_STACK_ALREADY_EXISTS);
//        }

        ProjectTechStack projectTechStack = ProjectTechStack.builder()
                .project(project)
                .skill(skill)
                .build();

        project.addTechStack(projectTechStack);
        projectTechStackRepository.save(projectTechStack);
        
        log.info("프로젝트 기술스택 추가 완료: {} - {}", projectSeq, skillSeq);
    }

    /**
     * 프로젝트에서 기술스택 제거
     */
    @Transactional
    public void removeProjectTechStack(Long projectSeq, Long skillSeq) {
        ProjectTechStack projectTechStack = projectTechStackRepository
                .findByProject_ProjectSeqAndSkill_SkillSeq(projectSeq, skillSeq)
                .orElseThrow(() -> new RestApiException(StatusCode.PROJECT_TECH_STACK_NOT_FOUND));

        Project project = projectTechStack.getProject();
        project.removeTechStack(projectTechStack);
        projectTechStackRepository.delete(projectTechStack);
        
        log.info("프로젝트 기술스택 제거 완료: {} - {}", projectSeq, skillSeq);
    }

    /**
     * 프로젝트 성과 추가
     */
    @Transactional
    public void addAchievement(Long projectSeq, AchievementRequest request) {
        Project project = projectRepository.findById(projectSeq)
                .orElseThrow(() -> new RestApiException(StatusCode.PROJECT_NOT_FOUND));

        // 다음 순서 계산
        int nextOrder = (int) achievementRepository.countByProject_ProjectSeq(projectSeq);

        Achievement achievement = Achievement.builder()
                .project(project)
                .title(request.getTitle())
                .problem(request.getProblem())
                .solution(request.getSolution())
                .displayOrder(nextOrder)
                .build();

        project.addAchievement(achievement);
        achievementRepository.save(achievement);
        
        log.info("프로젝트 성과 추가 완료: {}", projectSeq);
    }

    /**
     * 프로젝트 성과 수정
     */
    @Transactional
    public void updateAchievement(Long achievementSeq, AchievementRequest request) {
        Achievement achievement = achievementRepository.findById(achievementSeq)
                .orElseThrow(() -> new RestApiException(StatusCode.ACHIEVEMENT_NOT_FOUND));

        achievement.updateAchievement(
                request.getTitle(),
                request.getProblem(),
                request.getSolution()
        );

        log.info("프로젝트 성과 수정 완료: {}", achievementSeq);
    }

    /**
     * 프로젝트 성과 삭제
     */
    @Transactional
    public void deleteAchievement(Long achievementSeq) {
        Achievement achievement = achievementRepository.findById(achievementSeq)
                .orElseThrow(() -> new RestApiException(StatusCode.ACHIEVEMENT_NOT_FOUND));

        Project project = achievement.getProject();
        project.removeAchievement(achievement);
        achievementRepository.delete(achievement);
        
        log.info("프로젝트 성과 삭제 완료: {}", achievementSeq);
    }

    /**
     * 프로젝트 순서 변경
     */
    @Transactional
    public void updateProjectOrder(Long projectSeq, Integer newOrder) {
        Project project = projectRepository.findById(projectSeq)
                .orElseThrow(() -> new RestApiException(StatusCode.PROJECT_NOT_FOUND));

        project.updateDisplayOrder(newOrder);
        log.info("프로젝트 순서 변경 완료: {} -> {}", projectSeq, newOrder);
    }

    /**
     * 프로젝트 삭제
     */
    @Transactional
    public void deleteProject(Long projectSeq) {
        Project project = projectRepository.findById(projectSeq)
                .orElseThrow(() -> new RestApiException(StatusCode.PROJECT_NOT_FOUND));

        projectRepository.delete(project);
        log.info("프로젝트 삭제 완료: {}", projectSeq);
    }
}

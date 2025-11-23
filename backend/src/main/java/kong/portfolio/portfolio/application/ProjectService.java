package kong.portfolio.portfolio.application;

import kong.portfolio.portfolio.dto.AwardedProjectResponse;
import kong.portfolio.portfolio.dto.ProjectRequest;
import kong.portfolio.portfolio.dto.ProjectDetailResponse;
import kong.portfolio.portfolio.dto.ProjectListResponse;
import kong.portfolio.portfolio.infrastructure.ProjectRepository;
import kong.portfolio.portfolio.entity.Project;
import kong.portfolio.portfolio.entity.ProjectImage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProjectService {
    
    private final ProjectRepository projectRepository;
    
    /**
     * 프로젝트 목록 조회 (최신순)
     */
    public List<ProjectListResponse> getProjects() {
        return projectRepository.findAllByOrderByStartDateDesc()
                .stream()
                .map(ProjectListResponse::from)
                .collect(Collectors.toList());
    }
    
    /**
     * 프로젝트 상세 조회
     */
    public ProjectDetailResponse getProject(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("프로젝트를 찾을 수 없습니다."));
        return ProjectDetailResponse.from(project);
    }
    
    /**
     * 프로젝트 추가
     */
    @Transactional
    public ProjectDetailResponse createProject(
            ProjectRequest request,
            List<MultipartFile> projectImages,
            MultipartFile architectureImage) {
        
        // 프로젝트 기본 정보 생성
        Project project = Project.builder()
                .name(request.getName())
                .teamSize(request.getTeamSize())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .status(request.getStatus())
                .hasAward(request.getHasAward())
                .awardName(request.getAwardName())
                .awardOrganization(request.getAwardOrganization())
                .summaries(request.getSummaries())
                .coreValues(request.getCoreValues())
                .mainFeatures(request.getMainFeatures())
                .roles(request.getRoles())
                .techDatabase(request.getTechDatabase())
                .techBackend(request.getTechBackend())
                .techFrontend(request.getTechFrontend())
                .techIot(request.getTechIot())
                .techCicd(request.getTechCicd())
                .techExternalApi(request.getTechExternalApi())
                .problemSolutions(request.getProblemSolutions())
                .achievements(request.getAchievements())
                .regrets(request.getRegrets())
                .improvements(request.getImprovements())
                .build();
        
        // 시스템 아키텍처 이미지 처리
        if (architectureImage != null && !architectureImage.isEmpty()) {
            try {
                project.updateArchitectureImage(
                    architectureImage.getBytes(),
                    architectureImage.getContentType()
                );
            } catch (IOException e) {
                throw new RuntimeException("아키텍처 이미지 처리 중 오류가 발생했습니다.", e);
            }
        }
        
        Project savedProject = projectRepository.save(project);
        
        // 프로젝트 이미지들 처리
        if (projectImages != null && !projectImages.isEmpty()) {
            for (int i = 0; i < projectImages.size(); i++) {
                MultipartFile image = projectImages.get(i);
                if (!image.isEmpty()) {
                    try {
                        ProjectImage projectImage = ProjectImage.builder()
                                .imageData(image.getBytes())
                                .imageType(image.getContentType())
                                .displayOrder(i + 1)
                                .build();
                        savedProject.addImage(projectImage);
                    } catch (IOException e) {
                        throw new RuntimeException("프로젝트 이미지 처리 중 오류가 발생했습니다.", e);
                    }
                }
            }
        }
        
        return ProjectDetailResponse.from(savedProject);
    }
    
    /**
     * 프로젝트 수정
     */
    @Transactional
    public ProjectDetailResponse updateProject(
            Long id,
            ProjectRequest request,
            List<MultipartFile> projectImages,
            MultipartFile architectureImage) {
        
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("프로젝트를 찾을 수 없습니다."));
        
        // 기본 정보 업데이트
        project.update(
            request.getName(),
            request.getTeamSize(),
            request.getStartDate(),
            request.getEndDate(),
            request.getStatus(),
            request.getHasAward(),
            request.getAwardName(),
            request.getAwardOrganization(),
            request.getSummaries(),
            request.getCoreValues(),
            request.getMainFeatures(),
            request.getRoles(),
            request.getTechDatabase(),
            request.getTechBackend(),
            request.getTechFrontend(),
            request.getTechIot(),
            request.getTechCicd(),
            request.getTechExternalApi(),
            request.getProblemSolutions(),
            request.getAchievements(),
            request.getRegrets(),
            request.getImprovements()
        );
        
        // 시스템 아키텍처 이미지 업데이트 (제공된 경우)
        if (architectureImage != null && !architectureImage.isEmpty()) {
            try {
                project.updateArchitectureImage(
                    architectureImage.getBytes(),
                    architectureImage.getContentType()
                );
            } catch (IOException e) {
                throw new RuntimeException("아키텍처 이미지 처리 중 오류가 발생했습니다.", e);
            }
        }
        
        // 프로젝트 이미지 전체 교체 (제공된 경우)
        if (projectImages != null && !projectImages.isEmpty()) {
            project.clearImages();  // 기존 이미지 전체 삭제
            
            for (int i = 0; i < projectImages.size(); i++) {
                MultipartFile image = projectImages.get(i);
                if (!image.isEmpty()) {
                    try {
                        ProjectImage projectImage = ProjectImage.builder()
                                .imageData(image.getBytes())
                                .imageType(image.getContentType())
                                .displayOrder(i + 1)
                                .build();
                        project.addImage(projectImage);
                    } catch (IOException e) {
                        throw new RuntimeException("프로젝트 이미지 처리 중 오류가 발생했습니다.", e);
                    }
                }
            }
        }
        
        return ProjectDetailResponse.from(project);
    }

    /**
     * 수상작만 조회 (hasAward = true)
     */
    public List<AwardedProjectResponse> getAwardedProjects() {
        return projectRepository.findByHasAwardTrueOrderByStartDateDesc()
                .stream()
                .map(AwardedProjectResponse::from)
                .collect(Collectors.toList());
    }


    /**
     * 프로젝트 삭제
     */
    @Transactional
    public void deleteProject(Long id) {
        if (!projectRepository.existsById(id)) {
            throw new RuntimeException("프로젝트를 찾을 수 없습니다.");
        }
        projectRepository.deleteById(id);
    }
}

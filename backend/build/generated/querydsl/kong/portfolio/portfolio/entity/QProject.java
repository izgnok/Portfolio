package kong.portfolio.portfolio.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProject is a Querydsl query type for Project
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProject extends EntityPathBase<Project> {

    private static final long serialVersionUID = -1943259989L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProject project = new QProject("project");

    public final ListPath<Achievement, QAchievement> achievements = this.<Achievement, QAchievement>createList("achievements", Achievement.class, QAchievement.class, PathInits.DIRECT2);

    public final StringPath award = createString("award");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final StringPath delYn = createString("delYn");

    public final StringPath demoUrl = createString("demoUrl");

    public final NumberPath<Integer> displayOrder = createNumber("displayOrder", Integer.class);

    public final DatePath<java.time.LocalDate> endDate = createDate("endDate", java.time.LocalDate.class);

    public final StringPath githubUrl = createString("githubUrl");

    public final StringPath icon = createString("icon");

    public final QProjectDetail projectDetail;

    public final ListPath<ProjectImage, QProjectImage> projectImages = this.<ProjectImage, QProjectImage>createList("projectImages", ProjectImage.class, QProjectImage.class, PathInits.DIRECT2);

    public final NumberPath<Long> projectSeq = createNumber("projectSeq", Long.class);

    public final ListPath<ProjectTechStack, QProjectTechStack> projectTechStacks = this.<ProjectTechStack, QProjectTechStack>createList("projectTechStacks", ProjectTechStack.class, QProjectTechStack.class, PathInits.DIRECT2);

    public final StringPath projectType = createString("projectType");

    public final DatePath<java.time.LocalDate> startDate = createDate("startDate", java.time.LocalDate.class);

    public final StringPath subtitle = createString("subtitle");

    public final NumberPath<Integer> teamSize = createNumber("teamSize", Integer.class);

    public final StringPath title = createString("title");

    public final DateTimePath<java.time.LocalDateTime> updatedAt = createDateTime("updatedAt", java.time.LocalDateTime.class);

    public QProject(String variable) {
        this(Project.class, forVariable(variable), INITS);
    }

    public QProject(Path<? extends Project> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProject(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProject(PathMetadata metadata, PathInits inits) {
        this(Project.class, metadata, inits);
    }

    public QProject(Class<? extends Project> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.projectDetail = inits.isInitialized("projectDetail") ? new QProjectDetail(forProperty("projectDetail"), inits.get("projectDetail")) : null;
    }

}


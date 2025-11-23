package kong.portfolio.portfolio.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProjectTechStack is a Querydsl query type for ProjectTechStack
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProjectTechStack extends EntityPathBase<ProjectTechStack> {

    private static final long serialVersionUID = 1257290023L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProjectTechStack projectTechStack = new QProjectTechStack("projectTechStack");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final QProject project;

    public final NumberPath<Long> projectTechStackSeq = createNumber("projectTechStackSeq", Long.class);

    public final QSkill skill;

    public QProjectTechStack(String variable) {
        this(ProjectTechStack.class, forVariable(variable), INITS);
    }

    public QProjectTechStack(Path<? extends ProjectTechStack> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProjectTechStack(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProjectTechStack(PathMetadata metadata, PathInits inits) {
        this(ProjectTechStack.class, metadata, inits);
    }

    public QProjectTechStack(Class<? extends ProjectTechStack> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.project = inits.isInitialized("project") ? new QProject(forProperty("project"), inits.get("project")) : null;
        this.skill = inits.isInitialized("skill") ? new QSkill(forProperty("skill")) : null;
    }

}


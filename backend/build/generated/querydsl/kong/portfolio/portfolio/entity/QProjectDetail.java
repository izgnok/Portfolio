package kong.portfolio.portfolio.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProjectDetail is a Querydsl query type for ProjectDetail
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProjectDetail extends EntityPathBase<ProjectDetail> {

    private static final long serialVersionUID = 1322110172L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProjectDetail projectDetail = new QProjectDetail("projectDetail");

    public final StringPath coreValues = createString("coreValues");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final StringPath mainFeatures = createString("mainFeatures");

    public final StringPath myRole = createString("myRole");

    public final QProject project;

    public final NumberPath<Long> projectDetailSeq = createNumber("projectDetailSeq", Long.class);

    public final StringPath summary = createString("summary");

    public final DateTimePath<java.time.LocalDateTime> updatedAt = createDateTime("updatedAt", java.time.LocalDateTime.class);

    public QProjectDetail(String variable) {
        this(ProjectDetail.class, forVariable(variable), INITS);
    }

    public QProjectDetail(Path<? extends ProjectDetail> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProjectDetail(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProjectDetail(PathMetadata metadata, PathInits inits) {
        this(ProjectDetail.class, metadata, inits);
    }

    public QProjectDetail(Class<? extends ProjectDetail> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.project = inits.isInitialized("project") ? new QProject(forProperty("project"), inits.get("project")) : null;
    }

}


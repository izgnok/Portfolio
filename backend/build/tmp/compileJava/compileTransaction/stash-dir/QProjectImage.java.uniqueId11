package kong.portfolio.portfolio.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProjectImage is a Querydsl query type for ProjectImage
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProjectImage extends EntityPathBase<ProjectImage> {

    private static final long serialVersionUID = 324581232L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProjectImage projectImage = new QProjectImage("projectImage");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final NumberPath<Integer> displayOrder = createNumber("displayOrder", Integer.class);

    public final StringPath imageUrl = createString("imageUrl");

    public final QProject project;

    public final NumberPath<Long> projectImageSeq = createNumber("projectImageSeq", Long.class);

    public QProjectImage(String variable) {
        this(ProjectImage.class, forVariable(variable), INITS);
    }

    public QProjectImage(Path<? extends ProjectImage> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProjectImage(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProjectImage(PathMetadata metadata, PathInits inits) {
        this(ProjectImage.class, metadata, inits);
    }

    public QProjectImage(Class<? extends ProjectImage> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.project = inits.isInitialized("project") ? new QProject(forProperty("project"), inits.get("project")) : null;
    }

}


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

    public static final QProject project = new QProject("project");

    public final StringPath achievements = createString("achievements");

    public final ArrayPath<byte[], Byte> architectureImage = createArray("architectureImage", byte[].class);

    public final StringPath architectureImageType = createString("architectureImageType");

    public final StringPath awardName = createString("awardName");

    public final StringPath awardOrganization = createString("awardOrganization");

    public final StringPath coreValues = createString("coreValues");

    public final DatePath<java.time.LocalDate> endDate = createDate("endDate", java.time.LocalDate.class);

    public final BooleanPath hasAward = createBoolean("hasAward");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<ProjectImage, QProjectImage> images = this.<ProjectImage, QProjectImage>createList("images", ProjectImage.class, QProjectImage.class, PathInits.DIRECT2);

    public final StringPath improvements = createString("improvements");

    public final StringPath mainFeatures = createString("mainFeatures");

    public final StringPath name = createString("name");

    public final StringPath problemSolutions = createString("problemSolutions");

    public final StringPath regrets = createString("regrets");

    public final StringPath roles = createString("roles");

    public final DatePath<java.time.LocalDate> startDate = createDate("startDate", java.time.LocalDate.class);

    public final StringPath status = createString("status");

    public final StringPath summaries = createString("summaries");

    public final NumberPath<Integer> teamSize = createNumber("teamSize", Integer.class);

    public final StringPath techBackend = createString("techBackend");

    public final StringPath techCicd = createString("techCicd");

    public final StringPath techDatabase = createString("techDatabase");

    public final StringPath techExternalApi = createString("techExternalApi");

    public final StringPath techFrontend = createString("techFrontend");

    public final StringPath techIot = createString("techIot");

    public QProject(String variable) {
        super(Project.class, forVariable(variable));
    }

    public QProject(Path<? extends Project> path) {
        super(path.getType(), path.getMetadata());
    }

    public QProject(PathMetadata metadata) {
        super(Project.class, metadata);
    }

}


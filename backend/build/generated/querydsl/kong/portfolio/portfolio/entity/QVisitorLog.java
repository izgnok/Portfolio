package kong.portfolio.portfolio.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QVisitorLog is a Querydsl query type for VisitorLog
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QVisitorLog extends EntityPathBase<VisitorLog> {

    private static final long serialVersionUID = 343297316L;

    public static final QVisitorLog visitorLog = new QVisitorLog("visitorLog");

    public final StringPath device = createString("device");

    public final NumberPath<Integer> durationSeconds = createNumber("durationSeconds", Integer.class);

    public final StringPath ipAddress = createString("ipAddress");

    public final StringPath pageUrl = createString("pageUrl");

    public final StringPath userAgent = createString("userAgent");

    public final DateTimePath<java.time.LocalDateTime> visitedAt = createDateTime("visitedAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> visitorLogSeq = createNumber("visitorLogSeq", Long.class);

    public QVisitorLog(String variable) {
        super(VisitorLog.class, forVariable(variable));
    }

    public QVisitorLog(Path<? extends VisitorLog> path) {
        super(path.getType(), path.getMetadata());
    }

    public QVisitorLog(PathMetadata metadata) {
        super(VisitorLog.class, metadata);
    }

}


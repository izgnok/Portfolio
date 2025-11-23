package kong.portfolio.portfolio.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCertificate is a Querydsl query type for Certificate
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCertificate extends EntityPathBase<Certificate> {

    private static final long serialVersionUID = 686297641L;

    public static final QCertificate certificate = new QCertificate("certificate");

    public final NumberPath<Long> certificateSeq = createNumber("certificateSeq", Long.class);

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final StringPath credentialId = createString("credentialId");

    public final StringPath description = createString("description");

    public final NumberPath<Integer> displayOrder = createNumber("displayOrder", Integer.class);

    public final DatePath<java.time.LocalDate> issueDate = createDate("issueDate", java.time.LocalDate.class);

    public final StringPath issuer = createString("issuer");

    public final StringPath name = createString("name");

    public final DateTimePath<java.time.LocalDateTime> updatedAt = createDateTime("updatedAt", java.time.LocalDateTime.class);

    public QCertificate(String variable) {
        super(Certificate.class, forVariable(variable));
    }

    public QCertificate(Path<? extends Certificate> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCertificate(PathMetadata metadata) {
        super(Certificate.class, metadata);
    }

}


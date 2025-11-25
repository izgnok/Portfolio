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

    public final StringPath certificateNumber = createString("certificateNumber");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DatePath<java.time.LocalDate> issueDate = createDate("issueDate", java.time.LocalDate.class);

    public final StringPath issuer = createString("issuer");

    public final StringPath name = createString("name");

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


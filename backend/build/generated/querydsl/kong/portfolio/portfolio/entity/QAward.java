package kong.portfolio.portfolio.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QAward is a Querydsl query type for Award
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAward extends EntityPathBase<Award> {

    private static final long serialVersionUID = -1025793937L;

    public static final QAward award = new QAward("award");

    public final DatePath<java.time.LocalDate> awardDate = createDate("awardDate", java.time.LocalDate.class);

    public final NumberPath<Long> awardSeq = createNumber("awardSeq", Long.class);

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final BooleanPath deleted = createBoolean("deleted");

    public final StringPath description = createString("description");

    public final NumberPath<Integer> displayOrder = createNumber("displayOrder", Integer.class);

    public final StringPath organization = createString("organization");

    public final StringPath rank = createString("rank");

    public final StringPath title = createString("title");

    public final DateTimePath<java.time.LocalDateTime> updatedAt = createDateTime("updatedAt", java.time.LocalDateTime.class);

    public QAward(String variable) {
        super(Award.class, forVariable(variable));
    }

    public QAward(Path<? extends Award> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAward(PathMetadata metadata) {
        super(Award.class, metadata);
    }

}


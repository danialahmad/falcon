package com.twistlet.falcon.model.entity;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QFalconFeedback is a Querydsl query type for FalconFeedback
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QFalconFeedback extends EntityPathBase<FalconFeedback> {

    private static final long serialVersionUID = -1236675060;

    public static final QFalconFeedback falconFeedback = new QFalconFeedback("falconFeedback");

    public final StringPath content = createString("content");

    public final StringPath emailFrom = createString("emailFrom");

    public final StringPath feedbackType = createString("feedbackType");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public QFalconFeedback(String variable) {
        super(FalconFeedback.class, forVariable(variable));
    }

    @SuppressWarnings("all")
    public QFalconFeedback(Path<? extends FalconFeedback> path) {
        super((Class)path.getType(), path.getMetadata());
    }

    public QFalconFeedback(PathMetadata<?> metadata) {
        super(FalconFeedback.class, metadata);
    }

}


package com.twistlet.falcon.model.entity;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QFalconMessageLog is a Querydsl query type for FalconMessageLog
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QFalconMessageLog extends EntityPathBase<FalconMessageLog> {

    private static final long serialVersionUID = -1080246652;

    public static final QFalconMessageLog falconMessageLog = new QFalconMessageLog("falconMessageLog");

    public final StringPath destination = createString("destination");

    public final StringPath errorMessage = createString("errorMessage");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath message = createString("message");

    public final StringPath messageType = createString("messageType");

    public final StringPath sender = createString("sender");

    public final DateTimePath<java.util.Date> sentTime = createDateTime("sentTime", java.util.Date.class);

    public QFalconMessageLog(String variable) {
        super(FalconMessageLog.class, forVariable(variable));
    }

    @SuppressWarnings("all")
    public QFalconMessageLog(Path<? extends FalconMessageLog> path) {
        super((Class)path.getType(), path.getMetadata());
    }

    public QFalconMessageLog(PathMetadata<?> metadata) {
        super(FalconMessageLog.class, metadata);
    }

}


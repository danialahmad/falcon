package com.twistlet.falcon.model.entity;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QFalconService is a Querydsl query type for FalconService
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QFalconService extends EntityPathBase<FalconService> {

    private static final long serialVersionUID = 1119153998;

    private static final PathInits INITS = PathInits.DIRECT;

    public static final QFalconService falconService = new QFalconService("falconService");

    public final SetPath<FalconAppointment, QFalconAppointment> falconAppointments = this.<FalconAppointment, QFalconAppointment>createSet("falconAppointments", FalconAppointment.class, QFalconAppointment.class, PathInits.DIRECT);

    public final QFalconUser falconUser;

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath name = createString("name");

    public final BooleanPath valid = createBoolean("valid");

    public QFalconService(String variable) {
        this(FalconService.class, forVariable(variable), INITS);
    }

    @SuppressWarnings("all")
    public QFalconService(Path<? extends FalconService> path) {
        this((Class)path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QFalconService(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QFalconService(PathMetadata<?> metadata, PathInits inits) {
        this(FalconService.class, metadata, inits);
    }

    public QFalconService(Class<? extends FalconService> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.falconUser = inits.isInitialized("falconUser") ? new QFalconUser(forProperty("falconUser")) : null;
    }

}


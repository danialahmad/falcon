package com.twistlet.falcon.model.entity;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QFalconLocation is a Querydsl query type for FalconLocation
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QFalconLocation extends EntityPathBase<FalconLocation> {

    private static final long serialVersionUID = 855870012;

    private static final PathInits INITS = PathInits.DIRECT;

    public static final QFalconLocation falconLocation = new QFalconLocation("falconLocation");

    public final SetPath<FalconAppointment, QFalconAppointment> falconAppointments = this.<FalconAppointment, QFalconAppointment>createSet("falconAppointments", FalconAppointment.class, QFalconAppointment.class, PathInits.DIRECT);

    public final QFalconUser falconUser;

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath name = createString("name");

    public final BooleanPath valid = createBoolean("valid");

    public QFalconLocation(String variable) {
        this(FalconLocation.class, forVariable(variable), INITS);
    }

    @SuppressWarnings("all")
    public QFalconLocation(Path<? extends FalconLocation> path) {
        this((Class)path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QFalconLocation(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QFalconLocation(PathMetadata<?> metadata, PathInits inits) {
        this(FalconLocation.class, metadata, inits);
    }

    public QFalconLocation(Class<? extends FalconLocation> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.falconUser = inits.isInitialized("falconUser") ? new QFalconUser(forProperty("falconUser")) : null;
    }

}


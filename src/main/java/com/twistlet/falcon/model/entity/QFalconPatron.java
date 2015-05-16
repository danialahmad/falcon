package com.twistlet.falcon.model.entity;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QFalconPatron is a Querydsl query type for FalconPatron
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QFalconPatron extends EntityPathBase<FalconPatron> {

    private static final long serialVersionUID = 362218133;

    private static final PathInits INITS = PathInits.DIRECT;

    public static final QFalconPatron falconPatron = new QFalconPatron("falconPatron");

    public final SetPath<FalconAppointmentPatron, QFalconAppointmentPatron> falconAppointmentPatrons = this.<FalconAppointmentPatron, QFalconAppointmentPatron>createSet("falconAppointmentPatrons", FalconAppointmentPatron.class, QFalconAppointmentPatron.class, PathInits.DIRECT);

    public final QFalconUser falconUserByAdmin;

    public final QFalconUser falconUserByPatron;

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public QFalconPatron(String variable) {
        this(FalconPatron.class, forVariable(variable), INITS);
    }

    @SuppressWarnings("all")
    public QFalconPatron(Path<? extends FalconPatron> path) {
        this((Class)path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QFalconPatron(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QFalconPatron(PathMetadata<?> metadata, PathInits inits) {
        this(FalconPatron.class, metadata, inits);
    }

    public QFalconPatron(Class<? extends FalconPatron> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.falconUserByAdmin = inits.isInitialized("falconUserByAdmin") ? new QFalconUser(forProperty("falconUserByAdmin")) : null;
        this.falconUserByPatron = inits.isInitialized("falconUserByPatron") ? new QFalconUser(forProperty("falconUserByPatron")) : null;
    }

}


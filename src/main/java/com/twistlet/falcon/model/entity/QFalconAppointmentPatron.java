package com.twistlet.falcon.model.entity;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QFalconAppointmentPatron is a Querydsl query type for FalconAppointmentPatron
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QFalconAppointmentPatron extends EntityPathBase<FalconAppointmentPatron> {

    private static final long serialVersionUID = 809694246;

    private static final PathInits INITS = PathInits.DIRECT;

    public static final QFalconAppointmentPatron falconAppointmentPatron = new QFalconAppointmentPatron("falconAppointmentPatron");

    public final QFalconAppointment falconAppointment;

    public final QFalconPatron falconPatron;

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public QFalconAppointmentPatron(String variable) {
        this(FalconAppointmentPatron.class, forVariable(variable), INITS);
    }

    @SuppressWarnings("all")
    public QFalconAppointmentPatron(Path<? extends FalconAppointmentPatron> path) {
        this((Class)path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QFalconAppointmentPatron(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QFalconAppointmentPatron(PathMetadata<?> metadata, PathInits inits) {
        this(FalconAppointmentPatron.class, metadata, inits);
    }

    public QFalconAppointmentPatron(Class<? extends FalconAppointmentPatron> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.falconAppointment = inits.isInitialized("falconAppointment") ? new QFalconAppointment(forProperty("falconAppointment"), inits.get("falconAppointment")) : null;
        this.falconPatron = inits.isInitialized("falconPatron") ? new QFalconPatron(forProperty("falconPatron"), inits.get("falconPatron")) : null;
    }

}


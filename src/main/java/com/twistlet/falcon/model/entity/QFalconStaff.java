package com.twistlet.falcon.model.entity;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QFalconStaff is a Querydsl query type for FalconStaff
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QFalconStaff extends EntityPathBase<FalconStaff> {

    private static final long serialVersionUID = 707739065;

    private static final PathInits INITS = PathInits.DIRECT;

    public static final QFalconStaff falconStaff = new QFalconStaff("falconStaff");

    public final StringPath email = createString("email");

    public final SetPath<FalconAppointment, QFalconAppointment> falconAppointments = this.<FalconAppointment, QFalconAppointment>createSet("falconAppointments", FalconAppointment.class, QFalconAppointment.class, PathInits.DIRECT);

    public final QFalconUser falconUser;

    public final StringPath hpTel = createString("hpTel");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath name = createString("name");

    public final StringPath nric = createString("nric");

    public final BooleanPath sendEmail = createBoolean("sendEmail");

    public final BooleanPath sendSms = createBoolean("sendSms");

    public final BooleanPath valid = createBoolean("valid");

    public QFalconStaff(String variable) {
        this(FalconStaff.class, forVariable(variable), INITS);
    }

    @SuppressWarnings("all")
    public QFalconStaff(Path<? extends FalconStaff> path) {
        this((Class)path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QFalconStaff(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QFalconStaff(PathMetadata<?> metadata, PathInits inits) {
        this(FalconStaff.class, metadata, inits);
    }

    public QFalconStaff(Class<? extends FalconStaff> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.falconUser = inits.isInitialized("falconUser") ? new QFalconUser(forProperty("falconUser")) : null;
    }

}


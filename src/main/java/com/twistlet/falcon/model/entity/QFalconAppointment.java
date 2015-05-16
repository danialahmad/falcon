package com.twistlet.falcon.model.entity;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QFalconAppointment is a Querydsl query type for FalconAppointment
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QFalconAppointment extends EntityPathBase<FalconAppointment> {

    private static final long serialVersionUID = 270438328;

    private static final PathInits INITS = PathInits.DIRECT;

    public static final QFalconAppointment falconAppointment = new QFalconAppointment("falconAppointment");

    public final DateTimePath<java.util.Date> appointmentDate = createDateTime("appointmentDate", java.util.Date.class);

    public final DateTimePath<java.util.Date> appointmentDateEnd = createDateTime("appointmentDateEnd", java.util.Date.class);

    public final StringPath createBy = createString("createBy");

    public final DateTimePath<java.util.Date> createDate = createDateTime("createDate", java.util.Date.class);

    public final SetPath<FalconAppointmentPatron, QFalconAppointmentPatron> falconAppointmentPatrons = this.<FalconAppointmentPatron, QFalconAppointmentPatron>createSet("falconAppointmentPatrons", FalconAppointmentPatron.class, QFalconAppointmentPatron.class, PathInits.DIRECT);

    public final QFalconLocation falconLocation;

    public final QFalconService falconService;

    public final QFalconStaff falconStaff;

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final ComparablePath<Character> notified = createComparable("notified", Character.class);

    public final StringPath updateBy = createString("updateBy");

    public final DateTimePath<java.util.Date> updateDate = createDateTime("updateDate", java.util.Date.class);

    public QFalconAppointment(String variable) {
        this(FalconAppointment.class, forVariable(variable), INITS);
    }

    @SuppressWarnings("all")
    public QFalconAppointment(Path<? extends FalconAppointment> path) {
        this((Class)path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QFalconAppointment(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QFalconAppointment(PathMetadata<?> metadata, PathInits inits) {
        this(FalconAppointment.class, metadata, inits);
    }

    public QFalconAppointment(Class<? extends FalconAppointment> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.falconLocation = inits.isInitialized("falconLocation") ? new QFalconLocation(forProperty("falconLocation"), inits.get("falconLocation")) : null;
        this.falconService = inits.isInitialized("falconService") ? new QFalconService(forProperty("falconService"), inits.get("falconService")) : null;
        this.falconStaff = inits.isInitialized("falconStaff") ? new QFalconStaff(forProperty("falconStaff"), inits.get("falconStaff")) : null;
    }

}


package com.twistlet.falcon.model.entity;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QFalconUserRole is a Querydsl query type for FalconUserRole
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QFalconUserRole extends EntityPathBase<FalconUserRole> {

    private static final long serialVersionUID = -1311707800;

    private static final PathInits INITS = PathInits.DIRECT;

    public static final QFalconUserRole falconUserRole = new QFalconUserRole("falconUserRole");

    public final QFalconRole falconRole;

    public final QFalconUser falconUser;

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public QFalconUserRole(String variable) {
        this(FalconUserRole.class, forVariable(variable), INITS);
    }

    @SuppressWarnings("all")
    public QFalconUserRole(Path<? extends FalconUserRole> path) {
        this((Class)path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QFalconUserRole(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QFalconUserRole(PathMetadata<?> metadata, PathInits inits) {
        this(FalconUserRole.class, metadata, inits);
    }

    public QFalconUserRole(Class<? extends FalconUserRole> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.falconRole = inits.isInitialized("falconRole") ? new QFalconRole(forProperty("falconRole")) : null;
        this.falconUser = inits.isInitialized("falconUser") ? new QFalconUser(forProperty("falconUser")) : null;
    }

}


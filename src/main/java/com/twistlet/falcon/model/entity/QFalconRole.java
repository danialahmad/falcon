package com.twistlet.falcon.model.entity;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QFalconRole is a Querydsl query type for FalconRole
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QFalconRole extends EntityPathBase<FalconRole> {

    private static final long serialVersionUID = -115751299;

    public static final QFalconRole falconRole = new QFalconRole("falconRole");

    public final SetPath<FalconUserRole, QFalconUserRole> falconUserRoles = this.<FalconUserRole, QFalconUserRole>createSet("falconUserRoles", FalconUserRole.class, QFalconUserRole.class, PathInits.DIRECT);

    public final StringPath roleName = createString("roleName");

    public QFalconRole(String variable) {
        super(FalconRole.class, forVariable(variable));
    }

    @SuppressWarnings("all")
    public QFalconRole(Path<? extends FalconRole> path) {
        super((Class)path.getType(), path.getMetadata());
    }

    public QFalconRole(PathMetadata<?> metadata) {
        super(FalconRole.class, metadata);
    }

}


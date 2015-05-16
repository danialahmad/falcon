package com.twistlet.falcon.model.entity;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QFalconPasswordChange is a Querydsl query type for FalconPasswordChange
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QFalconPasswordChange extends EntityPathBase<FalconPasswordChange> {

    private static final long serialVersionUID = -1954692238;

    public static final QFalconPasswordChange falconPasswordChange = new QFalconPasswordChange("falconPasswordChange");

    public final DateTimePath<java.util.Date> dateRequest = createDateTime("dateRequest", java.util.Date.class);

    public final BooleanPath executed = createBoolean("executed");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath nric = createString("nric");

    public final StringPath randomString = createString("randomString");

    public QFalconPasswordChange(String variable) {
        super(FalconPasswordChange.class, forVariable(variable));
    }

    @SuppressWarnings("all")
    public QFalconPasswordChange(Path<? extends FalconPasswordChange> path) {
        super((Class)path.getType(), path.getMetadata());
    }

    public QFalconPasswordChange(PathMetadata<?> metadata) {
        super(FalconPasswordChange.class, metadata);
    }

}


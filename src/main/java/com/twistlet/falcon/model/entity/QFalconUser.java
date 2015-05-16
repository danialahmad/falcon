package com.twistlet.falcon.model.entity;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QFalconUser is a Querydsl query type for FalconUser
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QFalconUser extends EntityPathBase<FalconUser> {

    private static final long serialVersionUID = -115658286;

    public static final QFalconUser falconUser = new QFalconUser("falconUser");

    public final StringPath email = createString("email");

    public final SetPath<FalconLocation, QFalconLocation> falconLocations = this.<FalconLocation, QFalconLocation>createSet("falconLocations", FalconLocation.class, QFalconLocation.class, PathInits.DIRECT);

    public final SetPath<FalconPatron, QFalconPatron> falconPatronsForAdmin = this.<FalconPatron, QFalconPatron>createSet("falconPatronsForAdmin", FalconPatron.class, QFalconPatron.class, PathInits.DIRECT);

    public final SetPath<FalconPatron, QFalconPatron> falconPatronsForPatron = this.<FalconPatron, QFalconPatron>createSet("falconPatronsForPatron", FalconPatron.class, QFalconPatron.class, PathInits.DIRECT);

    public final SetPath<FalconService, QFalconService> falconServices = this.<FalconService, QFalconService>createSet("falconServices", FalconService.class, QFalconService.class, PathInits.DIRECT);

    public final SetPath<FalconStaff, QFalconStaff> falconStaffs = this.<FalconStaff, QFalconStaff>createSet("falconStaffs", FalconStaff.class, QFalconStaff.class, PathInits.DIRECT);

    public final SetPath<FalconUserRole, QFalconUserRole> falconUserRoles = this.<FalconUserRole, QFalconUserRole>createSet("falconUserRoles", FalconUserRole.class, QFalconUserRole.class, PathInits.DIRECT);

    public final StringPath name = createString("name");

    public final StringPath nric = createString("nric");

    public final StringPath password = createString("password");

    public final StringPath phone = createString("phone");

    public final BooleanPath sendEmail = createBoolean("sendEmail");

    public final BooleanPath sendSms = createBoolean("sendSms");

    public final NumberPath<Integer> smsRemaining = createNumber("smsRemaining", Integer.class);

    public final NumberPath<Integer> smsSentLifetime = createNumber("smsSentLifetime", Integer.class);

    public final StringPath username = createString("username");

    public final BooleanPath valid = createBoolean("valid");

    public QFalconUser(String variable) {
        super(FalconUser.class, forVariable(variable));
    }

    @SuppressWarnings("all")
    public QFalconUser(Path<? extends FalconUser> path) {
        super((Class)path.getType(), path.getMetadata());
    }

    public QFalconUser(PathMetadata<?> metadata) {
        super(FalconUser.class, metadata);
    }

}


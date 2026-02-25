package com.bluespace.marine_mis_service.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QVesselControlInfo is a Querydsl query type for VesselControlInfo
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QVesselControlInfo extends EntityPathBase<VesselControlInfo> {

    private static final long serialVersionUID = 1544396165L;

    public static final QVesselControlInfo vesselControlInfo = new QVesselControlInfo("vesselControlInfo");

    public final StringPath callSign = createString("callSign");

    public final NumberPath<Integer> controlCount = createNumber("controlCount", Integer.class);

    public final NumberPath<Integer> controlYear = createNumber("controlYear", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final StringPath entryDateTime = createString("entryDateTime");

    public final StringPath exitDateTime = createString("exitDateTime");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath infoCreateDateTime = createString("infoCreateDateTime");

    public final StringPath portName = createString("portName");

    public final NumberPath<Integer> reportCount = createNumber("reportCount", Integer.class);

    public final StringPath reportPortName = createString("reportPortName");

    public final NumberPath<Integer> reportYear = createNumber("reportYear", Integer.class);

    public final StringPath sourceApi = createString("sourceApi");

    public final StringPath status = createString("status");

    public QVesselControlInfo(String variable) {
        super(VesselControlInfo.class, forVariable(variable));
    }

    public QVesselControlInfo(Path<? extends VesselControlInfo> path) {
        super(path.getType(), path.getMetadata());
    }

    public QVesselControlInfo(PathMetadata metadata) {
        super(VesselControlInfo.class, metadata);
    }

}


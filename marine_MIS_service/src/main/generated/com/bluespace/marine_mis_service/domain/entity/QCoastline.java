package com.bluespace.marine_mis_service.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCoastline is a Querydsl query type for Coastline
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCoastline extends EntityPathBase<Coastline> {

    private static final long serialVersionUID = -1213949574L;

    public static final QCoastline coastline = new QCoastline("coastline");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath sggNam = createString("sggNam");

    public QCoastline(String variable) {
        super(Coastline.class, forVariable(variable));
    }

    public QCoastline(Path<? extends Coastline> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCoastline(PathMetadata metadata) {
        super(Coastline.class, metadata);
    }

}


package com.ss.camper.store.domain;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ss.camper.common.payload.PagingRequest;
import com.ss.camper.store.application.dto.StoreListDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import static com.ss.camper.store.domain.QStore.store;
import static com.ss.camper.store.domain.QStoreTag.storeTag;

@Repository
public class StoreRepositorySupport extends QuerydslRepositorySupport {

    private final JPAQueryFactory queryFactory;

    public StoreRepositorySupport(JPAQueryFactory queryFactory) {
        super(Store.class);
        this.queryFactory = queryFactory;
    }

    public Page<StoreListDTO> getStoreListPage(PagingRequest pagingRequest) {
        Pageable paging = pagingRequest.getPageable();

        QueryResults<StoreListDTO> result = queryFactory
            .select(Projections.constructor(StoreListDTO.class,
                store.id, store.storeType, store.storeName, store.address, store.tel,
                store.homepageUrl, store.reservationUrl, store.introduction,
                Expressions.stringTemplate("group_concat({0})", storeTag.title)
            ))
            .from(store)
            .leftJoin(storeTag).on(store.tags.contains(storeTag))
            .orderBy(store.id.desc())
            .groupBy(store.id)
            .offset(paging.getOffset())
            .limit(paging.getPageSize())
            .fetchResults();

        return new PageImpl<>(result.getResults(), paging, result.getTotal());
    }

}

package com.ss.camper.store.domain;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ss.camper.common.payload.PagingRequest;
import com.ss.camper.store.application.dto.StoreDTO;
import com.ss.camper.store.application.dto.StoreTagDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import static com.querydsl.core.group.GroupBy.groupBy;
import static com.ss.camper.store.domain.QStore.store;
import static com.ss.camper.store.domain.QStoreTag.storeTag;

@Repository
public class StoreRepositorySupport extends QuerydslRepositorySupport {

    private final JPAQueryFactory queryFactory;

    public StoreRepositorySupport(JPAQueryFactory queryFactory) {
        super(Store.class);
        this.queryFactory = queryFactory;
    }

    public Page<StoreDTO> getStorePage(PagingRequest pagingRequest) {
        Pageable paging = pagingRequest.getPageable();

        QueryResults<StoreDTO> result = queryFactory
                .select(Projections.fields(StoreDTO.class,
                        store.id, store.storeType, store.storeName, store.address, store.tel,
                        store.homepageUrl, store.reservationUrl, store.introduction
                )).from(store)
//                .leftJoin(storeTag).on(storeTag.store.equals(store))
                .orderBy(store.id.desc())
                .offset(paging.getOffset())
                .limit(paging.getPageSize())
                .fetchResults();

        return new PageImpl<>(result.getResults(), paging, result.getTotal());
    }

}

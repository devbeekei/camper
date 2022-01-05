package com.ss.camper.store.domain;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ss.camper.store.application.dto.StoreDTO;
import com.ss.camper.store.application.dto.StoreTagDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.set;

@Repository
public class StoreRepositorySupport extends QuerydslRepositorySupport {

    private final JPAQueryFactory queryFactory;

    public StoreRepositorySupport(JPAQueryFactory queryFactory) {
        super(Store.class);
        this.queryFactory = queryFactory;
    }

    public Page<StoreDTO> findPageListBySearch(int size, int page) {

        Pageable paging = PageRequest.of(page - 1, size);

        List<StoreDTO> results = queryFactory
            .from(QStore.store)
            .innerJoin(QStoreTag.storeTag)
            .on(QStore.store.id.eq(QStoreTag.storeTag.storeId))
            .limit(paging.getPageSize())
            .offset(paging.getOffset())
            .transform(
                groupBy(QStore.store.id).(
                    Projections.fields(
                        StoreDTO.class,
                        QStore.store.id, QStore.store.storeType, QStore.store.storeName, QStore.store.address,
                        QStore.store.tel, QStore.store.homepageUrl, QStore.store.reservationUrl, QStore.store.introduction,
                        set(
                            Projections.fields(
                                StoreTagDTO.class,
                                QStoreTag.storeTag.id,
                                QStoreTag.storeTag.title
                            )
                        ).as("tags")
                    )
                )
            );


        QueryResults<StoreDTO> result = queryFactory
            .select(Projections.fields(
                StoreDTO.class,
                QStore.store.id, QStore.store.storeType, QStore.store.storeName, QStore.store.address,
                QStore.store.tel, QStore.store.homepageUrl, QStore.store.reservationUrl, QStore.store.introduction,
                sortedSet(
                    Projections.fields(
                        StoreTagDTO.class,
                        QStoreTag.storeTag.id,
                        QStoreTag.storeTag.title
                    )
                ).as("tags")
            ))
            .from(QStore.store)
            .leftJoin(QStoreTag.storeTag)
            .on(QStore.store.id.eq(QStoreTag.storeTag.storeId))
            .orderBy(QStore.store.id.desc())
            .groupBy(QStore.store.id)
            .offset(paging.getOffset())
            .fetchResults();

        return new PageImpl<>(result.getResults(), paging, result.getTotal());
    }

}

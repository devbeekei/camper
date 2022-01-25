package com.ss.camper.user.infra;

import com.ss.camper.user.domain.AgreeTermsHistory;
import com.ss.camper.user.domain.AgreeTermsHistoryRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgreeTermsHistoryJpaRepository extends JpaRepository<AgreeTermsHistory, Long>, AgreeTermsHistoryRepository {
}

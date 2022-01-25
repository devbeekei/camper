package com.ss.camper.user.application;

import com.ss.camper.user.domain.AgreeTermsHistory;
import com.ss.camper.user.domain.AgreeTermsHistoryRepository;
import com.ss.camper.user.domain.TermsType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AgreeTermsService {

    private final AgreeTermsHistoryRepository agreeTermsHistoryRepository;

    public void agreeTerms(long userId, TermsType termsType, boolean agree) {
        agreeTermsHistoryRepository.save(AgreeTermsHistory.builder().user_id(userId).termsType(termsType).agree(agree).build());
    }

}

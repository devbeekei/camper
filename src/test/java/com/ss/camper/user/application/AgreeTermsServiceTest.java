package com.ss.camper.user.application;

import com.ss.camper.user.domain.AgreeTermsHistory;
import com.ss.camper.user.domain.AgreeTermsHistoryRepository;
import com.ss.camper.user.domain.TermsType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class AgreeTermsServiceTest {

    @Mock
    private AgreeTermsHistoryRepository agreeTermsHistoryRepository;

    @InjectMocks
    private AgreeTermsService agreeTermsService;

    @Test
    void 약관_동의() {
        final long userId = 2;
        final TermsType termsType = TermsType.USE;
        final boolean agree = true;
        AgreeTermsHistory agreeTermsHistory = AgreeTermsHistory.builder().id(1L).user_id(userId).termsType(termsType).agree(agree).build();
        given(agreeTermsHistoryRepository.save(any(AgreeTermsHistory.class))).willReturn(agreeTermsHistory);

        agreeTermsService.agreeTerms(userId, termsType, agree);
    }

}
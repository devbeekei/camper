package com.ss.camper.user.application;

import com.ss.camper.user.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AgreeTermsService {

    private final UserRepository userRepository;
    private final AgreeTermsHistoryRepository agreeTermsHistoryRepository;

    @Transactional
    public void agreeTerms(long userId, TermsType termsType, boolean agree) {
//        agreeTermsHistoryRepository.save(AgreeTermsHistory.builder().user_id(userId).termsType(termsType).agree(agree).build());
//        User user = userRepository.findById(userId).orElse(null);
    }

}

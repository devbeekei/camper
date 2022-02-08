package com.ss.camper.user.application;

import com.ss.camper.user.application.exception.NotFoundUserException;
import com.ss.camper.user.domain.TermsType;
import com.ss.camper.user.domain.User;
import com.ss.camper.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserAgreeTermsService {

    private final UserRepository userRepository;

    @Transactional
    public void agreeTerms(final long userId, final Map<TermsType, Boolean> terms) {
        final User user = userRepository.findById(userId).orElseThrow(NotFoundUserException::new);
        for (TermsType termsType : terms.keySet()) {
            final Boolean agree = terms.get(termsType);
            user.agreeTerms(termsType, agree != null && agree);
        }
    }

}

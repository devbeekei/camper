package com.ss.camper.user.infra;

import com.ss.camper.user.domain.SocialAuth;
import com.ss.camper.user.domain.SocialAuthRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SocialAuthJpaRepository extends JpaRepository<SocialAuth, Long>, SocialAuthRepository {
}

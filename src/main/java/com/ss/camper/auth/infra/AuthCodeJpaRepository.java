package com.ss.camper.auth.infra;

import com.ss.camper.auth.domain.AuthCode;
import com.ss.camper.auth.domain.AuthCodeRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthCodeJpaRepository extends JpaRepository<AuthCode, Long>, AuthCodeRepository {
}

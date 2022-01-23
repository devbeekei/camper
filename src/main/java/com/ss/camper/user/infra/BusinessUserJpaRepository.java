package com.ss.camper.user.infra;

import com.ss.camper.user.domain.BusinessUser;
import com.ss.camper.user.domain.BusinessUserRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusinessUserJpaRepository extends JpaRepository<BusinessUser, Long>, BusinessUserRepository {
}

package com.ss.camper.user.businessUser.infra;

import com.ss.camper.user.businessUser.domain.BusinessUser;
import com.ss.camper.user.businessUser.domain.BusinessUserRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusinessUserJpaRepository extends JpaRepository<BusinessUser, Long>, BusinessUserRepository {
}

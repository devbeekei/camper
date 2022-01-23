package com.ss.camper.user.infra;

import com.ss.camper.user.domain.ClientUser;
import com.ss.camper.user.domain.ClientUserRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientUserJpaRepository extends JpaRepository<ClientUser, Long>, ClientUserRepository {
}

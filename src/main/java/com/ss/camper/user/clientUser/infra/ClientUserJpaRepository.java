package com.ss.camper.user.clientUser.infra;

import com.ss.camper.user.clientUser.domain.ClientUser;
import com.ss.camper.user.clientUser.domain.ClientUserRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientUserJpaRepository extends JpaRepository<ClientUser, Long>, ClientUserRepository {
}

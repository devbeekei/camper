package com.ss.camper.user.infra;

import com.ss.camper.user.domain.User;
import com.ss.camper.user.domain.UserRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpgRepository extends JpaRepository<User, Long>, UserRepository {
}

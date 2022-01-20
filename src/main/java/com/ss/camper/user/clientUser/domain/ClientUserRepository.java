package com.ss.camper.user.clientUser.domain;

import java.util.Optional;

public interface ClientUserRepository {
    Optional<ClientUser> findById(long userId);
}

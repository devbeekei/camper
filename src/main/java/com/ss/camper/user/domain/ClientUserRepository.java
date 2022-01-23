package com.ss.camper.user.domain;

import java.util.Optional;

public interface ClientUserRepository {
    ClientUser save(ClientUser clientUser);
    Optional<ClientUser> findById(long userId);
}

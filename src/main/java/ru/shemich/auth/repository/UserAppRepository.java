package ru.shemich.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.shemich.auth.model.UserApp;

import java.util.Optional;

public interface UserAppRepository extends JpaRepository <UserApp,Long> {
    Optional<UserApp> findByLogin(String login);
}

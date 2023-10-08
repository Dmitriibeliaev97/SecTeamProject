package ru.secteam.teamwork.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.secteam.teamwork.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
}

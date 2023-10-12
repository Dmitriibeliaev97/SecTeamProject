package ru.secteam.teamwork.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.secteam.teamwork.model.Parent;

public interface ParentRepository extends JpaRepository<Parent, Long> {
}

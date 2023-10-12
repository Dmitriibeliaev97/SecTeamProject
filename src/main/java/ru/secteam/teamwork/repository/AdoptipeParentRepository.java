package ru.secteam.teamwork.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.secteam.teamwork.model.Parent;

public interface AdoptipeParentRepository extends JpaRepository<Parent, Long> {
}

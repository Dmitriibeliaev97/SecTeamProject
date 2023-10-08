package ru.secteam.teamwork.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.secteam.teamwork.model.Volunteer;

public interface VolunteerRepository extends JpaRepository<Volunteer, Long> {
}

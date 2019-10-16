package com.gohenry.coding.challenge.repositories;

import com.gohenry.coding.challenge.repositories.entities.Child;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChildRepository extends JpaRepository<Child, Long> {
}

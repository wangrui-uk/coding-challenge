package com.gohenry.coding.challenge.repositories;

import com.gohenry.coding.challenge.repositories.entities.Parent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParentRepository extends JpaRepository<Parent, Long> {

    Parent findByEmailAddress(String email);

}

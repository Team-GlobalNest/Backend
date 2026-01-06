package com.globalnest.backend.domain.property.repository;

import com.globalnest.backend.domain.property.entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PropertyRepository extends JpaRepository<Property, Long> {
}

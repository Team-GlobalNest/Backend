package com.globalnest.backend.api.property.repository;

import com.globalnest.backend.api.property.entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PropertyRepository extends JpaRepository<Property, Integer> {
}

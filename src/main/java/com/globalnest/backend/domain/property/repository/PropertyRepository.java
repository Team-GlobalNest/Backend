package com.globalnest.backend.domain.property.repository;

import com.globalnest.backend.domain.property.entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PropertyRepository extends JpaRepository<Property, Long>, JpaSpecificationExecutor<Property> {

    boolean existsByPropertyIdAndAgent_AgentId(Long propertyId, Long agentId);
}

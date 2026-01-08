package com.globalnest.backend.domain.property.repository;

import com.globalnest.backend.domain.property.entity.PropertyOptionList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PropertyOptionListRepository extends JpaRepository<PropertyOptionList, Long> {

    void deleteByProperty_PropertyId(Long propertyId);
}

package com.globalnest.backend.domain.property.entity;

import com.globalnest.backend.common.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Property extends BaseEntity {

    @Id
    @GeneratedValue()
    private Long id;
}

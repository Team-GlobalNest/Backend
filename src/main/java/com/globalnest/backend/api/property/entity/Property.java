package com.globalnest.backend.api.property.entity;

import com.globalnest.backend.api.global.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Property extends BaseEntity {

    @Id
    @GeneratedValue()
    private Long id;
}

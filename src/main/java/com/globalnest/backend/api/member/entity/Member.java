package com.globalnest.backend.api.member.entity;

import com.globalnest.backend.api.global.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Member extends BaseEntity {

    @Id
    @GeneratedValue()
    private Long id;
}

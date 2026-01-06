package com.globalnest.backend.domain.member.entity;

import com.globalnest.backend.common.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Member extends BaseEntity {

    @Id
    @GeneratedValue()
    private Long id;
}

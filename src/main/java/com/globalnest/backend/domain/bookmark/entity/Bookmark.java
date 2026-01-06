package com.globalnest.backend.domain.bookmark.entity;

import com.globalnest.backend.common.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;

@Getter
@Entity
public class Bookmark extends BaseEntity {

    @Id
    @GeneratedValue()
    private Long id;


}

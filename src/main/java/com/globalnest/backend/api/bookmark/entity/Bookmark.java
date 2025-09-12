package com.globalnest.backend.api.bookmark.entity;

import com.globalnest.backend.api.global.entity.BaseEntity;
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

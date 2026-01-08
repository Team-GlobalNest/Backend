package com.globalnest.backend.domain.bookmark.entity;

import com.globalnest.backend.common.BaseEntity;
import com.globalnest.backend.domain.user.entity.User;
import com.globalnest.backend.domain.property.entity.Property;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(uniqueConstraints = {
    @UniqueConstraint(columnNames = {"user_id", "property_id"})
})
public class Bookmark extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bookmark_seq_gen")
    @SequenceGenerator(
            name = "bookmark_seq_gen",
            sequenceName = "bookmark_seq",
            allocationSize = 1
    )
    private Long bookmarkId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_id", nullable = false)
    private Property property;

}

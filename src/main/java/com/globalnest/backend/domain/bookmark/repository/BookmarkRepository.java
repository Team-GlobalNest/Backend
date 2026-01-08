package com.globalnest.backend.domain.bookmark.repository;

import com.globalnest.backend.domain.bookmark.entity.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

    List<Bookmark> findByUser_UserId(Long userId);

    boolean existsByUser_UserIdAndProperty_PropertyId(Long userId, Long propertyId);

    Optional<Bookmark> findByUser_UserIdAndProperty_PropertyId(Long userId, Long propertyId);

    void deleteByUser_UserIdAndProperty_PropertyId(Long userId, Long propertyId);
}

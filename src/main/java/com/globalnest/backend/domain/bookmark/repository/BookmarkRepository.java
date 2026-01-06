package com.globalnest.backend.domain.bookmark.repository;

import com.globalnest.backend.domain.bookmark.entity.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
}

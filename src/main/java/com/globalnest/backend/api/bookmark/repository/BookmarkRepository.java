package com.globalnest.backend.api.bookmark.repository;

import com.globalnest.backend.api.bookmark.entity.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookmarkRepository extends JpaRepository<Bookmark, Integer> {
}

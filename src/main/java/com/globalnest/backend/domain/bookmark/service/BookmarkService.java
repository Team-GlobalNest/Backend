package com.globalnest.backend.domain.bookmark.service;

import com.globalnest.backend.api.bookmark.dto.response.BookmarkResponse;
import com.globalnest.backend.api.property.dto.response.PropertySummaryResponse;
import com.globalnest.backend.domain.bookmark.entity.Bookmark;
import com.globalnest.backend.domain.bookmark.repository.BookmarkRepository;
import com.globalnest.backend.domain.user.entity.User;
import com.globalnest.backend.domain.user.repository.UserRepository;
import com.globalnest.backend.domain.property.entity.Property;
import com.globalnest.backend.domain.property.repository.PropertyRepository;
import com.globalnest.backend.global.exception.DuplicateBookmarkException;
import com.globalnest.backend.global.exception.PropertyNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookmarkService {

    private final BookmarkRepository bookmarkRepository;
    private final PropertyRepository propertyRepository;
    private final UserRepository userRepository;

    @Transactional
    public BookmarkResponse addBookmark(Long propertyId, Long userId) {
        // Property 존재 확인
        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new PropertyNotFoundException("매물을 찾을 수 없습니다"));

        // User 존재 확인
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new PropertyNotFoundException("사용자를 찾을 수 없습니다"));

        // 중복 확인
        if (bookmarkRepository.existsByUser_UserIdAndProperty_PropertyId(userId, propertyId)) {
            throw new DuplicateBookmarkException("이미 즐겨찾기에 추가된 매물입니다");
        }

        // Bookmark 생성
        Bookmark bookmark = Bookmark.builder()
                .user(user)
                .property(property)
                .build();

        Bookmark savedBookmark = bookmarkRepository.save(bookmark);

        return convertToResponse(savedBookmark);
    }

    public List<BookmarkResponse> getBookmarks(Long userId) {
        List<Bookmark> bookmarks = bookmarkRepository.findByUser_UserId(userId);

        return bookmarks.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public void removeBookmark(Long propertyId, Long userId) {
        Bookmark bookmark = bookmarkRepository.findByUser_UserIdAndProperty_PropertyId(userId, propertyId)
                .orElseThrow(() -> new PropertyNotFoundException("즐겨찾기를 찾을 수 없습니다"));

        bookmarkRepository.delete(bookmark);
    }

    public boolean isBookmarked(Long propertyId, Long userId) {
        return bookmarkRepository.existsByUser_UserIdAndProperty_PropertyId(userId, propertyId);
    }

    // Private helper methods

    private BookmarkResponse convertToResponse(Bookmark bookmark) {
        Property property = bookmark.getProperty();

        PropertySummaryResponse propertyDto = PropertySummaryResponse.builder()
                .propertyId(property.getPropertyId())
                .title(property.getTitle())
                .deposit(property.getDeposit())
                .monthlyRent(property.getMonthlyRent())
                .city(property.getCity())
                .district(property.getDistrict())
                .propertyType(property.getPropertyType())
                .roomSize(property.getRoomSize())
                .numRooms(property.getNumRooms())
                .status(property.getStatus())
                .createdAt(property.getCreatedAt())
                .build();

        return BookmarkResponse.builder()
                .bookmarkId(bookmark.getBookmarkId())
                .property(propertyDto)
                .bookmarkedAt(bookmark.getCreatedAt())
                .build();
    }
}

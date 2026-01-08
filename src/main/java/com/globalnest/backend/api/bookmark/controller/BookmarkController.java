package com.globalnest.backend.api.bookmark.controller;

import com.globalnest.backend.api.bookmark.dto.response.BookmarkResponse;
import com.globalnest.backend.common.ApiResponse;
import com.globalnest.backend.domain.bookmark.service.BookmarkService;
import com.globalnest.backend.global.security.permission.PermissionChecker;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "즐겨찾기", description = "매물 즐겨찾기 관리 API")
@RestController
@RequestMapping("/api/v1/bookmarks")
@RequiredArgsConstructor
public class BookmarkController {

    private final BookmarkService bookmarkService;
    private final PermissionChecker permissionChecker;

    @Operation(summary = "즐겨찾기 추가", description = "매물을 즐겨찾기에 추가합니다.")
    @PostMapping("/{propertyId}")
    @PreAuthorize("isAuthenticated()")
    public ApiResponse<BookmarkResponse> addBookmark(@PathVariable Long propertyId) {
        Long userId = permissionChecker.getCurrentUserId();
        BookmarkResponse response = bookmarkService.addBookmark(propertyId, userId);
        return ApiResponse.success(response);
    }

    @Operation(summary = "즐겨찾기 목록 조회", description = "내 즐겨찾기 목록을 조회합니다.")
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ApiResponse<List<BookmarkResponse>> getBookmarks() {
        Long userId = permissionChecker.getCurrentUserId();
        List<BookmarkResponse> response = bookmarkService.getBookmarks(userId);
        return ApiResponse.success(response);
    }

    @Operation(summary = "즐겨찾기 삭제", description = "즐겨찾기에서 매물을 제거합니다.")
    @DeleteMapping("/{propertyId}")
    @PreAuthorize("isAuthenticated()")
    public ApiResponse<Void> removeBookmark(@PathVariable Long propertyId) {
        Long userId = permissionChecker.getCurrentUserId();
        bookmarkService.removeBookmark(propertyId, userId);
        return ApiResponse.success(null);
    }

    @Operation(summary = "즐겨찾기 여부 확인", description = "특정 매물이 즐겨찾기에 있는지 확인합니다.")
    @GetMapping("/{propertyId}/check")
    @PreAuthorize("isAuthenticated()")
    public ApiResponse<Boolean> checkBookmark(@PathVariable Long propertyId) {
        Long userId = permissionChecker.getCurrentUserId();
        boolean isBookmarked = bookmarkService.isBookmarked(propertyId, userId);
        return ApiResponse.success(isBookmarked);
    }
}

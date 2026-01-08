package com.globalnest.backend.api.bookmark.dto.response;

import com.globalnest.backend.api.property.dto.response.PropertySummaryResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookmarkResponse {

    private Long bookmarkId;
    private PropertySummaryResponse property;
    private LocalDateTime bookmarkedAt;
}

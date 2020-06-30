package com.recommendation.FRS.post;

import java.time.LocalDateTime;

public interface PostMapping {
    Long getId();
    String getTitle();
    String getEmail();
    LocalDateTime getCreated_at();
}

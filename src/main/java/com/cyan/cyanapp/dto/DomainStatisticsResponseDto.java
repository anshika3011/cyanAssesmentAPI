package com.cyan.cyanapp.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
/* Response Dto class for statistics api */
public class DomainStatisticsResponseDto {
    private long blockedMalwareAndPhishing;
    private long blockedOtherCategories;
    private long totalBlockedRequests;
}

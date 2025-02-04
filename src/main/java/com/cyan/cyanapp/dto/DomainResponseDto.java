package com.cyan.cyanapp.dto;
import com.cyan.cyanapp.model.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
/* Response Dto for domain-checker API */
public class DomainResponseDto {
    private String domain;
    private Category category;
    private boolean blocked;
}

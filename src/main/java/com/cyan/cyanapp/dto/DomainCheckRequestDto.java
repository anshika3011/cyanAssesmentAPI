package com.cyan.cyanapp.dto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
/* Request Dto class for domain-checker API */
public class DomainCheckRequestDto {
    private String url;
}

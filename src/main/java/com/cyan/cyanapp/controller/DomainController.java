package com.cyan.cyanapp.controller;
import com.cyan.cyanapp.dto.DomainCheckRequestDto;
import com.cyan.cyanapp.dto.DomainResponseDto;
import com.cyan.cyanapp.dto.DomainStatisticsResponseDto;
import com.cyan.cyanapp.service.DomainService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cyanapp/api")
/**
 * Provides endpoints for checking domain categories and retrieving statistics.
 */
public class DomainController {
    private final DomainService domainService;

    public DomainController(DomainService domainService) {
        this.domainService = domainService;
    }
    /**
     * Endpoint to check the category and blocked status of a given domain.
     *
     * @param request DTO containing the URL to check
     * @return ResponseEntity containing DomainResponseDto with domain details
     */
    @PostMapping("/domain-checker")
    public ResponseEntity<DomainResponseDto> checkDomain(@RequestBody DomainCheckRequestDto request) {
        DomainResponseDto result = domainService.checkDomain(request);
        return ResponseEntity.ok(result);
    }
    /**
     * Endpoint to retrieve statistics of blocked domains
     * @return ResponseEntity containing DomainStatisticsResponseDto with statistical data
     */
    @GetMapping("/domain-checker/statistics")
    public ResponseEntity<DomainStatisticsResponseDto> getStatistics() {
        DomainStatisticsResponseDto stats = domainService.getStatistics();
        return ResponseEntity.ok(stats);
    }
}

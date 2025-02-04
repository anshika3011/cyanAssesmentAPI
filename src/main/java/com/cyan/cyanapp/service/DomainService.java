package com.cyan.cyanapp.service;
import com.cyan.cyanapp.dto.DomainCheckRequestDto;
import com.cyan.cyanapp.dto.DomainResponseDto;
import com.cyan.cyanapp.dto.DomainStatisticsResponseDto;
import com.cyan.cyanapp.model.Category;
import com.cyan.cyanapp.model.Domain;
import com.cyan.cyanapp.repository.DomainRepository;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
/**
 * Provides methods to check the category and blocked status of a domain
 * and retrieve domain blocking statistics.
 */
@Service
public class DomainService {
    private final DomainRepository domainRepository;
    // Atomic counter to track the number of times a blocked domain is accessed
    private final AtomicLong totalBlockedRequests = new AtomicLong(0); 

    public DomainService(DomainRepository domainRepository) {
        this.domainRepository = domainRepository;
    }
    /**
     * Checks the category and blocked status of a given domain.
     *
     * @param request DTO containing the URL to check
     * @return DomainResponseDto containing the domain name, category, and blocked status
     */
    public DomainResponseDto  checkDomain(DomainCheckRequestDto request) {
        //Extracting domain name from the URL
        String domainName = extractDomain(request.getUrl());
        Optional<Domain> domain = domainRepository.findByDomain(domainName);
        //If domain found, then return the details
        if (!domain.isEmpty()) {
            if (domain.get().isBlocked()) {
                totalBlockedRequests.incrementAndGet();
            }

            return new DomainResponseDto(
                domain.get().getDomain(),
                domain.get().getCategory(),
                domain.get().isBlocked()
            );
        }

        //If domain is not found, return a default response with UNKNOWN category
        return new DomainResponseDto(domainName, Category.UNKNOWN, false);

    }
    /**
     * Get statistics of blocked domains
     * 
     * @return DomainStatisticsResponseDto containing counts of blocked domains
     */
    public DomainStatisticsResponseDto getStatistics() {
        // Get count of blocked domains in the "MALWARE_AND_PHISHING" category
        long blockedMalwareAndPhishing = domainRepository.countByCategoryAndBlocked(Category.MALWARE_AND_PHISHING, true);
        // Get count of blocked domains in all categories except malware
        long blockedOtherCategories = domainRepository.countByCategoryNotAndBlocked(Category.MALWARE_AND_PHISHING, true);
        // Get the total no. of times the blocked domain have been accessed
        long totalBlockedRequestCalls = totalBlockedRequests.get();

        return new DomainStatisticsResponseDto(blockedMalwareAndPhishing, blockedOtherCategories, totalBlockedRequestCalls);
    }
     /**
     * Extracts the domain name from a given URL.
     *
     * @param url The URL to extract the domain from
     * @return The extracted domain name without "www."
     * @throws IllegalArgumentException if the URL format is invalid
     */
    private String extractDomain(String url) {
        try {
            URI uri = new URI(url);
            return uri.getHost().replace("www.", "");
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("Invalid URL format");
        }
    }
}

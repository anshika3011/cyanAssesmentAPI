package com.cyan.cyanapp.service;
import com.cyan.cyanapp.dto.DomainCheckRequestDto;
import com.cyan.cyanapp.dto.DomainResponseDto;
import com.cyan.cyanapp.dto.DomainStatisticsResponseDto;
import com.cyan.cyanapp.model.Category;
import com.cyan.cyanapp.model.Domain;
import com.cyan.cyanapp.repository.DomainRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit test class for DomainService.
 */
@ExtendWith(MockitoExtension.class)
public class DomainServiceTest {
    @Mock
    private DomainRepository domainRepository;

    @InjectMocks
    private DomainService domainService;

    private final String testUrl = "https://www.instagram.com/cyandigitalsecurity";
    private final String testDomain = "instagram.com";

    /**
     * Test when the domain exists in the database, the service should return its details.
     */
    @Test
    void testCheckDomain_WhenDomainExists() {
        // Creating a mock domain object
        Domain mockDomain = new Domain();
        mockDomain.setDomain(testDomain);
        mockDomain.setBlocked(true);  
        mockDomain.setCategory(Category.SOCIAL_MEDIA);
        //Mock repository response: When findByDomain() is called, return the mock domain
        when(domainRepository.findByDomain(testDomain)).thenReturn(Optional.of(mockDomain));

        // Calling the service method
        DomainCheckRequestDto request = new DomainCheckRequestDto();
        request.setUrl(testUrl);
        DomainResponseDto response = domainService.checkDomain(request);
        

        // validating the response
        assertNotNull(response);
        assertEquals(testDomain, response.getDomain());
        assertEquals(Category.SOCIAL_MEDIA, response.getCategory());
        assertTrue(response.isBlocked());
    }
    
    /**
     * Test when the domain does not exist in the database, the service should return UNKNOWN category.
     */
    @Test
    void testCheckDomain_WhenDomainDoesNotExist() {
        // Mock repository response to return empty (domain not found)
        when(domainRepository.findByDomain(testDomain)).thenReturn(Optional.empty());

        // Calling the service class method
        DomainCheckRequestDto request = new DomainCheckRequestDto();
        request.setUrl(testUrl);
        DomainResponseDto response = domainService.checkDomain(request);

        // Validating the response
        assertNotNull(response);
        assertEquals(testDomain, response.getDomain());
        assertEquals(Category.UNKNOWN, response.getCategory());
        assertFalse(response.isBlocked());
    }
    /**
     * Test to verify that the getStatistics() method correctly retrieves domain blocking statistics.
     */
    @Test
    void testGetStatistics() {
        // Mock repository response for count methods
        when(domainRepository.countByCategoryAndBlocked(Category.MALWARE_AND_PHISHING, true)).thenReturn(2L);
        when(domainRepository.countByCategoryNotAndBlocked(Category.MALWARE_AND_PHISHING, true)).thenReturn(5L);

        // Calling the service class method
        DomainStatisticsResponseDto statistics = domainService.getStatistics();

        // Validating the response
        assertNotNull(statistics);
        assertEquals(2, statistics.getBlockedMalwareAndPhishing());
        assertEquals(5, statistics.getBlockedOtherCategories());
        assertEquals(0, statistics.getTotalBlockedRequests()); 
    }
}

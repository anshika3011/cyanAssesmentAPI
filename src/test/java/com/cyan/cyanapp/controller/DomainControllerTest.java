package com.cyan.cyanapp.controller;
import com.cyan.cyanapp.dto.DomainCheckRequestDto;
import com.cyan.cyanapp.dto.DomainResponseDto;
import com.cyan.cyanapp.dto.DomainStatisticsResponseDto;
import com.cyan.cyanapp.service.DomainService;
import com.cyan.cyanapp.model.Category;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit test class for the DomainController.
 * 
 */
@ExtendWith(MockitoExtension.class)
public class DomainControllerTest {
    @Mock
    private DomainService domainService;

    @InjectMocks
    private DomainController domainController;

    /**
     * Test case for checking a domain's category and blocked status.
     *
     */
    @Test
    void testCheckDomain() {
        // Creating a request DTO with a sample URL
        DomainCheckRequestDto request = new DomainCheckRequestDto();
        request.setUrl("https://www.instagram.com/cyandigitalsecurity");
        // Creating a expected response from the service class
        DomainResponseDto expectedResponse = new DomainResponseDto("instagram.com", Category.SOCIAL_MEDIA, true);
        // Mock the service call to return the expected response
        when(domainService.checkDomain(request)).thenReturn(expectedResponse);

        // Calling the controller method
        ResponseEntity<DomainResponseDto> responseEntity = domainController.checkDomain(request);

        // Validating th response
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse, responseEntity.getBody());

    }
    /**
     * Test case for retrieving domain blocking statistics.
     *
     */
    @Test
    void testGetStatistics() {
        //Mock the expected statistics response 
        DomainStatisticsResponseDto expectedStats = new DomainStatisticsResponseDto(2, 5, 1);
        // Mock the service call to return the expected statistics
        when(domainService.getStatistics()).thenReturn(expectedStats);

        // Calling the controller method
        ResponseEntity<DomainStatisticsResponseDto> responseEntity = domainController.getStatistics();

        // Validating the response
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedStats, responseEntity.getBody());

    }
}

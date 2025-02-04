package com.cyan.cyanapp.repository;
import com.cyan.cyanapp.model.Domain;
import com.cyan.cyanapp.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface  DomainRepository extends JpaRepository<Domain, Long>{
    /* Finds the domain in the DOMAIN table by the domain name passed as the parameter */
    Optional<Domain> findByDomain(String domain);
    /* Counts number of domains blocked for MALWARE_AND_PHISHING category */
    long countByCategoryAndBlocked(Category category, boolean blocked);

    /* Counts number of blocked domains for all categories except MALWARE_AND_PHISHING */
    @Query("SELECT COUNT(d) FROM Domain d WHERE d.category <> ?1 AND d.blocked = true")
    long countByCategoryNotAndBlocked(Category category, boolean blocked);
}

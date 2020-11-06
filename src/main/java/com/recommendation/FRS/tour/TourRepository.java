package com.recommendation.FRS.tour;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TourRepository extends JpaRepository<Tour, Long> {

    // tour name search
    Page<TourMapping> findByTourNameContaining(String name, Pageable pageable);

    // all tour
    Page<TourMapping> findAllBy(Pageable pageable);

    // location
    Page<TourMapping> findByAddressContaining(String Address, Pageable pageable);

    // Thema
    Page<TourMapping> findByBigColContaining(String BigCol, Pageable pageable);

}
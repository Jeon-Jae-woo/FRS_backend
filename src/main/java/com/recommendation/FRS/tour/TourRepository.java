package com.recommendation.FRS.tour;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TourRepository extends JpaRepository<Tour, Long> {

    Page<TourMapping> findByTourNameContaining(String name, Pageable pageable);
    Page<TourMapping> findAllBy(Pageable pageable);

    //테마 ?
    Page<TourMapping> findByAddressContaining(String Address, Pageable pageable);

}
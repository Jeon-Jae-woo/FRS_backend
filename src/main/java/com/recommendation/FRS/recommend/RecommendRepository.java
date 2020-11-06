package com.recommendation.FRS.recommend;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;

public interface RecommendRepository extends JpaRepository<RecommendDB, Long> {

    List<RecommendDB> findByFeatureIn(ArrayList<String> total);

}

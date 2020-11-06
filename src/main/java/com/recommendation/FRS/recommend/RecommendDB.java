package com.recommendation.FRS.recommend;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RecommendDB {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String feature;

    private float sampling;

    private float nature;

    private float humanity;

    private float reports;

    private float shopping;

    private float cuisine;

}

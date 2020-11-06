package com.recommendation.FRS.tour;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


// 여행지 정보 수정이 필요한 경우 createdAt, updatedAt, createUser, UpdateUser 필요
// base entity에 위 정보 넣고 받아서 사용 예정

@Getter
@Setter
@Entity
public class Tour {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String tourName;

    @Column(nullable = false)
    private String bigCol;

    @Column(nullable = false)
    private String midCol;

    @Column(nullable = false)
    private String smallCol;

    private String address;
    private String longitude;
    private String latitude;

    @Column(length=10000)
    private String summery;

}

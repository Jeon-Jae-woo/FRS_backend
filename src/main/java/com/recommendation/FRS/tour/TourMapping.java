package com.recommendation.FRS.tour;

import org.springframework.beans.factory.annotation.Autowired;

public interface TourMapping {
    Long getId();
    String getTourName();
    String getBigCol();
    String getMidCol();
    String getSmallCol();
    String getAddress();
    String getSummery();
}

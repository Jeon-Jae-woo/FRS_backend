package com.recommendation.FRS.tour;

import com.recommendation.FRS.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TourService {
    private final TourRepository tourRepository;


    public Page<TourMapping> FindAllTour(String name, int page){
        PageRequest pageRequest = PageRequest.of(page,10);
        if(name == null){
            Page<TourMapping> tourList = tourRepository.findAllBy(pageRequest);
            return tourList;
        }
        else{
            Page<TourMapping> tourList = tourRepository.findByTourNameContaining(name,pageRequest);
            return tourList;
        }
    }

    public Page<TourMapping> FindLocationTour(String location, int page){
        PageRequest pageRequest = PageRequest.of(page, 10);
        if(location == null){
            throw new CustomException.NotFoundException("Tour Not Found");
        }
        else{
            Page<TourMapping> tourLocation = tourRepository.findByAddressContaining(location, pageRequest);
            return tourLocation;
        }

    }

    public Page<TourMapping> FindThemaTour(String thema, int page){
        PageRequest pageRequest = PageRequest.of(page,10);
        if(thema == null){
            throw new CustomException.NotFoundException("Tour Not Found");
        }
        else{
            Page<TourMapping> tourThema = tourRepository.findByBigColContaining(thema, pageRequest);
            return tourThema;
        }
    }



    public Tour LoadTour(long id){
        Tour tour = tourRepository.findById(id)
                .orElseThrow(() -> new CustomException.NotFoundException("Tour Not Found"));
        return tour;
    }

}

package com.recommendation.FRS.recommend;

import com.recommendation.FRS.tour.Tour;
import com.recommendation.FRS.tour.TourMapping;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletRequest;

@RestController
@RequiredArgsConstructor
public class RecommendController {

    private final RecommendService recommendService;


    @GetMapping("/recommend")
    private HttpEntity<PagedResourcesAssembler<Tour>> RecommendList(
            ServletRequest request, @RequestParam(value = "page", defaultValue = "0")int page,
            PagedResourcesAssembler assembler
    )
    {
        System.out.println("recommend controller");
        Page<TourMapping> recommendList = recommendService.RecommendTour(request,page);
        return new ResponseEntity(assembler.toModel(recommendList), HttpStatus.OK);
    }


    /*
    @GetMapping("/recommend")
    private List<RecommendDB> RecommendList(
            ServletRequest request
    ){
        System.out.println("recommend Controller");
        List<RecommendDB> recommendDB = recommendService.RecommendTour(request);
        return recommendDB;
    }

     */
}

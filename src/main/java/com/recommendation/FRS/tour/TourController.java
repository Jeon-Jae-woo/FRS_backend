package com.recommendation.FRS.tour;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;


@RestController
@RequiredArgsConstructor
public class TourController {

    private final TourService tourService;

    @GetMapping("/tour/list")
    public HttpEntity<PagedResourcesAssembler<Tour>> TourList(
            @Param(value="name")String name, @RequestParam(value = "page", defaultValue = "0")int page,
                               PagedResourcesAssembler assembler){

        Page<TourMapping> tourList = tourService.FindAllTour(name,page);
        
        return new ResponseEntity(assembler.toModel(tourList), HttpStatus.OK);
    }

    @GetMapping("/tour/{id}")
    public EntityModel<Tour> LoadTour(@PathVariable long id){
        Tour tourDetails = tourService.LoadTour(id);
        EntityModel<Tour> entityModel = new EntityModel<>(tourDetails);
        return entityModel;
    }

}

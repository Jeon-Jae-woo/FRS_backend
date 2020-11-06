package com.recommendation.FRS.recommend;

import com.recommendation.FRS.tour.TourMapping;
import com.recommendation.FRS.tour.TourRepository;
import com.recommendation.FRS.user.User;
import com.recommendation.FRS.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.servlet.ServletRequest;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class RecommendService {

    private final UserService userService;
    private final RecommendRepository recommendRepository;
    private final TourRepository tourRepository;

    // 유저가 존재해야 한다. 토큰을 통해 유저를 확인하고, 유저에서 나이, 성별, 국적을 뽑아낸다
    // 유저 나이,성별,국적으로 쿼리를 날려 수치를 뽑아옴, 제일 높은 수치를 tour api 에 쿼리를 날려서 조회해서 반환해줌
    public Page<TourMapping> RecommendTour(ServletRequest request, int page){
        PageRequest pageRequest = PageRequest.of(page,10);
        String userEmail = userService.authUser(request);
        User user = userService.findUser(userEmail);
        
        try{
            // 국적,성별,나이로 쿼리 보내서 해당 국가 통계 뽑기
            ArrayList<String> total = new ArrayList<>();
            String userNationality = user.getNationality();
            String userGender = user.getGender();
            int userAge = Math.toIntExact(user.getAge());
            String dbAge;
            if(15<=userAge && 20>=userAge){
                dbAge = "15-20_Ages";
            }
            else if(21<=userAge && 30>=userAge){
                dbAge = "21-30_Ages";
            }
            else if(31<=userAge && 40>=userAge){
                dbAge = "31-40_Ages";
            }
            else if(41<=userAge && 50>=userAge){
                dbAge = "41-50_Ages";
            }
            else if(51<=userAge && 60>=userAge){
                dbAge = "51-60_Ages";
            }
            else{
                dbAge = "61-over_Ages";
            }

            total.add(userNationality);
            total.add(userGender);
            total.add(dbAge);

            List<RecommendDB> recommendResult = recommendRepository.findByFeatureIn(total);

            // 국적, 나이, 성별 통계들 더해서 가장 높은 column 찾기
            HashMap<String, Float> sumResult = new HashMap<>();
            sumResult.put("Cuisine" , recommendResult.get(0).getCuisine()+recommendResult.get(1).getCuisine()+recommendResult.get(2).getCuisine());
            sumResult.put("nature" , recommendResult.get(0).getNature()+recommendResult.get(1).getNature()+recommendResult.get(2).getNature());
            sumResult.put("humanity" , recommendResult.get(0).getHumanity()+recommendResult.get(1).getHumanity()+recommendResult.get(2).getHumanity());
            sumResult.put("reports" , recommendResult.get(0).getReports()+recommendResult.get(1).getReports()+recommendResult.get(2).getReports());
            sumResult.put("shopping" , recommendResult.get(0).getShopping()+recommendResult.get(1).getShopping()+recommendResult.get(2).getShopping());
            Float maxValue = Collections.max(sumResult.values());

            AtomicReference<String> recommendKey= new AtomicReference<>("");
            for(Map.Entry<String,Float> entry : sumResult.entrySet()){
                if (entry.getValue()==maxValue){
                    recommendKey.set(entry.getKey());
                }
            }
            Page<TourMapping> tourList = tourRepository.findByBigColContaining(recommendKey.get(), pageRequest);
            return tourList;
        }
        catch (Exception e){
            throw new IllegalArgumentException("The value is not valid");
        }
    }
}

package com.yeom.pass.service.instructor;

import com.yeom.pass.repository.instructor.InstructorEntity;
import com.yeom.pass.repository.instructor.InstructorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.*;

@Service
@RequiredArgsConstructor
public class InstructorService {
    private final EntityManager em;

    private final InstructorRepository instructorRepository;


    public List<Instructor> getInstructorsByDate(String date){

        TypedQuery<Object[]> query = em.createQuery("SELECT i.name, VALUE(t) FROM InstructorEntity i JOIN i.time t WHERE KEY(t) = :date", Object[].class)
                .setParameter("date", date);    // JPQL에서 여러 필드를 선택하면 Object[] 형태로 반환
        // KEY(t) =  테이블 t의 Map 컬렉션의 키에 해당하는 값 , VALUE(t) = 테이블 t의 Map 컬렉션의 value 에 해당하는 값
        List<Object[]> nameList = query.getResultList();
        List<Instructor> instructorList = new ArrayList<>();

        if(nameList.isEmpty())
            return null;

        for (Object[] result : nameList) {
            String name = (String) result[0];
            List<String> timeList = Arrays.asList(((String) result[1]).split(",")); // 시간 배열 -> 리스트

            Instructor instructor = new Instructor(name, timeList);
            instructorList.add(instructor);
        }

        return instructorList;

    }

    @Transactional
    public void updateTimes(String date, List<String> timeList, String name){

        Map<String, String> resultMap = new HashMap<>();
        InstructorEntity instructorEntity = instructorRepository.findAllByName(name).get(0);
        String times = instructorEntity.getTime().get(date);

        for(String time : timeList){
            times += " ," + time;
        }
//        times += " ,20:00-21:00";
//        times += " ,21:00-22:00";
        resultMap.put(date, times);
        instructorEntity.setTime(resultMap);

        em.persist(instructorEntity);   // 파라미터의 날짜와 이름에 해당하는 강사의 시간표 업데이트
    }

}

package com.yeom.pass.service.instructor;

import com.yeom.pass.repository.instructor.InstructDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.*;

@Service
@RequiredArgsConstructor
public class InstructorService {
    private final EntityManager em;

    public List<InstructDto> getInstructorsByDate(String date){

        List<InstructDto> instructDtoList = new ArrayList<>();

        List<Object[]> List = em.createQuery("SELECT idt.id, i.name, idt.time, idt.limitNumber, idt.reserveNumber\n" +
                "FROM Instruct i\n" +
                "INNER JOIN i.instructDates id\n" + "INNER JOIN id.instructDateTimes idt\n" +
                "WHERE id.date = :date", Object[].class).setParameter("date", date).getResultList();


        for (Object[] result : List) {
            Integer id = Integer.parseInt(result[0].toString());
            String name = result[1].toString();
            String time = result[2].toString();
            String limitNumber = result[3].toString();
            String reserveNumber = result[4].toString();

            InstructDto instructDto = new InstructDto(id, name,time,limitNumber,reserveNumber);
            instructDtoList.add(instructDto);
        }

        return instructDtoList;

    }

//    @Transactional
//    public void updateTimes(String date, List<String> timeList, String name){
//
//        Map<String, String> resultMap = new HashMap<>();
//        InstructorEntity instructorEntity = instructorRepository.findAllByName(name).get(0);
//        String times = instructorEntity.getTime().get(date);
//
//        for(String time : timeList){
//            times += " ," + time;
//        }
////        times += " ,20:00-21:00";
////        times += " ,21:00-22:00";
//        resultMap.put(date, times);
//        instructorEntity.setTime(resultMap);
//
//        em.persist(instructorEntity);   // 파라미터의 날짜와 이름에 해당하는 강사의 시간표 업데이트
//    }

}

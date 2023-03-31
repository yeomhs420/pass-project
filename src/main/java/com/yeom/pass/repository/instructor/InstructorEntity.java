package com.yeom.pass.repository.instructor;

import lombok.*;

import javax.persistence.*;
import java.util.Map;

@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "instructor")
public class InstructorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private String name;

    @ElementCollection(targetClass = String.class)
    @CollectionTable(
            name = "instructor_time",
            joinColumns = @JoinColumn(name = "instructor_id")
    )
    @MapKeyColumn(name = "date")    // key 이름 지정
    @Column(name = "time")  // value 이름 지정
    private Map<String, String> time;

    @Enumerated(EnumType.STRING)
    private Gender gender;
}
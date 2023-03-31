package com.yeom.pass.repository.instructor;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InstructorRepository extends JpaRepository<InstructorEntity, Long> {

    List<InstructorEntity> findAllByName(String name);
}

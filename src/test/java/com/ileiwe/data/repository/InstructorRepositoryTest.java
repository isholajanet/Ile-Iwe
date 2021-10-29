package com.ileiwe.data.repository;

import com.ileiwe.data.model.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
@Sql(scripts = {"/db/insert.sql"})
class InstructorRepositoryTest {

    @Autowired
    InstructorRepository instructorRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    void saveInstructorAsLearningPartyTest(){
        //create a learning party
        LearningParty user = new LearningParty("trainer@ileiwe.com", "password", new Authority(Role.ROLE_INSTRUCTOR));
        //create instructor
        Instructor instructor = Instructor.builder().firstname("John")
                .lastname("Doe").learningParty(user).build();
        //map instructor with learning party
        //save instructor
        log.info("Before saving -> {}", instructor);
        instructorRepository.save(instructor);
        assertThat(instructor.getId()).isNotNull();
        assertThat(instructor.getLearningParty().getId()).isNotNull();
        log.info("After saving -> {}", instructor);


    }
    @Test
    void saveUniqueInstructorAsLearningPartyTest(){
        //create a learning party
        LearningParty user = new LearningParty("trainer@ileiwe.com", "password", new Authority(Role.ROLE_INSTRUCTOR));
        //create instructor
        Instructor instructor = Instructor.builder().firstname("John")
                .lastname("Doe").learningParty(user).build();
        //save learning party
        instructorRepository.save(instructor);
        //Create a new learning party
        LearningParty user2 = new LearningParty("trainer@ileiwe.com", "password", new Authority(Role.ROLE_INSTRUCTOR));
        //create instructor
        Instructor instructor2 = Instructor.builder().firstname("John")
                .lastname("Doe").learningParty(user2).build();
        assertThrows(DataIntegrityViolationException.class, () -> instructorRepository.save(instructor2));
    }

    @Test
    void instructorWithNullValuesTest(){
        //create a learning party
        LearningParty user = new LearningParty("trainer@ileiwe.com", "password", new Authority(Role.ROLE_INSTRUCTOR));
        //create instructor
        Instructor instructor = Instructor.builder().firstname(null)
                .lastname(null).learningParty(user).build();
        //save instructor
        assertThrows(ConstraintViolationException.class, () -> instructorRepository.save(instructor));
    }
    @Test
    void instructorWithEmptyValuesTest(){
        //create a learning party
        LearningParty user = new LearningParty("trainer@ileiwe.com", "password", new Authority(Role.ROLE_INSTRUCTOR));
        //create instructor
        Instructor instructor = Instructor.builder().firstname("")
                .lastname("").learningParty(user).build();
        //save instructor
        assertThrows(ConstraintViolationException.class, () -> instructorRepository.save(instructor));
    }
    @Test
    void instructorWithEmptyStringValuesTest(){
        //create a learning party
        LearningParty user = new LearningParty("trainer@ileiwe.com", "password", new Authority(Role.ROLE_INSTRUCTOR));
        //create instructor
        Instructor instructor = Instructor.builder().firstname("  ")
                .lastname("  ").learningParty(user).build();
        //save instructor
        assertThrows(ConstraintViolationException.class, () -> instructorRepository.save(instructor));
    }
    @Test
    @Transactional
    @Rollback(value = false)
    void updateInstructorTest(){
        //create a learning party
        LearningParty user = new LearningParty("trainer@ileiwe.com", "password", new Authority(Role.ROLE_INSTRUCTOR));
        //create instructor
        Instructor instructor = Instructor.builder().firstname("John")
                .lastname("Doe").learningParty(user).build();
        //map instructor with learning party
        //save instructor
        log.info("Before saving -> {}", instructor);
        instructorRepository.save(instructor);
        assertThat(instructor.getId()).isNotNull();
        assertThat(instructor.getLearningParty().getId()).isNotNull();
        log.info("After saving -> {}", instructor);

        Instructor savedInstructor = instructorRepository.findById(instructor.getId()).orElse(null);

        assertThat(savedInstructor).isNotNull();
        assertThat(savedInstructor.getBio()).isNull();
        assertThat(savedInstructor.getGender()).isNull();

        savedInstructor.setBio("I am a java instructor");
        savedInstructor.setGender(Gender.MALE);

        instructorRepository.save(savedInstructor);
        assertThat(savedInstructor.getBio()).isNotNull();
        assertThat(savedInstructor.getGender()).isNotNull();
        log.info("After saving --> {}", savedInstructor);
    }

    @AfterEach
    void tearDown() {
    }
}
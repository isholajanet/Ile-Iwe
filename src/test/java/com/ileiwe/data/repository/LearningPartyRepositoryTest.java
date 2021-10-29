package com.ileiwe.data.repository;

import com.ileiwe.data.model.Authority;
import com.ileiwe.data.model.LearningParty;
import com.ileiwe.data.model.Role;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
@Sql(scripts = {"/db/insert.sql"})
@Transactional
class LearningPartyRepositoryTest {

    @Autowired
    LearningPartyRepository learningPartyRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    @Transactional
    @Rollback(value = false)
    void createLearningPartyTest(){
//        LearningParty learningUser = LearningParty.builder()
//                .email("janet@gmail.com")
//                .password("password123").build();
        LearningParty learningUser = new LearningParty("yomi@gmail.com",
                "Yomi123", new Authority(Role.ROLE_STUDENT));

        log.info("Before saving --> {}", learningUser);
        learningPartyRepository.save(learningUser);
        assertThat(learningUser.getId()).isNotNull();
        assertThat(learningUser.getEmail()).isEqualTo("yomi@gmail.com");
        assertThat(learningUser.getAuthorities().get(0).getAuthority()).isEqualTo(Role.ROLE_STUDENT);
        assertThat(learningUser.getAuthorities().get(0).getId()).isNotNull();
        log.info("After saving --> {}", learningUser);
    }

    @Test
    void createLearningPartyUniqueEmailsTest(){
        //create a learning party
        LearningParty learningUser = new LearningParty("yomi@gmail.com",
                "Yomi123", new Authority(Role.ROLE_STUDENT));
        //save to db
        learningPartyRepository.save(learningUser);
        assertThat(learningUser.getEmail()).isEqualTo("yomi@gmail.com");
        assertThat(learningUser.getId()).isNotNull();
        //create another learning party with the same email
        LearningParty learningUser2 = new LearningParty("yomi@gmail.com",
                "Yomi123", new Authority(Role.ROLE_STUDENT));
        //save and catch exception

        assertThrows(DataIntegrityViolationException.class, () -> learningPartyRepository.save(learningUser2));
        //assert that exception was thrown
    }

    @Test
    void learningPartyWithNullValuesTest(){
        //create a learning party with null values
        LearningParty learningUser2 = new LearningParty(null,
                null, new Authority(Role.ROLE_STUDENT));
        //save and catch exception

        assertThrows(ConstraintViolationException.class, () -> learningPartyRepository.save(learningUser2));
        //assert that exception was thrown
    }
    @Test
    void learningPartyWithEmptyValuesTest(){
        //create a learning party with null values
        LearningParty learningUser = new LearningParty("",
                "", new Authority(Role.ROLE_STUDENT));
        assertThrows(ConstraintViolationException.class, () -> learningPartyRepository.save(learningUser));

    }
    @Test
    void learningPartyWithEmptyStringValuesTest(){
        //create a learning party with null values
        LearningParty learningParty = new LearningParty("   ",
                "   ", new Authority(Role.ROLE_STUDENT));
        assertThrows(ConstraintViolationException.class, () -> learningPartyRepository.save(learningParty));
    }

    @Test
    void findByUserNameTest(){
        LearningParty learningParty = learningPartyRepository.findByEmail("tomi@gmail.com");
        assertThat(learningParty).isNotNull();
        assertThat(learningParty.getEmail()).isEqualTo("tomi@gmail.com");
        log.info("Learning party object --> {}", learningParty);

    }

    @AfterEach
    void tearDown() {
    }

}
package com.haraji.baseinfo.database.repository;

import com.haraji.baseinfo.database.repository.location.CityRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = CityRepository.class)
class CityRepositoryTest {

    @Autowired
    CityRepository cityRepository;

    @Test
    void findById() {
        cityRepository.findById(1L);
    }
}

package com.haraji.baseinfo.service.location.city;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@SpringBootTest(classes = CityService.class)
@RunWith(SpringRunner.class)
@EnableAutoConfiguration
class CityServiceTest {

    @Autowired
    CityService cityService;

    @Test
    public void getById() {
        cityService.getById(1L);
    }
}

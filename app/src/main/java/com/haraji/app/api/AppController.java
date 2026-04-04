package com.haraji.app.api;

import com.haraji.app.model.dto.UploadedMultipartFileDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class AppController {

    @RequestMapping("/")
    public String hello()
    {
        UploadedMultipartFileDto dto = new UploadedMultipartFileDto();
        return "Hello User";
    }


}

package com.haraji.app.mapper;

import com.haraji.app.model.ThunderUploadedMultipartFile;
import com.haraji.app.model.dto.UploadedMultipartFileDto;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public abstract class UploadedMultipartFileMapper {
    public abstract ThunderUploadedMultipartFile toModel(UploadedMultipartFileDto dto);
}

package com.haraji.business.mapper;


import com.haraji.business.api.SaleResponseDto;
import com.haraji.business.database.entity.SaleViewEntity;
import com.haraji.business.model.SaleView;
import com.haraji.common.mapper.StructMapper;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public abstract class SaleMapper extends StructMapper<SaleView, SaleViewEntity, SaleResponseDto> {


//    public abstract SaleView toModel(SaleViewEntity entity);
//
//    public abstract List<SaleView> toModelList(List<SaleViewEntity> stateEntityList);
//
//    public abstract SaleViewResponseDto toDtoResponse(SaleView entity);
//
//    public abstract List<SaleViewResponseDto> toDtoResponseList(List<SaleView> stateList);

}

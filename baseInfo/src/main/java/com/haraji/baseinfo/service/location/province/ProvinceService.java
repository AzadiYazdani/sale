package com.haraji.baseinfo.service.location.province;

import com.haraji.baseinfo.api.location.ProvinceResponse;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
public interface ProvinceService {

    List<ProvinceResponse> getAll();

    Page<ProvinceResponse> getAllByPaging(Pageable pageable);

    List<ProvinceResponse> searchTitle(@NotBlank String name);

    ProvinceResponse getById(@Min(1) Long provinceId);
}

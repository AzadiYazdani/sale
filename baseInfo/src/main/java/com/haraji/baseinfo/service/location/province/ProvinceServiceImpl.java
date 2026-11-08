package com.haraji.baseinfo.service.location.province;

import com.haraji.baseinfo.api.location.ProvinceResponse;
import com.haraji.baseinfo.database.entity.location.ProvinceEntity;
import com.haraji.baseinfo.database.repository.location.ProvinceRepository;
import com.haraji.baseinfo.mapper.location.ProvinceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProvinceServiceImpl implements ProvinceService {

    private final ProvinceRepository provinceRepository;
    private final ProvinceMapper provinceMapper;

    @Override
    public List<ProvinceResponse> getAll() {
        return provinceMapper.toResponseList(provinceRepository.findAll());
    }

    @Override
    public Page<ProvinceResponse> getAllByPaging(Pageable pageable) {
        return provinceRepository.findAll(pageable).map(provinceMapper::toResponse);
    }

    @Override
    public List<ProvinceResponse> searchTitle(String name) {
        return provinceMapper.toResponseList(provinceRepository.findAllByNameContainingIgnoreCase(name.trim()));
    }

    @Override
    public ProvinceResponse getById(Long provinceId) {
        ProvinceEntity province = provinceRepository.findById(provinceId)
                .orElseThrow(() -> new jakarta.persistence.EntityNotFoundException("استان موردنظر یافت نشد."));
        return provinceMapper.toResponse(province);
    }
}

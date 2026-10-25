
package com.haraji.baseinfo.service.business.businessunion;

import com.haraji.baseinfo.api.businessUnion.BusinessUnionRequest;
import com.haraji.baseinfo.api.businessUnion.BusinessUnionResponse;
import com.haraji.baseinfo.database.entity.business.BusinessUnionEntity;
import com.haraji.baseinfo.database.repository.business.BusinessUnionRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@Validated
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BusinessUnionServiceImpl implements BusinessUnionService {

    private final BusinessUnionRepository businessUnionRepository;

    @Override
    public BusinessUnionResponse getById(@Min(1) Long id) {
        return toResponse(findById(id));
    }

    @Override
    public List<BusinessUnionResponse> getAll() {
        return businessUnionRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    public Page<BusinessUnionResponse> getAllByPaging(@NotNull Pageable pageable) {
        return businessUnionRepository.findAll(pageable).map(this::toResponse);
    }

    @Override
    public List<BusinessUnionResponse> getByCityId(@Min(1) Long cityId) {
        return businessUnionRepository.findAllByCityId(cityId).stream().map(this::toResponse).toList();
    }

    @Override
    public Page<BusinessUnionResponse> getByCityIdPaging(@Min(1) Long cityId, @NotNull Pageable pageable) {
        return businessUnionRepository.findAllByCityId(cityId, pageable).map(this::toResponse);
    }

    @Override
    public List<BusinessUnionResponse> searchTitle(@NotBlank String title) {
        return businessUnionRepository.findAllByTitleContainingIgnoreCase(title.trim()).stream().map(this::toResponse).toList();
    }

    @Override
    @Transactional
    public BusinessUnionResponse create(@Valid @NotNull BusinessUnionRequest request) {
        BusinessUnionEntity entity = new BusinessUnionEntity();
        entity.setCityId(request.getCityId());
        entity.setTitle(request.getTitle().trim());
        entity.setBoss(request.getBoss());
        entity.setAddress(request.getAddress());
        entity.setTelephone(request.getTelephone());
        return toResponse(businessUnionRepository.save(entity));
    }

    @Override
    @Transactional
    public BusinessUnionResponse update(@Min(1) Long id, @Valid @NotNull BusinessUnionRequest request) {
        BusinessUnionEntity entity = findById(id);
        entity.setCityId(request.getCityId());
        entity.setTitle(request.getTitle().trim());
        entity.setBoss(request.getBoss());
        entity.setAddress(request.getAddress());
        entity.setTelephone(request.getTelephone());
        return toResponse(businessUnionRepository.save(entity));
    }

    @Override
    @Transactional
    public void delete(@Min(1) Long id) {
        businessUnionRepository.delete(findById(id));
    }

    private BusinessUnionEntity findById(Long id) {
        return businessUnionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("اتحادیه صنفی با شناسه " + id + " یافت نشد."));
    }

    private BusinessUnionResponse toResponse(BusinessUnionEntity entity) {
        return BusinessUnionResponse.newInstance()
                .setId(entity.getId())
                .setCityId(entity.getCityId())
                .setTitle(entity.getTitle())
                .setBoss(entity.getBoss())
                .setAddress(entity.getAddress())
                .setTelephone(entity.getTelephone())
                .build();
    }
}

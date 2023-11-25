package com.ecommerce.backend.service;

import com.ecommerce.backend.dto.ResponseListDto;
import com.ecommerce.backend.dto.provider.ProviderAdminDto;
import com.ecommerce.backend.dto.provider.ProviderDto;
import com.ecommerce.backend.form.provider.CreateProviderForm;
import com.ecommerce.backend.form.provider.UpdateProviderForm;
import com.ecommerce.backend.storage.criteria.ProviderCriteria;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProviderService {

    ProviderAdminDto getProvicderById(Long id);

    ResponseListDto<List<ProviderAdminDto>> getProvicderList(ProviderCriteria providerCriteria, Pageable pageable);

    List<ProviderDto> getProvicderListAutoComplete(ProviderCriteria providerCriteria);

    void createProvicder(CreateProviderForm providerForm);

    void updateProvicder(UpdateProviderForm updateProviderForm);

    void deleteProvicder(Long id);
}

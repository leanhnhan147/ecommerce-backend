package com.ecommerce.backend.service;

import com.ecommerce.backend.dto.ResponseListDto;
import com.ecommerce.backend.dto.optionValue.OptionValueAdminDto;
import com.ecommerce.backend.dto.optionValue.OptionValueDto;
import com.ecommerce.backend.form.optionValue.CreateOptionValueForm;
import com.ecommerce.backend.form.optionValue.UpdateOptionValueForm;
import com.ecommerce.backend.storage.criteria.OptionValueCriteria;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OptionValueService {

    OptionValueAdminDto getOptionValueById(Long id);

    ResponseListDto<List<OptionValueAdminDto>> getOptionValueList(OptionValueCriteria optionValueCriteria, Pageable pageable);

    List<OptionValueDto> getOptionValueListAutoComplete(OptionValueCriteria optionValueCriteria);

    OptionValueDto createOptionValue(CreateOptionValueForm createOptionValueForm);

    void updateOptionValue(UpdateOptionValueForm updateOptionValueForm);
}

package com.ecommerce.backend.service;

import com.ecommerce.backend.dto.ResponseListDto;
import com.ecommerce.backend.dto.option.OptionAdminDto;
import com.ecommerce.backend.dto.option.OptionDto;
import com.ecommerce.backend.form.option.CreateOptionForm;
import com.ecommerce.backend.form.option.UpdateOptionForm;
import com.ecommerce.backend.storage.criteria.OptionCriteria;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OptionService {

    OptionAdminDto getOptionByIdOrCode(Long id, String Code);

    ResponseListDto<List<OptionAdminDto>> getOptionList(OptionCriteria optionCriteria, Pageable pageable);

    List<OptionDto> getOptionListAutoComplete(OptionCriteria optionCriteria);

    OptionDto createOption(CreateOptionForm createOptionForm);

    void updateOption(UpdateOptionForm updateOptionForm);
}

package com.ecommerce.site.admin.service;

import com.ecommerce.site.admin.dto.ResponseListDto;
import com.ecommerce.site.admin.dto.optionValue.OptionValueAdminDto;
import com.ecommerce.site.admin.form.optionValue.CreateOptionValueForm;
import com.ecommerce.site.admin.form.optionValue.UpdateOptionValueForm;
import com.ecommerce.site.admin.storage.criteria.OptionValueCriteria;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OptionValueService {

    OptionValueAdminDto getOptionValueById(Long id);

    ResponseListDto<List<OptionValueAdminDto>> getOptionValueList(OptionValueCriteria optionValueCriteria, Pageable pageable);

    void createOptionValue(CreateOptionValueForm createOptionValueForm);

    void updateOptionValue(UpdateOptionValueForm updateOptionValueForm);
}

package com.ecommerce.site.admin.service;

import com.ecommerce.site.admin.dto.ResponseListDto;
import com.ecommerce.site.admin.dto.option.OptionAdminDto;
import com.ecommerce.site.admin.dto.option.OptionDto;
import com.ecommerce.site.admin.form.option.CreateOptionForm;
import com.ecommerce.site.admin.form.option.UpdateOptionForm;
import com.ecommerce.site.admin.storage.criteria.OptionCriteria;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OptionService {

    OptionAdminDto getOptionById(Long id);

    ResponseListDto<List<OptionAdminDto>> getOptionList(OptionCriteria optionCriteria, Pageable pageable);

    void createOption(CreateOptionForm createOptionForm);

    void updateOption(UpdateOptionForm updateOptionForm);

    List<OptionDto> getOptionListAutoComplete(OptionCriteria optionCriteria);
}

package com.ecommerce.site.admin.service.impl;

import com.ecommerce.site.admin.dto.ResponseListDto;
import com.ecommerce.site.admin.dto.optionValue.OptionValueAdminDto;
import com.ecommerce.site.admin.exception.NotFoundException;
import com.ecommerce.site.admin.form.optionValue.CreateOptionValueForm;
import com.ecommerce.site.admin.form.optionValue.UpdateOptionValueForm;
import com.ecommerce.site.admin.mapper.OptionValueMapper;
import com.ecommerce.site.admin.repository.OptionRepository;
import com.ecommerce.site.admin.repository.OptionValueRepository;
import com.ecommerce.site.admin.service.OptionValueService;
import com.ecommerce.site.admin.storage.criteria.OptionValueCriteria;
import com.ecommerce.site.admin.storage.entity.Option;
import com.ecommerce.site.admin.storage.entity.OptionValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OptionValueServiceImpl implements OptionValueService {

    @Autowired
    OptionValueRepository optionValueRepository;

    @Autowired
    OptionRepository optionRepository;

    @Autowired
    OptionValueMapper optionValueMapper;

    @Override
    public OptionValueAdminDto getOptionValueById(Long id) {
        OptionValue optionValue = optionValueRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Not found option value"));
        return optionValueMapper.fromEntityToOptionValueAdminDto(optionValue);
    }

    @Override
    public ResponseListDto<List<OptionValueAdminDto>> getOptionValueList(OptionValueCriteria optionValueCriteria, Pageable pageable) {
        Page<OptionValue> optionValues = optionValueRepository.findAll(optionValueCriteria.getCriteria(), pageable);
        ResponseListDto<List<OptionValueAdminDto>> responseListDto = new ResponseListDto<>();
        responseListDto.setContent(optionValueMapper.fromEntityListToOptionValueAdminDtoList(optionValues.getContent()));
        responseListDto.setPage(pageable.getPageNumber());
        responseListDto.setTotalPages(optionValues.getTotalPages());
        responseListDto.setTotalElements(optionValues.getTotalElements());
        return responseListDto;
    }

    @Override
    public void createOptionValue(CreateOptionValueForm createOptionValueForm) {
        Option option = optionRepository.findById(createOptionValueForm.getOptionId()).orElse(null);
        if(option == null){
            throw new NotFoundException("Not found option");
        }
        OptionValue optionValue = optionValueMapper.fromCreateOptionValueFormToEntity(createOptionValueForm);
        optionValue.setOption(option);
        optionValueRepository.save(optionValue);
    }

    @Override
    public void updateOptionValue(UpdateOptionValueForm updateOptionValueForm) {
        OptionValue optionValue = optionValueRepository.findById(updateOptionValueForm.getId()).orElse(null);
        if(optionValue == null){
            throw new NotFoundException("Not found option value");
        }
        optionValueMapper.fromUpdateOptionValueFormToEntity(updateOptionValueForm, optionValue);
        optionValueRepository.save(optionValue);
    }
}

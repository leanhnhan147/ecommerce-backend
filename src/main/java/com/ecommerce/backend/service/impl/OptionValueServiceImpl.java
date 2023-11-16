package com.ecommerce.backend.service.impl;

import com.ecommerce.backend.dto.ResponseListDto;
import com.ecommerce.backend.dto.optionValue.OptionValueAdminDto;
import com.ecommerce.backend.dto.optionValue.OptionValueDto;
import com.ecommerce.backend.exception.AlreadyExistsException;
import com.ecommerce.backend.exception.NotFoundException;
import com.ecommerce.backend.form.optionValue.CreateOptionValueForm;
import com.ecommerce.backend.form.optionValue.UpdateOptionValueForm;
import com.ecommerce.backend.mapper.OptionValueMapper;
import com.ecommerce.backend.repository.OptionRepository;
import com.ecommerce.backend.repository.OptionValueRepository;
import com.ecommerce.backend.service.OptionValueService;
import com.ecommerce.backend.storage.criteria.OptionValueCriteria;
import com.ecommerce.backend.storage.entity.Option;
import com.ecommerce.backend.storage.entity.OptionValue;
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
    public List<OptionValueDto> getOptionValueListAutoComplete(OptionValueCriteria optionValueCriteria) {
        List<OptionValue> optionValues = optionValueRepository.findAll(optionValueCriteria.getCriteria());
        return optionValueMapper.fromEntityListToOptionValueDtoAutoCompleteList(optionValues);
    }

    @Override
    public OptionValueDto createOptionValue(CreateOptionValueForm createOptionValueForm) {
        Option option = optionRepository.findById(createOptionValueForm.getOptionId()).orElse(null);
        if(option == null){
            throw new NotFoundException("Not found option");
        }
        OptionValue optionValueByDisplayName = optionValueRepository.findByDisplayName(createOptionValueForm.getDisplayName());
        if(optionValueByDisplayName != null){
            throw new AlreadyExistsException("Option value already exist display name");
        }
        OptionValue optionValueByCode = optionValueRepository.findByCode(createOptionValueForm.getCode());
        if(optionValueByCode != null){
            throw new AlreadyExistsException("Option value already exist code");
        }
        OptionValue optionValue = optionValueMapper.fromCreateOptionValueFormToEntity(createOptionValueForm);
        optionValue.setOption(option);
        optionValueRepository.save(optionValue);
        OptionValueDto optionValueDto = new OptionValueDto();
        optionValueDto.setId(optionValue.getId());
        return optionValueDto;
    }

    @Override
    public void updateOptionValue(UpdateOptionValueForm updateOptionValueForm) {
        OptionValue optionValue = optionValueRepository.findById(updateOptionValueForm.getId()).orElse(null);
        if(optionValue == null){
            throw new NotFoundException("Not found option value");
        }
        if(!optionValue.getDisplayName().equals(updateOptionValueForm.getDisplayName())){
            OptionValue optionValueByDisplayName = optionValueRepository.findByDisplayName(updateOptionValueForm.getDisplayName());
            if(optionValueByDisplayName != null){
                throw new AlreadyExistsException("Option value already exist display name");
            }
        }
        if(!optionValue.getCode().equals(updateOptionValueForm.getCode())){
            OptionValue optionValueByCode = optionValueRepository.findByCode(updateOptionValueForm.getCode());
            if(optionValueByCode != null){
                throw new AlreadyExistsException("Option value already exist code");
            }
        }
        optionValueMapper.fromUpdateOptionValueFormToEntity(updateOptionValueForm, optionValue);
        optionValueRepository.save(optionValue);
    }
}

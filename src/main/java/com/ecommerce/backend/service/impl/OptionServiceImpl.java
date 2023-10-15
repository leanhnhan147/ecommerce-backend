package com.ecommerce.backend.service.impl;

import com.ecommerce.backend.dto.ResponseListDto;
import com.ecommerce.backend.dto.option.OptionAdminDto;
import com.ecommerce.backend.exception.NotFoundException;
import com.ecommerce.backend.form.option.CreateOptionForm;
import com.ecommerce.backend.form.option.UpdateOptionForm;
import com.ecommerce.backend.mapper.OptionMapper;
import com.ecommerce.backend.repository.CategoryRepository;
import com.ecommerce.backend.repository.OptionRepository;
import com.ecommerce.backend.service.OptionService;
import com.ecommerce.backend.storage.criteria.OptionCriteria;
import com.ecommerce.backend.storage.entity.Category;
import com.ecommerce.backend.storage.entity.Option;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OptionServiceImpl implements OptionService {

    @Autowired
    OptionRepository optionRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    OptionMapper optionMapper;

    @Override
    public OptionAdminDto getOptionById(Long id) {
        Option option = optionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Not found option"));
        return optionMapper.fromEntityToOptionAdminDto(option);
    }

    @Override
    public ResponseListDto<List<OptionAdminDto>> getOptionList(OptionCriteria optionCriteria, Pageable pageable) {
        Page<Option> options = optionRepository.findAll(optionCriteria.getCriteria(), pageable);
        ResponseListDto<List<OptionAdminDto>> responseListDto = new ResponseListDto<>();
        responseListDto.setContent(optionMapper.fromEntityListToOptionAdminDtoList(options.getContent()));
        responseListDto.setPage(pageable.getPageNumber());
        responseListDto.setTotalPages(options.getTotalPages());
        responseListDto.setTotalElements(options.getTotalElements());
        return responseListDto;
    }

    @Override
    public void createOption(CreateOptionForm createOptionForm) {
        Category category = categoryRepository.findById(createOptionForm.getCategoryId()).orElse(null);
        if(category == null) {
            throw new NotFoundException("Not found category");
        }
        Option option = optionMapper.fromCreateOptionFormToEntity(createOptionForm);
        option.setCategory(category);
        optionRepository.save(option);
    }

    @Override
    public void updateOption(UpdateOptionForm updateOptionForm) {
        Option option = optionRepository.findById(updateOptionForm.getId()).orElse(null);
        if(option == null){
            throw new NotFoundException("Not found option");
        }
        optionMapper.fromUpdateOptionFormToEntity(updateOptionForm, option);
        optionRepository.save(option);
    }
}

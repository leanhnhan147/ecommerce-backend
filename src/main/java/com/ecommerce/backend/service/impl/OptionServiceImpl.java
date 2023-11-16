package com.ecommerce.backend.service.impl;

import com.ecommerce.backend.dto.ResponseListDto;
import com.ecommerce.backend.dto.option.OptionAdminDto;
import com.ecommerce.backend.dto.option.OptionDto;
import com.ecommerce.backend.exception.AlreadyExistsException;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
    public List<OptionDto> getOptionListAutoComplete(OptionCriteria optionCriteria) {
        List<Option> options = optionRepository.findAll(optionCriteria.getCriteria());
        return optionMapper.fromEntityListToOptionDtoAutoCompleteList(options);
    }

    @Transactional
    @Override
    public OptionDto createOption(CreateOptionForm createOptionForm) {
        Option optionByDisplayName = optionRepository.findByDisplayName(createOptionForm.getDisplayName());
        if(optionByDisplayName != null){
            throw new AlreadyExistsException("Option already exist display name");
        }
        Option optionByCode = optionRepository.findByCode(createOptionForm.getCode());
        if(optionByCode != null){
            throw new AlreadyExistsException("Option already exist code");
        }
        Option option = optionMapper.fromCreateOptionFormToEntity(createOptionForm);
        option.setCategories(createCategoryOption(option, createOptionForm.getCategoryIds()));
        optionRepository.save(option);

        OptionDto optionDto = new OptionDto();
        optionDto.setId(option.getId());
        return optionDto;
    }

    @Transactional
    @Override
    public void updateOption(UpdateOptionForm updateOptionForm) {
        Option option = optionRepository.findById(updateOptionForm.getId())
                .orElseThrow(() -> new NotFoundException("Not found option"));

        if(!option.getDisplayName().equals(updateOptionForm.getDisplayName())){
            Option optionByDisplayName = optionRepository.findByDisplayName(updateOptionForm.getDisplayName());
            if(optionByDisplayName != null){
                throw new AlreadyExistsException("Option already exist display name");
            }
        }
        if(!option.getCode().equals(updateOptionForm.getCode())){
            Option optionByCode = optionRepository.findByCode(updateOptionForm.getCode());
            if(optionByCode != null){
                throw new AlreadyExistsException("Option already exist code");
            }
        }
        optionMapper.fromUpdateOptionFormToEntity(updateOptionForm, option);
        option.setCategories(createCategoryOption(option, updateOptionForm.getCategoryIds()));
        optionRepository.save(option);
    }

    private List<Category> createCategoryOption(Option option, Long[] categoryIds){
        List<Category> categories = new ArrayList<>();
        for(int i = 0; i < categoryIds.length; i++){
            Category category = categoryRepository.findById(categoryIds[i])
                    .orElseThrow(() -> new NotFoundException("Not found category"));
            categories.add(category);
        }
        return categories;
    }
}

package com.ecommerce.backend.service.impl;

import com.ecommerce.backend.constant.Constant;
import com.ecommerce.backend.dto.ResponseListDto;
import com.ecommerce.backend.dto.nation.NationAdminDto;
import com.ecommerce.backend.dto.nation.NationDto;
import com.ecommerce.backend.exception.AlreadyExistsException;
import com.ecommerce.backend.exception.NotFoundException;
import com.ecommerce.backend.exception.RequestException;
import com.ecommerce.backend.form.nation.CreateNationForm;
import com.ecommerce.backend.form.nation.UpdateNationForm;
import com.ecommerce.backend.mapper.NationMapper;
import com.ecommerce.backend.repository.NationRepository;
import com.ecommerce.backend.service.NationService;
import com.ecommerce.backend.storage.criteria.NationCriteria;
import com.ecommerce.backend.storage.entity.Nation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
public class NationServiceImpl implements NationService {

    @Autowired
    NationRepository nationRepository;

    @Autowired
    NationMapper nationMapper;

    @Override
    public NationAdminDto getNationById(Long id) {
        Nation nation = nationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Not found nation"));
        return nationMapper.fromEntityToNationAdminDto(nation);
    }

    @Override
    public ResponseListDto<List<NationAdminDto>> getNationList(NationCriteria nationCriteria, Pageable pageable) {
        Page<Nation> nations = nationRepository.findAll(nationCriteria.getCriteria(), pageable);
        ResponseListDto<List<NationAdminDto>> responseListDto = new ResponseListDto<>();
        responseListDto.setContent(nationMapper.fromEntityListToNationAdminDtoList(nations.getContent()));
        responseListDto.setPage(pageable.getPageNumber());
        responseListDto.setTotalPages(nations.getTotalPages());
        responseListDto.setTotalElements(nations.getTotalElements());
        return responseListDto;
    }

    @Override
    public List<NationDto> getNationListAutoComplete(NationCriteria nationCriteria) {
        List<Nation> nations = nationRepository.findAll(nationCriteria.getCriteria());
        return nationMapper.fromEntityListToNationDtoAutoCompleteList(nations);

    }

    @Override
    public void createNation(CreateNationForm createNationForm) {
        Nation existNation = nationRepository.findByPostCode(createNationForm.getPostCode()).orElse(null);
        if(existNation != null){
            throw new AlreadyExistsException("Post code nation exist");
        }
        Nation nation = nationMapper.fromCreateNationFormToEntity(createNationForm);
        if(createNationForm.getParentId() != null) {
            nation.setParent(parentNation(createNationForm.getKind(), createNationForm.getParentId()));
        }
        nationRepository.save(nation);
    }

    @Override
    public void updateNation(UpdateNationForm updateNationForm) {
        Nation nation = nationRepository.findById(updateNationForm.getId())
                .orElseThrow(() -> new NotFoundException("Not found nation"));
        Nation existNation = nationRepository.findByPostCode(updateNationForm.getPostCode()).orElse(null);
        if(existNation != null && !Objects.equals(nation.getId(), existNation.getId())){
            throw new AlreadyExistsException("Post code nation exist");
        }
        nationMapper.fromUpdateNationFormToEntity(updateNationForm, nation);
        if(updateNationForm.getParentId() != null) {
            nation.setParent(parentNation(nation.getKind(), updateNationForm.getParentId()));
        }
        nationRepository.save(nation);
    }

    private Nation parentNation(Integer nationKind, Long parentId){
        if(Objects.equals(nationKind, Constant.NATION_KIND_PROVINCE)){
            throw new RequestException("Parent not allow with Province");
        }
        Nation parentNation = nationRepository.findById(parentId).orElse(null);
        if(parentNation == null) {
            throw new RequestException("Not found parent nation");
        }
        if (nationKind.intValue() - parentNation.getKind().intValue() != 1){
            throw new RequestException("Parent nation not valid");
        }
        return parentNation;
    }

    @Override
    public void deleteNation(Long id) {
        Nation nation = nationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Not found nation"));
        List<Long> children1 = nationRepository.getAllNationIdByParentId(Collections.singletonList(id));
        List<Long> children2 = nationRepository.getAllNationIdByParentId(children1);
        List<Long> nationIdsToDelete = new ArrayList<>();
        nationIdsToDelete.addAll(children1);
        nationIdsToDelete.addAll(children2);
        nationIdsToDelete.add(id);
        nationRepository.deleteAllByParentIdInList(children1);
        nationRepository.deleteAllByParentIdInList(Collections.singletonList(id));
        nationRepository.deleteById(id);
    }
}

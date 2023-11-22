package com.ecommerce.backend.service;

import com.ecommerce.backend.dto.ResponseListDto;
import com.ecommerce.backend.dto.nation.NationAdminDto;
import com.ecommerce.backend.dto.nation.NationDto;
import com.ecommerce.backend.form.nation.CreateNationForm;
import com.ecommerce.backend.form.nation.UpdateNationForm;
import com.ecommerce.backend.storage.criteria.NationCriteria;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface NationService {

    NationAdminDto getNationById(Long id);

    ResponseListDto<List<NationAdminDto>> getNationList(NationCriteria nationCriteria, Pageable pageable);

    List<NationDto> getNationListAutoComplete(NationCriteria nationCriteria);

    void createNation(CreateNationForm createNationForm);

    void updateNation(UpdateNationForm updateNationForm);

    void deleteNation(Long id);
}

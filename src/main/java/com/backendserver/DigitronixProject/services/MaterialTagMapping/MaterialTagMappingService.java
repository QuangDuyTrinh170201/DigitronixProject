package com.backendserver.DigitronixProject.services.MaterialTagMapping;

import com.backendserver.DigitronixProject.exceptions.DataNotFoundException;
import com.backendserver.DigitronixProject.models.MaterialTagMapping;
import com.backendserver.DigitronixProject.models.ProductTagMapping;
import com.backendserver.DigitronixProject.repositories.MaterialTagMappingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MaterialTagMappingService implements IMaterialTagMapping{
    private final MaterialTagMappingRepository materialTagMappingRepository;
    @Override
    public void removeTagOnMaterial(Long materialId, Long tagId) throws Exception {
        MaterialTagMapping mapping = materialTagMappingRepository.findByMaterial_IdAndTag_Id(materialId, tagId);
        if(mapping ==  null){
            throw new DataNotFoundException("Cannot find this mapping");
        }
        Long mappingId = mapping.getId();
        materialTagMappingRepository.deleteById(mappingId);
    }
}

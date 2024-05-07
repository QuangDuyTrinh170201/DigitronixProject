package com.backendserver.DigitronixProject.services.MaterialTagMapping;

public interface IMaterialTagMapping {
    void removeTagOnMaterial(Long materialId, Long tagId) throws Exception;
}

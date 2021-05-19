/*
 * Source code generated by Celerio, a Jaxio product.
 * Documentation: http://www.jaxio.com/documentation/celerio/
 * Follow us on twitter: @jaxiosoft
 * Need commercial support ? Contact us: info@jaxio.com
 * Template pack-custom:src/main/java/mapper/Mapper.e.vm.java
 */
package ru.ssau.lexus.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import ru.ssau.lexus.domain.dto.TrainingStatusDto;
import ru.ssau.lexus.domain.entity.TrainingStatus;

import java.util.Collection;

@Mapper(componentModel = "spring")
public interface TrainingStatusMapper {

    @Mappings({})
    TrainingStatusDto entityToDto(TrainingStatus entity);

    @Mappings({})
    TrainingStatus dtoToEntity(TrainingStatusDto dto);

    Collection<TrainingStatusDto> entityToDto(Collection<TrainingStatusDto> entity);

    Collection<TrainingStatus> dtoToEntity(Collection<TrainingStatusDto> dto);
}
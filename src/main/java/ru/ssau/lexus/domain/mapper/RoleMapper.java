/*
 * Source code generated by Celerio, a Jaxio product.
 * Documentation: http://www.jaxio.com/documentation/celerio/
 * Follow us on twitter: @jaxiosoft
 * Need commercial support ? Contact us: info@jaxio.com
 * Template pack-custom:src/main/java/mapper/Mapper.e.vm.java
 */
package ru.ssau.lexus.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import ru.ssau.lexus.domain.dto.RoleDto;
import ru.ssau.lexus.domain.entity.Role;

import java.util.Collection;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    @Mappings({
            @Mapping(source ="idUser.id", target = "idUser")
    })
    RoleDto entityToDto(Role entity);

    @Mappings({
            @Mapping(target ="idUser.id", source = "idUser")
    })
    Role dtoToEntity(RoleDto dto);

    Collection<RoleDto> entityToDto(Collection<RoleDto> entity);

    Collection<Role> dtoToEntity(Collection<RoleDto> dto);
}
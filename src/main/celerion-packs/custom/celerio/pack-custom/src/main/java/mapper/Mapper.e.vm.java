$output.java("${entity.model.packageName}.mapper","${entity.model.type}Mapper")##
        $output.require("${entity.model.packageName}.entity.${entity.model.type}")
        $output.require("${entity.model.packageName}.dto.${entity.model.type}Dto")

import org.mapstruct.Mapper;
import java.util.Collection;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface ${output.currentClass}${entity.spaceAndExtendsStatement}  {

@Mappings({
        #foreach ($manyToOne in $entity.manyToOne.list)
@Mapping(source = "${manyToOne.to.var}.id", target = "${manyToOne.to.var}"),
        $output.require("${entity.model.packageName}.entity.${manyToOne.to.type}")##
        #end
        })
        ${entity.model.type}Dto entityToDto(${entity.model.type} entity);

@Mappings({
        #foreach ($manyToOne in $entity.manyToOne.list)
@Mapping(target = "${manyToOne.to.var}.id", source = "${manyToOne.to.var}"),
        $output.require("${entity.model.packageName}.entity.${manyToOne.to.type}")##
        #end
        })
        ${entity.model.type} dtoToEntity(${entity.model.type}Dto dto);

        Collection<${entity.model.type}Dto> entityToDto(Collection<${entity.model.type}Dto> entity);

        Collection<${entity.model.type}> dtoToEntity(Collection<${entity.model.type}Dto> dto);
        }
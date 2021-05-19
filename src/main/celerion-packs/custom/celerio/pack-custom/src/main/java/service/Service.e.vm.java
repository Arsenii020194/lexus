$output.java($entity.service)


$output.require("org.springframework.stereotype.Service")##
$output.require("org.springframework.transaction.annotation.Transactional")##
$output.require("${entity.model.packageName}.dto.${entity.model.type}Dto")
$output.require("${entity.model.packageName}.entity.${entity.model.type}")
$output.require("${entity.model.packageName}.mapper.${entity.model.type}Mapper")
$output.require("${entity.repository.packageName}.crud.${entity.repository.type}")##
$output.require("java.util.Optional")##
import lombok.AllArgsConstructor;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class ${output.currentClass} {

protected final ${entity.model.type}Repository repository;

protected final ${entity.model.type}Mapper mapper;


/**
 * Save a ${entity.model.type}.
 *
 * @param dto the entity to save.
 * @return the persisted entity.
 */
public ${entity.model.type}Dto save(${entity.model.type}Dto dto){
        ${entity.model.type} entity =  mapper.dtoToEntity(dto);
        entity = repository.save(entity);
        return mapper.entityToDto(entity);
        }


/**
 * Get all the ${entity.model.type}s.
 *
 * @return the list of entities.
 */
@Transactional(readOnly = true)
public Collection<${entity.model.type}Dto> findAll() {
        return repository.findAll().stream()
        .map(mapper::entityToDto).collect(Collectors.toList());
        }


/**
 * Get one ${entity.model.type} by id.
 *
 * @param id the id of the entity.
 * @return the entity.
 */
@Transactional(readOnly = true)
$output.require($entity.primaryKey.fullType)##
public Optional<${entity.model.type}Dto> findOne($entity.primaryKey.type id) {
        return repository.findById(id)
        .map(mapper::entityToDto);
        }


/**
 * Delete the anodeHist by id.
 *
 * @param id the id of the entity.
 */
public void delete($entity.primaryKey.type id) {
        repository.deleteById(id);
}
}
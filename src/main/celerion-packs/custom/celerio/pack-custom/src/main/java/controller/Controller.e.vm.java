$output.java($entity.controller)

$output.require("org.springframework.web.bind.annotation.*")##
$output.require("org.springframework.http.ResponseEntity")##
$output.require("org.springframework.beans.factory.annotation.Value")##
$output.require("org.springframework.http.HttpStatus")##
$output.require("${entity.model.packageName}.dto.${entity.model.type}Dto")##
$output.require($entity.service)##

import javax.validation.Valid;
import java.net.URISyntaxException;

import java.util.Optional;
import lombok.AllArgsConstructor;
import java.util.Collection;

@RestController
@AllArgsConstructor
public class ${entity.model.type}Controller {
#set($url = ${entity.tableName.replace("_","-").toLowerCase()})

        protected final $entity.service.type $entity.service.var;

/**
 * {@code POST  /${url}} : Create a new ${entity.model.var}.
 *
 * @param ${entity.model.var}Dto the ${entity.model.var}Dto to create.
 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ${entity.model.var}Dto, or with status {@code 400 (Bad Request)} if the ${entity.model.var} has already an ID.
 * @throws URISyntaxException if the Location URI syntax is incorrect.
 */

@PostMapping("/${url}")
public ResponseEntity<${entity.model.type}Dto> create${entity.model.type}(@Valid @RequestBody ${entity.model.type}Dto ${entity.model.var}Dto) throws Exception {
        if (${entity.model.var}Dto.getId() != null) {
                throw new Exception("A new ${entity.model.var} cannot already have an ID");
        }
        ${entity.model.type}Dto result = ${entity.model.var}Service.save(${entity.model.var}Dto);
        return ResponseEntity.ok()
        .body(result);
        }
/**
 * {@code PUT  /${url}} : Updates an existing anodeHist.
 *
 * @param ${entity.model.var}Dto the ${entity.model.var}Dto to update.
 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ${entity.model.var}Dto,
 * or with status {@code 400 (Bad Request)} if the ${entity.model.var}Dto is not valid,
 * or with status {@code 500 (Internal Server Error)} if the ${entity.model.var}Dto couldn't be updated.
 * @throws URISyntaxException if the Location URI syntax is incorrect.
 */
@PutMapping("/${url}")
public ResponseEntity<${entity.model.type}Dto> update${entity.model.type}(@Valid @RequestBody ${entity.model.type}Dto ${entity.model.var}Dto) throws Exception {
        if (${entity.model.var}Dto.getId() == null) {
                throw new Exception("Invalid id");
        }
        ${entity.model.type}Dto result = ${entity.service.var}.save(${entity.model.var}Dto);
                return ResponseEntity.ok()
                        .body(result);
        }

/**
 * {@code GET  /${url}} : get all the ${entity.model.vars}.
 *
 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ${entity.model.vars} in body.
 */
@GetMapping("/${url}")
public ResponseEntity<Collection<${entity.model.type}Dto>> getAll${entity.model.varsUp}() {
        return ResponseEntity.ok().body(${entity.service.var}.findAll());
        }


/**
 * {@code GET  /${url}/:id} : get the "id" ${entity.model.var}.
 *
 * @param id the id of the ${entity.model.var}DTO to retrieve.
 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ${entity.model.var}Dto, or with status {@code 404 (Not Found)}.
 */
@GetMapping("/${url}/{id}")
public ResponseEntity<${entity.model.type}Dto> get${entity.model.type}(@PathVariable ${entity.primaryKey.type} id) {
        #if ($entity.isRoot() && $entity.primaryKey.isComposite())
                $output.require($entity.primaryKey)##
                $entity.primaryKey.type pkId = new ${entity.primaryKey.type}();
                pkId.setId(id);
                Optional<${entity.model.type}Dto> ${entity.model.var}Dto = ${entity.model.var}Service.findOne(pkId);
        #else
                Optional<${entity.model.type}Dto> ${entity.model.var}Dto = ${entity.model.var}Service.findOne(id);
        #end

                return ${entity.model.var}Dto
                        .map(dto -> ResponseEntity.ok().body(dto))
                        .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));

        }
/**
 * {@code DELETE  /${url}/:id} : delete the "id" ${entity.model.var}.
 *
 * @param id the id of the ${entity.model.var}Dto to delete.
 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
 */
@DeleteMapping("/${url}/{id}")
public ResponseEntity<Void> delete${entity.model.type}(@PathVariable ${entity.primaryKey.type} id) {
        #if ($entity.isRoot() && $entity.primaryKey.isComposite())
                $output.require($entity.primaryKey)##
                $entity.primaryKey.type pkId = new ${entity.primaryKey.type}();
                pkId.setId(id);
                ${entity.model.var}Service.delete(pkId);
        #else
                ${entity.model.var}Service.delete(id);
        #end

                return ResponseEntity.ok().build();
        }
}
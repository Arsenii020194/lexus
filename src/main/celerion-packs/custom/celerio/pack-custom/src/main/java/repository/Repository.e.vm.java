$output.java("${entity.repository.packageName}.crud","${entity.repository.type}")##


$output.require("org.springframework.stereotype.Repository")##
$output.require("org.springframework.data.jpa.repository.*")##
$output.require($entity.primaryKey.fullType)##
$output.require("${entity.model.packageName}.entity.${entity.model.type}")

@Repository
public interface ${output.currentClass}${entity.spaceAndExtendsStatement} extends JpaRepository<${entity.model.type}, $entity.primaryKey.type>, JpaSpecificationExecutor<${entity.model.type}> {

        }


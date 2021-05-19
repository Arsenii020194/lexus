$output.java("${entity.model.packageName}.dto","${entity.model.type}Dto")##

$output.require("java.io.Serializable")##
$output.require("lombok.Data")##
@Data
public class ${output.currentClass}${entity.spaceAndExtendsStatement} implements Serializable {

        #if ($entity.isRoot() && $entity.primaryKey.isComposite())
        #foreach ($pkAttribute in   $entity.primaryKey.attributes)
        $output.require($pkAttribute)##
private $pkAttribute.type $pkAttribute.var;
        #end
        #end

        ## --------------- Raw attributes (exception the one involved in XtoOneRelation)
        #foreach ($attribute in $entity.nonCpkAttributes.list)
        #if ($velocityCount == 1)
        #end

        $output.require($attribute)##
private $attribute.type $attribute.var;
        #end



}


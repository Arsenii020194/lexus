## Copyright 2015 JAXIO http://www.jaxio.com
##
## Licensed under the Apache License, Version 2.0 (the "License");
## you may not use this file except in compliance with the License.
## You may obtain a copy of the License at
##
##    http://www.apache.org/licenses/LICENSE-2.0
##
## Unless required by applicable law or agreed to in writing, software
## distributed under the License is distributed on an "AS IS" BASIS,
## WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
## See the License for the specific language governing permissions and
## limitations under the License.
##
$output.java("${entity.model.packageName}.entity","${entity.model.type}")##

$output.require("org.slf4j.Logger")##
$output.require("org.slf4j.LoggerFactory")##
$output.require("java.util.Objects")##
#if($entity.hasComment())
/**
 * $entity.comment
 */
#end
#if ($output.isAbstract())
## ==============================================
## /**
$output.createMetaModelTakeOver($entity.model,
"$output.headerComment##
package $entity.model.packageName;

import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(${entity.model.type}.class)
public class ${entity.model.type}_ extends ${entity.model.type}Base_ {
}
")
## */
## ==============================================
$output.dynamicAnnotation("javax.persistence.MappedSuperclass")
#end
#foreach($annotation in $entity.jpa.annotations)$annotation#end
#foreach($annotation in $entity.search.annotations)$annotation#end
#foreach($annotation in $entity.custom.annotations)$annotation#end

#if($entity.isRoot())
$output.require("java.io.Serializable")##
public#if ($output.isAbstract()) abstract#{end} class ${output.currentClass}${entity.spaceAndExtendsStatement} implements Serializable {
#else
$output.require($entity.parent.model)##
public#if ($output.isAbstract()) abstract#{end} class $output.currentClass extends $entity.parent.model.type {
#end
    private static final long serialVersionUID = 1L;
    private static final Logger log = LoggerFactory.getLogger(${output.currentClass}.class);
#if ($entity.isRoot() && $entity.primaryKey.isComposite())

    // Composite primary key
    private $entity.primaryKey.type $entity.primaryKey.var = new ${entity.primaryKey.type}();
#end
## --------------- Raw attributes (exception the one involved in XtoOneRelation)
#foreach ($attribute in $entity.nonCpkAttributes.list)
#if ($velocityCount == 1)

    // Raw attributes
#end
#if(!$attribute.isInFk() || $attribute.isSimplePk())
$output.require($attribute)##
    private $attribute.type $attribute.var;
#end
#end
## --------------- Many to One
#foreach ($manyToOne in $entity.manyToOne.list)
#if ($velocityCount == 1)

    // Many to one
#end
$output.require("${entity.model.packageName}.entity.${manyToOne.to.type}")##
    private $manyToOne.to.type $manyToOne.to.var;
#end
## --------------- One to One
#foreach ($oneToOne in $entity.oneToOne.list)
#if ($velocityCount == 1)

    // One to one
#end
$output.require($oneToOne.to)##
    private $oneToOne.to.type $oneToOne.to.var;
#end
## --------------- One to Virtual One
#foreach ($oneToVirtualOne in $entity.oneToVirtualOne.list)
#if ($velocityCount == 1)

    // One to virtual one
#end
$output.require($entity.collectionType.fullType)##
$output.require($entity.collectionType.implementationFullType)##
$output.require($oneToVirtualOne.to)##
    private ${entity.collectionType.type}<$oneToVirtualOne.to.type> $oneToVirtualOne.to.vars = new ${entity.collectionType.implementationType}<$oneToVirtualOne.to.type>();
#end
## --------------- One to many
#foreach ($oneToMany in $entity.oneToMany.list)
#if ($velocityCount == 1)

    // One to many
#end
$output.require($entity.collectionType.fullType)##
$output.require($entity.collectionType.implementationFullType)##
$output.require($oneToMany.to)##
    private ${entity.collectionType.type}<$oneToMany.to.type> $oneToMany.to.vars = new ${entity.collectionType.implementationType}<$oneToMany.to.type>();
#end
## --------------- Many to many
#foreach ($manyToMany in $entity.manyToMany.list)
#if ($velocityCount == 1)

    // Many to many
#end
$output.require($entity.collectionType.fullType)##
$output.require($entity.collectionType.implementationFullType)##
$output.require($manyToMany.to)##
    private ${entity.collectionType.type}<$manyToMany.to.type> $manyToMany.to.vars = new ${entity.collectionType.implementationType}<$manyToMany.to.type>();
#end
#if ($entity.isRoot() && $entity.primaryKey.isComposite())

    // -----------------------
    // Composite Primary Key
    // -----------------------

    /**
     * Returns the composite primary key.
     */
    $output.dynamicAnnotation("javax.persistence.EmbeddedId")
#if($entity.entityConfig.hasTrueIndexed())
    $output.dynamicAnnotation("org.hibernate.search.annotations.DocumentId")
    @FieldBridge(impl = ${entity.primaryKey.type}Bridge.class)
$output.require("org.hibernate.search.annotations.FieldBridge")##
#end
    public $entity.primaryKey.type ${entity.primaryKey.getter}() {
        return $entity.primaryKey.var;
    }

    /**
     * Set the composite primary key.
     * @param $entity.root.primaryKey.var the composite primary key.
     */
    public void ${entity.primaryKey.setter}($entity.primaryKey.type $entity.primaryKey.var) {
        this.$entity.primaryKey.var = $entity.primaryKey.var;
    }

    public ${output.currentRootClass} ${entity.primaryKey.with}($entity.primaryKey.type $entity.primaryKey.var) {
        ${entity.primaryKey.setter}($entity.primaryKey.var);
        return ${output.currentRootCast}this;
    }

    /**
     * Tells whether or not this instance has a non empty composite primary key set.
     * @return true if a non empty primary key is set, false otherwise
     */
    $output.dynamicAnnotation("javax.persistence.Transient")
    $output.dynamicAnnotation("javax.xml.bind.annotation.XmlTransient")
    public boolean ${entity.primaryKey.has}() {
        return ${entity.primaryKey.getter}() != null && ${entity.primaryKey.getter}().areFieldsSet();
    }
#end
#if ($entity.isAccount())
$output.require("$entity.collectionType.fullType")##
$output.require("$entity.collectionType.implementationFullType")##

    // -------------------------------
    // Role names support
    // -------------------------------

#if ($entity.accountAttributes.isRoleRelationSet())
#set ($roleRelation = $entity.accountAttributes.roleRelation)##
    /**
     * Returns the granted authorities for this user. You may override
     * this method to provide your own custom authorities.
     */
    $output.dynamicAnnotation("javax.persistence.Transient")
    $output.dynamicAnnotation("javax.xml.bind.annotation.XmlTransient")
    public ${entity.collectionType.type}<String> getRoleNames() {
        ${entity.collectionType.type}<String> roleNames = new ${entity.collectionType.implementationType}<String>();

        for ($roleRelation.to.type $roleRelation.to.var : ${roleRelation.to.getters}()) {
            roleNames.add(${roleRelation.to.var}.${roleRelation.toEntity.roleAttributes.roleName.getter}());
        }

        return roleNames;
    }
#else
    /**
     * Default implementation returns hard coded granted authorities for this account (i.e. "ROLE_USER" and "ROLE_ADMIN").
     * TODO: You should override this method to provide your own custom authorities using your own logic.
     * Or you can follow Celerio Account Table convention. Please refer to Celerio Documentation.
     */
    $output.dynamicAnnotation("javax.persistence.Transient")
    $output.dynamicAnnotation("javax.xml.bind.annotation.XmlTransient")
    public ${entity.collectionType.type}<String> getRoleNames() {
        ${entity.collectionType.type}<String> roleNames = new ${entity.collectionType.implementationType}<String>();
        if ("user".equalsIgnoreCase(${entity.accountAttributes.username.getter}())) {
            roleNames.add("ROLE_USER");
        } else if ("admin".equalsIgnoreCase(${entity.accountAttributes.username.getter}())) {
            roleNames.add("ROLE_USER");
            roleNames.add("ROLE_ADMIN");
        }

        log.warn("Returning hard coded role names. TODO: get the real role names");
        return roleNames;
    }
#end
#end
#foreach ($attribute in $entity.nonCpkAttributes.list)
#if(!$attribute.isInFk() || $attribute.isSimplePk())
    // -- [${attribute.var}] ------------------------

#if($attribute.hasComment())$attribute.javadoc#end
#if ($attribute.isSimplePk())

#end
#foreach ($annotation in $attribute.custom.annotations)
    $annotation
#end
#foreach ($annotation in $attribute.validation.annotations)
    $annotation
#end
#foreach ($annotation in $attribute.jpa.annotations)
    $annotation
#end
#foreach ($annotation in $attribute.formatter.annotations)
    $annotation
#end
#foreach ($annotation in $attribute.search.annotations)
    $annotation
#end
    public $attribute.type ${attribute.getter}() {
        return $attribute.var;
    }
#if ($attribute.isSetterAccessibilityPublic())

#if ($attribute.isSimplePk())

#end
    public void ${attribute.setter}($attribute.type $attribute.var) {
        this.$attribute.var = $attribute.var;
    }

    public ${output.currentRootClass} ${attribute.with}($attribute.type $attribute.var) {
        ${attribute.setter}($attribute.var);
        return ${output.currentRootCast}this;
    }
#else

    private void ${attribute.setter}($attribute.type $attribute.var) {
        this.$attribute.var = $attribute.var;
    }
#end
#if($attribute.isSimplePk())

##    @Override
##    $output.dynamicAnnotation("javax.persistence.Transient")
##    $output.dynamicAnnotation("javax.xml.bind.annotation.XmlTransient")
##    public boolean ${attribute.has}() {
##        return $attribute.var != null#if ($attribute.isString() && !$attribute.isEnum()) && !${attribute.var}.isEmpty()#end;
##    }
#end
#end
#end
## --------------- Many to one
#foreach ($relation in $entity.manyToOne.list)
#if ($velocityCount == 1)

    // -----------------------------------------------------------------
    // Many to One support
    // -----------------------------------------------------------------
#end

    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    // many-to-one: $relation.toString()
    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

#foreach ($annotation in $relation.validation.annotations)
    $annotation
#end
#foreach ($annotation in $relation.jpa.annotations)
    $annotation
#end
    public $relation.to.type ${relation.to.getter}() {
        return $relation.to.var;
    }

    /**
     * Set the {@link #$relation.to.var} without adding this $relation.from.type instance on the passed {@link #${relation.to.var}}
#if($relation.hasInverse())
     * If you want to preserve referential integrity we recommend to use
     * instead the corresponding adder method provided by {@link $relation.to.type}
#end
     */
    public void ${relation.to.setter}($relation.to.type $relation.to.var) {
        this.$relation.to.var = $relation.to.var;
#if (!$relation.isIntermediate())        
#foreach ($attributePair in $relation.attributePairs)
#if ($attributePair.fromAttribute.isInCpk() && $attributePair.toAttribute.isInCpk())
         ${identifiableProperty.getter}().${attributePair.fromAttribute.setter}($relation.to.var != null ? ${relation.to.var}.${identifiableProperty.getter}().${attributePair.toAttribute.getter}() : null);
#end
#end
#end
    }

    public ${output.currentRootClass} ${relation.to.with}($relation.to.type $relation.to.var) {
        ${relation.to.setter}($relation.to.var);
        return ${output.currentRootCast}this;
    }
#end
##---------------- One to one
#foreach ($relation in $entity.oneToOne.list)
#if ($velocityCount == 1)

    // -----------------------------------------------------------------
    // One to one
    // -----------------------------------------------------------------
#end

    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    // #{if}($relation.isInverse())Inverse#{else}Owner#{end} side of one-to-one relation: $relation.toString()
    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
## TODO : add @NotNull annotation
#foreach ($annotation in $relation.validation.annotations)
    $annotation
#end
#foreach ($annotation in $relation.jpa.annotations)
    $annotation
#end
    public $relation.to.type ${relation.to.getter}() {
        return $relation.to.var;
    }

#if($relation.isInverse())
## Inverse ONE TO ONE SETTER (analog to a one to many)
    public void ${relation.to.setter}($relation.to.type $relation.to.var) {
        this.$relation.to.var = $relation.to.var;

        if (this.$relation.to.var != null) {
            this.${relation.to.var}.${relation.inverse.to.setter}(${output.currentRootCast}this);
        }
    }
#else
## FORWARD ONE TO ONE SETTER
    public void ${relation.to.setter}($relation.to.type $relation.to.var) {
        this.$relation.to.var = $relation.to.var;
#if (!$relation.isIntermediate())        
#foreach ($attributePair in $relation.attributePairs)
#if ($attributePair.fromAttribute.isInCpk() && $attributePair.toAttribute.isInCpk())
        ${identifiableProperty.getter}().${attributePair.fromAttribute.setter}($relation.to.var != null ? ${relation.to.var}.${identifiableProperty.getter}().${attributePair.toAttribute.getter}() : null);
#end
#end
#end
    }
#end

    public ${output.currentRootClass} ${relation.to.with}($relation.to.type $relation.to.var) {
        ${relation.to.setter}($relation.to.var);
        return ${output.currentRootCast}this;
    }

#end
##---------------- One to virtual one
#foreach ($relation in $entity.oneToVirtualOne.list)
#if ($velocityCount == 1)

    // -----------------------------------------------------------------
    // One to virtual one
    // -----------------------------------------------------------------
#end
    /**
     * Helper method to set the unique ${relation.to.var} pointing to this instance.
     * Preserve referential integrity by updating the passed {@link $relation.to.type}
     *
     * @param  $relation.to.var
     */
    public void ${relation.to.setter}($relation.to.type $relation.to.var) {
        // remove current value from distant bean
        int i = 0;
        for ($relation.to.type other : ${relation.to.getters}()) {
            other.${relation.from.setter}(null);
            if (++i > 1) {
                throw new IllegalStateException("virtual one to one contract broken!");
            }
        }

        if (i > 0) {
            // clear our ref to current distant bean
            ${relation.to.getters}().clear();
        }

        // add the passed bean
        if ($relation.to.var != null) {
            if (${relation.to.getters}().add($relation.to.var)) {
                ${relation.to.var}.${relation.from.setter}(${output.currentRootCast}this);
            }
        }
    }

    public ${output.currentRootClass} ${relation.to.with}($relation.to.type $relation.to.var) {
        ${relation.to.setter}($relation.to.var);
        return ${output.currentRootCast}this;
    }

    /**
     * Helper method to directly get the unique {@link #$relation.to.var} pointing to this instance.
     *
     * @return relation.to.var
     */
    $output.dynamicAnnotation("javax.persistence.Transient")
    $output.dynamicAnnotation("javax.xml.bind.annotation.XmlTransient")
    public $relation.to.type ${relation.to.getter}() {
        for ($relation.to.type other : ${relation.to.getters}()) {
            return other;
        }
        return null;
    }

    /**
     * Get the $relation.to.type list which is supposed to contain at most one element (virtual one to one relation).
     * This method is not intended to be used directly. Use instead the helper method {@link ${pound}${relation.to.getter}()}
     *
     * @return the $relation.to.vars list
     */
#foreach ($annotation in $relation.jpa.annotations)
    $annotation
#end
    protected ${entity.collectionType.type}<$relation.to.type> ${relation.to.getters}() {
        return $relation.to.vars;
    }

    /**
     * Set the {@link #$relation.to.vars} list which is supposed to contain at most one element (virtual one to one relation).
     * This method is not intended to be used directly. Use instead the helper method {@link ${pound}${relation.to.setter}($relation.to.type)}
     * which preserve referential integrity at the object level and ease code readability.
     *
     * @param $relation.to.vars list
     */
    protected void ${relation.to.setters}(${entity.collectionType.type}<$relation.to.type> $relation.to.vars) {
        this.$relation.to.vars = $relation.to.vars;
    }
#end
## --------------- One to many
#foreach ($relation in $entity.oneToMany.list)
#if ($velocityCount == 1)

    // -----------------------------------------------------------------
    // One to Many support
    // -----------------------------------------------------------------
#end

    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    // one to many: $relation.fromEntity.model.var ==> $relation.to.vars
    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

#foreach ($annotation in $relation.jpa.annotations)
    $annotation
#end
    public ${entity.collectionType.type}<$relation.to.type> ${relation.to.getters}() {
        return $relation.to.vars;
    }

    /**
     * Set the {@link $relation.to.type} list.
     * It is recommended to use the helper method {@link ${pound}${relation.to.adder}($relation.to.type)} / {@link ${pound}${relation.to.remover}($relation.to.type)}
     * if you want to preserve referential integrity at the object level.
     *
     * @param $relation.to.vars the list to set
     */
    public void ${relation.to.setters}(${entity.collectionType.type}<$relation.to.type> $relation.to.vars) {
        this.$relation.to.vars = $relation.to.vars;
    }
    

    /**
     * Helper method to add the passed {@link $relation.to.type} to the {@link #$relation.to.vars} list
     * and set this $relation.from.var on the passed $relation.to.var to preserve referential
     * integrity at the object level.
     *
     * @param $relation.to.var the to add
     * @return true if the $relation.to.var could be added to the $relation.to.vars list, false otherwise
     */
    public boolean ${relation.to.adder}($relation.to.type $relation.to.var) {
        if (${relation.to.getters}().add($relation.to.var)) {
            ${relation.to.var}.${relation.from.setter}(${output.currentRootCast}this);
            return true;
        }
        return false;
    }

    /**
     * Helper method to remove the passed {@link $relation.to.type} from the {@link #$relation.to.vars} list and unset
     * this $relation.from.var from the passed $relation.to.var to preserve referential integrity at the object level.
     *
     * @param $relation.to.var the instance to remove
     * @return true if the $relation.to.var could be removed from the $relation.to.vars list, false otherwise
     */
    public boolean ${relation.to.remover}($relation.to.type $relation.to.var) {
        if (${relation.to.getters}().remove($relation.to.var)) {
            ${relation.to.var}.${relation.from.setter}(null);
            return true;
        }
        return false;
    }

#end
#foreach ($relation in $entity.manyToMany.list)
#if ($velocityCount == 1)

    // -----------------------------------------------------------------
    // Many to Many
    // -----------------------------------------------------------------
#end

    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
    // many-to-many: $relation.fromEntity.model.var ==> $relation.to.vars
    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 

    /**
     * Returns the {@link #$relation.to.vars} list.
     */
#foreach ($annotation in $relation.jpa.annotations)
    $annotation
#end
    public ${entity.collectionType.type}<$relation.to.type> ${relation.to.getters}() {
        return $relation.to.vars;
    }

    /**
     * Set the {@link #$relation.to.vars} list.
#if ($relation.hasInverse())
     * <p>
     * It is recommended to use the helper method {@link ${pound}${relation.to.adder}($relation.to.type)} / {@link ${pound}${relation.to.remover}($relation.to.type)}
     * if you want to preserve referential integrity at the object level.
#end
     *
     * @param $relation.to.vars the list of $relation.to.type
     */
    public void ${relation.to.setters}(${entity.collectionType.type}<$relation.to.type> $relation.to.vars) {
        this.$relation.to.vars = $relation.to.vars;
    }

#if ($relation.hasInverse())
    /**
     * Helper method to add the passed {@link $relation.to.type} to the {@link #$relation.to.vars} list
     * and add this $relation.from.var to the passed ${relation.to.var}'s {@link #$relation.from.vars} list
     * to preserve referential integrity at the object level.
     */
    public boolean ${relation.to.adder}($relation.to.type $relation.to.var) {
        if (${relation.to.getters}().add(${relation.to.var})) {
            return ${relation.to.var}.${relation.from.getters}().add(${output.currentRootCast}this);
        }
        return false;
    }

    /**
     * Helper method to remove the passed {@link $relation.to.type} from the {@link #$relation.to.vars} list
     * and remove this $relation.from.var from the passed ${relation.to.var}'s {@link #$relation.from.vars} list.
     * to preserve referential integrity at the object level.
     */
    public boolean ${relation.to.remover}($relation.to.type $relation.to.var) {
        if (${relation.to.getters}().remove($relation.to.var)) {
            return ${relation.to.var}.${relation.from.getters}().remove(${output.currentRootCast}this);
        }
        return false;
    }

#else
    /**
     * Helper method to add the passed {@link $relation.to.type} to the {@link #$relation.to.vars} list.
     */
    public boolean ${relation.to.adder}($relation.to.type $relation.to.var) {
        return ${relation.to.getters}().add($relation.to.var);
    }

    /**
     * Helper method to remove the passed {@link $relation.to.type} from the {@link #$relation.to.vars} list.
     */
    public boolean ${relation.to.remover}($relation.to.type $relation.to.var) {
        return ${relation.to.getters}().remove($relation.to.var);
    }
#end

    /**
     * Helper method to determine if the passed {@link $relation.to.type} is present in the {@link #$relation.to.vars} list.
     */
    public boolean ${relation.to.contains}($relation.to.type $relation.to.var) {
        return ${relation.to.getters}() != null && ${relation.to.getters}().contains($relation.to.var);
    }
#end

    /**
     * Apply the default values.
     */
    public ${output.currentRootClass} withDefaults() {
#if ($entity.hasParent())
        super.withDefaults();
#end
#foreach ($attribute in $entity.pertinentDefaultValueAttributes.list)
        ${attribute.setter}($attribute.javaDefaultValue);
#end
        return ${output.currentRootCast}this;
    }

    /**
     * Equals implementation using a business key.
     */
    @Override
    public boolean equals(Object other) {
        return ${output.currentRootCast}this == other || (other instanceof $output.currentClass && hashCode() == other.hashCode());
    }

#if($entity.useBusinessKey())

    private volatile int previousHashCode = 0;

    @Override
    public int hashCode() {
        int hashCode = Objects.hash(
#foreach($bka in $entity.businessKey)
#if ($bka.isJavaUtilDate())
            DateUtil.toSecondPrecisionDate(${bka.getter}())$project.print($velocityHasNext,", //", ");")
#elseif($bka.isSimpleFk())
## can happen if user configured it => risky, but it is his choice
            ${bka.xToOneRelation.to.getter}().${identifiableProperty.getter}()$project.print($velocityHasNext,", //", ");")
#else
            ${bka.getter}()$project.print($velocityHasNext,", //", ");")
#end
#end

        if (previousHashCode != 0 && previousHashCode != hashCode) {
            log.warn("DEVELOPER: hashCode has changed!." //
                    + "If you encounter this message you should take the time to carefully review equals/hashCode for: " //
                    + getClass().getCanonicalName());
        }

        previousHashCode = hashCode;
        return hashCode;
    }

#elseif($entity.isRoot())

$output.require("java.util.Objects");
    @Override
    public int hashCode() {
        return Objects.hash(this);
    }

#end
#foreach ($bundle in $entity.attributeBundles)
#if ($velocityCount == 1)
    // -----------------------
    // Localization shortcuts
    // -----------------------
$output.require($Context, "LocaleHolder")##
#end

    /**
     * Locale aware getter for fields whose column's name starts
     * with $bundle.base and ends with _xx where xx is a language code.
     */
    $output.dynamicAnnotation("javax.persistence.Transient")
    $output.dynamicAnnotation("javax.xml.bind.annotation.XmlTransient")
    public $bundle.type ${bundle.getter}() {
        String language = LocaleHolder.getLocale().getLanguage().toLowerCase();
#foreach($attribute in $bundle.attributes)
        if (language.equals("$attribute.columnNameLanguage")) {
            return ${attribute.getter}();
        }
#end
        throw new IllegalStateException("Could not find the proper getter for current locale's language " + language);
    }

#end

    /**
     * Construct a readable string representation for this ${output.currentClass} instance.
     * @see java.lang.Object${pound}toString()
     */
    @Override
    public String toString() {
        return #if ($entity.hasParent())super.toString() + #{end}"$entity.model.type{" //
#foreach ($attribute in $entity.nonCpkAttributes.list)
#if(!$attribute.isInFk() || $attribute.isSimplePk())
            + "${attribute.var}"+ #if($attribute.isPassword())"XXXX"#else${attribute.getter}()#end //
#end
#end
            +"}";
    }
#if ($configuration.has($COPYABLE))

    /**
     * Return a copy of the current object
     */
    @Override
    $output.dynamicAnnotation("javax.persistence.Transient")
    $output.dynamicAnnotation("javax.xml.bind.annotation.XmlTransient")
    public $entity.model.type copy() {
        ${entity.model.type} ${entity.model.var} = new ${entity.model.type}();
        copyTo(${entity.model.var});
        return ${entity.model.var};
    }

    /**
     * Copy the current properties to the given object
     */
    @Override
    $output.dynamicAnnotation("javax.persistence.Transient")
    $output.dynamicAnnotation("javax.xml.bind.annotation.XmlTransient")
    public void copyTo($entity.model.type $entity.model.var) {
#if ($entity.isRoot() && $entity.primaryKey.isComposite())
        if (${entity.primaryKey.getter}() != null) {
            ${entity.model.var}.${entity.primaryKey.setter}(${entity.primaryKey.getter}().copy());
        }
#end
#foreach ($attribute in $entity.nonCpkAttributes.list)
#if ($attribute.isSetterAccessibilityPublic())
        ${entity.model.var}.${attribute.setter}(${attribute.getter}());
#end
#end
#foreach ($xToOne in $entity.xToOne.list)
        if (${xToOne.to.var} != null) {
            ${entity.model.var}.${xToOne.to.setter}(new ${xToOne.to.type}().${entity.primaryKey.with}(${xToOne.to.var}.${identifiableProperty.getter}()));
        }
#end
    }
#end
#if($entity.auditEntityAttributes.hasCreationAttributes())
$output.require($Audit, "AuditContextHolder")##

    $output.dynamicAnnotation("javax.persistence.PrePersist")
    protected void prePersist() {
        if (AuditContextHolder.audit()) {
#if($entity.auditEntityAttributes.isCreationAuthorSet())
            ${entity.auditEntityAttributes.creationAuthor.setter}(AuditContextHolder.username());
#end
#if($entity.auditEntityAttributes.isCreationDateSet())
            ${entity.auditEntityAttributes.creationDate.setter}(${entity.auditEntityAttributes.creationDate.mappedType.getJavaValue('now()')});
#end
        }
    }
#end
#if($entity.auditEntityAttributes.hasLastModificationAttributes())
$output.require($Audit, "AuditContextHolder")##

    $output.dynamicAnnotation("javax.persistence.PreUpdate")
    protected void preUpdate() {
        if (AuditContextHolder.audit()) {
#if($entity.auditEntityAttributes.isLastModificationAuthorSet())
            ${entity.auditEntityAttributes.lastModificationAuthor.setter}(AuditContextHolder.username());
#end
#if($entity.auditEntityAttributes.isLastModificationDateSet())
            ${entity.auditEntityAttributes.lastModificationDate.setter}(${entity.auditEntityAttributes.lastModificationDate.mappedType.getJavaValue('now()')});
#end
        }
    }
#end
}
/*
 * Source code generated by Celerio, a Jaxio product.
 * Documentation: http://www.jaxio.com/documentation/celerio/
 * Follow us on twitter: @jaxiosoft
 * Need commercial support ? Contact us: info@jaxio.com
 * Template pack-custom:src/main/java/entity/EntityMeta_.e.vm.java
 */
package ru.ssau.lexus.domain.metamodel;

import java.time.LocalDate;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import ru.ssau.lexus.domain.entity.Group;
import ru.ssau.lexus.domain.entity.User;

@StaticMetamodel(User.class)
public abstract class User_ {

    // Raw attributes
    public static volatile SingularAttribute<User, Long> id;
    public static volatile SingularAttribute<User, String> name;
    public static volatile SingularAttribute<User, String> password;
    public static volatile SingularAttribute<User, byte[]> image;
    public static volatile SingularAttribute<User, LocalDate> dateBirth;

    // Many to one
    public static volatile SingularAttribute<User, Group> idGroup;
}
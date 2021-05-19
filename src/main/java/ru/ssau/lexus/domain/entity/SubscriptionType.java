/*
 * Source code generated by Celerio, a Jaxio product.
 * Documentation: http://www.jaxio.com/documentation/celerio/
 * Follow us on twitter: @jaxiosoft
 * Need commercial support ? Contact us: info@jaxio.com
 * Template pack-custom:src/main/java/entity/Entity.e.vm.java
 */
package ru.ssau.lexus.domain.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Entity
@Table(schema = "list", name = "subscription_type")
public class SubscriptionType implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final Logger log = LoggerFactory.getLogger(SubscriptionType.class);

    // Raw attributes
    private Long id;
    private String name;
    private String code;
    // -- [id] ------------------------

    @Column(name = "id", precision = 19)
    @GeneratedValue(strategy = IDENTITY)
    @Id
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SubscriptionType id(Long id) {
        setId(id);
        return this;
    }

    // -- [name] ------------------------

    @NotEmpty
    @Size(max = 2147483647)
    @Column(name = "name", nullable = false, length = 2147483647)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SubscriptionType name(String name) {
        setName(name);
        return this;
    }
    // -- [code] ------------------------

    @NotEmpty
    @Size(max = 2147483647)
    @Column(name = "code", nullable = false, length = 2147483647)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public SubscriptionType code(String code) {
        setCode(code);
        return this;
    }

    /**
     * Apply the default values.
     */
    public SubscriptionType withDefaults() {
        return this;
    }

    /**
     * Equals implementation using a business key.
     */
    @Override
    public boolean equals(Object other) {
        return this == other || (other instanceof SubscriptionType && hashCode() == other.hashCode());
    }

    ;

    @Override
    public int hashCode() {
        return Objects.hash(this);
    }

    /**
     * Construct a readable string representation for this SubscriptionType instance.
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "SubscriptionType{" //
                + "id" + getId() //
                + "name" + getName() //
                + "code" + getCode() //
                + "}";
    }
}
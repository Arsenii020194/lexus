/*
 * Source code generated by Celerio, a Jaxio product.
 * Documentation: http://www.jaxio.com/documentation/celerio/
 * Follow us on twitter: @jaxiosoft
 * Need commercial support ? Contact us: info@jaxio.com
 * Template pack-custom:src/main/java/repository/Repository.e.vm.java
 */
package ru.ssau.lexus.repository.crud;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.ssau.lexus.domain.entity.Token;

@Repository
public interface TokenRepository extends JpaRepository<Token, String>, JpaSpecificationExecutor<Token> {

}
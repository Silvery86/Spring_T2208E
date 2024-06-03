package org.aptech.t2208e.jpaRepository.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE) // only for class
public @interface Entity
{
    // @Entity use for mappint class with table in db  (ex : Student class == student table)
    String tableName();

}

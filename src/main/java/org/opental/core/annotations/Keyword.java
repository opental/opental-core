package org.opental.core.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * interface describing a keyword
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Keyword {

	/**
	 * Name of the module (which means: adapter)
	 * @return adapter name
	 */
	String module() default "";
	
	/**
	 * Name of the command (which means: keyword)
	 * @return keyword name
	 */
    String command() default "";
    
    /**
     * Description of the keyword
     * @return the description
     */
    String description() default "";
    
    /**
     * defines a hint pattern for the target
     * @return hint pattern
     */
    String hintTarget() default "";
    
    /**
     * defines a hint pattern of the value
     * @return hint pattern
     */
    String hintValue() default "";
}

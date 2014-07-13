package net.njay.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Annotation to represent a nested menu
 * </p>
 * Allows for a hierarchical registering of menus
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface NestedMenu {
    /**
     * Array of classes to register as nested
     *
     * @return array of classes
     */
    Class[] value();
}

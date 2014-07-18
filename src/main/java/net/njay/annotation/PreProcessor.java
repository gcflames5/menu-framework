package net.njay.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Annotation to signify a method that modifies the inventory when the inventory is first created
 * </p>
 * Removes the limitations of setting items through the @ItemStackAnnotation only
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface PreProcessor {
}

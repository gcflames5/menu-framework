package net.njay.annotation;


import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Annotation to define which slots should be ignored
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface IgnoreSlots {

    int[] slots();

    ItemStackAnnotation[] items();
}

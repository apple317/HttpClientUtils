package com.apple.butterknife;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Bind a field to the view for the specified ID. The view will automatically be cast to the field
 * type.
 * <pre><code>
 * {@literal @}Bind(R.id.title) TextView title;
 * </code></pre>
 */
//@Target(ElementType.FIELD)
//@Retention(RetentionPolicy.RUNTIME)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface BindView {
    /** View ID to which the field will be bound. */
    int value();
}

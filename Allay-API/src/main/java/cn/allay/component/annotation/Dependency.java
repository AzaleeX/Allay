package cn.allay.component.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Author: daoge_cmd <br>
 * Date: 2023/3/4 <br>
 * Allay Project <br>
 * <p>
 * This annotation is used to declare other components that one component depends on <br/>
 * This annotation needs to be marked on a field of the component's implementation class <br/>
 * When building an object, the injector checks the field list of each component implementation class instance and checks whether the fields are marked by this annotation <br/>
 * If so, the injector will try to find the corresponding component instance through inheritance relationships (if namespaceId is empty) or through namespaceId (if namespaceId is not empty), and inject it into the field <br/>
 * The scope of dependency lookup is limited by the list of components provided to the injector, and if the dependency is not found, will throw {@link cn.allay.component.exception.ComponentInjectException}
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Dependency {
    /**
     * This value is used to specify the namespaceId of the component that needs to be dependent
     */
    String namespaceId() default "";
}
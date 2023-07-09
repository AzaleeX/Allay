package cn.allay.api.component.interfaces;

import cn.allay.api.component.annotation.Dependency;
import cn.allay.api.identifier.Identified;

/**
 * Describe the implementation of a component <br>
 * Any component implementation needs to implement this interface, otherwise obviously you can't pass it into the injector <br>
 * <p>
 * The namespace ID of this component implementation will be used when injecting component dependencies
 * <p>
 * Allay Project 2023/3/4
 *
 * @author daoge_cmd
 * @see Dependency
 */
public interface ComponentImpl extends Identified {
}
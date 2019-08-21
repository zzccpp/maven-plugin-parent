package cn.zcp.mave.plugin.Annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author zhongchunping
 * @version 1.0
 * @Time 2019-08-21 16:36
 * @describe maven-plugin-parent
 *
 * 定义此注解用于定义模块
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface DoMain {

    /**
     * 模块名称
     * @return
     */
    String value() default "";

}

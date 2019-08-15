package cn.zcp.mave.plugin;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

/**
 * @author zhongchunping
 * @version 1.0
 * @Time 2019-08-15 10:31
 * @describe maven-plugin-parent
 *
 * 3、添加核心执行的Mojo
 *
 *
 */
@Mojo(name="apidoc")
public class ApiDocMojo extends AbstractMojo {

    /**
     * 注释之前的部分是参数的描述。该parameter注释标识变量作为魔力参数。
     * defaultValue注释的参数定义变量的默认值。该值可以包括引用项目的表达式，
     * 例如“ ${project.version}”（更多可以在“参数表达式”文档中找到）。
     * 该property参数可用于允许通过引用用户通过-D选项设置的系统属性从命令行配置mojo参数。
     */
    @Parameter(name = "basePackage",property = "basePackage")
    private String basePackage;

    public void execute() throws MojoExecutionException, MojoFailureException {

        System.out.println("需要扫描的库..."+basePackage);
    }
}

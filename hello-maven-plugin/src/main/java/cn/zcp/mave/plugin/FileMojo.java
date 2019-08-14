package cn.zcp.mave.plugin;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;

/**
 * 1、创建自己的一个Mojo
 */
@Mojo(name="copy-file")
public class FileMojo extends AbstractMojo {

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {

        System.out.println("已经拷贝一个文件");
    }
}

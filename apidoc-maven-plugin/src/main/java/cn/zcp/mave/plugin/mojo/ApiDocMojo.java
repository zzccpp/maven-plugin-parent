package cn.zcp.mave.plugin.mojo;

import cn.zcp.mave.plugin.parser.HTMLParser;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandler;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhongchunping
 * @version 1.0
 * @Time 2019-08-15 10:31
 * @describe maven-plugin-parent
 *
 * 3、添加核心执行的Mojo
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

    @Parameter(name = "classpath",property = "classpath")
    private String classpath;

    @Parameter(name = "libDir",property = "libDir")
    private String libDir;

    @Parameter(name = "targetFile",property = "targetFile")
    private String targetFile;


    private URLClassLoader loader;

    /**
     * 存放扫描的得到的class文件
     */
    private List<String> clsList = new ArrayList<String>();
    /**
     * 带有@Controller注解
     */
    private List<Class> controllerCls = new ArrayList<Class>();

    public void execute() throws MojoExecutionException, MojoFailureException {
        try {
            /**
             * D:\workspaces-idea\maven-plugin-parent\zcp-demo/target/classes
             * D:\workspaces-idea\maven-plugin-parent\zcp-demo/target/zcp-demo-1.0-SNAPSHOT/WEB-INF/lib
             */
            getLog().info("类加载路径,"+classpath);
            getLog().info("类加载路径!,"+new File(classpath).getCanonicalPath());
            getLog().info("第三方类加载路径,"+libDir);
            getLog().info("配置需要扫描的包,"+basePackage);
            //扫描基础包下面的所有带Controller注解的class,
            File baseFile = new File(classpath);
            scanFile("-",baseFile);
            getLog().info("扫描处理结束!");

            //初始化自定义类加载器
            List<URL> libs = new ArrayList<URL>();
            URLStreamHandler ush = null;


            URL classUrl = new URL("file",null,new File(classpath).getCanonicalPath()+File.separator);
            libs.add(new URL(null,classUrl.toString(),ush));

           /* URL libUrl = new URL("file",null,new File(libDir).getCanonicalPath()+File.separator);
            String libPath = libUrl.toString();
            File file = new File(libPath.replaceAll("file:",""));
            if(null!=file.listFiles()){
                for (File listFile : file.listFiles()) {
                    getLog().info("======>"+listFile.getAbsolutePath());
                    libs.add(new URL(null,libPath+listFile.getName(),ush));
                }
            }*/
            loader = new URLClassLoader(libs.toArray(new URL[libs.size()]),Thread.currentThread().getContextClassLoader());
            //过滤@Controller
            filterCls();
            //进行解析
            if(null==targetFile){
                targetFile = new File("src"+File.separator+"main"+File.separator+"webapp"+File.separator+"apidoc.html").getAbsolutePath();
            }
            HTMLParser.generate(targetFile,controllerCls);
        } catch (IOException e) {
            getLog().error("异常",e);
        }

    }

    /**
     * 过滤扫描的包中,非Controller
     */
    private void filterCls() {
        getLog().info("开始筛除非@Controller的类..."+clsList.size());
        for (String clsName : clsList) {
            try {
                Class<?> cls = Class.forName(clsName,true,loader);
                if(cls.isAnnotationPresent(Controller.class)){
                    controllerCls.add(cls);
                    getLog().info(">>>"+clsName);
                }
            } catch (ClassNotFoundException e) {
                getLog().error("获取字节码异常!",e);
            }
        }
    }

    /**
     * 当前扫描的文件路径,获取所有的CLASS类名
     * @param file
     */
    private void scanFile(String pre,File file) {
        if(file.isDirectory()){
            File[] tempFiles = file.listFiles();
            String filePath;
            String className;
            for (File temp : tempFiles) {
                if(temp.isDirectory()){
                    pre+="-";
                    scanFile(pre,temp);
                }
                if(temp.getName().endsWith(".class")){//只处理class
                    filePath = temp.getAbsolutePath();
                    //getLog().info("AAAAAA<<>>"+filePath);
                    //列如：D:\workspaces-idea\maven-plugin-parent\zcp-demo\target\classes\cn\zcp\mave\plugin\App.class
                    className = filePath.replaceAll("\\\\","/")
                            .replaceAll(classpath.replaceAll("\\\\","/")+"/","")
                            .replaceAll("/",".")//cn.zcp.mave.plugin.App.class
                            .replace(".class","");
                    //getLog().info("BBBBBB<<>>"+pre+filePath);
                    if(className.startsWith(basePackage)){
                        clsList.add(className);
                    }
                }
            }
        }
    }
}

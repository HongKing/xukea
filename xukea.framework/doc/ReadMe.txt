打包：
    com.xukea.framework.*;
    ibatis3/*;
    ttfs/*;
    用eclipse打包导出时，需要选择 Add directory entries，从而保证jar包中的xml配置文件生效

与主框架（main）整合步骤：
    添加工程lib ：把com.xukea.framework-${version}.jar和引用的第三方包复制到你的工程lib目录下，做为你工程的lib；

依赖关系：
    com.xukea.common.util.cache.Config;
    com.xukea.common.util.WebUtil;

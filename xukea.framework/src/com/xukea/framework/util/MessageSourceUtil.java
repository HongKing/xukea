package com.xukea.framework.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.util.ClassUtils;

/**
 * 支持通配符的语言文件加载
 * 
 * @author 木木大叔
 * @QQ     285198830
 * @version 1.0
 * @date    2012.02.09
 */
public class MessageSourceUtil extends ReloadableResourceBundleMessageSource {
    
    static Log logger = LogFactory.getLog(ResourceBundleMessageSource.class);
    
    // 默认语言文件的后缀
	private static final String PROPERTIES_SUFFIX = ".properties";
	
	private static final boolean ONLY_LOAD_BASE_FILE = false;
	
	// 支持通配符匹配
	private PathMatchingResourcePatternResolver patternResolver = new PathMatchingResourcePatternResolver();
  
	/**
	 * 设置语言文件，支持通配符
	 */
	public void setBasenames(String[] basenames) {
		List<String> baseNameList = new ArrayList<String>();
		// 语言文件所在路径
		String basepath = ClassUtils.getDefaultClassLoader().getResource("").toString();
		// 获取语言文件夹下的所有语言文件
		try {
			for(String baseName : basenames) {
				Resource[] resources = patternResolver.getResources(baseName); //通过通配符取得到所有对应的source
				for(Resource resource : resources) {
					String fileUrl = resource.getURL().toString();
					String fileName = fileUrl.substring( basepath.length() );
	                //除掉后的.properties后缀，因为直接用ResourceBundleMessageSource，是不用加后缀的
					fileName = fileName.substring(0, fileName.indexOf(PROPERTIES_SUFFIX));
	                //ReloadableResourceBundleMessageSource加载文件需要添加“classpath:”前缀
					fileName = "classpath:" + fileName; 
	                baseNameList.add(fileName);
	                
	                if(logger.isInfoEnabled()) {
	                    logger.info("Add properties file: [" + resource.getDescription() + "]");
	                }
	            }
			}
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
		// 设置所有语言文件
		super.setBasenames(baseNameList.toArray(new String[baseNameList.size()]));
	}

	/**
	 * 过滤语言后缀结尾的文件，提高检索效率
	 */
	public List<String> calculateAllFilenames(String basename, Locale locale) {
		String language = locale.getLanguage();
		String country = locale.getCountry();
		String variant = locale.getVariant();
		
		if( ONLY_LOAD_BASE_FILE 
			&& (  ( language.length()>0 && basename.endsWith(language) )
			     || ( country.length()>0  && basename.endsWith(country)  )
			     || ( variant.length()>0  && basename.endsWith(variant)  ) 
			   )
		){
			// 跳过以语言结尾的文件
			return new ArrayList<String>();
		}else{
			return super.calculateAllFilenames(basename, locale);
		}
	}
	
}
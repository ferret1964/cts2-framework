package edu.mayo.cts2.framework.core.xml;

import java.io.IOException;
import java.util.Properties;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import com.atlassian.plugin.spring.AvailableToPlugins;

@Component
@AvailableToPlugins
public class DefaultModelXmlPropertiesService implements ModelXmlPropertiesService {
	
	public static final String CASTORBUILDER_PROPS = "castorbuilder.properties";
	public static final String NAMESPACE_LOCATION_PROPS = "namespaceLocations.properties";
	public static final String NAMESPACE_MAPPINGS_PROPS = "namespaceMappings.properties";
	
	public Properties getCastorBuilderProperties(){
		return this.getProperties(CASTORBUILDER_PROPS);
	}
	
	public Properties getNamespaceLocationProperties(){
		return this.getProperties(NAMESPACE_LOCATION_PROPS);
	}
	
	public Properties getNamespaceMappingProperties(){
		return this.getProperties(NAMESPACE_MAPPINGS_PROPS);
	}
	
	protected Properties getProperties(String path){
		Properties props = new Properties();
		try {
			props.load(new ClassPathResource(path).getInputStream());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
		return props;
	}

}

/*
 * Copyright: (c) 2004-2011 Mayo Foundation for Medical Education and 
 * Research (MFMER). All rights reserved. MAYO, MAYO CLINIC, and the
 * triple-shield Mayo logo are trademarks and service marks of MFMER.
 *
 * Except as contained in the copyright notice above, or as used to identify 
 * MFMER as the author of this software, the trade names, trademarks, service
 * marks, or product names of the copyright holder shall not be used in
 * advertising, promotion or otherwise in connection with this software without
 * prior written authorization of the copyright holder.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package edu.mayo.cts2.framework.model.util;

import java.lang.reflect.Method;

import edu.mayo.cts2.framework.model.codesystemversion.CodeSystemVersionCatalogEntry;
import edu.mayo.cts2.framework.model.core.AbstractResourceDescription;
import edu.mayo.cts2.framework.model.core.EntryDescription;
import edu.mayo.cts2.framework.model.core.OpaqueData;
import edu.mayo.cts2.framework.model.core.ResourceDescriptionDirectoryEntry;
import edu.mayo.cts2.framework.model.core.ScopedEntityName;
import edu.mayo.cts2.framework.model.core.TsAnyType;
import edu.mayo.cts2.framework.model.entity.Designation;
import edu.mayo.cts2.framework.model.entity.EntityDescription;
import edu.mayo.cts2.framework.model.entity.EntityDescriptionBase;
import edu.mayo.cts2.framework.model.entity.types.DesignationRole;

/**
 * The Class RestModelUtils.
 *
 * @author <a href="mailto:kevin.peterson@mayo.edu">Kevin Peterson</a>
 */
public class ModelUtils {
	
	/**
	 * Instantiates a new rest model utils.
	 */
	private ModelUtils(){
		super();
	}
	
	/**
	 * To ts any type.
	 *
	 * @param string the string
	 * @return the ts any type
	 */
	public static TsAnyType toTsAnyType(String string){
		TsAnyType ts = new TsAnyType();
		ts.setContent(string);
		
		return ts;
	}
	
	/**
	 * Gets the resource synopsis value.
	 *
	 * @param entry the entry
	 * @return the resource synopsis value
	 */
	public static String getResourceSynopsisValue(AbstractResourceDescription entry){
		EntryDescription synopsis = entry.getResourceSynopsis();
		if(synopsis != null){
			TsAnyType value = synopsis.getValue();
			
			if(value != null){
				return value.getContent();
			}
		}
		
		return null;
	}
	
	/**
	 * Gets the resource synopsis value.
	 *
	 * @param entry the entry
	 * @return the resource synopsis value
	 */
	public static String getResourceSynopsisValue(ResourceDescriptionDirectoryEntry entry){
		EntryDescription synopsis = entry.getResourceSynopsis();
		if(synopsis != null){
			TsAnyType value = synopsis.getValue();
			
			if(value != null){
				return value.getContent();
			}
		}
		
		return null;
	}
	
	/**
	 * Creates the scoped entity name.
	 *
	 * @param name the name
	 * @param namespace the namespace
	 * @return the scoped entity name
	 */
	public static ScopedEntityName createScopedEntityName(String name, String namespace){
		ScopedEntityName scopedName = new ScopedEntityName();
		scopedName.setName(name);
		scopedName.setNamespace(namespace);
		
		return scopedName;
	}
	
	/**
	 * Creates the opaque data.
	 *
	 * @param text the text
	 * @return the opaque data
	 */
	public static OpaqueData createOpaqueData(String text){
		OpaqueData data = new OpaqueData();
		data.setValue(toTsAnyType(text));
		
		return data;
	}
	
	/**
	 * Gets the entity.
	 *
	 * @param entityDescription the entity description
	 * @return the entity
	 */
	public static EntityDescriptionBase getEntity(EntityDescription entityDescription){
		return (EntityDescriptionBase) entityDescription.getChoiceValue();
	}

	/**
	 * Sets the entity.
	 *
	 * @param wrapper the wrapper
	 * @param entityDescription the entity description
	 */
	public static void setEntity(EntityDescription wrapper,
			EntityDescriptionBase entityDescription) {
		try {
			for(Method method : EntityDescription.class.getDeclaredMethods()){
				if(method.getName().startsWith("set") && 
						method.getParameterTypes().length == 1 &&
						method.getParameterTypes()[0].equals(entityDescription.getClass())){
					method.invoke(wrapper, entityDescription);
				}
			}
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}
	
	/**
	 * To entity description.
	 *
	 * @param entityDescriptionBase the entity description base
	 * @return the entity description
	 */
	public static EntityDescription toEntityDescription(
			EntityDescriptionBase entityDescriptionBase) {
		EntityDescription wrapper = new EntityDescription();
		
		setEntity(wrapper, entityDescriptionBase);
		
		return wrapper;
	}
	
	/**
	 * Gets the preferred designation.
	 *
	 * @param entity the entity
	 * @return the preferred designation
	 */
	public static Designation getPreferredDesignation(EntityDescriptionBase entity){
		if(entity.getDesignationCount() == 1){
			return entity.getDesignation(0);
		}
		
		for(Designation designation : entity.getDesignation()){
			DesignationRole role = designation.getDesignationRole();
			if(role != null && role.equals(DesignationRole.PREFERRED)){
				return designation;
			}
		}
		
		return null;
	}

	/**
	 * Gets the code system name of code system version.
	 *
	 * @param resource the resource
	 * @return the code system name of code system version
	 */
	public static String getCodeSystemNameOfCodeSystemVersion(
			CodeSystemVersionCatalogEntry resource) {
		return resource.getVersionOf().getContent();
	}
}
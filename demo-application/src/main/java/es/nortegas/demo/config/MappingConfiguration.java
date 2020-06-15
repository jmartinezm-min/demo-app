/*******************************************************************************
 * 
 * Autor: NORTEGAS
 * 
 * © NORTEGAS 2019. Todos los derechos reservados. 
 * 
 ******************************************************************************/
package es.nortegas.demo.config;

import java.util.Arrays;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Clase de configuración de la clase Mapper
 * 
 * @author NORTEGAS
 *
 */
@Configuration
public class MappingConfiguration {

  @Bean(name = "org.dozer.Mapper")
  public DozerBeanMapper dozerBean() {
    List<String> mappingFiles = Arrays.asList(
      "config/dozer/gdoc-dozer-mapper.xml");

    DozerBeanMapper dozerBean = new DozerBeanMapper();
    dozerBean.setMappingFiles(mappingFiles);
    return dozerBean;
  }
}


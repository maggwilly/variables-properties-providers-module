/*
 * (c) 2003-2018 MuleSoft, Inc. This software is protected under international copyright
 * law. All use of this software is subject to MuleSoft's Master Subscription Agreement
 * (or other master license agreement) separately entered into in writing between you and
 * MuleSoft. If such an agreement is not in place, you may not use the software.
 */
package com.my.company.custom.provider.api;

import static org.mule.runtime.api.component.ComponentIdentifier.builder;
import static org.mule.runtime.extension.api.util.NameUtils.defaultNamespace;

import static com.my.company.custom.provider.api.VariablesConfigurationPropertiesExtension.EXTENSION_NAME;

import org.mule.runtime.api.component.ComponentIdentifier;
import org.mule.runtime.api.util.Preconditions;
import org.mule.runtime.config.api.dsl.model.ConfigurationParameters;
import org.mule.runtime.config.api.dsl.model.ResourceProvider;
import org.mule.runtime.config.api.dsl.model.properties.*;

import java.util.Map;

/**
 * Builds the provider for a custom-properties-provider:config element.
 *
 * @since 1.0
 */
public class VariablesConfigurationPropertiesProviderFactory implements ConfigurationPropertiesProviderFactory {

  public static final String EXTENSION_NAMESPACE = defaultNamespace(EXTENSION_NAME);
  private static final ComponentIdentifier CUSTOM_PROPERTIES_PROVIDER = builder()
    .namespace(EXTENSION_NAMESPACE)
    .name("config")
    .build();
  @Override
  public ComponentIdentifier getSupportedComponentIdentifier() {
    return CUSTOM_PROPERTIES_PROVIDER;
  }

  @Override
  public ConfigurationPropertiesProvider createProvider(ConfigurationParameters parameters,
                                                        ResourceProvider externalResourceProvider) {
    String file = parameters.getStringParameter("file");
    Preconditions.checkArgument(file != null, "Required attribute 'file' of 'configuration-properties' not found");
    Map<String, ConfigurationProperty> configurationAttributes = new FileConfigurationProvider(externalResourceProvider).yaml2map(file);
    return new VariablesConfigurationPropertiesProvider(configurationAttributes);
  }

}

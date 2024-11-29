/*
 * (c) 2003-2018 MuleSoft, Inc. This software is protected under international copyright
 * law. All use of this software is subject to MuleSoft's Master Subscription Agreement
 * (or other master license agreement) separately entered into in writing between you and
 * MuleSoft. If such an agreement is not in place, you may not use the software.
 */
package com.my.company.custom.provider.api;

import org.mule.runtime.extension.api.annotation.Alias;
import org.mule.runtime.extension.api.annotation.Export;
import org.mule.runtime.extension.api.annotation.Expression;
import org.mule.runtime.extension.api.annotation.Extension;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.sdk.api.annotation.JavaVersionSupport;

import static org.mule.runtime.api.meta.Category.SELECT;
import static org.mule.runtime.api.meta.ExpressionSupport.NOT_SUPPORTED;
import static org.mule.sdk.api.meta.JavaVersion.*;

/**
 * Declares extension for Custom Properties Configuration module
 *
 * @since 1.0
 */
@Extension(name = VariablesConfigurationPropertiesExtension.EXTENSION_NAME,
           category = SELECT,
           vendor = "EDF")
@JavaVersionSupport({JAVA_8, JAVA_11, JAVA_17})
@Export(classes = VariablesConfigurationPropertiesProviderFactory.class,
        resources = {"META-INF/services/org.mule.runtime.config.api.dsl.model.properties.ConfigurationPropertiesProviderFactory"})
public class VariablesConfigurationPropertiesExtension {
  public static final String EXTENSION_NAME = "Variables Properties Provider";

  @Parameter
  @Alias(value="customParameter", description = "Meaningful description of what customParameter is for")
  @Expression(NOT_SUPPORTED)
  private String customParameter;
}

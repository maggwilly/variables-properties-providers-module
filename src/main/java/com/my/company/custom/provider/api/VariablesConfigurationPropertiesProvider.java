package com.my.company.custom.provider.api;

import org.mule.runtime.config.api.dsl.model.properties.ConfigurationPropertiesProvider;
import org.mule.runtime.config.api.dsl.model.properties.ConfigurationProperty;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VariablesConfigurationPropertiesProvider implements ConfigurationPropertiesProvider {
    private final static String DYNAMIC_PREFIX = "variable::";
    private final static Pattern SECURE_PATTERN = Pattern.compile("\\$\\{" + DYNAMIC_PREFIX + "[^}]*}");
    private final Pattern VARIABLE_PATTERN = Pattern.compile("#\\[(.+?)\\]");
    private final Map<String, ConfigurationProperty> configurationAttributes;

    public VariablesConfigurationPropertiesProvider(Map<String, ConfigurationProperty> configurationAttributes) {
        this.configurationAttributes = configurationAttributes;
    }

    @Override
    public Optional<ConfigurationProperty> getConfigurationProperty(String configurationAttributeKey) {
        if (configurationAttributeKey.startsWith(DYNAMIC_PREFIX)) {
            String dynamicActualKey = configurationAttributeKey.substring(DYNAMIC_PREFIX.length());
            String propertyActualKey = evaluate(dynamicActualKey);
            return Optional.ofNullable(this.configurationAttributes.get(propertyActualKey));
        }
        return Optional.empty();
    }

    public String getDescription() {
        return "variables-configuration-properties";
    }

    public String evaluate(String input) {
        Matcher matcher = VARIABLE_PATTERN.matcher(input);
        StringBuffer builder = new StringBuffer();
        while (matcher.find()) {
            String variable = matcher.group(1);
            ConfigurationProperty property = this.configurationAttributes.get(variable);
            matcher.appendReplacement(builder, getPropertyRawValue(property));
        }
        matcher.appendTail(builder);
        return builder.toString();
    }

    private static String getPropertyRawValue(ConfigurationProperty property) {
        if (Objects.nonNull(property)) {
            return (String) property.getRawValue();
        }
        return "null";
    }

}

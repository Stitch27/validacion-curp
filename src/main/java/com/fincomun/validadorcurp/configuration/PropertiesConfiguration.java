package com.fincomun.validadorcurp.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = "file:./validadorcurp/conf/curp.properties")
public class PropertiesConfiguration {
}

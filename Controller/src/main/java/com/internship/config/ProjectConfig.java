package com.internship.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan("com.internship")
@EnableAspectJAutoProxy
public class ProjectConfig {
}

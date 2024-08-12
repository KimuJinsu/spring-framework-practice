package com.jinsu.springframe.test;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

//Spring Configuration class
@Configuration
@ComponentScan("com.jinsu.springframe.test")
class AppConfig {

 @Bean
 public Command command() {
     return new ConcreteCommand();
 }

 @Bean
 public CommandManager commandManager() {
     return new CommandManager();
 }
}
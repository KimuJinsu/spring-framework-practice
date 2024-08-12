package com.jinsu.springframe.test;

import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class CommandManager implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    public Object process(Map<String, Object> commandState) {
        Command command = createCommand();
        command.setState(commandState);
        return command.execute();
    }

    protected Command createCommand() {
        return this.applicationContext.getBean("command", Command.class);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    // Main method to run the example
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        CommandManager commandManager = context.getBean(CommandManager.class);

        // Creating a sample state map
        Map<String, Object> state = Map.of("key1", "value1", "key2", "value2");

        // Processing the command
        Object result = commandManager.process(state);
        System.out.println(result);
    }
}
package com.mjc.school.controller.command;

import org.springframework.stereotype.Component;

@Component
public class Invoker {

    private Command command;

    public void setCommand(Command command) {
        this.command = command;
        execute();
    }

    private void execute() {
        command.execute();
    }
}

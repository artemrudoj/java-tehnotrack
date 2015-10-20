package ru.mipt.comands;

import ru.mipt.session.Session;

import java.util.Map;



/**
 * Вывести помощь
 */
public class HelpCommand implements Command {

    private Map<String, Command> commands;

    public HelpCommand(Map<String, Command> commands) {
        this.commands = commands;
    }

    @Override
    public int execute(Session session, String[] args) {
        System.out.println("Executing help");
        /*
        В простом случае просто выводим данные на консоль
        Если будем работать чере сеть, то команде придется передать также объект для работы с сетью

         */
        return 0;
    }
}

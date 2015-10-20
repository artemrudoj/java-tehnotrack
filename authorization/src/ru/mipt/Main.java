package ru.mipt;


import ru.mipt.authorization.AuthorizationService;
import ru.mipt.authorization.UserStore;
import ru.mipt.comands.*;
import ru.mipt.hisorystorage.BasedOnListStorage;
import ru.mipt.hisorystorage.HistoryStorage;
import ru.mipt.session.Session;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;



// Это псевдокод. Показывает работу паттерна Команда
public class Main {

    private static final String EXIT = "q|exit";

    public static void main(String[] args) {

        Map<String, Command> commands = new HashMap<>();


        HistoryStorage historyStorage= new BasedOnListStorage();
        Session session = new Session(historyStorage);

        // Реализация интерфейса задается в одном месте
        UserStore userStore = new UserStore();
        AuthorizationService authService = new AuthorizationService(userStore);

        //Создаем команды
        Command loginCommand = new LoginCommand(authService);
        Command helpCommand = new HelpCommand(commands);
        Command historyCommand = new HistoryCommand();
        Command userCommand = new UserCommand();
        Command findCommand = new FindCommand();

        commands.put("\\find", findCommand);
        commands.put("\\user", userCommand);
        commands.put("\\history", historyCommand);
        commands.put("\\login", loginCommand);
        commands.put("\\help", helpCommand);

        InputHandler handler = new InputHandler(session, commands);

        Scanner scanner = new Scanner(System.in);
        while (true) {
            String line = scanner.nextLine();
            if (line != null && line.matches(EXIT)) {
                break;
            }
            handler.handle(line);
        }
    }
}

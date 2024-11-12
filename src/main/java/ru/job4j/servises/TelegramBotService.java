package ru.job4j.servises;

import org.springframework.stereotype.Service;
import ru.job4j.content.Content;

@Service
public class TelegramBotService {
    private final BotCommandHandler handler;

    public TelegramBotService(BotCommandHandler handler) {
        this.handler = handler;
    }

    public void content(Content content) {
        handler.receive(content);
    }
}

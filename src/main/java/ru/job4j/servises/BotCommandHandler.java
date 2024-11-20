package ru.job4j.servises;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.job4j.content.Content;
import ru.job4j.model.User;
import ru.job4j.repository.UserRepository;

import java.util.Optional;

@Service
public class BotCommandHandler {
    private final UserRepository userRepository;
    private final MoodService moodService;
    private final TgUI tgUI;

    public BotCommandHandler(UserRepository userRepository,
                             MoodService moodService,
                             TgUI tgUI) {
        this.userRepository = userRepository;
        this.moodService = moodService;
        this.tgUI = tgUI;
    }

    Optional<Content> commands(Message message) {
        String text = message.getText();
        Long chatId = message.getChatId();
        Long clientId = message.getFrom().getId();
        switch (text) {
            case "/start":
                handleStartCommand(chatId, clientId);
                return Optional.empty();
            case "/week_mood_log":
                return moodService.weekMoodLogCommand(chatId, clientId);
            case "/month_mood_log":
                return moodService.monthMoodLogCommand(chatId, clientId);
            case "/award":
                return moodService.awards(chatId, clientId);
            default:
                return Optional.empty();
        }
    }

    Optional<Content> handleCallback(CallbackQuery callback) {
        var moodId = Long.valueOf(callback.getData());
        var user = userRepository.findByClientId(callback.getFrom().getId());
        return user.map(value -> moodService.chooseMood(value, moodId));
    }

    private Optional<Content> handleStartCommand(long chatId, Long clientId) {
        var user = new User();
        user.setClientId(clientId);
        user.setChatId(chatId);
        userRepository.save(user);
        var content = new Content(user.getChatId());
        content.setText("Как настроение?");
        content.setMarkup(tgUI.buildButtons());
        return Optional.of(content);
    }
}

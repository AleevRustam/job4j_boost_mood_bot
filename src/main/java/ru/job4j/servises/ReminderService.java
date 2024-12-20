package ru.job4j.servises;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.job4j.repository.UserRepository;

@Service
public class ReminderService {

    private final TelegramBotService tgRemoteService;
    private final UserRepository userRepository;

    public ReminderService(TelegramBotService tgRemoteService, UserRepository userRepository) {
        this.tgRemoteService = tgRemoteService;
        this.userRepository = userRepository;
    }

    @Scheduled(fixedRateString = "${remind.period}")
    public void ping() {
        for (var user : userRepository.findAll()) {
            var message = new SendMessage();
            message.setChatId(user.getChatId());
            message.setText("Ping");
           /* tgRemoteService.sent(message);*/
        }
    }
}

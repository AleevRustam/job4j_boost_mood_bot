package ru.job4j.servises;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendAudio;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.job4j.content.Content;
import ru.job4j.exceptions.SentContentException;

@Service
public class TelegramBotService extends TelegramLongPollingBot implements SentContent {
    private final BotCommandHandler handler;
    private final String botName;

    public TelegramBotService(@Value("${telegram.bot.name}") String botName,
                              @Value("${telegram.bot.token}") String botToken,
                              BotCommandHandler handler) {
        super(botToken);
        this.handler = handler;
        this.botName = botName;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasCallbackQuery()) {
            handler.handleCallback(update.getCallbackQuery())
                    .ifPresent(this::sent);
        } else if (update.hasMessage() && update.getMessage().getText() != null) {
            handler.commands(update.getMessage())
                    .ifPresent(this::sent);
        }
    }

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public void sent(Content content) {
        try {
            if (content.getAudio() != null) {
                sendAudio(content);
                return;
            }

            if (content.getText() != null && content.getMarkup() != null) {
                sendTextWithMarkup(content);
                return;
            }

            if (content.getText() != null) {
                sendTextWithoutMarkup(content);
                return;
            }

            if (content.getPhoto() != null) {
                sendPhoto(content);
            }
        } catch (TelegramApiException e) {
            throw new SentContentException("Ошибка отправки сообщения", e);
        }
    }

    private void sendPhoto(Content content) throws TelegramApiException {
        var sendPhoto = new SendPhoto();
        sendPhoto.setChatId(content.getChatId());
        sendPhoto.setPhoto(content.getPhoto());
        if (content.getText() != null) {
            sendPhoto.setCaption(content.getText());
        }
        execute(sendPhoto);
    }

    private void sendTextWithoutMarkup(Content content) throws TelegramApiException {
        var sendMessage = new SendMessage();
        sendMessage.setChatId(content.getChatId());
        sendMessage.setText(content.getText());
        execute(sendMessage);
    }

    private void sendTextWithMarkup(Content content) throws TelegramApiException {
        var sendMessage = new SendMessage();
        sendMessage.setChatId(content.getChatId());
        sendMessage.setText(content.getText());
        sendMessage.setReplyMarkup(content.getMarkup());
        execute(sendMessage);
    }

    private void sendAudio(Content content) throws TelegramApiException {
        var sendAudio = new SendAudio();
        sendAudio.setChatId(content.getChatId());
        sendAudio.setAudio(content.getAudio());
        if (content.getText() != null) {
            sendAudio.setCaption(content.getText());
        }
        execute(sendAudio);
    }
}

package ru.secteam.teamwork.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class TelegramBotUpdatesListener implements UpdatesListener {
    private final Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);
    @Autowired
    private TelegramBot telegramBot;

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            // информация об апдейтах
            logger.info("Processing update: {}", update);
            // записываю данные: сообщение и айди чата
            String messageText = update.message().text();
            Long chatId = update.message().chat().id();
            // проверяю на /start
            try {
                if (messageText.equals("/start")) {
                    // отправляю приветствие
                    sendStartMessage(chatId, update.message().chat().firstName());
                } else {
                    // обрабатываю входящее сообщение (уведомление об ошибке/ уведомление о создании напоминания)
                    // пока что тут отправка "ошибочного" стартового сообщения, тк бот пока ничего не понимает
                    sendErrorStartMessage(chatId, update.message().chat().firstName());
                }
            } catch (NullPointerException e) {
                // обработка исключения получения смайлов или стикеров
                logger.warn("Получены некорректные данные в чате " + chatId);
                sendMessage(chatId, "Извини, я тебя не понимаю, попробуй еще раз");
            }
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    private void sendStartMessage(Long chatId, String name) {
        // создаю и отправляю приветственное сообщение
        String startMessage = "Привет, " + name + "! Скоро здесь будет бот для приюта!";
        sendMessage(chatId, startMessage);
        logger.info("Приветственное сообщение отправлено в чат " + chatId);
    }
    private void sendMessage(Long chatId, String message) {
        // создаю сущность сообщения
        SendMessage sendMessage = new SendMessage(chatId, message);
        // создаю сущность ответа и и отправляю его
        SendResponse response = telegramBot.execute(sendMessage);
    }

    private void sendErrorStartMessage(Long chatId, String name) {
        // создаю и отправляю "ошибочное" приветственное сообщение
        String errorStartMessage = "Извини, " + name + "! Пока что я тебя не понимаю";
        sendMessage(chatId, errorStartMessage);
        logger.info("Приветственное сообщение отправлено в чат " + chatId);
    }

//    директория liquibase создана как шаблон и пока не заполнена, соответствующая строка в пропертях аналогично составлена по шаблону
//    зависимость в помнике закомментирована, liquibase на данный момент НЕ ПОДКЛЮЧЕН!!!
}

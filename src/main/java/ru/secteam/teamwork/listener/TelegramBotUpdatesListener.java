package ru.secteam.teamwork.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.*;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Slf4j
public class TelegramBotUpdatesListener implements UpdatesListener {
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
            log.info("Processing update: {}", update);
            // записываю данные: сообщение и айди чата
            String messageText = update.message().text();
            Long chatId = update.message().chat().id();
            String username = update.message().chat().username();
            // проверяю на /start
            try {
                switch (messageText) {
                    case "/start" ->
                        // отправляю приветствие с выбором приюта
                            sendStartMessage(chatId, update.message().chat().firstName());
                    case "Приют для собак", "Приют для кошек" ->
                        // отправляю сообщение с выбором помощи по вопросам о приюте
                            sendChoiceMessage(chatId);
                    case "К выбору приюта" ->
                        // отправляю сообщение с выбором приюта
                            sendSecStartMessage(chatId);
                    case "Инфо" ->
                        // отправляю сообщение с информацией о боте
                            sendInfoMessage(chatId);
                    case "Позвать волонтера" ->
                        // отправляю сообщение о вызове волонтера
                            sendVolunteerMessage(chatId, username);
                    default ->
                        // обрабатываю входящее сообщение, которое бот не умеет распознавать (любой текст, кроме кнопок)
                            sendErrorMessage(chatId, update.message().chat().firstName());
                }
            } catch (NullPointerException e) {
                // обработка исключения получения смайлов или стикеров
                sendErrorMessage(chatId, update.message().chat().firstName());
            }
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    private void sendStartMessage(Long chatId, String name) {
        // создаю и отправляю приветственное сообщение
        String startMessage =
                "Привет, " + name + "! Я - бот приюта в Астане.\n" +
                "Могу помочь выбрать тебе нового друга, подскажу информацию о приюте и предоставлю инструкцию получения животного.\n" +
                "Сюда ты можешь присылать отчеты о своем питомце.\n" +
                "При необходимости я могу направить твое обращение нашим волонтерам.\n\n" +
                "Выбери интересующий тебя приют.";
        SendMessage sendMessage = new SendMessage(chatId, startMessage).replyMarkup(replyKeyboardMarkupChoiceShelter);
        SendResponse response = telegramBot.execute(sendMessage);
        log.info("Приветственное сообщение с предоставлением выбора приюта отправлено в чат " + chatId);
    }

    private void sendChoiceMessage(Long chatId) {
        // создаю и отправляю сообщение с выбором дальнейшей помощи по вопросам о приюте
        String choiceMessage = "По какому вопросу я могу тебе помочь?";
        SendMessage sendMessage = new SendMessage(chatId, choiceMessage).replyMarkup(replyKeyboardMarkupChoiceInfo);
        SendResponse response = telegramBot.execute(sendMessage);

        log.info("Сообщение с предоставлением выбора возможной помощи отправлено в чат " + chatId);
    }

    private void sendSecStartMessage(Long chatId) {
        // создаю и отправляю повторное приветственное сообщение, но только для выбора приюта
        String startSecMessage = "Выбери интересующий тебя приют!";
        SendMessage sendMessage = new SendMessage(chatId, startSecMessage).replyMarkup(replyKeyboardMarkupChoiceShelter);
        SendResponse response = telegramBot.execute(sendMessage);
        log.info("Повторное сообщение с предоставлением выбора приюта отправлено в чат " + chatId);
    }

    private void sendErrorMessage(Long chatId, String name) {
        // создаю и отправляю сообщение об ошибке
        String errorMessage = "Извини, " + name + "! Я тебя не понимаю, воспользуйся кнопками";
        SendMessage sendMessage = new SendMessage(chatId, errorMessage);
        SendResponse response = telegramBot.execute(sendMessage);

        log.info("Сообщение об ошибке отправлено в чат " + chatId);
    }

    private void sendInfoMessage(Long chatId) {
        // создаю и отправляю сообщение с информацией о боте
        String infoMessage = """
                Чтобы получить подробную информацию о выбранном приюте, нажми "Узнать информацию о приюте".

                Чтобы узнать о процессе получения питомца, нажми "Как взять животное из приюта".

                Чтобы прислать отчет о животном, нажми "Прислать отчет о питомце".

                Если я не справляюсь или ты привык общаться с кожаными мешками, нажми "Позвать волонтера".

                Чтобы вернуться к выбору приюта, нажми "К выбору приюта".""";
        SendMessage sendMessage = new SendMessage(chatId, infoMessage).replyMarkup(replyKeyboardMarkupChoiceInfo);
        SendResponse response = telegramBot.execute(sendMessage);

        log.info("Сообщение с информацией о боте отправлено в чат " + chatId);
    }

    private void sendVolunteerMessage(Long chatId, String username) {
        // создаю и отправляю сообщение о вызове волонтера пользователю
        String volunteerMessage = "Отправил твое обращение нашим волонтерам.\n" + "Скоро с тобой свяжутся!";
        SendMessage sendMessage = new SendMessage(chatId, volunteerMessage).replyMarkup(replyKeyboardMarkupChoiceInfo);
        SendResponse response = telegramBot.execute(sendMessage);

        // создаю и отправляю сообщения о вызове волонтера волонтерам
        String messageToVolunteer1 = "Пользователю @" + username + " нужна помощь волонтера!";
        SendMessage sendMessageToVolunteer1 = new SendMessage(672082791, messageToVolunteer1);
        SendResponse responseToVolunteer1 = telegramBot.execute(sendMessageToVolunteer1);

        String messageToVolunteer2 = "Пользователю @" + username + " нужна помощь волонтера!";
        SendMessage sendMessageToVolunteer2 = new SendMessage(397268984, messageToVolunteer2);
        SendResponse responseToVolunteer2 = telegramBot.execute(sendMessageToVolunteer2);

        log.info("Сообщение о вызове волонтера отправлено в чат " + chatId);
        log.info("Сообщение с вызовом волонтера отправлено в чаты " + 672082791 + ", " + 397268984);
    }

//    директория liquibase создана как шаблон и пока не заполнена, соответствующая строка в пропертях аналогично составлена по шаблону
//    зависимость в помнике закомментирована, liquibase на данный момент НЕ ПОДКЛЮЧЕН!!!

//    клавиатура с выбором приюта
    Keyboard replyKeyboardMarkupChoiceShelter = new ReplyKeyboardMarkup(
            new String[]{"Приют для кошек", "Приют для собак"})
            .oneTimeKeyboard(true)   // optional
            .resizeKeyboard(true)    // optional
            .selective(true);        // optional

//    клавиатура с выбором помощи по вопросам о приюте
    Keyboard replyKeyboardMarkupChoiceInfo = new ReplyKeyboardMarkup(
            new String[]{"Узнать информацию о приюте"},
            new String[]{"Как взять животное из приюта"},
            new String[]{"Прислать отчет о питомце"},
            new String[]{"Позвать волонтера"},
            new String[]{"К выбору приюта", "Инфо"})
            .oneTimeKeyboard(true)   // optional
            .resizeKeyboard(true)    // optional
            .selective(true);        // optional
}

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
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.secteam.teamwork.model.Parent;
import ru.secteam.teamwork.model.Volunteer;
import ru.secteam.teamwork.model.enums.ButtonSelection;
import ru.secteam.teamwork.model.enums.PetType;
import ru.secteam.teamwork.repository.ParentRepository;
import ru.secteam.teamwork.repository.ShelterRepository;
import ru.secteam.teamwork.repository.VolunteerRepository;

import java.time.LocalDate;
import java.util.List;

/**
 * Основной класс для работы с ботом. Принимает и обрабатывает сообщения.
 */
@Service
@Slf4j
public class TelegramBotUpdatesListener implements UpdatesListener {
    @Autowired
    private TelegramBot telegramBot;

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    private final ParentRepository parentRepository;
    private final VolunteerRepository volunteerRepository;
    private final ShelterRepository shelterRepository;

    public TelegramBotUpdatesListener(ParentRepository parentRepository, VolunteerRepository volunteerRepository, ShelterRepository shelterRepository) {
        this.parentRepository = parentRepository;
        this.volunteerRepository = volunteerRepository;
        this.shelterRepository = shelterRepository;
    }

    /**
     * Метод, собирающий в себе все команды к боту.
     * При нажатии определенной кнопки меню появляется следующее клавиатурное меню.
     * Принимает список апдейтов чата.
     * Из апдейта метод вытаскивает <b>chatId</b> и <b>текст сообщения</b>, а так же <b>username</b> пользователя.
     *
     * @param updates апдейт не должен быть <b>null</b>.
     * @return
     * @throws {@link NullPointerException} если в чате написали стикер или смайлик.
     */
    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            // информация об апдейтах
            log.info("Processing update: {}", update);
            // записываю данные: сообщение и айди чата
            if (update.message() != null) {
                Long chatId = update.message().chat().id();
                String messageText = update.message().text();
                String username = update.message().chat().username();
                createParent(chatId, username);
                // проверяю на /start
                try {
                    switch (messageText) {
                        case "/start" ->
                            // отправляю приветствие с выбором приюта
                                sendStartMessage(chatId, update.message().chat().firstName());
                        case "Приют для кошек" ->
                            // отправляю сообщение с выбором помощи по вопросам о приюте КОШЕК
                                sendCatChoiceMessage(chatId);
                        case "Приют для собак" ->
                            // отправляю сообщение с выбором помощи по вопросам о приюте СОБАК
                                sendDogChoiceMessage(chatId);
                        case "К выбору приюта" ->
                            // отправляю сообщение с выбором приюта
                                sendSecStartMessage(chatId);
                        case "Инфо" ->
                            // отправляю сообщение с информацией о боте
                                sendInfoMessage(chatId);
                        case "Позвать волонтера" ->
                            // отправляю сообщение о вызове волонтера
                                sendHelpVolunteerMessage(chatId, username);
                        case "Узнать информацию о приюте" ->
                            // отправляю сообщение с информацией о выбранном приюте
                                sendShelterInfoMessage(chatId);
                        case "Как взять животное из приюта" ->
                            // отправляю сообщение с инструкцией о том как взять животное
                                sendInstructionMessage(chatId);
                        case "К выбору помощи по вопросам о приюту" ->
                            // отправляю сообщение с повторным выбором помощи с вопросами о выбранном приюте
                                sendBackMessage(chatId);
                        case "Стать усыновителем" ->
                            // отправляю сообщение с запросом анкеты для того, чтобы стать усыновителем
                                sendParentMessage(chatId);
                        default -> {
                            if (parentRepository.findByChatId(chatId).getButtonSelection() == ButtonSelection.CAT_PARENT_SELECTION || parentRepository.findByChatId(chatId).getButtonSelection() == ButtonSelection.DOG_PARENT_SELECTION) {
                                // отправляю сообщение с данными анкеты волонтерам и ответ пользователю, мол данные приняты
                                sendDoneParentMessage(chatId, messageText, username);
                            } else {
                                // обрабатываю входящее сообщение, которое бот не умеет распознавать (любой текст, кроме кнопок)
                                sendErrorMessage(chatId, update.message().chat().firstName());
                            }
                        }
                    }
                } catch (NullPointerException e) {
                    // обработка исключения получения смайлов или стикеров
                    sendErrorMessage(chatId, update.message().chat().firstName());
                }
            }
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    /**
     * Создание объекта класса Усыновитель.
     *
     * @param chatId
     * @param username
     */
    private void createParent(long chatId, String username) {
        if (parentRepository.findByChatId(chatId) == null) {
            // создаю объект класса Усыновитель
            Parent parent = new Parent();
            parent.setUserName(username);
            parent.setChatId(chatId);
            parentRepository.save(parent);
            log.info("Усыновитель создан из чата " + chatId);
        }
    }

    /**
     * Отправка приветственного сообщения.
     *
     * @param chatId
     * @param name
     */
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

        // обновляю данные о выборе приюта усыновителем
        Parent parent = parentRepository.findByChatId(chatId);
        parent.setButtonSelection(null);
        parentRepository.save(parent);

        log.info("Приветственное сообщение с предоставлением выбора приюта отправлено в чат " + chatId);
    }

    /**
     * После выбора приюта предоставляет дальнейший выбор по работе с приютом КОШЕК.
     *
     * @param chatId
     */
    private void sendCatChoiceMessage(Long chatId) {
        // создаю и отправляю сообщение с выбором дальнейшей помощи по вопросам о приюте КОШЕК
        String choiceMessage = "По какому вопросу я могу тебе помочь?";
        SendMessage sendMessage = new SendMessage(chatId, choiceMessage).replyMarkup(replyKeyboardMarkupChoiceInfo);
        SendResponse response = telegramBot.execute(sendMessage);

        // обновляю данные о выборе приюта усыновителем
        Parent parent = parentRepository.findByChatId(chatId);
        parent.setButtonSelection(ButtonSelection.CAT_SELECTION);
        parentRepository.save(parent);

        log.info("Сообщение с предоставлением выбора возможной помощи отправлено в чат " + chatId);
    }

    /**
     * После выбора приюта предоставляет дальнейший выбор по работе с приютом СОБАК.
     *
     * @param chatId
     */
    private void sendDogChoiceMessage(Long chatId) {
        // создаю и отправляю сообщение с выбором дальнейшей помощи по вопросам о приюте СОБАК
        String choiceMessage = "По какому вопросу я могу тебе помочь?";
        SendMessage sendMessage = new SendMessage(chatId, choiceMessage).replyMarkup(replyKeyboardMarkupChoiceInfo);
        SendResponse response = telegramBot.execute(sendMessage);

        // обновляю данные о выборе приюта усыновителем
        Parent parent = parentRepository.findByChatId(chatId);
        parent.setButtonSelection(ButtonSelection.DOG_SELECTION);
        parentRepository.save(parent);

        log.info("Сообщение с предоставлением выбора возможной помощи отправлено в чат " + chatId);
    }

    /**
     * Приветственное сообщение при повтором обращении к боту или возврат к выбору приюта.
     *
     * @param chatId
     */
    private void sendSecStartMessage(Long chatId) {
        // создаю и отправляю повторное приветственное сообщение, но только для выбора приюта
        String startSecMessage = "Выбери интересующий тебя приют!";
        SendMessage sendMessage = new SendMessage(chatId, startSecMessage).replyMarkup(replyKeyboardMarkupChoiceShelter);
        SendResponse response = telegramBot.execute(sendMessage);

        // обновляю данные о выборе приюта усыновителем
        Parent parent = parentRepository.findByChatId(chatId);
        parent.setButtonSelection(null);
        parentRepository.save(parent);

        log.info("Повторное сообщение с предоставлением выбора приюта отправлено в чат " + chatId);
    }

    /**
     * Отправка сообщения об ошибке, если в чат отправлено сообщение с неверным форматом.
     * Предлагает использовать кнопки, так как весь функционал через эти сообщения.
     *
     * @param chatId
     * @param name
     */
    private void sendErrorMessage(Long chatId, String name) {
        // создаю и отправляю сообщение об ошибке
        String errorMessage = "Извини, " + name + "! Я тебя не понимаю, воспользуйся кнопками";
        SendMessage sendMessage = new SendMessage(chatId, errorMessage);
        SendResponse response = telegramBot.execute(sendMessage);

        log.info("Сообщение об ошибке отправлено в чат " + chatId);
    }

    /**
     * Отправка информации при нажати кнопки инфо.
     * Рассказывает о каждой кнопке.
     *
     * @param chatId
     */
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

    /**
     * При нажатии кнопки вызова волонтера отправляет соответствующее сообщение в чате,
     * а так же отправляет в ответственным волонтерам сообщение, что пользователь просит о помощи.
     * Волонтерами являются 3 участника команды разработки, распределенные по приютам.
     *
     * @param chatId
     * @param username
     */
    private void sendHelpVolunteerMessage(Long chatId, String username) {
        ButtonSelection buttonSelection = parentRepository.findByChatId(chatId).getButtonSelection();
        if (buttonSelection == ButtonSelection.CAT_SELECTION) {
            // создаю и отправляю сообщение о вызове волонтера волонтеру приюта кошек (помощь)
            String messageToVolunteer = "Пользователю @" + username + " нужна помощь волонтера!";
            List<Volunteer> volunteers = volunteerRepository.findVolunteersByShelter(shelterRepository.findByPetType(PetType.CAT));
            for (Volunteer volunteer : volunteers) {
                Long chatIdVolunteer = volunteer.getChatId();
                SendMessage sendMessageToVolunteer = new SendMessage(chatIdVolunteer, messageToVolunteer);
                SendResponse responseToVolunteer = telegramBot.execute(sendMessageToVolunteer);
                log.info("Сообщение с вызовом волонтера отправлено в чат " + chatIdVolunteer);
            }
        } else {
            // создаю и отправляю сообщение о вызове волонтера волонтеру приюта собак (помощь)
            String messageToVolunteer = "Пользователю @" + username + " нужна помощь волонтера!";
            List<Volunteer> volunteers = volunteerRepository.findVolunteersByShelter(shelterRepository.findByPetType(PetType.DOG));
            for (Volunteer volunteer : volunteers) {
                Long chatIdVolunteer = volunteer.getChatId();
                SendMessage sendMessageToVolunteer = new SendMessage(chatIdVolunteer, messageToVolunteer);
                SendResponse responseToVolunteer = telegramBot.execute(sendMessageToVolunteer);
                log.info("Сообщение с вызовом волонтера отправлено в чат " + chatIdVolunteer);
            }
        }
        // создаю и отправляю сообщение о вызове волонтера пользователю
        String volunteerMessage = "Отправил твое обращение нашим волонтерам.\n" + "Скоро с тобой свяжутся!";
        SendMessage sendMessage = new SendMessage(chatId, volunteerMessage).replyMarkup(replyKeyboardMarkupChoiceInfo);
        SendResponse response = telegramBot.execute(sendMessage);
        log.info("Сообщение о вызове волонтера отправлено в чат " + chatId);
    }

    /**
     * Отправка сообщения с информацией о приюте кошек или о приюте собак, в зависимости от buttonSelection.
     *
     * @param chatId
     */
    private void sendShelterInfoMessage(Long chatId) {
        ButtonSelection buttonSelection = parentRepository.findByChatId(chatId).getButtonSelection();
        if (buttonSelection == ButtonSelection.CAT_SELECTION) {
            // создаю и отправляю сообщение с информацией о приюте кошек
            String catShelterName = shelterRepository.findByPetType(PetType.CAT).getName();
            String catShelterAddress = shelterRepository.findByPetType(PetType.CAT).getAddress();
            String catShelterInfo = shelterRepository.findByPetType(PetType.CAT).getInfo();
            String catInfoMessage = catShelterName + "\n" + "Адрес: " + catShelterAddress + "\n" + catShelterInfo;
            SendMessage sendMessage = new SendMessage(chatId, catInfoMessage).replyMarkup(replyKeyboardMarkupChoiceInfo);
            SendResponse response = telegramBot.execute(sendMessage);
            log.info("Сообщение с информацией о приюте кошек отправлено в чат " + chatId);
        } else {
            // создаю и отправляю сообщение с информацией о приюте собак
            String dogShelterName = shelterRepository.findByPetType(PetType.DOG).getName();
            String dogShelterAddress = shelterRepository.findByPetType(PetType.DOG).getAddress();
            String dogShelterInfo = shelterRepository.findByPetType(PetType.DOG).getInfo();
            String dogInfoMessage = dogShelterName + "\n" + "Адрес: " + dogShelterAddress + "\n" + dogShelterInfo;
            SendMessage sendMessage = new SendMessage(chatId, dogInfoMessage).replyMarkup(replyKeyboardMarkupChoiceInfo);
            SendResponse response = telegramBot.execute(sendMessage);
            log.info("Сообщение с информацией о приюте собак отправлено в чат " + chatId);
        }
    }

    /**
     * Отправка сообщения с инструкцией о том, как стать усыновителем кошки или собаки, в зависимости от buttonSelection.
     *
     * @param chatId
     */
    private void sendInstructionMessage(Long chatId) {
        ButtonSelection buttonSelection = parentRepository.findByChatId(chatId).getButtonSelection();
        if (buttonSelection == ButtonSelection.CAT_SELECTION) {
            // создаю и отправляю сообщение с инструкцией по усыновлению кошки
            String catInstructionMessage = shelterRepository.findByPetType(PetType.CAT).getInstruction();
            SendMessage sendMessage = new SendMessage(chatId, catInstructionMessage).replyMarkup(replyKeyboardMarkupInstruction);
            SendResponse response = telegramBot.execute(sendMessage);
            log.info("Сообщение с инструкцией по усыновлению кошки отправлено в чат " + chatId);
        } else {
            // создаю и отправляю сообщение с инструкцией по усыновлению собаки
            String dogInstructionMessage = shelterRepository.findByPetType(PetType.DOG).getInstruction();
            SendMessage sendMessage = new SendMessage(chatId, dogInstructionMessage).replyMarkup(replyKeyboardMarkupInstruction);
            SendResponse response = telegramBot.execute(sendMessage);
            log.info("Сообщение с инструкцией по усыновлению собаки отправлено в чат " + chatId);
        }
    }

    /**
     * Возврат к выбору помощи по вопросам о выбранном приюте.
     *
     * @param chatId
     */
    private void sendBackMessage(Long chatId) {
        // отправляю повторный выбор помощи по вопросам о выбранном приюте
        String startSecMessage = "Выбери, что именно тебя интересует";
        SendMessage sendMessage = new SendMessage(chatId, startSecMessage).replyMarkup(replyKeyboardMarkupChoiceInfo);
        SendResponse response = telegramBot.execute(sendMessage);

        log.info("Сообщение с повторным выбором помощи по вопросам о выбранном приюте отправлено в чат " + chatId);
    }

    /**
     * Отправка сообщения с запросом анкеты для того, чтобы стать усыновителем.
     * Дополнительно отправляются уведомления ответственным волонтерам, содержащие анкету усыновителя.
     *
     * @param chatId
     */
    private void sendParentMessage(Long chatId) {
        // отправляю сообщение с запросом анкеты для того, чтобы стать усыновителем
        String message = """
                Здорово, что ты принял это решение!
                Пожалуйста, заполни анкету по шаблону и отправь ответным сообщением:
                1. ФИО:
                2. Возраст:
                3. Пол:""";
        ;
        SendMessage sendMessage = new SendMessage(chatId, message).replyMarkup(replyKeyboardMarkupInstructionBack);
        SendResponse response = telegramBot.execute(sendMessage);
        log.info("Сообщение с запросом анкеты для того, чтобы стать усыновителем отправлено в чат " + chatId);

        // меняю значение кнопки в зависимости от ветки (кошка/собака) на этап анкетирования
        ButtonSelection buttonSelection = parentRepository.findByChatId(chatId).getButtonSelection();
        if (buttonSelection == ButtonSelection.CAT_SELECTION) {
            Parent parent = parentRepository.findByChatId(chatId);
            parent.setButtonSelection(ButtonSelection.CAT_PARENT_SELECTION);
            parentRepository.save(parent);
        } else {
            Parent parent = parentRepository.findByChatId(chatId);
            parent.setButtonSelection(ButtonSelection.DOG_PARENT_SELECTION);
            parentRepository.save(parent);
        }
    }

    /**
     * Отправка одобрительного сообщения после получения анкеты.
     * Дополнительно отправляются уведомления ответственным волонтерам, содержащие анкету усыновителя.
     *
     * @param chatId
     * @param messageText
     * @param username
     */
    private void sendDoneParentMessage(Long chatId, String messageText, String username) {
        ButtonSelection buttonSelection = parentRepository.findByChatId(chatId).getButtonSelection();
        if (buttonSelection == ButtonSelection.CAT_PARENT_SELECTION) {
            // создаю и отправляю сообщение с данными усыновителя волонтеру приюта кошек
            String messageToVolunteer = "Пользователь @" + username + " готов стать усыновителем!\n" + "Его данные:\n" + messageText;
            List<Volunteer> volunteers = volunteerRepository.findVolunteersByShelter(shelterRepository.findByPetType(PetType.CAT));
            for (Volunteer volunteer : volunteers) {
                Long chatIdVolunteer = volunteer.getChatId();
                SendMessage sendMessageToVolunteer = new SendMessage(chatIdVolunteer, messageToVolunteer);
                SendResponse responseToVolunteer = telegramBot.execute(sendMessageToVolunteer);
                log.info("Сообщение с оповещением волонтера отправлено в чат " + chatIdVolunteer);
            }
        } else {
            // создаю и отправляю сообщение с данными усыновителя волонтеру приюта собак
            String messageToVolunteer = "Пользователь @" + username + " готов стать усыновителем!\n" + "Его данные:\n" + messageText;
            List<Volunteer> volunteers = volunteerRepository.findVolunteersByShelter(shelterRepository.findByPetType(PetType.DOG));
            for (Volunteer volunteer : volunteers) {
                Long chatIdVolunteer = volunteer.getChatId();
                SendMessage sendMessageToVolunteer = new SendMessage(chatIdVolunteer, messageToVolunteer);
                SendResponse responseToVolunteer = telegramBot.execute(sendMessageToVolunteer);
                log.info("Сообщение с оповещением волонтера отправлено в чат " + chatIdVolunteer);
            }
        }
        // отправляю сообщение о передаче данных волонтерам
        String message = """
                Наши волонтеры свяжутся с тобой в ближайшее время и все согласуют.
                Скоро у тебя появится новый друг!
                                
                Чтобы начать сначала, воспользуйся кнопками.""";
        ;
        SendMessage sendMessage = new SendMessage(chatId, message).replyMarkup(replyKeyboardMarkupChoiceShelter);
        SendResponse response = telegramBot.execute(sendMessage);
        log.info("Сообщение о передаче данных волонтерам отправлено в чат " + chatId);

        // меняю значение кнопки на нулл
        Parent parent = parentRepository.findByChatId(chatId);
        parent.setButtonSelection(null);
        parentRepository.save(parent);
    }

    @Scheduled(cron = "0 0/1 * * * *")
    private String animalReportReminder(Long chatId) {
        LocalDate today = LocalDate.now();
        if (!parentRepository.findByChatId(chatId).getReport().equals(today)) {
            log.info("Напоминание отправлено");
            return "Ты забыл отправить сегодня отчет о питомце. Для отправки нажми кнопку -Отправить отчет о питомце- ";
        } else {
            log.info("Сообщение о принятом отчете отправлено");
            return "Твой сегодняшний отчет принят, спасибо!";
        }
    }


    /**
     * Клавиатура с выбором приюта.
     */
    Keyboard replyKeyboardMarkupChoiceShelter = new ReplyKeyboardMarkup(
            new String[]{"Приют для кошек", "Приют для собак"})
            .oneTimeKeyboard(true)   // optional
            .resizeKeyboard(true)    // optional
            .selective(true);        // optional

    /**
     * Клавиатура с выбором помощи по вопросам о приюте
     */
    Keyboard replyKeyboardMarkupChoiceInfo = new ReplyKeyboardMarkup(
            new String[]{"Узнать информацию о приюте"},
            new String[]{"Как взять животное из приюта"},
            new String[]{"Прислать отчет о питомце"},
            new String[]{"Позвать волонтера"},
            new String[]{"К выбору приюта", "Инфо"})
            .oneTimeKeyboard(true)   // optional
            .resizeKeyboard(true)    // optional
            .selective(true);        // optional

    /**
     * Клавиатура с возможностью стать усыновителем после получения инструкции
     */
    Keyboard replyKeyboardMarkupInstruction = new ReplyKeyboardMarkup(
            new String[]{"Стать усыновителем"},
            new String[]{"К выбору помощи по вопросам о приюту"})
            .oneTimeKeyboard(true)   // optional
            .resizeKeyboard(true)    // optional
            .selective(true);        // optional

    /**
     * Клавиатура с возможностью вернуться к предыдущему этапу после готовности стать усыновителем
     */
    Keyboard replyKeyboardMarkupInstructionBack = new ReplyKeyboardMarkup(
            new String[]{"К выбору помощи по вопросам о приюту"})
            .oneTimeKeyboard(true)   // optional
            .resizeKeyboard(true)    // optional
            .selective(true);        // optional
}
/**
 * Ниже опциональные комментарии, которые будут удалены после подключения liquibase.
 */
//    директория liquibase создана как шаблон и пока не заполнена, соответствующая строка в пропертях аналогично составлена по шаблону
//    зависимость в помнике закомментирована, liquibase на данный момент НЕ ПОДКЛЮЧЕН!!!

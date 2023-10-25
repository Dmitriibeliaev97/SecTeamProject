package ru.secteam.teamwork.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.secteam.teamwork.listener.TelegramBotUpdatesListener;
import ru.secteam.teamwork.model.Animal;
import ru.secteam.teamwork.model.Parent;
import ru.secteam.teamwork.repository.ParentRepository;
import ru.secteam.teamwork.services.ParentService;

import java.util.List;

/**
 * Имплементация сервиса усыновителей
 * @see ParentService
 */
@Service
@Slf4j
public class ParentServiceImpl implements ParentService {
    private final AnimalServiceImpl animalService;
    private final ParentRepository parentRepository;

    private final TelegramBotUpdatesListener listener;

    public ParentServiceImpl(AnimalServiceImpl animalService, ParentRepository parentRepository, TelegramBotUpdatesListener listener) {
        this.animalService = animalService;
        this.parentRepository = parentRepository;
        this.listener = listener;
    }

    /**
     * Добавление нового усыновителя.
     * Используется метод репозитория {@link ParentRepository#save(Object)}
     * @param parent
     * @return Добавлен усыновитель.
     * @see ParentService#add(Parent)
     */
    @Override
    public Parent add(Parent parent) {
        log.info("Метод добавления усыновителя выполнен");
        return parentRepository.save(parent);
    }

    /**
     * Получение информации по усыновителю через chat ID
     * Используетяс метод репозитория {@link ParentRepository#findById(Object)}
     * @param chatId
     * @return Вся нформация по искомому усыновителю.
     * @see ParentService#get(Long)
     */
    @Override
    public Parent get(Long chatId) {
        log.info("Метод поиска усыновителя выполнен");
        return parentRepository.findByChatId(chatId);
    }

    /**
     * Метод обновления ифнормации об усыновителе.
     * Создается новый объект усыновителя, ему присваивается переданный chat ID.
     * Этому объекту сохраняются переданная информация по усыновителю.
     * @param chatId
     * @param parent
     * @return Данные по усыновителю обновлены
     * @see ParentService#update(Long, Parent)
     */
    @Override
    public Parent update(Long chatId, Parent parent) {
//        String text = "Ваш испытательный срок заканчивается: " + parent.getDateOfFinishAdoption();
        // создается новый объект усыновителя.
        // передаётся ему chat ID существующего усыновителя, которого необходимо отредактировать
        Parent savedParent = get(chatId);
        if (savedParent == null) {
            return null;
        }
        // передаются новые параметры для усыновителя
        savedParent.setAge(parent.getAge());
        savedParent.setName(parent.getName());
        savedParent.setGender(parent.getGender());
//        savedParent.setDateOfFinishAdoption(parent.getDateOfFinishAdoption());
//        listener.sendMessage(chatId, text);
        log.info("Метод обновления данных усыновителя выполнен");
        return parentRepository.save(savedParent);
    }

    /**
     * Метод удаления усыновителя.
     * Используется метод репозитория {@link ParentRepository#deleteById(Object)}
     * @param chatId
     * @return Информационное сообщение об успешном удалении усыновителя.
     * @see ParentService#delete(Long)
     */
    @Override
    public String delete(Long chatId) {
        parentRepository.deleteByChatId(chatId);
        log.info("Метод удаления усыновителя выполнен");
        return "Усыновитель удалён";
    }

    /**
     * Метод выведения списка всех усыновителей.
     * Используется метод репозитория {@link ParentRepository#findAll()}
     * @return Список всех усыновителей.
     * @see ParentService#allParents()
     */
    @Override
    public List<Parent> allParents() {
        log.info("Метод выведения списках всех усыновителей выполнен");
        return parentRepository.findAll();
    }

    /**
     * Метод добавления животного усыновителю.
     * @return усыновитель, которому добавили животного.
     * @see ParentService#allParents()
     */
    @Override
    public Parent addAnimal(Long chatId, Long id) {
        Parent savedParent = get(chatId);
        Animal addedAnimal = animalService.get(id);

        savedParent.setAnimal(addedAnimal);
        addParentToAnimal(chatId, id);
        log.info("Животное добавлено");
        return parentRepository.save(savedParent);
    }

    /**
     * Метод добавления животному chat ID усыновителя.
     * @param chatId
     * @param id
     */
    public void addParentToAnimal(Long chatId, Long id) {
        Animal savedAnimal = animalService.get(id);
        Parent addedParent = get(chatId);
        log.info("Усыновитель добавлен");
        savedAnimal.setParent(addedParent);
    }

    /**
     * Метод добавления даты последнего отчета усыновителя.
     * @param chatId
     * @param date
     */
    @Override
    public Parent addDateOfReport(Long chatId, String date) {
        Parent savedParent = get(chatId);
        savedParent.setReport(date);
        log.info("Усыновитель добавлен");
        return parentRepository.save(savedParent);
    }

    /**
     * Метод отправляет сообщение пользователю бота.
     * @param userName
     * @param textMessage
     */
    @Override
    public void sendMessageToParent(String userName, String textMessage) {
        // поиск усыновителя по юзернейму
        Parent searchedParent = parentRepository.findByUserName(userName);
        // вызывается метод отправки сообщения в бот
        listener.sendMessage(searchedParent.getChatId(), textMessage);
        log.info("Метод отправки сообщений пользователям бота выполнен");
    }

    @Override
    public void sendCongratulatoryMessage(String userName) {
        // поиск усыновителя по юзернейму
        Parent searchedParent = parentRepository.findByUserName(userName);
        // вызывается метод отправки поздравительного сообщения в бот
        listener.sendFinishMessage(searchedParent.getChatId());
        // после отправки сообщения об успешном прохождении испытательного срок у пользователя удаляется животное из строчки в БД
        animalService.delete(searchedParent.getAnimal().getId());
        log.info("Метод отправки поздравительного сообщения пользователям бота выполнен");
    }

    @Override
    public void sendMessageAdoptionFailed(String userName) {
        // поиск усыновителя по юзернейму
        Parent searchedParent = parentRepository.findByUserName(userName);
        // вызывается метод отправки сообщения о неудачном прохождении испытательного срока в бот
        listener.sendMessageAdoptionFailed(searchedParent.getChatId());
        log.info("Метод отправки сообщения о неудачном прохождении испытательного срока пользователям бота выполнен");
    }
}

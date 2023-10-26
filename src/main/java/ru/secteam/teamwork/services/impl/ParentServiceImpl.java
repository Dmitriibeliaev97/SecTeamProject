package ru.secteam.teamwork.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.secteam.teamwork.listener.TelegramBotUpdatesListener;
import ru.secteam.teamwork.model.Animal;
import ru.secteam.teamwork.model.Parent;
import ru.secteam.teamwork.repository.AnimalRepository;
import ru.secteam.teamwork.repository.ParentRepository;
import ru.secteam.teamwork.services.ParentService;

import java.time.LocalDate;
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
    private final AnimalRepository animalRepository;
    private final TelegramBotUpdatesListener listener;

    public ParentServiceImpl(AnimalServiceImpl animalService, ParentRepository parentRepository, AnimalRepository animalRepository, TelegramBotUpdatesListener listener) {
        this.animalService = animalService;
        this.parentRepository = parentRepository;
        this.animalRepository = animalRepository;
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
        // Проверяем, что у усыновителя есть животное
        if (get(chatId).getAnimal() != null) {
            // Ищем айди животного этого усыновителя
            long parentsAnimalId = get(chatId).getAnimal().getId();
            // Создаем животное, которое соответствует этому айди и переносим ему все данные
            Animal animal = animalRepository.findById(parentsAnimalId).orElse(null);
            // Новому животному прописываем поле с усыновителем как null
            animal.setParent(null);
            // Создаем нового усыновителя, передаем все данные старого
            Parent deletedParent = get(chatId);
            // Новому усыновителю убираем животное
            deletedParent.setAnimal(null);
            // Перезаписываем в БД усыновителя уже без животного
            parentRepository.save(deletedParent);
            log.info("Усыновитель на удаление обновлен");
            // Сохраняем новое животного, перезаписываем старое
            animalRepository.save(animal);
            log.info("У животного " + animal.getName() + " удален усыновитель");

        }
        parentRepository.deleteById(chatId);
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
    public Parent addDateOfReport(Long chatId, LocalDate date) {
        Parent savedParent = get(chatId);
        savedParent.setReport(date);
        log.info("Дата последнего отчета обновлена");
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
        // после отправки сообщения об успешном прохождении испытательного срока, у пользователя удаляется животное из строчки в БД
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

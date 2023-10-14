package ru.secteam.teamwork.services.impl;

import org.springframework.stereotype.Service;
import ru.secteam.teamwork.model.Animal;
import ru.secteam.teamwork.model.Shelter;
import ru.secteam.teamwork.repository.ShelterRepository;
import ru.secteam.teamwork.services.ShelterService;

@Service
public class ShelterServiceImpl implements ShelterService {
    private final ShelterRepository shelterRepository;

    public ShelterServiceImpl(ShelterRepository shelterRepository) {
        this.shelterRepository = shelterRepository;
    }

    @Override
    public Shelter add(Shelter shelter) {
        return shelterRepository.save(shelter);
    }

    @Override
    public Shelter get(Long id) {
        return shelterRepository.findById(id).orElse(null);
    }

    @Override
    public Shelter update(Long id, Shelter shelter) {
        Shelter savedShelter = get(id);
        if (savedShelter == null) {
            return null;
        }
        savedShelter.setName(shelter.getName());
        savedShelter.setAddress(shelter.getAddress());
        savedShelter.setInfo(shelter.getInfo());
        savedShelter.setInstruction(shelter.getInstruction());
        savedShelter.setPetType(shelter.getPetType());

        return shelterRepository.save(savedShelter);
    }

    @Override
    public String delete(Long id) {
        shelterRepository.deleteById(id);
        return "Приют удален";
    }
}

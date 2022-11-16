package acc.inzynierka.services.mobileapp;

import acc.inzynierka.exception.flashcard.FlashcardAlreadyExistsException;
import acc.inzynierka.exception.flashcard.FlashcardNotFoundException;
import acc.inzynierka.models.Flashcard;
import acc.inzynierka.models.Level;
import acc.inzynierka.modelsDTO.mobileapp.FlashcardMobileDto;
import acc.inzynierka.modelsDTO.mobileapp.LevelMobileDto;
import acc.inzynierka.modelsDTO.webapp.FlashcardDto;
import acc.inzynierka.payload.request.webapp.FlashcardRequest;
import acc.inzynierka.payload.response.webapp.FlashcardResponse;
import acc.inzynierka.repository.FlashcardRepository;
import acc.inzynierka.services.webapp.ImageService;
import acc.inzynierka.services.webapp.LevelService;
import acc.inzynierka.utils.ObjectMapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FlashcardMobileService {

    @Autowired
    private LevelMobileService levelMobileService;

    @Autowired
    private ImageService imageService;

    public List<FlashcardMobileDto> getAllFlashcards(Long levelID) {
        Level level = levelMobileService.findById(levelID);
        List<Flashcard> flashcardList = level.getFlashcards();

        return ObjectMapperUtil.mapToDTO(flashcardList, FlashcardMobileDto.class);
    }
}

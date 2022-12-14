package acc.inzynierka.services.mobileapp;

import acc.inzynierka.models.Flashcard;
import acc.inzynierka.models.Level;
import acc.inzynierka.modelsDTO.mobileapp.FlashcardMobileDto;
import acc.inzynierka.utils.ObjectMapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class FlashcardMobileService {

    @Autowired
    private LevelMobileService levelMobileService;

    public List<FlashcardMobileDto> getAllFlashcards(Long levelID) {
        Level level = levelMobileService.findById(levelID);
        List<Flashcard> flashcardList = level.getFlashcards();

        List<FlashcardMobileDto> flashcardMobileDtoList = ObjectMapperUtil.mapToDTO(flashcardList, FlashcardMobileDto.class);
        Collections.shuffle(flashcardMobileDtoList);

        return flashcardMobileDtoList;
    }
}

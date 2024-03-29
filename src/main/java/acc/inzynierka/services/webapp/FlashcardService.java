package acc.inzynierka.services.webapp;

import acc.inzynierka.exception.flashcard.FlashcardAlreadyExistsException;
import acc.inzynierka.exception.flashcard.FlashcardNotFoundException;
import acc.inzynierka.models.Flashcard;
import acc.inzynierka.models.Level;
import acc.inzynierka.modelsDTO.webapp.FlashcardDto;
import acc.inzynierka.payload.request.webapp.FlashcardRequest;
import acc.inzynierka.payload.response.webapp.FlashcardResponse;
import acc.inzynierka.repository.FlashcardRepository;
import acc.inzynierka.utils.ObjectMapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FlashcardService {

    @Autowired
    private FlashcardRepository flashcardRepository;

    @Autowired
    private LevelService levelService;

    @Autowired
    private ImageService imageService;

    public List<FlashcardDto> getAllFlashcards(Long levelID) {
        Level level = levelService.findById(levelID);
        List<Flashcard> flashcardList = level.getFlashcards();

        return ObjectMapperUtil.mapToDTO(flashcardList, FlashcardDto.class);
    }

    public FlashcardDto getFlashcardById(Long flashcardID) {
        Flashcard flashcard = findById(flashcardID);

        return (FlashcardDto) ObjectMapperUtil.mapToDTOSingle(flashcard, FlashcardDto.class);
    }

    public void deleteFlashcardById(Long id) {
        Flashcard flashcard = findById(id);

        flashcardRepository.delete(flashcard);
    }

    public FlashcardResponse addFlashcard(Long levelID, FlashcardRequest flashcardRequest) {
        checkIfFlashcardExpressionIsUsed(levelID, flashcardRequest);

        Flashcard newFlashcard = new Flashcard();
        newFlashcard.setExpOriginal(flashcardRequest.getExpOriginal());
        newFlashcard.setExpTranslation(flashcardRequest.getExpTranslation());
        newFlashcard.setExpDescription(flashcardRequest.getExpDescription());
        newFlashcard.setLevel(levelService.findById(levelID));
        newFlashcard.setImage(imageService.findByName(flashcardRequest.getImageName()));

        Flashcard savedFlashcard = flashcardRepository.save(newFlashcard);
        FlashcardResponse flashcardResponse = new FlashcardResponse();
        flashcardResponse.setFlashcard((FlashcardDto) ObjectMapperUtil.mapToDTOSingle(savedFlashcard, FlashcardDto.class));
        flashcardResponse.setMessage("Pomyślnie utworzono fiszkę");

        return flashcardResponse;
    }

    public void editFlashcard(Long levelID, Long flashcardID, FlashcardRequest flashcardRequest) {
        Flashcard flashcard = findById(flashcardID);

        if (!flashcard.getExpOriginal().equals(flashcardRequest.getExpOriginal())) {
            checkIfFlashcardExpressionIsUsed(levelID, flashcardRequest);
        }

        flashcard.setExpOriginal(flashcardRequest.getExpOriginal());
        flashcard.setExpTranslation(flashcardRequest.getExpTranslation());
        flashcard.setExpDescription(flashcardRequest.getExpDescription());
        flashcard.setImage(imageService.findByName(flashcardRequest.getImageName()));


        flashcardRepository.save(flashcard);
    }

    public void checkIfFlashcardExpressionIsUsed(Long levelID, FlashcardRequest flashcardRequest) {
        Level level = levelService.findById(levelID);

        List<Flashcard> flashcardList = level.getFlashcards();

        Optional checkIfFlashcardExists = flashcardList.stream()
                .filter(flashcard -> flashcard.getExpOriginal().equals(flashcardRequest.getExpOriginal()))
                .findFirst();

        if (checkIfFlashcardExists.isPresent()) {
            throw new FlashcardAlreadyExistsException();
        }
    }


    public Flashcard findById(long id) {
        Flashcard flashcard = flashcardRepository.findById(id)
                .orElseThrow(FlashcardNotFoundException::new);

        return flashcard;
    }

}

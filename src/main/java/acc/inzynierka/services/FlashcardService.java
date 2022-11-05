package acc.inzynierka.services;

import acc.inzynierka.exception.course.CourseAlreadyExistsException;
import acc.inzynierka.exception.course.CourseNotFoundException;
import acc.inzynierka.exception.flashcard.FlashcardAlreadyExistsException;
import acc.inzynierka.exception.flashcard.FlashcardNotFoundException;
import acc.inzynierka.exception.image.ImageNotFoundException;
import acc.inzynierka.exception.level.LevelNotFoundException;
import acc.inzynierka.models.Flashcard;
import acc.inzynierka.models.Level;
import acc.inzynierka.modelsDTO.FlashcardDto;
import acc.inzynierka.payload.request.FlashcardRequest;
import acc.inzynierka.payload.response.FlashcardResponse;
import acc.inzynierka.repository.FlashcardRepository;
import acc.inzynierka.repository.ImageRepository;
import acc.inzynierka.repository.LevelRepository;
import acc.inzynierka.utils.ObjectMapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FlashcardService {

    @Autowired
    FlashcardRepository flashcardRepository;

    @Autowired
    LevelRepository levelRepository;

    @Autowired
    ImageRepository imageRepository;

    public List<FlashcardDto> getAllFlashcards(Long levelID) {
        Level level = levelRepository.findById(levelID).get();
        List<Flashcard> flashcardList = level.getFlashcards();

        return ObjectMapperUtil.mapToDTO(flashcardList, FlashcardDto.class);
    }

    public FlashcardDto getFlashcardById(Long flashcardID){
        Flashcard flashcard = flashcardRepository.findById(flashcardID).orElseThrow(FlashcardNotFoundException::new);

        return (FlashcardDto) ObjectMapperUtil.mapToDTOSingle(flashcard, FlashcardDto.class);
    }

    public void deleteFlashcardById(Long id) {
        Flashcard flashcard = flashcardRepository.findById(id)
                .orElseThrow(CourseNotFoundException::new);

        flashcardRepository.delete(flashcard);
    }

    public FlashcardResponse addFlashcard(Long levelID, FlashcardRequest flashcardRequest){
        Optional checkIfExists = flashcardRepository.findByExpOriginal(flashcardRequest.getExpOriginal());
        if (checkIfExists.isPresent()) {
            throw new CourseAlreadyExistsException();
        }

        Flashcard newFlashcard = new Flashcard();
        newFlashcard.setExpOriginal(flashcardRequest.getExpOriginal());
        newFlashcard.setExpTranslation(flashcardRequest.getExpTranslation());
        newFlashcard.setExpDescription(flashcardRequest.getExpDescription());
        newFlashcard.setLevel(levelRepository.findById(levelID)
                .orElseThrow(LevelNotFoundException::new));
        newFlashcard.setImage(imageRepository.findByName(flashcardRequest.getImageName())
                .orElseThrow(ImageNotFoundException::new));

        FlashcardResponse flashcardResponse = new FlashcardResponse();
        flashcardResponse.setFlashcard((FlashcardDto) ObjectMapperUtil.mapToDTOSingle(newFlashcard, FlashcardDto.class));
        flashcardResponse.setMessage("Pomyślnie utworzono kurs");

        return flashcardResponse;
    }

    public void editFlashcard(Long levelID, Long flashcardID, FlashcardRequest flashcardRequest){
        Flashcard flashcard = flashcardRepository.findByExpOriginal(flashcardRequest.getExpOriginal()).get();
        if(!flashcard.getExpOriginal().equals(flashcardRequest.getExpOriginal())){
            throw new FlashcardAlreadyExistsException();
        }

        flashcard.setExpOriginal(flashcardRequest.getExpOriginal());
        flashcard.setExpTranslation(flashcardRequest.getExpTranslation());
        flashcard.setExpDescription(flashcardRequest.getExpDescription());
        flashcard.setImage(imageRepository.findByName(flashcardRequest.getImageName())
                .orElseThrow(ImageNotFoundException::new));


        flashcardRepository.save(flashcard);
    }



}

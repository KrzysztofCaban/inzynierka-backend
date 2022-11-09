package acc.inzynierka.services;

import acc.inzynierka.exception.course.CourseNotFoundException;
import acc.inzynierka.exception.exercise.ExerciseAlreadyExistsException;
import acc.inzynierka.exception.exercise.ExerciseNotFoundException;
import acc.inzynierka.exception.image.ImageNotFoundException;
import acc.inzynierka.exception.level.LevelNotFoundException;
import acc.inzynierka.models.Exercise;
import acc.inzynierka.models.Flashcard;
import acc.inzynierka.models.Level;
import acc.inzynierka.modelsDTO.ExerciseDto;
import acc.inzynierka.modelsDTO.FlashcardDto;
import acc.inzynierka.payload.request.ExerciseRequest;
import acc.inzynierka.payload.response.ExerciseResponse;
import acc.inzynierka.repository.ExerciseRepository;
import acc.inzynierka.repository.ImageRepository;
import acc.inzynierka.repository.LevelRepository;
import acc.inzynierka.utils.ObjectMapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExerciseService {

    @Autowired
    ExerciseRepository exerciseRepository;

    @Autowired
    LevelRepository levelRepository;

    @Autowired
    ImageRepository imageRepository;

    public List<ExerciseDto> getAllExercises(Long levelID) {
        Level level = levelRepository.findById(levelID)
                .orElseThrow(LevelNotFoundException::new);
        List<Exercise> exerciseList = level.getExercises();

        return ObjectMapperUtil.mapToDTO(exerciseList, ExerciseDto.class);
    }

    public ExerciseDto getExerciseById(Long exerciseID){
        Exercise exercise = exerciseRepository.findById(exerciseID).orElseThrow(ExerciseNotFoundException::new);

        return (ExerciseDto) ObjectMapperUtil.mapToDTOSingle(exercise, ExerciseDto.class);
    }

    public void deleteExerciseById(Long id) {
        Exercise exercise = exerciseRepository.findById(id)
                .orElseThrow(ExerciseNotFoundException::new);

        exerciseRepository.delete(exercise);
    }

    public ExerciseResponse addExercise(Long levelID, ExerciseRequest exerciseRequest){
        checkIfExerciseExpressionIsUsed(levelID, exerciseRequest);

        Exercise newExercise = new Exercise();
        newExercise.setExpression(exerciseRequest.getExpression());
        newExercise.setQuestion(exerciseRequest.getQuestion());
        newExercise.setBad_answer1(exerciseRequest.getBad_answer1());
        newExercise.setBad_answer2(exerciseRequest.getBad_answer2());
        newExercise.setBad_answer3(exerciseRequest.getBad_answer3());
        newExercise.setLevel(levelRepository.findById(levelID)
                .orElseThrow(LevelNotFoundException::new));
        newExercise.setImage(imageRepository.findByName(exerciseRequest.getImageName())
                .orElseThrow(ImageNotFoundException::new));

        Exercise savedExercise = exerciseRepository.save(newExercise);
        ExerciseResponse exerciseResponse = new ExerciseResponse();
        exerciseResponse.setExercise((ExerciseDto) ObjectMapperUtil.mapToDTOSingle(savedExercise, ExerciseDto.class));
        exerciseResponse.setMessage("Pomyślnie utworzono ćwiczenie");

        return exerciseResponse;
    }

    public void editExercise(Long levelID ,Long exerciseID, ExerciseRequest exerciseRequest){
        Exercise exercise = exerciseRepository.findById(exerciseID)
                .orElseThrow(ExerciseNotFoundException::new);
        if(!exercise.getExpression().equals(exercise.getExpression())){
            checkIfExerciseExpressionIsUsed(levelID, exerciseRequest);
        }

        exercise.setExpression(exerciseRequest.getExpression());
        exercise.setQuestion(exerciseRequest.getQuestion());
        exercise.setBad_answer1(exerciseRequest.getBad_answer1());
        exercise.setBad_answer2(exerciseRequest.getBad_answer2());
        exercise.setBad_answer3(exerciseRequest.getBad_answer3());
        exercise.setImage(imageRepository.findByName(exerciseRequest.getImageName())
                .orElseThrow(ImageNotFoundException::new));

        exerciseRepository.save(exercise);
    }

    public void checkIfExerciseExpressionIsUsed(Long levelID, ExerciseRequest exerciseRequest){
        Level level = levelRepository.findById(levelID)
                .orElseThrow(LevelNotFoundException::new);

        List<Exercise> exerciseList = level.getExercises();

        Optional checkIfExerciseExists = exerciseList.stream()
                .filter(exercise -> exercise.getExpression().equals(exerciseRequest.getExpression()))
                .findFirst();

        if(checkIfExerciseExists.isPresent()){
            throw new ExerciseAlreadyExistsException();
        }
    }
}

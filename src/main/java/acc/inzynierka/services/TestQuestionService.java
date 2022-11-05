package acc.inzynierka.services;

import acc.inzynierka.exception.course.CourseNotFoundException;
import acc.inzynierka.exception.image.ImageNotFoundException;
import acc.inzynierka.exception.level.LevelNotFoundException;
import acc.inzynierka.exception.testQuestion.TestQuestionAlreadyExistsException;
import acc.inzynierka.exception.testQuestion.TestQuestionNotFoundException;
import acc.inzynierka.models.Level;
import acc.inzynierka.models.TestQuestion;
import acc.inzynierka.modelsDTO.TestQuestionDto;
import acc.inzynierka.payload.request.TestQuestionRequest;
import acc.inzynierka.payload.response.TestQuestionResponse;
import acc.inzynierka.repository.ImageRepository;
import acc.inzynierka.repository.LevelRepository;
import acc.inzynierka.repository.TestQuestionRepository;
import acc.inzynierka.utils.ObjectMapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TestQuestionService {

    @Autowired
    TestQuestionRepository testQuestionRepository;

    @Autowired
    LevelRepository levelRepository;

    @Autowired
    ImageRepository imageRepository;

    public List<TestQuestionDto> getAllTestQuestions(Long levelID) {
        Level level = levelRepository.findById(levelID)
                .orElseThrow(LevelNotFoundException::new);
        List<TestQuestion> testQuestionList = level.getTestQuestions();

        return ObjectMapperUtil.mapToDTO(testQuestionList, TestQuestionDto.class);
    }

    public TestQuestionDto getTestQuestionById(Long testQuestionID){
        TestQuestion testQuestion = testQuestionRepository.findById(testQuestionID)
                .orElseThrow(TestQuestionNotFoundException::new);

        return (TestQuestionDto) ObjectMapperUtil.mapToDTOSingle(testQuestion, TestQuestionDto.class);
    }

    public void deleteTestQuestionById(Long id) {
        TestQuestion testQuestion = testQuestionRepository.findById(id)
                .orElseThrow(TestQuestionNotFoundException::new);

        testQuestionRepository.delete(testQuestion);
    }

    public TestQuestionResponse addTestQuestion(Long levelID, TestQuestionRequest testQuestionRequest){
        checkIfTestAnswerIsUsed(levelID, testQuestionRequest);

        TestQuestion newTestQuestion = new TestQuestion();
        newTestQuestion.setQuestion(testQuestionRequest.getQuestion());
        newTestQuestion.setAnswer(testQuestionRequest.getAnswer());
        newTestQuestion.setLevel(levelRepository.findById(levelID)
                .orElseThrow(LevelNotFoundException::new));
        newTestQuestion.setImage(imageRepository.findByName(testQuestionRequest.getImageName())
                .orElseThrow(ImageNotFoundException::new));

        TestQuestion savedTestQuestion = testQuestionRepository.save(newTestQuestion);
        TestQuestionResponse testQuestionResponse = new TestQuestionResponse();
        testQuestionResponse.setTestQuestion((TestQuestionDto) ObjectMapperUtil.mapToDTOSingle(newTestQuestion, TestQuestionDto.class));
        testQuestionResponse.setMessage("Pomyślnie utworzono pytanie testowe");

        return testQuestionResponse;
    }

    public void editTestQuestion(Long levelID ,Long testQuestionID, TestQuestionRequest testQuestionRequest){
        TestQuestion testQuestion = testQuestionRepository.findById(testQuestionID)
                .orElseThrow(TestQuestionNotFoundException::new);
        if(!testQuestion.getAnswer().equals(testQuestionRequest.getAnswer())){
            checkIfTestAnswerIsUsed(levelID, testQuestionRequest);
        }

        testQuestion.setQuestion(testQuestionRequest.getQuestion());
        testQuestion.setAnswer(testQuestionRequest.getAnswer());
        testQuestion.setImage(imageRepository.findByName(testQuestionRequest.getImageName())
                .orElseThrow(ImageNotFoundException::new));

        testQuestionRepository.save(testQuestion);
    }

    public void checkIfTestAnswerIsUsed(Long levelID, TestQuestionRequest testQuestionRequest){
        Level level = levelRepository.findById(levelID)
                .orElseThrow(LevelNotFoundException::new);

        List<TestQuestion> testQuestionList = level.getTestQuestions();

        Optional checkIfExerciseExists = testQuestionList.stream()
                .filter(exercise -> exercise.getAnswer().equals(testQuestionRequest.getAnswer()))
                .findFirst();

        if(checkIfExerciseExists.isPresent()){
            throw new TestQuestionAlreadyExistsException();
        }
    }
}

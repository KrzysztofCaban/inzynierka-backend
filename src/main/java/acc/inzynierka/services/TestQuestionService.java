package acc.inzynierka.services;

import acc.inzynierka.exception.testQuestion.TestQuestionAlreadyExistsException;
import acc.inzynierka.exception.testQuestion.TestQuestionNotFoundException;
import acc.inzynierka.models.Level;
import acc.inzynierka.models.TestQuestion;
import acc.inzynierka.modelsDTO.TestQuestionDto;
import acc.inzynierka.payload.request.TestQuestionRequest;
import acc.inzynierka.payload.response.TestQuestionResponse;
import acc.inzynierka.repository.TestQuestionRepository;
import acc.inzynierka.utils.ObjectMapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TestQuestionService {

    @Autowired
    private TestQuestionRepository testQuestionRepository;

    @Autowired
    private LevelService levelService;

    @Autowired
    private ImageService imageService;

    public List<TestQuestionDto> getAllTestQuestions(Long levelID) {
        Level level = levelService.findById(levelID);
        List<TestQuestion> testQuestionList = level.getTestQuestions();

        return ObjectMapperUtil.mapToDTO(testQuestionList, TestQuestionDto.class);
    }

    public TestQuestionDto getTestQuestionById(Long testQuestionID) {
        TestQuestion testQuestion = findById(testQuestionID);

        return (TestQuestionDto) ObjectMapperUtil.mapToDTOSingle(testQuestion, TestQuestionDto.class);
    }

    public void deleteTestQuestionById(Long id) {
        TestQuestion testQuestion = findById(id);

        testQuestionRepository.delete(testQuestion);
    }

    public TestQuestionResponse addTestQuestion(Long levelID, TestQuestionRequest testQuestionRequest) {
        checkIfTestAnswerIsUsed(levelID, testQuestionRequest);

        TestQuestion newTestQuestion = new TestQuestion();
        newTestQuestion.setQuestion(testQuestionRequest.getQuestion());
        newTestQuestion.setAnswer(testQuestionRequest.getAnswer());
        newTestQuestion.setLevel(levelService.findById(levelID));
        newTestQuestion.setImage(imageService.findByName(testQuestionRequest.getImageName()));

        TestQuestion savedTestQuestion = testQuestionRepository.save(newTestQuestion);
        TestQuestionResponse testQuestionResponse = new TestQuestionResponse();
        testQuestionResponse.setTestQuestion((TestQuestionDto) ObjectMapperUtil.mapToDTOSingle(savedTestQuestion, TestQuestionDto.class));
        testQuestionResponse.setMessage("Pomyślnie utworzono pytanie testowe");

        return testQuestionResponse;
    }

    public void editTestQuestion(Long levelID, Long testQuestionID, TestQuestionRequest testQuestionRequest) {
        TestQuestion testQuestion = findById(testQuestionID);
        if (!testQuestion.getAnswer().equals(testQuestionRequest.getAnswer())) {
            checkIfTestAnswerIsUsed(levelID, testQuestionRequest);
        }

        testQuestion.setQuestion(testQuestionRequest.getQuestion());
        testQuestion.setAnswer(testQuestionRequest.getAnswer());
        testQuestion.setImage(imageService.findByName(testQuestionRequest.getImageName()));

        testQuestionRepository.save(testQuestion);
    }

    public void checkIfTestAnswerIsUsed(Long levelID, TestQuestionRequest testQuestionRequest) {
        Level level = levelService.findById(levelID);
        List<TestQuestion> testQuestionList = level.getTestQuestions();

        Optional checkIfExerciseExists = testQuestionList.stream()
                .filter(exercise -> exercise.getAnswer().equals(testQuestionRequest.getAnswer()))
                .findFirst();

        if (checkIfExerciseExists.isPresent()) {
            throw new TestQuestionAlreadyExistsException();
        }
    }

    public TestQuestion findById(long id) {
        TestQuestion testQuestion = testQuestionRepository.findById(id)
                .orElseThrow(TestQuestionNotFoundException::new);

        return testQuestion;
    }
}

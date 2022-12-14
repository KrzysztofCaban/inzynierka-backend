package acc.inzynierka.services.mobileapp;

import acc.inzynierka.exception.level.LevelNotFoundException;
import acc.inzynierka.exception.user.UserNotFoundException;
import acc.inzynierka.models.Level;
import acc.inzynierka.models.Result;
import acc.inzynierka.models.TestQuestion;
import acc.inzynierka.modelsDTO.mobileapp.TestQuestionMobileDto;
import acc.inzynierka.payload.request.mobile.UserResultRequest;
import acc.inzynierka.payload.response.MessageResponse;
import acc.inzynierka.repository.LevelRepository;
import acc.inzynierka.repository.ResultRepository;
import acc.inzynierka.repository.UserRepository;
import acc.inzynierka.utils.ObjectMapperUtil;
import acc.inzynierka.utils.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TestQuestionMobileService {
    @Autowired
    LevelMobileService levelMobileService;

    @Autowired
    ResultRepository resultRepository;
    @Autowired
    LevelRepository levelRepository;
    @Autowired
    private UserRepository userRepository;

    public List<TestQuestionMobileDto> getAllTestQuestions(Long levelID) {
        Level level = levelMobileService.findById(levelID);
        List<TestQuestion> testQuestionList = level.getTestQuestions();
        Collections.shuffle(testQuestionList);

        testQuestionList = testQuestionList.stream().limit(15).collect(Collectors.toList());
        return ObjectMapperUtil.mapToDTO(testQuestionList, TestQuestionMobileDto.class);
    }


    public MessageResponse addUserResult(UserResultRequest userResultRequest) {
        Long userid = UserUtil.getUser();
        if (resultRepository.existsByUser_IdAndLevel_Id(userid, userResultRequest.getLevelID())) {
            Result result = resultRepository.findByUser_IdAndLevel_Id(userid, userResultRequest.getLevelID());
            if (result.getValue() < userResultRequest.getUserResult()) {
                result.setValue(userResultRequest.getUserResult());
                result.setDate(Timestamp.from(Instant.now()));
                resultRepository.save(result);
            }
        } else {
            Result result = new Result();
            result.setDate(Timestamp.from(Instant.now()));
            result.setUser(userRepository.findById(userid)
                    .orElseThrow(UserNotFoundException::new));
            result.setLevel(levelRepository.findById(userResultRequest.getLevelID())
                    .orElseThrow(LevelNotFoundException::new));
            result.setValue(userResultRequest.getUserResult());
            System.out.println(resultRepository.save(result).getValue());
        }
        return new MessageResponse("Pomyślnie zapisano wynik");
    }
}

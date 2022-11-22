package acc.inzynierka.services.mobileapp;

import acc.inzynierka.models.Level;
import acc.inzynierka.models.TestQuestion;
import acc.inzynierka.modelsDTO.mobileapp.TestQuestionMobileDto;
import acc.inzynierka.utils.ObjectMapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class TestQuestionMobileService {
    @Autowired
    LevelMobileService levelMobileService;

    public List<TestQuestionMobileDto> getAllTestQuestions(Long levelID) {
        Level level = levelMobileService.findById(levelID);
        List<TestQuestion> testQuestionList = level.getTestQuestions();

        List<TestQuestionMobileDto> testQuestionMobileDtoList = ObjectMapperUtil.mapToDTO(testQuestionList, TestQuestionMobileDto.class);
        Collections.shuffle(testQuestionMobileDtoList);

        return testQuestionMobileDtoList;
    }
}

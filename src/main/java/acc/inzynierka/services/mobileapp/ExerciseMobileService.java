package acc.inzynierka.services.mobileapp;

import acc.inzynierka.models.Exercise;
import acc.inzynierka.models.Level;
import acc.inzynierka.modelsDTO.mobileapp.ExerciseMobileDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExerciseMobileService {

    @Autowired
    private LevelMobileService levelMobileService;

    public List<ExerciseMobileDto> getAllExercises(Long levelID) {
        Level level = levelMobileService.findById(levelID);
        List<Exercise> exerciseList = level.getExercises();

        List<ExerciseMobileDto> exerciseMobileDtos = exerciseList.stream()
                .map(exercise -> {
                    List<String> options = new ArrayList<>();
                    options.add(exercise.getAnswer());
                    options.add(exercise.getBad_answer1());
                    options.add(exercise.getBad_answer2());
                    options.add(exercise.getBad_answer3());

                    Collections.shuffle(options);

                    return new ExerciseMobileDto(
                            exercise.getQuestion(),
                            exercise.getAnswer(),
                            options,
                            exercise.getImage().getUrl());
                })
                .collect(Collectors.toList());
        Collections.shuffle(exerciseMobileDtos);

        exerciseMobileDtos = exerciseMobileDtos.stream().limit(15).collect(Collectors.toList());

        return exerciseMobileDtos;
    }
}

package acc.inzynierka.utils;

import acc.inzynierka.models.*;
import acc.inzynierka.models.enums.ERole;
import acc.inzynierka.models.enums.EStatus;
import acc.inzynierka.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class DataLoader implements ApplicationRunner {

    RoleRepository roleRepository;
    CategoryRepository categoryRepository;
    StatusRepository statusRepository;
    CourseRepository courseRepository;
    LevelRepository levelRepository;
    ExerciseRepository exerciseRepository;
    TestQuestionRepository testQuestionRepository;
    ImageRepository imageRepository;
    FlashcardRepository flashcardRepository;
    UserCourseRepository userCourseRepository;
    ResultRepository resultRepository;
    FollowedRepository followedRepository;
    private UserRepository userRepository;

    @Autowired
    public DataLoader(UserRepository userRepository,
                      RoleRepository roleRepository,
                      CategoryRepository categoryRepository,
                      StatusRepository statusRepository,
                      CourseRepository courseRepository,
                      LevelRepository levelRepository,
                      ExerciseRepository exerciseRepository,
                      TestQuestionRepository testQuestionRepository,
                      ImageRepository imageRepository,
                      FlashcardRepository flashcardRepository,
                      UserCourseRepository userCourseRepository,
                      ResultRepository resultRepository,
                      FollowedRepository followedRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.categoryRepository = categoryRepository;
        this.statusRepository = statusRepository;
        this.courseRepository = courseRepository;
        this.levelRepository = levelRepository;
        this.exerciseRepository = exerciseRepository;
        this.testQuestionRepository = testQuestionRepository;
        this.imageRepository = imageRepository;
        this.flashcardRepository = flashcardRepository;
        this.userCourseRepository = userCourseRepository;
        this.resultRepository = resultRepository;
        this.followedRepository = followedRepository;
    }

    public void run(ApplicationArguments args) {
        if (roleRepository.findByName(ERole.ROLE_USER).isEmpty()) {
            insertRequiredData();
            if (true) {
                insertTestData();
            }
        }
    }

    private void insertRequiredData() {

        Role role = new Role();
        role.setName(ERole.ROLE_USER);

        Role role2 = new Role();
        role2.setName(ERole.ROLE_ADMIN);

        Role role3 = new Role();
        role3.setName(ERole.ROLE_SUPERADMIN);

        List<Role> roles = new ArrayList<>();
        roles.add(role);
        roles.add(role2);
        roles.add(role3);

        roleRepository.saveAll(roles);

        Status status = new Status();
        status.setName(EStatus.STATUS_ACTIVE);
        Status status2 = new Status();
        status2.setName(EStatus.STATUS_SUSPENDED);
        Status status3 = new Status();
        status3.setName(EStatus.STATUS_ARCHIVED);

        List<Status> statuses = new ArrayList<>();

        statuses.add(status);
        statuses.add(status2);
        statuses.add(status3);

        statusRepository.saveAll(statuses);
    }

    private void insertTestData() {
        User user = new User("test1234", "maill@abc.abc", "$2a$10$9SEKVUXwNa8NGnpYBjQU/eIuZE2olkYaRC58S0ZqXJci0y/xgfiNO");
        User user2 = new User("test123", "mail@abc.abc", "$2a$10$5.rWkw8sLxlhCY0OTk9r5ujUtyuO63lOmWhTB5Smxs/Cdj9c6vkHy");
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(roleRepository.findByName(ERole.ROLE_ADMIN).get());
        user.setRoles(roleSet);
        userRepository.save(user);
        roleSet.clear();
        roleSet.add(roleRepository.findByName(ERole.ROLE_USER).get());
        user2.setRoles(roleSet);
        userRepository.save(user2);

        Category category = new Category();
        category.setName("KAT1");

        categoryRepository.save(category);

        Course course = new Course();
        course.setAuthor(user);
        course.setName("test");
        course.setCategory(category);
        course.setCreated(Timestamp.from(Instant.now()));
        course.setStatus(statusRepository.findById(1L).get());
        course.setModified(Timestamp.from(Instant.now()));

        courseRepository.save(course);

        Level level = new Level();
        level.setStatus(statusRepository.findById(1L).get());
        level.setName("test Level");
        level.setCourse(course);
        level.setDifficulty(1);

        levelRepository.save(level);

        Image image = new Image();
        image.setName("test Image");
        image.setUrl("https://inzblobstorage.blob.core.windows.net/images/37073d54-81fe-4ae6-b623-1a0628903a07.jfif");
        image.setCategory(category);

        imageRepository.save(image);

        Flashcard flashcard = new Flashcard();
        flashcard.setExpDescription("testExpDesc");
        flashcard.setExpOriginal("testExpOrg");
        flashcard.setExpTranslation("testExpTrans");
        flashcard.setLevel(level);
        flashcard.setImage(image);

        flashcardRepository.save(flashcard);

        Exercise exercise = new Exercise();
        exercise.setImage(image);
        exercise.setLevel(level);
        exercise.setBad_answer1("testBadAnswer1");
        exercise.setBad_answer2("testBadAnswer1");
        exercise.setBad_answer3("testBadAnswer1");
        exercise.setAnswer("testExpresion");
        exercise.setQuestion("test question");

        exerciseRepository.save(exercise);

        TestQuestion testQuestion = new TestQuestion();
        testQuestion.setQuestion("testQuestion");
        testQuestion.setImage(image);
        testQuestion.setLevel(level);
        testQuestion.setAnswer("testAnswer");

        testQuestionRepository.save(testQuestion);


        UserCourse userCourse = new UserCourse();
        userCourse.setCourse(course);
        userCourse.setUser(user2);
        userCourse.setJoinDate(Timestamp.from(Instant.now()));

        userCourseRepository.save(userCourse);

        Result result = new Result();
        result.setUser(user2);
        result.setLevel(level);
        result.setValue(1);
        result.setDate(Timestamp.from(Instant.now()));

        resultRepository.save(result);

        Followed followed = new Followed();

        followed.setUser(user2);
        followed.setFollowedUser(user);
        followedRepository.save(followed);
    }
}

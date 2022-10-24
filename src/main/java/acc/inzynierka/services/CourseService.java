package acc.inzynierka.services;

import acc.inzynierka.models.Course;
import acc.inzynierka.models.User;
import acc.inzynierka.modelsDTO.CourseDto;
import acc.inzynierka.repository.CourseRepository;
import acc.inzynierka.repository.UserRepository;
import acc.inzynierka.utils.objectMapperUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired UserRepository userRepository;

    ModelMapper mMapper = new ModelMapper();
    public List<CourseDto> getAllCourses(){
        return objectMapperUtil.mapToDTO(courseRepository.findAll(), CourseDto.class);
    }

    public List<CourseDto> getAllAdminCourses(User admin) {
        return objectMapperUtil.mapToDTO(courseRepository.findAllByAuthor(admin), CourseDto.class);
    }

    public CourseDto getCourseByName(String name){
        Optional course = courseRepository.findByName(name);
        if(course.isEmpty())
            return null;

        return (CourseDto) objectMapperUtil.mapToDTOSingle(course.get(), CourseDto.class);
    }

    public boolean deleteCourseById(Long id){
        Optional course = courseRepository.findById(id);
        if(course.isEmpty())
          return false;

        courseRepository.delete((Course) course.get());
        return true;
    }

    public boolean deleteCourseByName(String name){
        Optional course = courseRepository.findByName(name);
        if(course.isEmpty())
            return false;

        courseRepository.deleteByName((String) course.get());
        return true;
    }
}

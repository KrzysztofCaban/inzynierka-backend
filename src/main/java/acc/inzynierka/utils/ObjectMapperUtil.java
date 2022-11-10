package acc.inzynierka.utils;

import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public class ObjectMapperUtil {
    public static <T> List<T> mapToDTO(List<?> oList, Class<T> destClass) {
        ModelMapper modelMapper = new ModelMapper();
        return oList.stream().map(object -> modelMapper.map(object, destClass)).collect(Collectors.toList());
    }

    public static <T> Object mapToDTOSingle(Object sourcClass, Class<T> destClass) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(sourcClass, destClass);
    }
}

package acc.inzynierka.services;

import acc.inzynierka.modelsDTO.StatusDto;
import acc.inzynierka.repository.StatusRepository;
import acc.inzynierka.utils.ObjectMapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatusService {

    @Autowired
    StatusRepository statusRepository;

    public List<StatusDto> getStatuses(){
        return ObjectMapperUtil.mapToDTO(statusRepository.findAll(), StatusDto.class);
    }
}

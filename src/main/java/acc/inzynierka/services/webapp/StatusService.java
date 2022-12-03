package acc.inzynierka.services.webapp;

import acc.inzynierka.exception.status.StatusNotFoundException;
import acc.inzynierka.models.Status;
import acc.inzynierka.models.enums.EStatus;
import acc.inzynierka.modelsDTO.webapp.StatusDto;
import acc.inzynierka.repository.StatusRepository;
import acc.inzynierka.utils.ObjectMapperUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatusService {

    final
    StatusRepository statusRepository;

    public StatusService(StatusRepository statusRepository) {
        this.statusRepository = statusRepository;
    }

    public List<StatusDto> getStatuses() {
        return ObjectMapperUtil.mapToDTO(statusRepository.findAll(), StatusDto.class);
    }


    public Status findByName(EStatus name) {
        Status status = statusRepository.findByName(name)
                .orElseThrow(StatusNotFoundException::new);

        return status;
    }
}

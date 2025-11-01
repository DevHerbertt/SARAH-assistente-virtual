package com.sarah.service.memories;

import com.sarah.entity.MemoriesCommand;
import com.sarah.repository.MemoriesCommandRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Log4j2
@Service
@Transactional
public class MemoriesCommandService {
    @Autowired
    private MemoriesCommandRepository memoriesCommandRepository;

    public MemoriesCommand saveMemories(String type){
        MemoriesCommand memories = new MemoriesCommand();

        memories.setType(type);
        memories.setDateTimeOfCreate(LocalDateTime.now());
        memories.setNumberRepetition(0);

       return memoriesCommandRepository.save(memories);
    }

    public MemoriesCommand updateRepetition(String command){
        log.info("trying updateRepetition memories of command of " + command);
        MemoriesCommand foundCommand = memoriesCommandRepository.findByType(command);
        if (foundCommand == null){
            log.debug("command not found");
        }

        foundCommand.setDateTimeUpdate(LocalDateTime.now());
        foundCommand.setNumberRepetition(foundCommand.getNumberRepetition() + 1);

       return memoriesCommandRepository.save(foundCommand);
    }
}

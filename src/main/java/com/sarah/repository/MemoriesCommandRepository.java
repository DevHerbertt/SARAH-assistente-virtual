package com.sarah.repository;

import com.sarah.entity.MemoriesCommand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemoriesCommandRepository extends JpaRepository<MemoriesCommand, Long> {

    MemoriesCommand findByType(String command);
}

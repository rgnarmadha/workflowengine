package com.workflow.system.repository;

import com.workflow.system.entity.StageInputOutput;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StageInputOutputRepository extends JpaRepository<StageInputOutput, Long> {
    List<StageInputOutput> findByWorkflowStageId(Long workflowStageId);
    List<StageInputOutput> findByWorkflowStageIdAndType(Long workflowStageId, StageInputOutput.IOType type);
}

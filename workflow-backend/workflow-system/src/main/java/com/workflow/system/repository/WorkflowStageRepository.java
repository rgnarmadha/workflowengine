package com.workflow.system.repository;

import com.workflow.system.entity.WorkflowStage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkflowStageRepository extends JpaRepository<WorkflowStage, Long> {
    List<WorkflowStage> findByWorkflowTemplateIdOrderByStageNumber(Long workflowTemplateId);
}

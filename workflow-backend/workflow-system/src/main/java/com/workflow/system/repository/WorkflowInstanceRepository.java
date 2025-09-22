package com.workflow.system.repository;

import com.workflow.system.entity.WorkflowInstance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WorkflowInstanceRepository extends JpaRepository<WorkflowInstance, Long> {
    Optional<WorkflowInstance> findByProcessInstanceId(String processInstanceId);
    List<WorkflowInstance> findByWorkflowTemplateId(Long workflowTemplateId);
    List<WorkflowInstance> findByStatus(WorkflowInstance.InstanceStatus status);
}

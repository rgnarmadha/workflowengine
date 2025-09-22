package com.workflow.system.service;

import com.workflow.system.dto.WorkflowTemplateDto;
import com.workflow.system.entity.WorkflowInstance;
import com.workflow.system.entity.WorkflowTemplate;
import com.workflow.system.repository.WorkflowInstanceRepository;
import com.workflow.system.repository.WorkflowTemplateRepository;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class WorkflowService {

    @Autowired
    private WorkflowTemplateRepository workflowTemplateRepository;
    
    @Autowired
    private WorkflowInstanceRepository workflowInstanceRepository;
    
    @Autowired
    private ProcessEngine processEngine;
    
    @Autowired
    private RuntimeService runtimeService;
    
    @Autowired
    private TaskService taskService;
    
    @Autowired
    private WorkflowMapperService mapperService;

    public List<WorkflowTemplateDto> getAllTemplates() {
        List<WorkflowTemplate> templates = workflowTemplateRepository.findAll();
        return mapperService.toWorkflowTemplateDtoList(templates);
    }

    public WorkflowTemplateDto createTemplate(WorkflowTemplateDto templateDto) {
        WorkflowTemplate template = mapperService.toWorkflowTemplate(templateDto);
        template.setProcessDefinitionKey("five-stage-workflow");
        
        for (int i = 0; i < template.getStages().size(); i++) {
            template.getStages().get(i).setStageNumber(i + 1);
            template.getStages().get(i).setTaskDefinitionKey("Stage" + (i + 1));
            template.getStages().get(i).setWorkflowTemplate(template);
        }
        
        WorkflowTemplate savedTemplate = workflowTemplateRepository.save(template);
        return mapperService.toWorkflowTemplateDto(savedTemplate);
    }

    public Map<String, Object> startWorkflow(Long templateId, Map<String, Object> variables) {
        Optional<WorkflowTemplate> templateOpt = workflowTemplateRepository.findById(templateId);
        if (templateOpt.isEmpty()) {
            throw new RuntimeException("Workflow template not found");
        }
        
        WorkflowTemplate template = templateOpt.get();
        
        Map<String, Object> processVariables = new HashMap<>(variables);
        processVariables.put("workflowTemplateId", templateId);
        
        for (int i = 0; i < template.getStages().size(); i++) {
            processVariables.put("assignee" + (i + 1), template.getStages().get(i).getAssigneeExpression());
        }
        
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(
            template.getProcessDefinitionKey(), 
            processVariables
        );
        
        WorkflowInstance workflowInstance = new WorkflowInstance();
        workflowInstance.setProcessInstanceId(processInstance.getId());
        workflowInstance.setBusinessKey(processInstance.getBusinessKey());
        workflowInstance.setStatus(WorkflowInstance.InstanceStatus.ACTIVE);
        workflowInstance.setStartedBy("system");
        workflowInstance.setWorkflowTemplate(template);
        
        workflowInstanceRepository.save(workflowInstance);
        
        Map<String, Object> result = new HashMap<>();
        result.put("processInstanceId", processInstance.getId());
        result.put("businessKey", processInstance.getBusinessKey());
        result.put("workflowInstanceId", workflowInstance.getId());
        
        return result;
    }

    public Map<String, Object> getWorkflowInstance(String processInstanceId) {
        Optional<WorkflowInstance> instanceOpt = workflowInstanceRepository.findByProcessInstanceId(processInstanceId);
        if (instanceOpt.isEmpty()) {
            throw new RuntimeException("Workflow instance not found");
        }
        
        WorkflowInstance instance = instanceOpt.get();
        List<Task> tasks = taskService.createTaskQuery()
            .processInstanceId(processInstanceId)
            .list();
        
        Map<String, Object> result = new HashMap<>();
        result.put("workflowInstance", instance);
        result.put("activeTasks", tasks);
        result.put("processVariables", runtimeService.getVariables(processInstanceId));
        
        return result;
    }

    public void completeTask(String taskId, Map<String, Object> variables) {
        taskService.complete(taskId, variables);
        
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        if (task != null) {
            String processInstanceId = task.getProcessInstanceId();
            
            List<Task> remainingTasks = taskService.createTaskQuery()
                .processInstanceId(processInstanceId)
                .list();
            
            if (remainingTasks.isEmpty()) {
                Optional<WorkflowInstance> instanceOpt = workflowInstanceRepository.findByProcessInstanceId(processInstanceId);
                if (instanceOpt.isPresent()) {
                    WorkflowInstance instance = instanceOpt.get();
                    instance.setStatus(WorkflowInstance.InstanceStatus.COMPLETED);
                    instance.setEndedAt(LocalDateTime.now());
                    workflowInstanceRepository.save(instance);
                }
            }
        }
    }

    public WorkflowTemplateDto getTemplate(Long id) {
        Optional<WorkflowTemplate> templateOpt = workflowTemplateRepository.findById(id);
        if (templateOpt.isEmpty()) {
            throw new RuntimeException("Workflow template not found");
        }
        return mapperService.toWorkflowTemplateDto(templateOpt.get());
    }

    public WorkflowTemplateDto updateTemplate(Long id, WorkflowTemplateDto templateDto) {
        Optional<WorkflowTemplate> existingOpt = workflowTemplateRepository.findById(id);
        if (existingOpt.isEmpty()) {
            throw new RuntimeException("Workflow template not found");
        }
        
        WorkflowTemplate existing = existingOpt.get();
        existing.setName(templateDto.getName());
        existing.setDescription(templateDto.getDescription());
        
        WorkflowTemplate savedTemplate = workflowTemplateRepository.save(existing);
        return mapperService.toWorkflowTemplateDto(savedTemplate);
    }

    public void deleteTemplate(Long id) {
        workflowTemplateRepository.deleteById(id);
    }
}

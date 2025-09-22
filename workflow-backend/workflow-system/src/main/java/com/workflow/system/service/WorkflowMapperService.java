package com.workflow.system.service;

import com.workflow.system.dto.StageInputOutputDto;
import com.workflow.system.dto.WorkflowStageDto;
import com.workflow.system.dto.WorkflowTemplateDto;
import com.workflow.system.entity.StageInputOutput;
import com.workflow.system.entity.WorkflowStage;
import com.workflow.system.entity.WorkflowTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WorkflowMapperService {

    public WorkflowTemplateDto toWorkflowTemplateDto(WorkflowTemplate template) {
        WorkflowTemplateDto dto = new WorkflowTemplateDto();
        dto.setId(template.getId());
        dto.setName(template.getName());
        dto.setDescription(template.getDescription());
        dto.setProcessDefinitionKey(template.getProcessDefinitionKey());
        dto.setCreatedAt(template.getCreatedAt());
        dto.setUpdatedAt(template.getUpdatedAt());
        
        if (template.getStages() != null) {
            dto.setStages(template.getStages().stream()
                .map(this::toWorkflowStageDto)
                .collect(Collectors.toList()));
        }
        
        return dto;
    }

    public WorkflowTemplate toWorkflowTemplate(WorkflowTemplateDto dto) {
        WorkflowTemplate template = new WorkflowTemplate();
        template.setId(dto.getId());
        template.setName(dto.getName());
        template.setDescription(dto.getDescription());
        template.setProcessDefinitionKey(dto.getProcessDefinitionKey());
        
        if (dto.getStages() != null) {
            template.setStages(dto.getStages().stream()
                .map(this::toWorkflowStage)
                .collect(Collectors.toList()));
        }
        
        return template;
    }

    public WorkflowStageDto toWorkflowStageDto(WorkflowStage stage) {
        WorkflowStageDto dto = new WorkflowStageDto();
        dto.setId(stage.getId());
        dto.setStageNumber(stage.getStageNumber());
        dto.setName(stage.getName());
        dto.setDescription(stage.getDescription());
        dto.setAssigneeExpression(stage.getAssigneeExpression());
        dto.setDueDateExpression(stage.getDueDateExpression());
        dto.setTaskDefinitionKey(stage.getTaskDefinitionKey());
        dto.setCreatedAt(stage.getCreatedAt());
        dto.setUpdatedAt(stage.getUpdatedAt());
        
        if (stage.getInputsOutputs() != null) {
            dto.setInputsOutputs(stage.getInputsOutputs().stream()
                .map(this::toStageInputOutputDto)
                .collect(Collectors.toList()));
        }
        
        return dto;
    }

    public WorkflowStage toWorkflowStage(WorkflowStageDto dto) {
        WorkflowStage stage = new WorkflowStage();
        stage.setId(dto.getId());
        stage.setStageNumber(dto.getStageNumber());
        stage.setName(dto.getName());
        stage.setDescription(dto.getDescription());
        stage.setAssigneeExpression(dto.getAssigneeExpression());
        stage.setDueDateExpression(dto.getDueDateExpression());
        stage.setTaskDefinitionKey(dto.getTaskDefinitionKey());
        
        if (dto.getInputsOutputs() != null) {
            stage.setInputsOutputs(dto.getInputsOutputs().stream()
                .map(this::toStageInputOutput)
                .collect(Collectors.toList()));
        }
        
        return stage;
    }

    public StageInputOutputDto toStageInputOutputDto(StageInputOutput inputOutput) {
        StageInputOutputDto dto = new StageInputOutputDto();
        dto.setId(inputOutput.getId());
        dto.setName(inputOutput.getName());
        dto.setType(inputOutput.getType());
        dto.setDataType(inputOutput.getDataType());
        dto.setRequired(inputOutput.getRequired());
        dto.setDescription(inputOutput.getDescription());
        dto.setDefaultValue(inputOutput.getDefaultValue());
        dto.setValidationRules(inputOutput.getValidationRules());
        dto.setCreatedAt(inputOutput.getCreatedAt());
        dto.setUpdatedAt(inputOutput.getUpdatedAt());
        
        return dto;
    }

    public StageInputOutput toStageInputOutput(StageInputOutputDto dto) {
        StageInputOutput inputOutput = new StageInputOutput();
        inputOutput.setId(dto.getId());
        inputOutput.setName(dto.getName());
        inputOutput.setType(dto.getType());
        inputOutput.setDataType(dto.getDataType());
        inputOutput.setRequired(dto.getRequired());
        inputOutput.setDescription(dto.getDescription());
        inputOutput.setDefaultValue(dto.getDefaultValue());
        inputOutput.setValidationRules(dto.getValidationRules());
        
        return inputOutput;
    }

    public List<WorkflowTemplateDto> toWorkflowTemplateDtoList(List<WorkflowTemplate> templates) {
        return templates.stream()
            .map(this::toWorkflowTemplateDto)
            .collect(Collectors.toList());
    }
}

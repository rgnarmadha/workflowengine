package com.workflow.system.dto;

import java.time.LocalDateTime;
import java.util.List;

public class WorkflowStageDto {
    private Long id;
    private Integer stageNumber;
    private String name;
    private String description;
    private String assigneeExpression;
    private String dueDateExpression;
    private String taskDefinitionKey;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<StageInputOutputDto> inputsOutputs;
    
    public WorkflowStageDto() {}
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Integer getStageNumber() {
        return stageNumber;
    }
    
    public void setStageNumber(Integer stageNumber) {
        this.stageNumber = stageNumber;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getAssigneeExpression() {
        return assigneeExpression;
    }
    
    public void setAssigneeExpression(String assigneeExpression) {
        this.assigneeExpression = assigneeExpression;
    }
    
    public String getDueDateExpression() {
        return dueDateExpression;
    }
    
    public void setDueDateExpression(String dueDateExpression) {
        this.dueDateExpression = dueDateExpression;
    }
    
    public String getTaskDefinitionKey() {
        return taskDefinitionKey;
    }
    
    public void setTaskDefinitionKey(String taskDefinitionKey) {
        this.taskDefinitionKey = taskDefinitionKey;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    public List<StageInputOutputDto> getInputsOutputs() {
        return inputsOutputs;
    }
    
    public void setInputsOutputs(List<StageInputOutputDto> inputsOutputs) {
        this.inputsOutputs = inputsOutputs;
    }
}

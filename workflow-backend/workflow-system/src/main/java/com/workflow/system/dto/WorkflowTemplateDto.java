package com.workflow.system.dto;

import java.time.LocalDateTime;
import java.util.List;

public class WorkflowTemplateDto {
    private Long id;
    private String name;
    private String description;
    private String processDefinitionKey;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<WorkflowStageDto> stages;
    
    public WorkflowTemplateDto() {}
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
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
    
    public String getProcessDefinitionKey() {
        return processDefinitionKey;
    }
    
    public void setProcessDefinitionKey(String processDefinitionKey) {
        this.processDefinitionKey = processDefinitionKey;
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
    
    public List<WorkflowStageDto> getStages() {
        return stages;
    }
    
    public void setStages(List<WorkflowStageDto> stages) {
        this.stages = stages;
    }
}

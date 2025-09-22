package com.workflow.system.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "workflow_stages")
public class WorkflowStage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "stage_number", nullable = false)
    private Integer stageNumber;
    
    @Column(nullable = false)
    private String name;
    
    @Column(length = 1000)
    private String description;
    
    @Column(name = "assignee_expression")
    private String assigneeExpression;
    
    @Column(name = "due_date_expression")
    private String dueDateExpression;
    
    @Column(name = "task_definition_key")
    private String taskDefinitionKey;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workflow_template_id")
    private WorkflowTemplate workflowTemplate;
    
    @OneToMany(mappedBy = "workflowStage", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<StageInputOutput> inputsOutputs;
    
    public WorkflowStage() {}
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
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
    
    public WorkflowTemplate getWorkflowTemplate() {
        return workflowTemplate;
    }
    
    public void setWorkflowTemplate(WorkflowTemplate workflowTemplate) {
        this.workflowTemplate = workflowTemplate;
    }
    
    public List<StageInputOutput> getInputsOutputs() {
        return inputsOutputs;
    }
    
    public void setInputsOutputs(List<StageInputOutput> inputsOutputs) {
        this.inputsOutputs = inputsOutputs;
    }
}

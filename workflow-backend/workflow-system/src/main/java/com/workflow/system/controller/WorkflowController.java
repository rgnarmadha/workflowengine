package com.workflow.system.controller;

import com.workflow.system.dto.WorkflowTemplateDto;
import com.workflow.system.service.WorkflowService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/workflows")
@Tag(name = "Workflow Management", description = "APIs for managing configurable 5-stage workflows")
@CrossOrigin(origins = "*")
public class WorkflowController {

    @Autowired
    private WorkflowService workflowService;

    @GetMapping("/templates")
    @Operation(summary = "Get all workflow templates", description = "Retrieve all configured workflow templates")
    public ResponseEntity<List<WorkflowTemplateDto>> getAllTemplates() {
        return ResponseEntity.ok(workflowService.getAllTemplates());
    }

    @GetMapping("/templates/{id}")
    @Operation(summary = "Get workflow template by ID", description = "Retrieve a specific workflow template by its ID")
    public ResponseEntity<WorkflowTemplateDto> getTemplate(@PathVariable Long id) {
        return ResponseEntity.ok(workflowService.getTemplate(id));
    }

    @PostMapping("/templates")
    @Operation(summary = "Create workflow template", description = "Create a new configurable workflow template")
    public ResponseEntity<WorkflowTemplateDto> createTemplate(@RequestBody WorkflowTemplateDto template) {
        return ResponseEntity.ok(workflowService.createTemplate(template));
    }

    @PutMapping("/templates/{id}")
    @Operation(summary = "Update workflow template", description = "Update an existing workflow template")
    public ResponseEntity<WorkflowTemplateDto> updateTemplate(@PathVariable Long id, @RequestBody WorkflowTemplateDto template) {
        return ResponseEntity.ok(workflowService.updateTemplate(id, template));
    }

    @DeleteMapping("/templates/{id}")
    @Operation(summary = "Delete workflow template", description = "Delete a workflow template")
    public ResponseEntity<Void> deleteTemplate(@PathVariable Long id) {
        workflowService.deleteTemplate(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/instances")
    @Operation(summary = "Start workflow instance", description = "Start a new workflow instance from a template")
    public ResponseEntity<Map<String, Object>> startWorkflow(
            @RequestParam Long templateId,
            @RequestBody Map<String, Object> variables) {
        return ResponseEntity.ok(workflowService.startWorkflow(templateId, variables));
    }

    @GetMapping("/instances/{instanceId}")
    @Operation(summary = "Get workflow instance", description = "Get details of a specific workflow instance")
    public ResponseEntity<Map<String, Object>> getWorkflowInstance(@PathVariable String instanceId) {
        return ResponseEntity.ok(workflowService.getWorkflowInstance(instanceId));
    }

    @PostMapping("/tasks/{taskId}/complete")
    @Operation(summary = "Complete workflow task", description = "Complete a specific task in the workflow")
    public ResponseEntity<Void> completeTask(
            @PathVariable String taskId,
            @RequestBody Map<String, Object> variables) {
        workflowService.completeTask(taskId, variables);
        return ResponseEntity.ok().build();
    }
}

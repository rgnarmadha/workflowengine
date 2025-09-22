import { useState, useEffect } from 'react'
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from '@/components/ui/card'
import { Button } from '@/components/ui/button'
import { Input } from '@/components/ui/input'
import { Textarea } from '@/components/ui/textarea'
import { Badge } from '@/components/ui/badge'
import { Plus, Play, Settings, Database } from 'lucide-react'
import './App.css'

interface WorkflowTemplate {
  id: number
  name: string
  description: string
  processDefinitionKey: string
  createdAt: string
  updatedAt: string
  stages: WorkflowStage[]
}

interface WorkflowStage {
  id: number
  stageNumber: number
  name: string
  description: string
  assigneeExpression: string
  dueDateExpression: string
  taskDefinitionKey: string
  inputsOutputs: StageInputOutput[]
}

interface StageInputOutput {
  id: number
  name: string
  type: 'INPUT' | 'OUTPUT'
  dataType: string
  required: boolean
  description: string
  defaultValue: string
  validationRules: string
}

function App() {
  const [templates, setTemplates] = useState<WorkflowTemplate[]>([])
  const [loading, setLoading] = useState(true)
  const [showCreateForm, setShowCreateForm] = useState(false)
  const [newTemplate, setNewTemplate] = useState({
    name: '',
    description: '',
    stages: Array.from({ length: 5 }, (_, i) => ({
      stageNumber: i + 1,
      name: `Stage ${i + 1}`,
      description: '',
      assigneeExpression: 'admin',
      dueDateExpression: '',
      inputsOutputs: []
    }))
  })

  const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080'

  useEffect(() => {
    fetchTemplates()
  }, [])

  const fetchTemplates = async () => {
    try {
      const response = await fetch(`${API_BASE_URL}/api/workflows/templates`)
      if (response.ok) {
        const data = await response.json()
        setTemplates(data)
      }
    } catch (error) {
      console.error('Error fetching templates:', error)
    } finally {
      setLoading(false)
    }
  }

  const createTemplate = async () => {
    try {
      const response = await fetch(`${API_BASE_URL}/api/workflows/templates`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(newTemplate),
      })
      
      if (response.ok) {
        await fetchTemplates()
        setShowCreateForm(false)
        setNewTemplate({
          name: '',
          description: '',
          stages: Array.from({ length: 5 }, (_, i) => ({
            stageNumber: i + 1,
            name: `Stage ${i + 1}`,
            description: '',
            assigneeExpression: 'admin',
            dueDateExpression: '',
            inputsOutputs: []
          }))
        })
      }
    } catch (error) {
      console.error('Error creating template:', error)
    }
  }

  const startWorkflow = async (templateId: number) => {
    try {
      const response = await fetch(`${API_BASE_URL}/api/workflows/instances?templateId=${templateId}`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({}),
      })
      
      if (response.ok) {
        const result = await response.json()
        alert(`Workflow started! Process Instance ID: ${result.processInstanceId}`)
      }
    } catch (error) {
      console.error('Error starting workflow:', error)
    }
  }

  const updateStage = (stageIndex: number, field: string, value: string) => {
    const updatedStages = [...newTemplate.stages]
    updatedStages[stageIndex] = { ...updatedStages[stageIndex], [field]: value }
    setNewTemplate({ ...newTemplate, stages: updatedStages })
  }

  if (loading) {
    return (
      <div className="min-h-screen bg-gray-50 flex items-center justify-center">
        <div className="text-center">
          <div className="animate-spin rounded-full h-32 w-32 border-b-2 border-blue-600 mx-auto"></div>
          <p className="mt-4 text-gray-600">Loading workflow system...</p>
        </div>
      </div>
    )
  }

  return (
    <div className="min-h-screen bg-gray-50">
      <div className="container mx-auto px-4 py-8">
        <div className="flex items-center justify-between mb-8">
          <div>
            <h1 className="text-3xl font-bold text-gray-900 flex items-center gap-2">
              <Settings className="h-8 w-8 text-blue-600" />
              Workflow System
            </h1>
            <p className="text-gray-600 mt-2">Configurable 5-stage workflow management with Camunda BPM</p>
          </div>
          <div className="flex gap-2">
            <Button 
              onClick={() => window.open(`${API_BASE_URL}/swagger-ui.html`, '_blank')}
              variant="outline"
              className="flex items-center gap-2"
            >
              <Database className="h-4 w-4" />
              API Documentation
            </Button>
            <Button 
              onClick={() => setShowCreateForm(true)}
              className="flex items-center gap-2"
            >
              <Plus className="h-4 w-4" />
              Create Workflow Template
            </Button>
          </div>
        </div>

        {showCreateForm && (
          <Card className="mb-8">
            <CardHeader>
              <CardTitle>Create New Workflow Template</CardTitle>
              <CardDescription>Configure a new 5-stage workflow template</CardDescription>
            </CardHeader>
            <CardContent className="space-y-6">
              <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                <div>
                  <label className="block text-sm font-medium mb-2">Template Name</label>
                  <Input
                    value={newTemplate.name}
                    onChange={(e) => setNewTemplate({ ...newTemplate, name: e.target.value })}
                    placeholder="Enter template name"
                  />
                </div>
                <div>
                  <label className="block text-sm font-medium mb-2">Description</label>
                  <Textarea
                    value={newTemplate.description}
                    onChange={(e) => setNewTemplate({ ...newTemplate, description: e.target.value })}
                    placeholder="Enter template description"
                    rows={3}
                  />
                </div>
              </div>

              <div>
                <h3 className="text-lg font-semibold mb-4">Configure 5 Stages</h3>
                <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
                  {newTemplate.stages.map((stage, index) => (
                    <Card key={index} className="border-2">
                      <CardHeader className="pb-3">
                        <CardTitle className="text-sm">Stage {stage.stageNumber}</CardTitle>
                      </CardHeader>
                      <CardContent className="space-y-3">
                        <div>
                          <label className="block text-xs font-medium mb-1">Stage Name</label>
                          <Input
                            value={stage.name}
                            onChange={(e) => updateStage(index, 'name', e.target.value)}
                            placeholder="Stage name"
                            className="text-sm"
                          />
                        </div>
                        <div>
                          <label className="block text-xs font-medium mb-1">Description</label>
                          <Textarea
                            value={stage.description}
                            onChange={(e) => updateStage(index, 'description', e.target.value)}
                            placeholder="Stage description"
                            rows={2}
                            className="text-sm"
                          />
                        </div>
                        <div>
                          <label className="block text-xs font-medium mb-1">Assignee</label>
                          <Input
                            value={stage.assigneeExpression}
                            onChange={(e) => updateStage(index, 'assigneeExpression', e.target.value)}
                            placeholder="Assignee expression"
                            className="text-sm"
                          />
                        </div>
                      </CardContent>
                    </Card>
                  ))}
                </div>
              </div>

              <div className="flex gap-2">
                <Button onClick={createTemplate}>Create Template</Button>
                <Button variant="outline" onClick={() => setShowCreateForm(false)}>Cancel</Button>
              </div>
            </CardContent>
          </Card>
        )}

        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
          {templates.map((template) => (
            <Card key={template.id} className="hover:shadow-lg transition-shadow">
              <CardHeader>
                <div className="flex items-start justify-between">
                  <div>
                    <CardTitle className="text-lg">{template.name}</CardTitle>
                    <CardDescription className="mt-2">{template.description}</CardDescription>
                  </div>
                  <Badge variant="secondary">5 Stages</Badge>
                </div>
              </CardHeader>
              <CardContent>
                <div className="space-y-3">
                  <div className="text-sm text-gray-600">
                    <strong>Process Key:</strong> {template.processDefinitionKey}
                  </div>
                  <div className="text-sm text-gray-600">
                    <strong>Created:</strong> {new Date(template.createdAt).toLocaleDateString()}
                  </div>
                  
                  {template.stages && template.stages.length > 0 && (
                    <div>
                      <div className="text-sm font-medium mb-2">Stages:</div>
                      <div className="space-y-1">
                        {template.stages.slice(0, 3).map((stage) => (
                          <div key={stage.id} className="text-xs bg-gray-100 rounded px-2 py-1">
                            {stage.stageNumber}. {stage.name}
                          </div>
                        ))}
                        {template.stages.length > 3 && (
                          <div className="text-xs text-gray-500">
                            +{template.stages.length - 3} more stages
                          </div>
                        )}
                      </div>
                    </div>
                  )}
                  
                  <Button 
                    onClick={() => startWorkflow(template.id)}
                    className="w-full flex items-center gap-2"
                  >
                    <Play className="h-4 w-4" />
                    Start Workflow
                  </Button>
                </div>
              </CardContent>
            </Card>
          ))}
        </div>

        {templates.length === 0 && !showCreateForm && (
          <div className="text-center py-12">
            <Settings className="h-16 w-16 text-gray-400 mx-auto mb-4" />
            <h3 className="text-lg font-semibold text-gray-900 mb-2">No Workflow Templates</h3>
            <p className="text-gray-600 mb-4">Create your first configurable 5-stage workflow template to get started.</p>
            <Button onClick={() => setShowCreateForm(true)} className="flex items-center gap-2">
              <Plus className="h-4 w-4" />
              Create Your First Template
            </Button>
          </div>
        )}
      </div>
    </div>
  )
}

export default App

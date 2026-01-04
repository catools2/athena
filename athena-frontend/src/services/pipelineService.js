import api from './api';

const pipelineService = {
  getLastPipeline: (name, number, project, version, environment) => {
    return api.get('/pipeline', {
      params: { name, number, project, version, environment },
    });
  },

  getById: (id) => {
    return api.get(`/pipeline/${id}`);
  },

  getAll: (params) => {
    return api.get('/pipelines', { params });
  },

  create: (pipelineDto) => {
    return api.post('/pipeline', pipelineDto);
  },

  update: (id, pipelineDto) => {
    return api.put(`/pipeline/${id}`, pipelineDto);
  },

  updateEndDate: (pipelineId, endDate) => {
    return api.put('/pipeline', null, {
      params: { pipelineId, date: endDate },
    });
  },

  getExecutions: (pipelineId, params) => {
    return api.get(`/pipeline/${pipelineId}/executions`, { params });
  },

  getMetrics: (pipelineId) => {
    return api.get(`/pipeline/${pipelineId}/metrics`);
  },
};

export default pipelineService;


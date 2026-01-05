import api from './api';

const testService = {
  getExecutions: (params) => {
    return api.get('/tests/executions', { params });
  },

  getById: (id) => {
    return api.get(`/tests/${id}`);
  },

  getSuites: (params) => {
    return api.get('/tests/suites', { params });
  },

  getResults: (params) => {
    return api.get('/tests/results', { params });
  },

  getByExecutionId: (executionId) => {
    return api.get(`/tests/execution/${executionId}`);
  },

  getTrend: (days) => {
    return api.get('/tests/trend', { params: { days } });
  },

  getStatistics: () => {
    return api.get('/tests/statistics');
  },

  save: (testData) => {
    return api.post('/tests', testData);
  },

  update: (id, testData) => {
    return api.put(`/tests/${id}`, testData);
  },
};

export default testService;


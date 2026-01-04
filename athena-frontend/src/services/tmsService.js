// TMS (Test Management System) Service - API calls for TMS module
import api from './api';

const TMS_API = '/api/tms';

export const tmsService = {
  // Item operations
  getAllItems: (params) => api.get(`${TMS_API}/item`, { params }),
  getItemById: (id) => api.get(`${TMS_API}/item/${id}`),
  getItemByCode: (code) => api.get(`${TMS_API}/item?keyword=${code}`),
  createItem: (data) => api.post(`${TMS_API}/item`, data),
  updateItem: (data) => api.put(`${TMS_API}/item`, data),
  deleteItem: (id) => api.delete(`${TMS_API}/item/${id}`),

  // Test Cycle operations
  getAllCycles: (params) => api.get(`${TMS_API}/cycle`, { params }),
  getCycleById: (id) => api.get(`${TMS_API}/cycle/${id}`),
  getCycleByCode: (code) => api.get(`${TMS_API}/cycle?keyword=${code}`),
  createCycle: (data) => api.post(`${TMS_API}/cycle`, data),
  updateCycle: (data) => api.put(`${TMS_API}/cycle`, data),
  deleteCycle: (id) => api.delete(`${TMS_API}/cycle/${id}`),

  // Test Execution operations
  getAllExecutions: (params) => api.get(`${TMS_API}/execution`, { params }),
  getExecutionById: (id) => api.get(`${TMS_API}/execution/${id}`),
  createExecution: (data) => api.post(`${TMS_API}/execution`, data),
  updateExecution: (data) => api.put(`${TMS_API}/execution`, data),
  deleteExecution: (id) => api.delete(`${TMS_API}/execution/${id}`),

  // Status operations
  getAllStatuses: () => api.get(`${TMS_API}/status`),
  getStatusById: (id) => api.get(`${TMS_API}/status/${id}`),
  getStatusByCode: (code) => api.get(`${TMS_API}/status?keyword=${code}`),
  createStatus: (data) => api.post(`${TMS_API}/status`, data),
  updateStatus: (data) => api.put(`${TMS_API}/status`, data),

  // Priority operations
  getAllPriorities: () => api.get(`${TMS_API}/priority`),
  getPriorityById: (id) => api.get(`${TMS_API}/priority/${id}`),
  getPriorityByCode: (code) => api.get(`${TMS_API}/priority?keyword=${code}`),
  createPriority: (data) => api.post(`${TMS_API}/priority`, data),
  updatePriority: (data) => api.put(`${TMS_API}/priority`, data),

  // Item Type operations
  getAllItemTypes: () => api.get(`${TMS_API}/itemtype`),
  getItemTypeById: (id) => api.get(`${TMS_API}/itemtype/${id}`),
  getItemTypeByCode: (code) => api.get(`${TMS_API}/itemtype?keyword=${code}`),
  createItemType: (data) => api.post(`${TMS_API}/itemtype`, data),
  updateItemType: (data) => api.put(`${TMS_API}/itemtype`, data),

  // Search and filters
  searchItems: (query) => api.get(`${TMS_API}/item/search`, { params: query }),
  getItemsByProject: (projectCode) => api.get(`${TMS_API}/item?project=${projectCode}`),
  getItemsByStatus: (statusCode) => api.get(`${TMS_API}/item?status=${statusCode}`),
  getItemsByType: (typeCode) => api.get(`${TMS_API}/item?type=${typeCode}`),

  // Statistics
  getItemStats: () => api.get(`${TMS_API}/item/stats`),
  getCycleStats: () => api.get(`${TMS_API}/cycle/stats`),
  getExecutionStats: () => api.get(`${TMS_API}/execution/stats`),
  getProjectTestStats: (projectCode) => api.get(`${TMS_API}/project/${projectCode}/stats`),
};

export default tmsService;


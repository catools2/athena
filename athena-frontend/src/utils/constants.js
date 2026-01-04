/**
 * Application Constants
 */

// API Configuration
export const API_TIMEOUT = 30000; // 30 seconds
export const RETRY_ATTEMPTS = 3;
export const RETRY_DELAY = 1000; // 1 second

// Pagination
export const DEFAULT_PAGE_SIZE = 10;
export const PAGE_SIZE_OPTIONS = [10, 20, 50, 100];

// Status Colors and Icons
export const STATUS_COLORS = {
  SUCCESS: 'green',
  FAILED: 'red',
  WARNING: 'orange',
  PENDING: 'blue',
  SKIPPED: 'default',
};

// Quality Grades
export const QUALITY_GRADES = {
  A: { color: '#52c41a', label: 'A' },
  B: { color: '#faad14', label: 'B' },
  C: { color: '#ff7875', label: 'C' },
  D: { color: '#ff4d4f', label: 'D' },
  E: { color: '#eb2f96', label: 'E' },
};

// Chart Colors
export const CHART_COLORS = {
  PRIMARY: '#1890ff',
  SUCCESS: '#52c41a',
  ERROR: '#ff4d4f',
  WARNING: '#faad14',
  INFO: '#1890ff',
  SECONDARY: '#722ed1',
};

// Time Intervals
export const TIME_INTERVALS = {
  MINUTE: 60000,
  HOUR: 3600000,
  DAY: 86400000,
  WEEK: 604800000,
  MONTH: 2592000000,
};

// Refresh Intervals
export const REFRESH_INTERVALS = {
  FAST: 5000, // 5 seconds
  NORMAL: 10000, // 10 seconds
  SLOW: 30000, // 30 seconds
};

// Dashboard Metrics
export const DASHBOARD_METRICS = {
  COVERAGE_THRESHOLD: 80,
  BUG_THRESHOLD: 10,
  VULNERABILITY_THRESHOLD: 0,
  TEST_SUCCESS_THRESHOLD: 90,
};

// Local Storage Keys
export const STORAGE_KEYS = {
  USER: 'athena_user',
  TOKEN: 'athena_token',
  THEME: 'athena_theme',
  PREFERENCES: 'athena_preferences',
};

// Routes
export const ROUTES = {
  HOME: '/',
  PIPELINE: '/pipeline',
  QUALITY: '/quality',
  TESTS: '/tests',
  SPEC: '/spec',
  SETTINGS: '/settings',
  PROFILE: '/profile',
};

// Environment
export const ENV = process.env.REACT_APP_ENV || 'development';
export const API_URL = process.env.REACT_APP_API_URL || 'http://localhost:8080';
export const WS_URL = process.env.REACT_APP_WS_URL || 'ws://localhost:8080';


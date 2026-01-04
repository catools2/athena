import React from 'react';
import { Card, Row, Col, Table, Statistic, Tag } from 'antd';
import { BarChart, Bar, PieChart, Pie, Cell, XAxis, YAxis, CartesianGrid, Tooltip, Legend, ResponsiveContainer } from 'recharts';
import { ArrowUpOutlined, ArrowDownOutlined, CheckCircleOutlined, CloseCircleOutlined } from '@ant-design/icons';

const TestsPage = () => {
  const testTrendData = [
    { suite: 'Unit Tests', passed: 450, failed: 15, skipped: 5 },
    { suite: 'Integration Tests', passed: 320, failed: 12, skipped: 8 },
    { suite: 'E2E Tests', passed: 180, failed: 8, skipped: 4 },
    { suite: 'Performance Tests', passed: 95, failed: 3, skipped: 2 },
  ];

  const testStatusData = [
    { name: 'Passed', value: 1045, color: '#52c41a' },
    { name: 'Failed', value: 38, color: '#ff4d4f' },
    { name: 'Skipped', value: 19, color: '#faad14' },
  ];

  const testExecutionData = [
    {
      key: '1',
      name: 'Unit Tests - Core',
      status: 'Passed',
      total: 450,
      passed: 450,
      failed: 0,
      duration: '15m',
    },
    {
      key: '2',
      name: 'Integration Tests - API',
      status: 'Passed',
      total: 320,
      passed: 318,
      failed: 2,
      duration: '32m',
    },
    {
      key: '3',
      name: 'E2E Tests - Gateway',
      status: 'Failed',
      total: 180,
      passed: 172,
      failed: 8,
      duration: '28m',
    },
  ];

  const columns = [
    { title: 'Test Suite', dataIndex: 'name', key: 'name' },
    {
      title: 'Status',
      dataIndex: 'status',
      key: 'status',
      render: (status) => (
        <Tag color={status === 'Passed' ? 'green' : 'red'}>
          {status === 'Passed' ? <CheckCircleOutlined /> : <CloseCircleOutlined />} {status}
        </Tag>
      ),
    },
    { title: 'Total', dataIndex: 'total', key: 'total' },
    { title: 'Passed', dataIndex: 'passed', key: 'passed' },
    { title: 'Failed', dataIndex: 'failed', key: 'failed' },
    { title: 'Duration', dataIndex: 'duration', key: 'duration' },
  ];

  return (
    <div>
      <h1>Test Metrics</h1>

      <Row gutter={[16, 16]} style={{ marginBottom: '24px' }}>
        <Col xs={24} sm={12} md={6}>
          <Card>
            <Statistic
              title="Total Tests"
              value={1102}
              prefix={<ArrowUpOutlined style={{ color: '#52c41a' }} />}
            />
          </Card>
        </Col>
        <Col xs={24} sm={12} md={6}>
          <Card>
            <Statistic
              title="Pass Rate"
              value={94.8}
              suffix="%"
              prefix={<ArrowUpOutlined style={{ color: '#52c41a' }} />}
              valueStyle={{ color: '#52c41a' }}
            />
          </Card>
        </Col>
        <Col xs={24} sm={12} md={6}>
          <Card>
            <Statistic
              title="Failed Tests"
              value={38}
              prefix={<ArrowDownOutlined style={{ color: '#ff4d4f' }} />}
              valueStyle={{ color: '#ff4d4f' }}
            />
          </Card>
        </Col>
        <Col xs={24} sm={12} md={6}>
          <Card>
            <Statistic
              title="Avg Duration"
              value={28}
              suffix="m"
              prefix={<ArrowDownOutlined style={{ color: '#faad14' }} />}
              valueStyle={{ color: '#faad14' }}
            />
          </Card>
        </Col>
      </Row>

      <Row gutter={[16, 16]}>
        <Col xs={24} md={12}>
          <Card title="Test Results by Suite">
            <ResponsiveContainer width="100%" height={300}>
              <BarChart data={testTrendData}>
                <CartesianGrid strokeDasharray="3 3" />
                <XAxis dataKey="suite" />
                <YAxis />
                <Tooltip />
                <Legend />
                <Bar dataKey="passed" fill="#52c41a" name="Passed" />
                <Bar dataKey="failed" fill="#ff4d4f" name="Failed" />
                <Bar dataKey="skipped" fill="#faad14" name="Skipped" />
              </BarChart>
            </ResponsiveContainer>
          </Card>
        </Col>
        <Col xs={24} md={12}>
          <Card title="Overall Test Status">
            <ResponsiveContainer width="100%" height={300}>
              <PieChart>
                <Pie
                  data={testStatusData}
                  cx="50%"
                  cy="50%"
                  labelLine={false}
                  label={({ name, value }) => `${name}: ${value}`}
                  outerRadius={80}
                  fill="#8884d8"
                  dataKey="value"
                >
                  {testStatusData.map((entry) => (
                    <Cell key={entry.name} fill={entry.color} />
                  ))}
                </Pie>
                <Tooltip />
              </PieChart>
            </ResponsiveContainer>
          </Card>
        </Col>
      </Row>

      <Row style={{ marginTop: '24px' }}>
        <Col span={24}>
          <Card title="Recent Test Executions">
            <Table
              columns={columns}
              dataSource={testExecutionData}
              pagination={false}
              bordered
            />
          </Card>
        </Col>
      </Row>
    </div>
  );
};

export default TestsPage;


import React from 'react';
import { Card, Row, Col, Statistic, Table } from 'antd';
import { BarChart, Bar, LineChart, Line, XAxis, YAxis, CartesianGrid, Tooltip, Legend, ResponsiveContainer } from 'recharts';
import { ArrowUpOutlined, ArrowDownOutlined } from '@ant-design/icons';

const HomePage = () => {
  // Sample data for charts
  const metricsData = [
    { month: 'Jan', pipelines: 24, quality: 13, tests: 98 },
    { month: 'Feb', pipelines: 30, quality: 29, tests: 120 },
    { month: 'Mar', pipelines: 20, quality: 39, tests: 110 },
    { month: 'Apr', pipelines: 30, quality: 48, tests: 130 },
    { month: 'May', pipelines: 40, quality: 38, tests: 100 },
    { month: 'Jun', pipelines: 50, quality: 47, tests: 150 },
  ];

  const tableData = [
    {
      key: '1',
      project: 'Core Service',
      status: 'Healthy',
      pipelines: 45,
      coverage: '85%',
      tests: 1250,
    },
    {
      key: '2',
      project: 'Git Service',
      status: 'Healthy',
      pipelines: 38,
      coverage: '78%',
      tests: 980,
    },
    {
      key: '3',
      project: 'API Spec',
      status: 'Warning',
      pipelines: 25,
      coverage: '65%',
      tests: 650,
    },
  ];

  const columns = [
    { title: 'Project', dataIndex: 'project', key: 'project' },
    { title: 'Status', dataIndex: 'status', key: 'status' },
    { title: 'Pipelines', dataIndex: 'pipelines', key: 'pipelines' },
    { title: 'Coverage', dataIndex: 'coverage', key: 'coverage' },
    { title: 'Tests', dataIndex: 'tests', key: 'tests' },
  ];

  return (
    <div>
      <h1>Athena Dashboard</h1>

      <Row gutter={[16, 16]} style={{ marginBottom: '24px' }}>
        <Col xs={24} sm={12} md={6}>
          <Card>
            <Statistic
              title="Total Pipelines"
              value={248}
              prefix={<ArrowUpOutlined style={{ color: '#52c41a' }} />}
              valueStyle={{ color: '#52c41a' }}
            />
          </Card>
        </Col>
        <Col xs={24} sm={12} md={6}>
          <Card>
            <Statistic
              title="Avg Coverage"
              value={82}
              suffix="%"
              prefix={<ArrowUpOutlined style={{ color: '#52c41a' }} />}
              valueStyle={{ color: '#52c41a' }}
            />
          </Card>
        </Col>
        <Col xs={24} sm={12} md={6}>
          <Card>
            <Statistic
              title="Test Success Rate"
              value={94.5}
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
              value={125}
              prefix={<ArrowDownOutlined style={{ color: '#ff4d4f' }} />}
              valueStyle={{ color: '#ff4d4f' }}
            />
          </Card>
        </Col>
      </Row>

      <Row gutter={[16, 16]}>
        <Col xs={24} md={12}>
          <Card title="Metrics Trend">
            <ResponsiveContainer width="100%" height={300}>
              <LineChart data={metricsData}>
                <CartesianGrid strokeDasharray="3 3" />
                <XAxis dataKey="month" />
                <YAxis />
                <Tooltip />
                <Legend />
                <Line type="monotone" dataKey="pipelines" stroke="#1890ff" />
                <Line type="monotone" dataKey="quality" stroke="#52c41a" />
                <Line type="monotone" dataKey="tests" stroke="#faad14" />
              </LineChart>
            </ResponsiveContainer>
          </Card>
        </Col>
        <Col xs={24} md={12}>
          <Card title="Quality Metrics">
            <ResponsiveContainer width="100%" height={300}>
              <BarChart data={metricsData}>
                <CartesianGrid strokeDasharray="3 3" />
                <XAxis dataKey="month" />
                <YAxis />
                <Tooltip />
                <Legend />
                <Bar dataKey="quality" fill="#1890ff" />
                <Bar dataKey="tests" fill="#52c41a" />
              </BarChart>
            </ResponsiveContainer>
          </Card>
        </Col>
      </Row>

      <Row style={{ marginTop: '24px' }}>
        <Col span={24}>
          <Card title="Project Overview">
            <Table
              columns={columns}
              dataSource={tableData}
              pagination={false}
              bordered
            />
          </Card>
        </Col>
      </Row>
    </div>
  );
};

export default HomePage;


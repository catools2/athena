import React from 'react';
import { Card, Row, Col, Table, Statistic, Tag } from 'antd';
import { LineChart, Line, BarChart, Bar, XAxis, YAxis, CartesianGrid, Tooltip, Legend, ResponsiveContainer } from 'recharts';
import { ArrowUpOutlined, ArrowDownOutlined, CheckCircleOutlined, CloseCircleOutlined } from '@ant-design/icons';

const PipelinePage = () => {
  const pipelineData = [
    { name: 'Pipeline 1', time: '10:00', success: 95, fail: 5, duration: 45 },
    { name: 'Pipeline 2', time: '11:00', success: 88, fail: 12, duration: 52 },
    { name: 'Pipeline 3', time: '12:00', success: 92, fail: 8, duration: 48 },
    { name: 'Pipeline 4', time: '13:00', success: 98, fail: 2, duration: 42 },
    { name: 'Pipeline 5', time: '14:00', success: 91, fail: 9, duration: 50 },
  ];

  const executionData = [
    {
      key: '1',
      name: 'Core Service Pipeline',
      status: 'Success',
      commits: 5,
      duration: '45m',
      tests: 1250,
      coverage: '85%',
    },
    {
      key: '2',
      name: 'API Gateway Pipeline',
      status: 'Success',
      commits: 3,
      duration: '38m',
      tests: 890,
      coverage: '78%',
    },
    {
      key: '3',
      name: 'Spec Service Pipeline',
      status: 'Failed',
      commits: 2,
      duration: '25m',
      tests: 650,
      coverage: '65%',
    },
  ];

  const columns = [
    { title: 'Pipeline Name', dataIndex: 'name', key: 'name' },
    {
      title: 'Status',
      dataIndex: 'status',
      key: 'status',
      render: (status) => (
        <Tag color={status === 'Success' ? 'green' : 'red'}>
          {status === 'Success' ? <CheckCircleOutlined /> : <CloseCircleOutlined />} {status}
        </Tag>
      ),
    },
    { title: 'Commits', dataIndex: 'commits', key: 'commits' },
    { title: 'Duration', dataIndex: 'duration', key: 'duration' },
    { title: 'Tests', dataIndex: 'tests', key: 'tests' },
    { title: 'Coverage', dataIndex: 'coverage', key: 'coverage' },
  ];

  return (
    <div>
      <h1>Pipeline Metrics</h1>

      <Row gutter={[16, 16]} style={{ marginBottom: '24px' }}>
        <Col xs={24} sm={12} md={6}>
          <Card>
            <Statistic
              title="Total Executions"
              value={2450}
              prefix={<ArrowUpOutlined style={{ color: '#52c41a' }} />}
            />
          </Card>
        </Col>
        <Col xs={24} sm={12} md={6}>
          <Card>
            <Statistic
              title="Success Rate"
              value={93.2}
              suffix="%"
              prefix={<ArrowUpOutlined style={{ color: '#52c41a' }} />}
              valueStyle={{ color: '#52c41a' }}
            />
          </Card>
        </Col>
        <Col xs={24} sm={12} md={6}>
          <Card>
            <Statistic
              title="Avg Duration"
              value={47}
              suffix="m"
              prefix={<ArrowDownOutlined style={{ color: '#faad14' }} />}
              valueStyle={{ color: '#faad14' }}
            />
          </Card>
        </Col>
        <Col xs={24} sm={12} md={6}>
          <Card>
            <Statistic
              title="Failed Runs"
              value={169}
              prefix={<ArrowDownOutlined style={{ color: '#ff4d4f' }} />}
              valueStyle={{ color: '#ff4d4f' }}
            />
          </Card>
        </Col>
      </Row>

      <Row gutter={[16, 16]}>
        <Col xs={24} md={12}>
          <Card title="Pipeline Success Rate Trend">
            <ResponsiveContainer width="100%" height={300}>
              <LineChart data={pipelineData}>
                <CartesianGrid strokeDasharray="3 3" />
                <XAxis dataKey="name" />
                <YAxis />
                <Tooltip />
                <Legend />
                <Line type="monotone" dataKey="success" stroke="#52c41a" name="Success %" />
                <Line type="monotone" dataKey="fail" stroke="#ff4d4f" name="Fail %" />
              </LineChart>
            </ResponsiveContainer>
          </Card>
        </Col>
        <Col xs={24} md={12}>
          <Card title="Pipeline Duration">
            <ResponsiveContainer width="100%" height={300}>
              <BarChart data={pipelineData}>
                <CartesianGrid strokeDasharray="3 3" />
                <XAxis dataKey="name" />
                <YAxis />
                <Tooltip />
                <Legend />
                <Bar dataKey="duration" fill="#1890ff" name="Duration (min)" />
              </BarChart>
            </ResponsiveContainer>
          </Card>
        </Col>
      </Row>

      <Row style={{ marginTop: '24px' }}>
        <Col span={24}>
          <Card title="Recent Pipeline Executions">
            <Table
              columns={columns}
              dataSource={executionData}
              pagination={false}
              bordered
            />
          </Card>
        </Col>
      </Row>
    </div>
  );
};

export default PipelinePage;


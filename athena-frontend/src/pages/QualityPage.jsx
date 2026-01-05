import React from 'react';
import { Card, Row, Col, Table, Statistic } from 'antd';
import { LineChart, Line, AreaChart, Area, XAxis, YAxis, CartesianGrid, Tooltip, Legend, ResponsiveContainer } from 'recharts';
import { ArrowUpOutlined, ArrowDownOutlined } from '@ant-design/icons';

const QualityPage = () => {
  const qualityData = [
    { month: 'Jan', coverage: 75, duplicates: 12, bugs: 45, vulnerabilities: 3 },
    { month: 'Feb', coverage: 78, duplicates: 10, bugs: 38, vulnerabilities: 2 },
    { month: 'Mar', coverage: 80, duplicates: 9, bugs: 32, vulnerabilities: 2 },
    { month: 'Apr', coverage: 82, duplicates: 8, bugs: 28, vulnerabilities: 1 },
    { month: 'May', coverage: 85, duplicates: 6, bugs: 22, vulnerabilities: 1 },
    { month: 'Jun', coverage: 87, duplicates: 5, bugs: 18, vulnerabilities: 0 },
  ];

  const repositoryData = [
    {
      key: '1',
      name: 'athena-core',
      language: 'Java',
      coverage: '85%',
      bugs: 5,
      vulnerabilities: 0,
      quality: 'A',
    },
    {
      key: '2',
      name: 'athena-gateway',
      language: 'Java',
      coverage: '80%',
      bugs: 8,
      vulnerabilities: 1,
      quality: 'B',
    },
    {
      key: '3',
      name: 'athena-spec',
      language: 'Java',
      coverage: '78%',
      bugs: 12,
      vulnerabilities: 1,
      quality: 'B',
    },
  ];

  const columns = [
    { title: 'Repository', dataIndex: 'name', key: 'name' },
    { title: 'Language', dataIndex: 'language', key: 'language' },
    { title: 'Coverage', dataIndex: 'coverage', key: 'coverage' },
    { title: 'Bugs', dataIndex: 'bugs', key: 'bugs' },
    { title: 'Vulnerabilities', dataIndex: 'vulnerabilities', key: 'vulnerabilities' },
    { title: 'Quality', dataIndex: 'quality', key: 'quality' },
  ];

  return (
    <div>
      <h1>Code Quality Metrics</h1>

      <Row gutter={[16, 16]} style={{ marginBottom: '24px' }}>
        <Col xs={24} sm={12} md={6}>
          <Card>
            <Statistic
              title="Avg Coverage"
              value={87}
              suffix="%"
              prefix={<ArrowUpOutlined style={{ color: '#52c41a' }} />}
              valueStyle={{ color: '#52c41a' }}
            />
          </Card>
        </Col>
        <Col xs={24} sm={12} md={6}>
          <Card>
            <Statistic
              title="Total Bugs"
              value={45}
              prefix={<ArrowDownOutlined style={{ color: '#52c41a' }} />}
              valueStyle={{ color: '#52c41a' }}
            />
          </Card>
        </Col>
        <Col xs={24} sm={12} md={6}>
          <Card>
            <Statistic
              title="Vulnerabilities"
              value={3}
              prefix={<ArrowDownOutlined style={{ color: '#52c41a' }} />}
              valueStyle={{ color: '#52c41a' }}
            />
          </Card>
        </Col>
        <Col xs={24} sm={12} md={6}>
          <Card>
            <Statistic
              title="Code Duplicates"
              value={5}
              suffix="%"
              prefix={<ArrowDownOutlined style={{ color: '#52c41a' }} />}
              valueStyle={{ color: '#52c41a' }}
            />
          </Card>
        </Col>
      </Row>

      <Row gutter={[16, 16]}>
        <Col xs={24} md={12}>
          <Card title="Code Coverage Trend">
            <ResponsiveContainer width="100%" height={300}>
              <AreaChart data={qualityData}>
                <CartesianGrid strokeDasharray="3 3" />
                <XAxis dataKey="month" />
                <YAxis />
                <Tooltip />
                <Legend />
                <Area type="monotone" dataKey="coverage" fill="#1890ff" stroke="#1890ff" name="Coverage %" />
              </AreaChart>
            </ResponsiveContainer>
          </Card>
        </Col>
        <Col xs={24} md={12}>
          <Card title="Code Quality Issues">
            <ResponsiveContainer width="100%" height={300}>
              <LineChart data={qualityData}>
                <CartesianGrid strokeDasharray="3 3" />
                <XAxis dataKey="month" />
                <YAxis />
                <Tooltip />
                <Legend />
                <Line type="monotone" dataKey="bugs" stroke="#ff4d4f" name="Bugs" />
                <Line type="monotone" dataKey="vulnerabilities" stroke="#faad14" name="Vulnerabilities" />
                <Line type="monotone" dataKey="duplicates" stroke="#52c41a" name="Duplicates %" />
              </LineChart>
            </ResponsiveContainer>
          </Card>
        </Col>
      </Row>

      <Row style={{ marginTop: '24px' }}>
        <Col span={24}>
          <Card title="Repository Quality Report">
            <Table
              columns={columns}
              dataSource={repositoryData}
              pagination={false}
              bordered
            />
          </Card>
        </Col>
      </Row>
    </div>
  );
};

export default QualityPage;


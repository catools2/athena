import React, { useState, useEffect } from 'react';
import {
  Card, Table, Button, Modal, Form, Input, message, Space, Tag,
  Tabs, Statistic, Row, Col, InputNumber, DatePicker
} from 'antd';
import {
  PlusOutlined, EditOutlined, DeleteOutlined, ReloadOutlined,
  DashboardOutlined, ThunderboltOutlined, LineChartOutlined
} from '@ant-design/icons';
import metricService from '../services/metricService';
import dayjs from 'dayjs';

const { TabPane } = Tabs;

const MetricPage = () => {
  const [metrics, setMetrics] = useState([]);
  const [actions, setActions] = useState([]);
  const [loading, setLoading] = useState(false);
  const [modalVisible, setModalVisible] = useState(false);
  const [modalType, setModalType] = useState('create');
  const [currentRecord, setCurrentRecord] = useState(null);
  const [activeTab, setActiveTab] = useState('metrics');
  const [form] = Form.useForm();

  useEffect(() => {
    loadData();
  }, []);

  const loadData = async () => {
    try {
      setLoading(true);
      const [metricsRes, actionsRes] = await Promise.all([
        metricService.getAllMetrics(),
        metricService.getAllActions()
      ]);
      setMetrics(metricsRes.data || []);
      setActions(actionsRes.data || []);
    } catch (error) {
      message.error('Failed to load data');
    } finally {
      setLoading(false);
    }
  };

  const handleCreate = () => {
    setModalType('create');
    setCurrentRecord(null);
    form.resetFields();
    setModalVisible(true);
  };

  const handleEdit = (record) => {
    setModalType('edit');
    setCurrentRecord(record);
    form.setFieldsValue(record);
    setModalVisible(true);
  };

  const handleDelete = async (id) => {
    Modal.confirm({
      title: 'Confirm Delete',
      content: 'Are you sure you want to delete this record?',
      onOk: async () => {
        try {
          if (activeTab === 'metrics') await metricService.deleteMetric(id);
          else await metricService.deleteAction(id);
          loadData();
          message.success('Deleted successfully');
        } catch (error) {
          message.error('Failed to delete');
        }
      }
    });
  };

  const handleSubmit = async (values) => {
    try {
      if (activeTab === 'metrics') {
        if (modalType === 'create') await metricService.createMetric(values);
        else await metricService.updateMetric({ ...values, id: currentRecord.id });
      } else {
        if (modalType === 'create') await metricService.createAction(values);
        else await metricService.updateAction({ ...values, id: currentRecord.id });
      }
      loadData();
      message.success(`${modalType === 'create' ? 'Created' : 'Updated'} successfully`);
      setModalVisible(false);
    } catch (error) {
      message.error(`Failed to ${modalType}`);
    }
  };

  const metricColumns = [
    { title: 'ID', dataIndex: 'id', key: 'id' },
    { title: 'Project', dataIndex: 'project', key: 'project' },
    { title: 'Environment', dataIndex: 'environment', key: 'environment', render: (text) => <Tag color="green">{text}</Tag> },
    { title: 'Action', dataIndex: 'action', key: 'action', render: (action) => action?.name || '-' },
    { title: 'Duration (ms)', dataIndex: 'duration', key: 'duration', render: (val) => val?.toLocaleString() },
    { title: 'Time', dataIndex: 'actionTime', key: 'actionTime', render: (date) => dayjs(date).format('YYYY-MM-DD HH:mm') },
    {
      title: 'Actions',
      key: 'actions',
      render: (_, record) => (
        <Space>
          <Button icon={<EditOutlined />} onClick={() => handleEdit(record)} size="small" />
          <Button icon={<DeleteOutlined />} onClick={() => handleDelete(record.id)} danger size="small" />
        </Space>
      ),
    },
  ];

  const actionColumns = [
    { title: 'Name', dataIndex: 'name', key: 'name', render: (text) => <><ThunderboltOutlined /> {text}</> },
    { title: 'Category', dataIndex: 'category', key: 'category', render: (text) => <Tag color="blue">{text}</Tag> },
    { title: 'Type', dataIndex: 'type', key: 'type' },
    { title: 'Target', dataIndex: 'target', key: 'target' },
    {
      title: 'Actions',
      key: 'actions',
      render: (_, record) => (
        <Space>
          <Button icon={<EditOutlined />} onClick={() => handleEdit(record)} size="small" />
          <Button icon={<DeleteOutlined />} onClick={() => handleDelete(record.id)} danger size="small" />
        </Space>
      ),
    },
  ];

  return (
    <div>
      <Card
        title={<><DashboardOutlined /> Metrics Management</>}
        extra={
          <Space>
            <Button icon={<ReloadOutlined />} onClick={loadData}>Refresh</Button>
            <Button type="primary" icon={<PlusOutlined />} onClick={handleCreate}>
              Add {activeTab === 'metrics' ? 'Metric' : 'Action'}
            </Button>
          </Space>
        }
      >
        <Tabs activeKey={activeTab} onChange={setActiveTab}>
          <TabPane tab="Metrics" key="metrics">
            <Table columns={metricColumns} dataSource={metrics} rowKey="id" loading={loading} pagination={{ pageSize: 10 }} />
          </TabPane>
          <TabPane tab="Actions" key="actions">
            <Table columns={actionColumns} dataSource={actions} rowKey="id" loading={loading} pagination={{ pageSize: 10 }} />
          </TabPane>
          <TabPane tab="Statistics" key="stats">
            <Row gutter={[16, 16]}>
              <Col span={6}>
                <Card><Statistic title="Total Metrics" value={metrics.length} prefix={<DashboardOutlined />} /></Card>
              </Col>
              <Col span={6}>
                <Card><Statistic title="Actions" value={actions.length} prefix={<ThunderboltOutlined />} /></Card>
              </Col>
              <Col span={6}>
                <Card><Statistic title="Avg Duration" value={1234} suffix="ms" prefix={<LineChartOutlined />} /></Card>
              </Col>
              <Col span={6}>
                <Card><Statistic title="Today's Metrics" value={0} prefix={<DashboardOutlined />} /></Card>
              </Col>
            </Row>
          </TabPane>
        </Tabs>
      </Card>

      <Modal
        title={`${modalType === 'create' ? 'Create' : 'Edit'} ${activeTab === 'metrics' ? 'Metric' : 'Action'}`}
        open={modalVisible}
        onCancel={() => setModalVisible(false)}
        onOk={() => form.submit()}
        width={600}
      >
        <Form form={form} layout="vertical" onFinish={handleSubmit}>
          {activeTab === 'metrics' ? (
            <>
              <Form.Item name="project" label="Project" rules={[{ required: true }]}>
                <Input placeholder="PROJ-001" />
              </Form.Item>
              <Form.Item name="environment" label="Environment">
                <Input placeholder="production" />
              </Form.Item>
              <Form.Item name="duration" label="Duration (ms)" rules={[{ required: true }]}>
                <InputNumber style={{ width: '100%' }} placeholder="1000" />
              </Form.Item>
            </>
          ) : (
            <>
              <Form.Item name="name" label="Action Name" rules={[{ required: true }]}>
                <Input placeholder="api.call" />
              </Form.Item>
              <Form.Item name="category" label="Category" rules={[{ required: true }]}>
                <Input placeholder="API" />
              </Form.Item>
              <Form.Item name="type" label="Type">
                <Input placeholder="REST" />
              </Form.Item>
              <Form.Item name="target" label="Target">
                <Input placeholder="/api/endpoint" />
              </Form.Item>
            </>
          )}
        </Form>
      </Modal>
    </div>
  );
};

export default MetricPage;


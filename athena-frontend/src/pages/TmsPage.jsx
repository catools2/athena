import React, { useState, useEffect } from 'react';
import {
  Card, Table, Button, Modal, Form, Input, message, Space, Tag,
  Tabs, Statistic, Row, Col, Select
} from 'antd';
import {
  PlusOutlined, EditOutlined, DeleteOutlined, ReloadOutlined,
  CheckCircleOutlined, FileTextOutlined, ExperimentOutlined, BarChartOutlined
} from '@ant-design/icons';
import tmsService from '../services/tmsService';
import coreService from '../services/coreService';
import dayjs from 'dayjs';

const { TabPane } = Tabs;
const { Option } = Select;

const TmsPage = () => {
  const [items, setItems] = useState([]);
  const [cycles, setCycles] = useState([]);
  const [executions, setExecutions] = useState([]);
  const [statuses, setStatuses] = useState([]);
  const [priorities, setPriorities] = useState([]);
  const [itemTypes, setItemTypes] = useState([]);
  const [projects, setProjects] = useState([]);
  const [loading, setLoading] = useState(false);
  const [modalVisible, setModalVisible] = useState(false);
  const [modalType, setModalType] = useState('create');
  const [currentRecord, setCurrentRecord] = useState(null);
  const [activeTab, setActiveTab] = useState('items');
  const [form] = Form.useForm();

  useEffect(() => {
    loadData();
    loadReferenceData();
  }, []);

  const loadData = async () => {
    try {
      setLoading(true);
      const [itemsRes, cyclesRes, execRes] = await Promise.all([
        tmsService.getAllItems(),
        tmsService.getAllCycles(),
        tmsService.getAllExecutions()
      ]);
      setItems(itemsRes.data || []);
      setCycles(cyclesRes.data || []);
      setExecutions(execRes.data || []);
    } catch (error) {
      message.error('Failed to load data');
    } finally {
      setLoading(false);
    }
  };

  const loadReferenceData = async () => {
    try {
      const [statusesRes, prioritiesRes, typesRes, projectsRes] = await Promise.all([
        tmsService.getAllStatuses(),
        tmsService.getAllPriorities(),
        tmsService.getAllItemTypes(),
        coreService.getAllProjects()
      ]);
      setStatuses(statusesRes.data || []);
      setPriorities(prioritiesRes.data || []);
      setItemTypes(typesRes.data || []);
      setProjects(projectsRes.data || []);
    } catch (error) {
      console.error('Failed to load reference data');
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
          if (activeTab === 'items') await tmsService.deleteItem(id);
          else if (activeTab === 'cycles') await tmsService.deleteCycle(id);
          else await tmsService.deleteExecution(id);
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
      if (activeTab === 'items') {
        if (modalType === 'create') await tmsService.createItem(values);
        else await tmsService.updateItem({ ...values, id: currentRecord.id });
      } else if (activeTab === 'cycles') {
        if (modalType === 'create') await tmsService.createCycle(values);
        else await tmsService.updateCycle({ ...values, id: currentRecord.id });
      } else {
        if (modalType === 'create') await tmsService.createExecution(values);
        else await tmsService.updateExecution({ ...values, id: currentRecord.id });
      }
      loadData();
      message.success(`${modalType === 'create' ? 'Created' : 'Updated'} successfully`);
      setModalVisible(false);
    } catch (error) {
      message.error(`Failed to ${modalType}`);
    }
  };

  const itemColumns = [
    { title: 'Code', dataIndex: 'code', key: 'code', render: (text) => <Tag color="blue">{text}</Tag> },
    { title: 'Name', dataIndex: 'name', key: 'name' },
    { title: 'Type', dataIndex: 'type', key: 'type', render: (text) => <Tag>{text}</Tag> },
    { title: 'Status', dataIndex: 'status', key: 'status', render: (text) => <Tag color="green">{text}</Tag> },
    { title: 'Priority', dataIndex: 'priority', key: 'priority' },
    { title: 'Project', dataIndex: 'project', key: 'project' },
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

  const cycleColumns = [
    { title: 'Code', dataIndex: 'code', key: 'code', render: (text) => <Tag color="purple">{text}</Tag> },
    { title: 'Name', dataIndex: 'name', key: 'name' },
    { title: 'Start Date', dataIndex: 'startDate', key: 'startDate', render: (date) => dayjs(date).format('YYYY-MM-DD') },
    { title: 'End Date', dataIndex: 'endDate', key: 'endDate', render: (date) => dayjs(date).format('YYYY-MM-DD') },
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

  const executionColumns = [
    { title: 'ID', dataIndex: 'id', key: 'id' },
    { title: 'Item', dataIndex: 'item', key: 'item' },
    { title: 'Status', dataIndex: 'status', key: 'status', render: (text) => <Tag color="green">{text}</Tag> },
    { title: 'Executor', dataIndex: 'executor', key: 'executor' },
    { title: 'Executed On', dataIndex: 'executedOn', key: 'executedOn', render: (date) => dayjs(date).format('YYYY-MM-DD HH:mm') },
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

  const renderItemForm = () => (
    <>
      <Form.Item name="code" label="Code" rules={[{ required: true }]}>
        <Input placeholder="ITEM-001" />
      </Form.Item>
      <Form.Item name="name" label="Name" rules={[{ required: true }]}>
        <Input placeholder="Test Item Name" />
      </Form.Item>
      <Form.Item name="type" label="Type" rules={[{ required: true }]}>
        <Select placeholder="Select type">
          {itemTypes.map(t => <Option key={t.code} value={t.code}>{t.name}</Option>)}
        </Select>
      </Form.Item>
      <Form.Item name="status" label="Status" rules={[{ required: true }]}>
        <Select placeholder="Select status">
          {statuses.map(s => <Option key={s.code} value={s.code}>{s.name}</Option>)}
        </Select>
      </Form.Item>
      <Form.Item name="priority" label="Priority">
        <Select placeholder="Select priority">
          {priorities.map(p => <Option key={p.code} value={p.code}>{p.name}</Option>)}
        </Select>
      </Form.Item>
      <Form.Item name="project" label="Project" rules={[{ required: true }]}>
        <Select placeholder="Select project">
          {projects.map(p => <Option key={p.code} value={p.code}>{p.name}</Option>)}
        </Select>
      </Form.Item>
    </>
  );

  const renderCycleForm = () => (
    <>
      <Form.Item name="code" label="Code" rules={[{ required: true }]}>
        <Input placeholder="CYCLE-001" />
      </Form.Item>
      <Form.Item name="name" label="Name" rules={[{ required: true }]}>
        <Input placeholder="Sprint 1 Testing" />
      </Form.Item>
    </>
  );

  return (
    <div>
      <Card
        title={<><ExperimentOutlined /> Test Management</>}
        extra={
          <Space>
            <Button icon={<ReloadOutlined />} onClick={loadData}>Refresh</Button>
            <Button type="primary" icon={<PlusOutlined />} onClick={handleCreate}>
              Add {activeTab === 'items' ? 'Item' : activeTab === 'cycles' ? 'Cycle' : 'Execution'}
            </Button>
          </Space>
        }
      >
        <Tabs activeKey={activeTab} onChange={setActiveTab}>
          <TabPane tab="Test Items" key="items">
            <Table columns={itemColumns} dataSource={items} rowKey="id" loading={loading} pagination={{ pageSize: 10 }} />
          </TabPane>
          <TabPane tab="Test Cycles" key="cycles">
            <Table columns={cycleColumns} dataSource={cycles} rowKey="id" loading={loading} pagination={{ pageSize: 10 }} />
          </TabPane>
          <TabPane tab="Executions" key="executions">
            <Table columns={executionColumns} dataSource={executions} rowKey="id" loading={loading} pagination={{ pageSize: 10 }} />
          </TabPane>
          <TabPane tab="Statistics" key="stats">
            <Row gutter={[16, 16]}>
              <Col span={6}>
                <Card><Statistic title="Total Items" value={items.length} prefix={<FileTextOutlined />} /></Card>
              </Col>
              <Col span={6}>
                <Card><Statistic title="Test Cycles" value={cycles.length} prefix={<ExperimentOutlined />} /></Card>
              </Col>
              <Col span={6}>
                <Card><Statistic title="Executions" value={executions.length} prefix={<CheckCircleOutlined />} /></Card>
              </Col>
              <Col span={6}>
                <Card><Statistic title="Pass Rate" value={85} suffix="%" prefix={<BarChartOutlined />} valueStyle={{ color: '#3f8600' }} /></Card>
              </Col>
            </Row>
          </TabPane>
        </Tabs>
      </Card>

      <Modal
        title={`${modalType === 'create' ? 'Create' : 'Edit'} ${activeTab === 'items' ? 'Item' : activeTab === 'cycles' ? 'Cycle' : 'Execution'}`}
        open={modalVisible}
        onCancel={() => setModalVisible(false)}
        onOk={() => form.submit()}
        width={600}
      >
        <Form form={form} layout="vertical" onFinish={handleSubmit}>
          {activeTab === 'items' && renderItemForm()}
          {activeTab === 'cycles' && renderCycleForm()}
        </Form>
      </Modal>
    </div>
  );
};

export default TmsPage;


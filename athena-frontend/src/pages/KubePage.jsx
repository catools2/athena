import React, { useState, useEffect } from 'react';
import {
  Card, Table, Button, Modal, Form, Input, message, Space, Tag,
  Tabs, Statistic, Row, Col, Select
} from 'antd';
import {
  PlusOutlined, EditOutlined, DeleteOutlined, ReloadOutlined,
  CloudServerOutlined, ContainerOutlined, AppstoreOutlined
} from '@ant-design/icons';
import kubeService from '../services/kubeService';
import coreService from '../services/coreService';
import dayjs from 'dayjs';

const { TabPane } = Tabs;
const { Option } = Select;

const KubePage = () => {
  const [pods, setPods] = useState([]);
  const [projects, setProjects] = useState([]);
  const [stats, setStats] = useState({});
  const [loading, setLoading] = useState(false);
  const [modalVisible, setModalVisible] = useState(false);
  const [modalType, setModalType] = useState('create');
  const [currentRecord, setCurrentRecord] = useState(null);
  const [form] = Form.useForm();

  useEffect(() => {
    loadPods();
    loadProjects();
    loadStats();
  }, []);

  const loadPods = async () => {
    try {
      setLoading(true);
      const response = await kubeService.getAllPods();
      setPods(response.data || []);
    } catch (error) {
      message.error('Failed to load pods');
    } finally {
      setLoading(false);
    }
  };

  const loadProjects = async () => {
    try {
      const response = await coreService.getAllProjects();
      setProjects(response.data || []);
    } catch (error) {
      console.error('Failed to load projects');
    }
  };

  const loadStats = async () => {
    try {
      const response = await kubeService.getPodStats();
      setStats(response.data || {});
    } catch (error) {
      console.error('Failed to load stats');
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
      content: 'Are you sure you want to delete this pod?',
      onOk: async () => {
        try {
          await kubeService.deletePod(id);
          loadPods();
          message.success('Deleted successfully');
        } catch (error) {
          message.error('Failed to delete');
        }
      }
    });
  };

  const handleSubmit = async (values) => {
    try {
      if (modalType === 'create') {
        await kubeService.createPod(values);
      } else {
        await kubeService.updatePod({ ...values, id: currentRecord.id });
      }
      loadPods();
      message.success(`${modalType === 'create' ? 'Created' : 'Updated'} successfully`);
      setModalVisible(false);
      form.resetFields();
    } catch (error) {
      message.error(`Failed to ${modalType}`);
    }
  };

  const podColumns = [
    {
      title: 'Name',
      dataIndex: 'name',
      key: 'name',
      render: (text) => <><ContainerOutlined /> {text}</>,
    },
    {
      title: 'Namespace',
      dataIndex: 'namespace',
      key: 'namespace',
      render: (text) => <Tag color="blue">{text}</Tag>,
    },
    {
      title: 'Project',
      dataIndex: 'project',
      key: 'project',
    },
    {
      title: 'Status',
      dataIndex: 'status',
      key: 'status',
      render: (status) => {
        const color = status?.phase === 'Running' ? 'green' :
                     status?.phase === 'Pending' ? 'orange' : 'red';
        return <Tag color={color}>{status?.phase || 'Unknown'}</Tag>;
      },
    },
    {
      title: 'Node',
      dataIndex: 'nodeName',
      key: 'nodeName',
      ellipsis: true,
    },
    {
      title: 'Created',
      dataIndex: 'createdAt',
      key: 'createdAt',
      render: (date) => date ? dayjs(date).format('YYYY-MM-DD HH:mm') : '-',
    },
    {
      title: 'Actions',
      key: 'actions',
      render: (_, record) => (
        <Space>
          <Button icon={<EditOutlined />} onClick={() => handleEdit(record)} size="small" />
          <Button
            icon={<DeleteOutlined />}
            onClick={() => handleDelete(record.id)}
            danger
            size="small"
          />
        </Space>
      ),
    },
  ];

  return (
    <div>
      <Card
        title={<><CloudServerOutlined /> Kubernetes Management</>}
        extra={
          <Space>
            <Button icon={<ReloadOutlined />} onClick={loadPods}>Refresh</Button>
            <Button type="primary" icon={<PlusOutlined />} onClick={handleCreate}>
              Add Pod
            </Button>
          </Space>
        }
      >
        <Tabs defaultActiveKey="pods">
          <TabPane tab="Pods" key="pods">
            <Table
              columns={podColumns}
              dataSource={pods}
              rowKey="id"
              loading={loading}
              pagination={{ pageSize: 10 }}
            />
          </TabPane>

          <TabPane tab="Statistics" key="stats">
            <Row gutter={[16, 16]}>
              <Col span={6}>
                <Card>
                  <Statistic
                    title="Total Pods"
                    value={pods.length}
                    prefix={<ContainerOutlined />}
                  />
                </Card>
              </Col>
              <Col span={6}>
                <Card>
                  <Statistic
                    title="Running"
                    value={pods.filter(p => p.status?.phase === 'Running').length}
                    prefix={<AppstoreOutlined />}
                    valueStyle={{ color: '#3f8600' }}
                  />
                </Card>
              </Col>
              <Col span={6}>
                <Card>
                  <Statistic
                    title="Pending"
                    value={pods.filter(p => p.status?.phase === 'Pending').length}
                    prefix={<AppstoreOutlined />}
                    valueStyle={{ color: '#faad14' }}
                  />
                </Card>
              </Col>
              <Col span={6}>
                <Card>
                  <Statistic
                    title="Failed"
                    value={pods.filter(p => p.status?.phase === 'Failed').length}
                    prefix={<AppstoreOutlined />}
                    valueStyle={{ color: '#cf1322' }}
                  />
                </Card>
              </Col>
            </Row>
          </TabPane>
        </Tabs>
      </Card>

      <Modal
        title={`${modalType === 'create' ? 'Create' : 'Edit'} Pod`}
        open={modalVisible}
        onCancel={() => setModalVisible(false)}
        onOk={() => form.submit()}
        width={600}
      >
        <Form form={form} layout="vertical" onFinish={handleSubmit}>
          <Form.Item
            name="name"
            label="Pod Name"
            rules={[{ required: true, message: 'Please enter pod name' }]}
          >
            <Input placeholder="my-pod-12345" />
          </Form.Item>
          <Form.Item
            name="namespace"
            label="Namespace"
            rules={[{ required: true, message: 'Please enter namespace' }]}
          >
            <Input placeholder="default" />
          </Form.Item>
          <Form.Item
            name="project"
            label="Project"
            rules={[{ required: true, message: 'Please select project' }]}
          >
            <Select placeholder="Select project">
              {projects.map(proj => (
                <Option key={proj.code} value={proj.code}>{proj.name}</Option>
              ))}
            </Select>
          </Form.Item>
          <Form.Item
            name="nodeName"
            label="Node Name"
          >
            <Input placeholder="node-01" />
          </Form.Item>
          <Form.Item
            name="hostname"
            label="Hostname"
          >
            <Input placeholder="my-pod-12345.default.svc.cluster.local" />
          </Form.Item>
        </Form>
      </Modal>
    </div>
  );
};

export default KubePage;


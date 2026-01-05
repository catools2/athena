import React, { useState, useEffect } from 'react';
import {
  Card, Table, Button, Modal, Form, Input, message, Space, Tag,
  Tabs, Statistic, Row, Col
} from 'antd';
import {
  PlusOutlined, EditOutlined, DeleteOutlined, ReloadOutlined,
  ApiOutlined, FileTextOutlined, LinkOutlined
} from '@ant-design/icons';
import specService from '../services/specService';
import dayjs from 'dayjs';

const { TabPane } = Tabs;
const { TextArea } = Input;

const SpecPage = () => {
  const [specs, setSpecs] = useState([]);
  const [paths, setPaths] = useState([]);
  const [loading, setLoading] = useState(false);
  const [modalVisible, setModalVisible] = useState(false);
  const [modalType, setModalType] = useState('create');
  const [currentRecord, setCurrentRecord] = useState(null);
  const [activeTab, setActiveTab] = useState('specs');
  const [form] = Form.useForm();

  useEffect(() => {
    loadData();
  }, []);

  const loadData = async () => {
    try {
      setLoading(true);
      const specsRes = await specService.getAllSpecs();
      setSpecs(specsRes.data || []);
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
      content: 'Are you sure you want to delete this specification?',
      onOk: async () => {
        try {
          await specService.deleteSpec(id);
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
      if (modalType === 'create') {
        await specService.createSpec(values);
      } else {
        await specService.updateSpec({ ...values, id: currentRecord.id });
      }
      loadData();
      message.success(`${modalType === 'create' ? 'Created' : 'Updated'} successfully`);
      setModalVisible(false);
    } catch (error) {
      message.error(`Failed to ${modalType}`);
    }
  };

  const specColumns = [
    { title: 'Name', dataIndex: 'name', key: 'name', render: (text) => <><ApiOutlined /> {text}</> },
    { title: 'Title', dataIndex: 'title', key: 'title' },
    { title: 'Version', dataIndex: 'version', key: 'version', render: (text) => <Tag color="blue">{text}</Tag> },
    { title: 'Project', dataIndex: 'project', key: 'project' },
    {
      title: 'Last Sync',
      dataIndex: 'lastSyncTime',
      key: 'lastSyncTime',
      render: (date) => date ? dayjs(date).format('YYYY-MM-DD HH:mm') : '-'
    },
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
        title={<><ApiOutlined /> API Specification Management</>}
        extra={
          <Space>
            <Button icon={<ReloadOutlined />} onClick={loadData}>Refresh</Button>
            <Button type="primary" icon={<PlusOutlined />} onClick={handleCreate}>
              Add Specification
            </Button>
          </Space>
        }
      >
        <Tabs activeKey={activeTab} onChange={setActiveTab}>
          <TabPane tab="Specifications" key="specs">
            <Table
              columns={specColumns}
              dataSource={specs}
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
                    title="Total Specifications"
                    value={specs.length}
                    prefix={<FileTextOutlined />}
                  />
                </Card>
              </Col>
              <Col span={6}>
                <Card>
                  <Statistic
                    title="API Endpoints"
                    value={specs.reduce((sum, spec) => sum + (spec.paths?.length || 0), 0)}
                    prefix={<LinkOutlined />}
                  />
                </Card>
              </Col>
              <Col span={6}>
                <Card>
                  <Statistic
                    title="Projects"
                    value={new Set(specs.map(s => s.project)).size}
                    prefix={<ApiOutlined />}
                  />
                </Card>
              </Col>
              <Col span={6}>
                <Card>
                  <Statistic
                    title="Average Paths"
                    value={specs.length ? Math.round(specs.reduce((sum, spec) => sum + (spec.paths?.length || 0), 0) / specs.length) : 0}
                    prefix={<ApiOutlined />}
                  />
                </Card>
              </Col>
            </Row>
          </TabPane>
        </Tabs>
      </Card>

      <Modal
        title={`${modalType === 'create' ? 'Create' : 'Edit'} API Specification`}
        open={modalVisible}
        onCancel={() => setModalVisible(false)}
        onOk={() => form.submit()}
        width={600}
      >
        <Form form={form} layout="vertical" onFinish={handleSubmit}>
          <Form.Item
            name="name"
            label="Specification Name"
            rules={[{ required: true, message: 'Please enter specification name' }]}
          >
            <Input placeholder="my-api-spec" />
          </Form.Item>
          <Form.Item
            name="title"
            label="Title"
            rules={[{ required: true, message: 'Please enter title' }]}
          >
            <Input placeholder="My API Specification" />
          </Form.Item>
          <Form.Item
            name="version"
            label="Version"
            rules={[{ required: true, message: 'Please enter version' }]}
          >
            <Input placeholder="1.0.0" />
          </Form.Item>
          <Form.Item
            name="project"
            label="Project"
            rules={[{ required: true, message: 'Please enter project' }]}
          >
            <Input placeholder="PROJ-001" />
          </Form.Item>
        </Form>
      </Modal>
    </div>
  );
};

export default SpecPage;


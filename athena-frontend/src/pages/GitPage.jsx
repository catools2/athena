import React, { useState, useEffect } from 'react';
import {
  Card, Table, Button, Modal, Form, Input, message, Space, Tag,
  Tabs, Descriptions, Statistic, Row, Col, DatePicker
} from 'antd';
import {
  PlusOutlined, EditOutlined, DeleteOutlined, ReloadOutlined,
  BranchesOutlined, CodeOutlined, UserOutlined, CalendarOutlined
} from '@ant-design/icons';
import gitService from '../services/gitService';
import dayjs from 'dayjs';

const { TabPane } = Tabs;
const { RangePicker } = DatePicker;

const GitPage = () => {
  const [repositories, setRepositories] = useState([]);
  const [commits, setCommits] = useState([]);
  const [stats, setStats] = useState({});
  const [loading, setLoading] = useState(false);
  const [modalVisible, setModalVisible] = useState(false);
  const [modalType, setModalType] = useState('create'); // 'create' or 'edit'
  const [currentRecord, setCurrentRecord] = useState(null);
  const [activeTab, setActiveTab] = useState('repositories');
  const [form] = Form.useForm();

  useEffect(() => {
    loadRepositories();
    loadCommits();
    loadStats();
  }, []);

  const loadRepositories = async () => {
    try {
      setLoading(true);
      const response = await gitService.getAllRepositories();
      setRepositories(response.data || []);
    } catch (error) {
      message.error('Failed to load repositories');
    } finally {
      setLoading(false);
    }
  };

  const loadCommits = async () => {
    try {
      const response = await gitService.getAllCommits();
      setCommits(response.data || []);
    } catch (error) {
      message.error('Failed to load commits');
    }
  };

  const loadStats = async () => {
    try {
      const [commitStats, authorStats] = await Promise.all([
        gitService.getCommitStats(),
        gitService.getAuthorStats()
      ]);
      setStats({
        commits: commitStats.data,
        authors: authorStats.data
      });
    } catch (error) {
      console.error('Failed to load stats', error);
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
          if (activeTab === 'repositories') {
            await gitService.deleteRepository(id);
            loadRepositories();
          } else {
            await gitService.deleteCommit(id);
            loadCommits();
          }
          message.success('Deleted successfully');
        } catch (error) {
          message.error('Failed to delete');
        }
      }
    });
  };

  const handleSubmit = async (values) => {
    try {
      if (activeTab === 'repositories') {
        if (modalType === 'create') {
          await gitService.createRepository(values);
        } else {
          await gitService.updateRepository({ ...values, id: currentRecord.id });
        }
        loadRepositories();
      } else {
        if (modalType === 'create') {
          await gitService.createCommit(values);
        } else {
          await gitService.updateCommit({ ...values, id: currentRecord.id });
        }
        loadCommits();
      }
      message.success(`${modalType === 'create' ? 'Created' : 'Updated'} successfully`);
      setModalVisible(false);
      form.resetFields();
    } catch (error) {
      message.error(`Failed to ${modalType}`);
    }
  };

  const repositoryColumns = [
    {
      title: 'Name',
      dataIndex: 'name',
      key: 'name',
      render: (text) => <><BranchesOutlined /> {text}</>,
    },
    {
      title: 'URL',
      dataIndex: 'url',
      key: 'url',
      ellipsis: true,
    },
    {
      title: 'Last Sync',
      dataIndex: 'lastSync',
      key: 'lastSync',
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

  const commitColumns = [
    {
      title: 'Hash',
      dataIndex: 'hash',
      key: 'hash',
      render: (text) => <Tag color="blue">{text?.substring(0, 8)}</Tag>,
    },
    {
      title: 'Message',
      dataIndex: 'shortMessage',
      key: 'shortMessage',
      ellipsis: true,
    },
    {
      title: 'Author',
      dataIndex: 'author',
      key: 'author',
      render: (text) => <><UserOutlined /> {text}</>,
    },
    {
      title: 'Repository',
      dataIndex: 'repository',
      key: 'repository',
    },
    {
      title: 'Date',
      dataIndex: 'commitTime',
      key: 'commitTime',
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
        title={<><CodeOutlined /> Git Management</>}
        extra={
          <Space>
            <Button icon={<ReloadOutlined />} onClick={activeTab === 'repositories' ? loadRepositories : loadCommits}>
              Refresh
            </Button>
            <Button type="primary" icon={<PlusOutlined />} onClick={handleCreate}>
              Add {activeTab === 'repositories' ? 'Repository' : 'Commit'}
            </Button>
          </Space>
        }
      >
        <Tabs activeKey={activeTab} onChange={setActiveTab}>
          <TabPane tab="Repositories" key="repositories">
            <Table
              columns={repositoryColumns}
              dataSource={repositories}
              rowKey="id"
              loading={loading}
              pagination={{ pageSize: 10 }}
            />
          </TabPane>

          <TabPane tab="Commits" key="commits">
            <Table
              columns={commitColumns}
              dataSource={commits}
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
                    title="Total Commits"
                    value={commits.length}
                    prefix={<CodeOutlined />}
                  />
                </Card>
              </Col>
              <Col span={6}>
                <Card>
                  <Statistic
                    title="Repositories"
                    value={repositories.length}
                    prefix={<BranchesOutlined />}
                  />
                </Card>
              </Col>
              <Col span={6}>
                <Card>
                  <Statistic
                    title="Active Authors"
                    value={stats.authors?.length || 0}
                    prefix={<UserOutlined />}
                  />
                </Card>
              </Col>
              <Col span={6}>
                <Card>
                  <Statistic
                    title="Today's Commits"
                    value={0}
                    prefix={<CalendarOutlined />}
                  />
                </Card>
              </Col>
            </Row>
          </TabPane>
        </Tabs>
      </Card>

      <Modal
        title={`${modalType === 'create' ? 'Create' : 'Edit'} ${activeTab === 'repositories' ? 'Repository' : 'Commit'}`}
        open={modalVisible}
        onCancel={() => setModalVisible(false)}
        onOk={() => form.submit()}
        width={600}
      >
        <Form form={form} layout="vertical" onFinish={handleSubmit}>
          {activeTab === 'repositories' ? (
            <>
              <Form.Item
                name="name"
                label="Repository Name"
                rules={[{ required: true, message: 'Please enter repository name' }]}
              >
                <Input placeholder="my-repository" />
              </Form.Item>
              <Form.Item
                name="url"
                label="Repository URL"
                rules={[{ required: true, message: 'Please enter repository URL' }]}
              >
                <Input placeholder="https://github.com/user/repo.git" />
              </Form.Item>
            </>
          ) : (
            <>
              <Form.Item
                name="hash"
                label="Commit Hash"
                rules={[{ required: true, message: 'Please enter commit hash' }]}
              >
                <Input placeholder="abc123def456..." />
              </Form.Item>
              <Form.Item
                name="shortMessage"
                label="Commit Message"
                rules={[{ required: true, message: 'Please enter commit message' }]}
              >
                <Input.TextArea rows={3} placeholder="Fix bug in..." />
              </Form.Item>
              <Form.Item
                name="author"
                label="Author"
                rules={[{ required: true, message: 'Please enter author' }]}
              >
                <Input placeholder="john.doe" />
              </Form.Item>
              <Form.Item
                name="repository"
                label="Repository"
                rules={[{ required: true, message: 'Please enter repository' }]}
              >
                <Input placeholder="my-repository" />
              </Form.Item>
            </>
          )}
        </Form>
      </Modal>
    </div>
  );
};

export default GitPage;


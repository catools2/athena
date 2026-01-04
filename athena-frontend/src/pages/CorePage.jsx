import React, { useState, useEffect, useCallback } from 'react';
import {
  Card, Table, Button, Modal, Form, Input, message, Space, Tag,
  Tabs, Statistic, Row, Col, AutoComplete
} from 'antd';
import {
  PlusOutlined, EditOutlined, DeleteOutlined, ReloadOutlined,
  UserOutlined, ProjectOutlined, EnvironmentOutlined, TagOutlined, SettingOutlined
} from '@ant-design/icons';
import coreService from '../services/coreService';
import dayjs from 'dayjs';

const { TabPane } = Tabs;

const CorePage = () => {
  const [users, setUsers] = useState([]);
  const [projects, setProjects] = useState([]);
  const [environments, setEnvironments] = useState([]);
  const [versions, setVersions] = useState([]);
  const [loading, setLoading] = useState(false);
  const [modalVisible, setModalVisible] = useState(false);
  const [modalType, setModalType] = useState('create');
  const [currentRecord, setCurrentRecord] = useState(null);
  const [activeTab, setActiveTab] = useState('users');
  const [searchFilters, setSearchFilters] = useState({
    users: { username: '', alias: '' },
    projects: { code: '', name: '' },
    environments: { code: '', name: '', project: '' },
    versions: { code: '', name: '', project: '' }
  });
  const [activeFilters, setActiveFilters] = useState({
    users: { username: '', alias: '' },
    projects: { code: '', name: '' },
    environments: { code: '', name: '', project: '' },
    versions: { code: '', name: '', project: '' }
  });
  const [pagination, setPagination] = useState({
    users: { current: 1, pageSize: 10, total: 0, totalPages: 0 },
    projects: { current: 1, pageSize: 10, total: 0, totalPages: 0 },
    environments: { current: 1, pageSize: 10, total: 0, totalPages: 0 },
    versions: { current: 1, pageSize: 10, total: 0, totalPages: 0 }
  });
  const [selectedUserIds, setSelectedUserIds] = useState([]);
  const [mergeModalVisible, setMergeModalVisible] = useState(false);
  const [editingAliases, setEditingAliases] = useState([]);
  const [aliasInput, setAliasInput] = useState('');
  const [projectList, setProjectList] = useState([]);
  const [filteredProjectList, setFilteredProjectList] = useState([]);
  const [userList, setUserList] = useState([]);
  const [filteredUserList, setFilteredUserList] = useState([]);
  const [environmentList, setEnvironmentList] = useState([]);
  const [filteredEnvironmentList, setFilteredEnvironmentList] = useState([]);
  const [versionList, setVersionList] = useState([]);
  const [filteredVersionList, setFilteredVersionList] = useState([]);
  const [form] = Form.useForm();

  useEffect(() => {
    loadData();
  }, []);

  // Load data only for the active tab
  const loadTabData = async (tab = activeTab) => {
    try {
      setLoading(true);
      let response;

      if (tab === 'users') {
        response = await coreService.getAllUsers({ page: 0, size: 10 });
        setUsers(response.data?.content || []);
      } else if (tab === 'projects') {
        response = await coreService.getAllProjects({ page: 0, size: 10 });
        setProjects(response.data?.content || []);
      } else if (tab === 'environments') {
        response = await coreService.getAllEnvironments({ page: 0, size: 10 });
        setEnvironments(response.data?.content || []);
      } else if (tab === 'versions') {
        response = await coreService.getAllVersions({ page: 0, size: 10 });
        setVersions(response.data?.content || []);
      }

      // Update pagination for the loaded tab
      if (response) {
        setPagination(prev => ({
          ...prev,
          [tab]: {
            current: 1,
            pageSize: 10,
            total: response.data?.page?.totalElements || 0,
            totalPages: response.data?.page?.totalPages || 0
          }
        }));
      }
    } catch (error) {
      console.error(`Error loading ${tab} data:`, error);
      message.error(`Failed to load ${tab}`);
    } finally {
      setLoading(false);
    }
  };

  const [autocompleteCached, setAutocompleteCached] = useState(false);

  // ...existing code...

  // Load autocomplete lists (called when needed) - with caching
  const loadAutocompleteLists = async () => {
    // Skip if already cached
    if (autocompleteCached) {
      console.log('Using cached autocomplete lists');
      return;
    }

    try {
      const [projectsListRes, usersListRes, envsListRes, versionsListRes] = await Promise.all([
        coreService.getProjectsList(),
        coreService.getUsersList(),
        coreService.getEnvironmentsList(),
        coreService.getVersionsList()
      ]);

      const projects = projectsListRes.data?.content || [];
      setProjectList(projects);
      setFilteredProjectList(projects);

      const users = usersListRes.data?.content || [];
      setUserList(users);
      setFilteredUserList(users);

      const environments = envsListRes.data?.content || [];
      setEnvironmentList(environments);
      setFilteredEnvironmentList(environments);

      const versions = versionsListRes.data?.content || [];
      setVersionList(versions);
      setFilteredVersionList(versions);

      setAutocompleteCached(true);
      console.log('Autocomplete lists loaded and cached');
    } catch (error) {
      console.error('Error loading autocomplete lists:', error);
    }
  };

  // Load data on component mount (only active tab)
  const loadData = useCallback(async () => {
    await loadTabData(activeTab);
    // Load autocomplete lists in background (only if not cached)
    if (!autocompleteCached) {
      loadAutocompleteLists();
    }
  }, [activeTab, autocompleteCached]);

  const handleCreate = () => {
    setModalType('create');
    setCurrentRecord(null);
    setEditingAliases([]);
    setAliasInput('');
    form.resetFields();
    setModalVisible(true);
  };

  const handleEdit = (record) => {
    setModalType('edit');
    setCurrentRecord(record);

    // Convert aliases array for display
    let formData = { ...record };
    if (record.aliases && Array.isArray(record.aliases)) {
      setEditingAliases(record.aliases.map(alias => alias.alias || alias));
    } else {
      setEditingAliases([]);
    }
    setAliasInput('');

    form.setFieldsValue(formData);
    setModalVisible(true);
  };

  const addAlias = () => {
    if (aliasInput.trim()) {
      const newAliases = [...editingAliases, aliasInput.trim()];
      setEditingAliases(newAliases);
      setAliasInput('');
    }
  };

  const removeAlias = (aliasToRemove) => {
    const newAliases = editingAliases.filter(alias => alias !== aliasToRemove);
    setEditingAliases(newAliases);
  };

  const handleDelete = async (id) => {
    Modal.confirm({
      title: 'Confirm Delete',
      content: 'Are you sure you want to delete this record?',
      onOk: async () => {
        try {
          if (activeTab === 'users') await coreService.deleteUser(id);
          else if (activeTab === 'projects') await coreService.deleteProject(id);
          else if (activeTab === 'environments') await coreService.deleteEnvironment(id);
          else await coreService.deleteVersion(id);
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
      if (activeTab === 'users') {
        // Use editingAliases directly
        let userData = { ...values };
        userData.aliases = editingAliases.map(alias => ({ alias: alias }));

        if (modalType === 'create') {
          await coreService.createUser(userData);
        } else {
          await coreService.updateUser({ ...userData, id: currentRecord.id });
        }
      } else if (activeTab === 'projects') {
        if (modalType === 'create') await coreService.createProject(values);
        else await coreService.updateProject({ ...values, id: currentRecord.id });
      } else if (activeTab === 'environments') {
        if (modalType === 'create') await coreService.createEnvironment(values);
        else await coreService.updateEnvironment({ ...values, id: currentRecord.id });
      } else {
        if (modalType === 'create') await coreService.createVersion(values);
        else await coreService.updateVersion({ ...values, id: currentRecord.id });
      }
      loadData();
      message.success(`${modalType === 'create' ? 'Created' : 'Updated'} successfully`);
      setModalVisible(false);
    } catch (error) {
      message.error(`Failed to ${modalType}`);
    }
  };

  const handleTableChange = async (paginationInfo, filters, sorter, tab) => {
    try {
      setLoading(true);
      const page = paginationInfo.current - 1;
      const size = paginationInfo.pageSize;

      const params = { page, size };

      // Build filter query from active filters object
      const filterObj = activeFilters[tab];
      const activeFilterKeys = Object.keys(filterObj).filter(key => filterObj[key] && filterObj[key].trim());

      if (activeFilterKeys.length > 0) {
        const filterPairs = activeFilterKeys.map(key => `${key}:${filterObj[key]}`);
        params.filters = filterPairs.join(',');
      }

      // Add sort if specified
      if (sorter && sorter.field) {
        params.sort = sorter.field;
        params.direction = sorter.order === 'descend' ? 'DESC' : 'ASC';
      }

      console.log(`handleTableChange for ${tab}, params:`, params);

      let response;
      if (tab === 'users') {
        response = await coreService.getAllUsers(params);
        setUsers(response.data?.content || []);
      } else if (tab === 'projects') {
        response = await coreService.getAllProjects(params);
        setProjects(response.data?.content || []);
      } else if (tab === 'environments') {
        response = await coreService.getAllEnvironments(params);
        setEnvironments(response.data?.content || []);
      } else {
        response = await coreService.getAllVersions(params);
        setVersions(response.data?.content || []);
      }

      const newPaginationState = {
        current: paginationInfo.current,
        pageSize: size,
        total: response.data?.page?.totalElements || 0,
        totalPages: response.data?.page?.totalPages || 0
      };

      setPagination(prev => ({
        ...prev,
        [tab]: newPaginationState
      }));
    } catch (error) {
      console.error(`Error in handleTableChange for ${tab}:`, error);
      message.error('Failed to load page');
    } finally {
      setLoading(false);
    }
  };

  // Helper function to fetch data based on tab and filters
  const fetchTabData = useCallback(async (tab, filters) => {
    try {
      setLoading(true);
      const params = { page: 0, size: 10 };

      // Build filter query from object
      const activeFilterKeys = Object.keys(filters).filter(key => filters[key] && filters[key].trim());
      if (activeFilterKeys.length > 0) {
        const filterPairs = activeFilterKeys.map(key => `${key}:${filters[key]}`);
        params.filters = filterPairs.join(',');
      }

      let response;
      if (tab === 'users') {
        response = await coreService.getAllUsers(params);
        setUsers(response.data?.content || []);
      } else if (tab === 'projects') {
        response = await coreService.getAllProjects(params);
        setProjects(response.data?.content || []);
      } else if (tab === 'environments') {
        response = await coreService.getAllEnvironments(params);
        setEnvironments(response.data?.content || []);
      } else {
        response = await coreService.getAllVersions(params);
        setVersions(response.data?.content || []);
      }

      setPagination(prev => ({
        ...prev,
        [tab]: {
          current: 1,
          pageSize: 10,
          total: response.data?.page?.totalElements || 0,
          totalPages: response.data?.page?.totalPages || 0
        }
      }));
    } catch (error) {
      console.error('Error fetching data:', error);
      message.error('Failed to load data');
    } finally {
      setLoading(false);
    }
  }, []);

  // Update filter for a specific property
  const updatePropertyFilter = useCallback(async (tab, property, value) => {
    const newFilters = {
      ...activeFilters[tab],
      [property]: value
    };

    setActiveFilters(prev => ({
      ...prev,
      [tab]: newFilters
    }));

    // Reload data with new filters
    await fetchTabData(tab, newFilters);
  }, [activeFilters, fetchTabData]);

  // Clear all filters for a tab
  const clearAllFilters = useCallback(async (tab) => {
    const emptyFilters = Object.keys(activeFilters[tab]).reduce((acc, key) => {
      acc[key] = '';
      return acc;
    }, {});

    setActiveFilters(prev => ({
      ...prev,
      [tab]: emptyFilters
    }));

    // Reload all data using helper
    await fetchTabData(tab, emptyFilters);
  }, [activeFilters, fetchTabData]);

  const handleMergeUsers = async (sourceUserId, targetUserId) => {
    try {
      setLoading(true);
      // Merge source user into target user
      // This will move all aliases from source to target
      await coreService.mergeUsers(sourceUserId, targetUserId);
      message.success('Users merged successfully!');
      setMergeModalVisible(false);
      setSelectedUserIds([]);
      loadData();
    } catch (error) {
      console.error('Error merging users:', error);
      message.error('Failed to merge users');
    } finally {
      setLoading(false);
    }
  };

  const handleProjectSearch = useCallback((searchValue) => {
    if (!searchValue || searchValue.trim() === '') {
      setFilteredProjectList(projectList);
    } else {
      const searchLower = searchValue.toLowerCase();
      const filtered = projectList.filter(project => {
        return (
          project.code?.toLowerCase().includes(searchLower) ||
          project.name?.toLowerCase().includes(searchLower)
        );
      });
      setFilteredProjectList(filtered);
    }
  }, [projectList]);

  const handleUserSearch = useCallback((searchValue) => {
    if (!searchValue || searchValue.trim() === '') {
      setFilteredUserList(userList);
    } else {
      const searchLower = searchValue.toLowerCase();
      const filtered = userList.filter(user => {
        return (
          user.username?.toLowerCase().includes(searchLower) ||
          user.aliases?.some(alias => alias.alias?.toLowerCase().includes(searchLower))
        );
      });
      setFilteredUserList(filtered);
    }
  }, [userList]);


  const handleEnvironmentSearch = useCallback((searchValue) => {
    if (!searchValue || searchValue.trim() === '') {
      setFilteredEnvironmentList(environmentList);
    } else {
      const searchLower = searchValue.toLowerCase();
      const filtered = environmentList.filter(env => {
        return (
          env.code?.toLowerCase().includes(searchLower) ||
          env.name?.toLowerCase().includes(searchLower) ||
          env.project?.toLowerCase().includes(searchLower)
        );
      });
      setFilteredEnvironmentList(filtered);
    }
  }, [environmentList]);

  const handleVersionSearch = useCallback((searchValue) => {
    if (!searchValue || searchValue.trim() === '') {
      setFilteredVersionList(versionList);
    } else {
      const searchLower = searchValue.toLowerCase();
      const filtered = versionList.filter(version => {
        return (
          version.code?.toLowerCase().includes(searchLower) ||
          version.name?.toLowerCase().includes(searchLower) ||
          version.project?.toLowerCase().includes(searchLower)
        );
      });
      setFilteredVersionList(filtered);
    }
  }, [versionList]);

  const userColumns = [
    {
      title: () => (
        <div>
          <div>Username</div>
          <AutoComplete
            placeholder="Filter..."
            value={activeFilters.users.username}
            onSearch={text => handleUserSearch(text)}
            onSelect={value => updatePropertyFilter('users', 'username', value)}
            onChange={value => updatePropertyFilter('users', 'username', value)}
            onFocus={() => setFilteredUserList(userList)}
            options={userList
              .map(user => user.username)
              .filter((v, i, a) => a.indexOf(v) === i)
              .map(username => ({
                label: username,
                value: username
              }))}
            filterOption={false}
            style={{ width: '100%', marginTop: '4px' }}
          />
        </div>
      ),
      dataIndex: 'username',
      key: 'username',
      sorter: true,
      render: (text) => <><UserOutlined /> {text}</>
    },
    {
      title: () => (
        <div>
          <div>Aliases</div>
          <AutoComplete
            placeholder="Filter..."
            value={activeFilters.users.alias}
            onSearch={text => handleUserSearch(text)}
            onSelect={value => updatePropertyFilter('users', 'alias', value)}
            onChange={value => updatePropertyFilter('users', 'alias', value)}
            onFocus={() => setFilteredUserList(userList)}
            options={userList
              .flatMap(user => user.aliases || [])
              .map(alias => alias.alias)
              .filter((v, i, a) => a.indexOf(v) === i)
              .map(alias => ({
                label: alias,
                value: alias
              }))}
            filterOption={false}
            style={{ width: '100%', marginTop: '4px' }}
          />
        </div>
      ),
      dataIndex: 'aliases',
      key: 'aliases',
      render: (aliases) => (
        <>
          {aliases && aliases.length > 0 ? (
            aliases.map((alias) => (
              <Tag key={alias.id} color="blue" style={{ marginBottom: '4px' }}>
                {alias.alias}
              </Tag>
            ))
          ) : (
            <span style={{ color: '#999' }}>No aliases</span>
          )}
        </>
      ),
    },
    {
      title: 'ID',
      dataIndex: 'id',
      key: 'id',
      width: 80
    },
    {
      title: 'Actions',
      key: 'actions',
      width: 100,
      render: (_, record) => (
        <Space>
          <Button icon={<EditOutlined />} onClick={() => handleEdit(record)} size="small" />
          <Button icon={<DeleteOutlined />} onClick={() => handleDelete(record.id)} danger size="small" />
        </Space>
      ),
    },
  ];

  const projectColumns = [
    {
      title: () => (
        <div>
          <div>Code</div>
          <AutoComplete
            placeholder="Filter..."
            value={activeFilters.projects.code}
            onSearch={text => handleProjectSearch(text)}
            onSelect={value => updatePropertyFilter('projects', 'code', value)}
            onChange={value => updatePropertyFilter('projects', 'code', value)}
            onFocus={() => setFilteredProjectList(projectList)}
            options={projectList
              .map(p => p.code)
              .filter((v, i, a) => a.indexOf(v) === i)
              .map(code => ({
                label: code,
                value: code
              }))}
            filterOption={false}
            style={{ width: '100%', marginTop: '4px' }}
          />
        </div>
      ),
      dataIndex: 'code',
      key: 'code',
      sorter: true,
      render: (text) => <Tag color="blue">{text}</Tag>
    },
    {
      title: () => (
        <div>
          <div>Name</div>
          <AutoComplete
            placeholder="Filter..."
            value={activeFilters.projects.name}
            onSearch={text => handleProjectSearch(text)}
            onSelect={value => updatePropertyFilter('projects', 'name', value)}
            onChange={value => updatePropertyFilter('projects', 'name', value)}
            onFocus={() => setFilteredProjectList(projectList)}
            options={projectList
              .map(p => p.name)
              .filter((v, i, a) => a.indexOf(v) === i)
              .map(name => ({
                label: name,
                value: name
              }))}
            filterOption={false}
            style={{ width: '100%', marginTop: '4px' }}
          />
        </div>
      ),
      dataIndex: 'name',
      key: 'name',
      sorter: true
    },
    {
      title: 'ID',
      dataIndex: 'id',
      key: 'id',
      width: 80
    },
    {
      title: 'Actions',
      key: 'actions',
      width: 100,
      render: (_, record) => (
        <Space>
          <Button icon={<EditOutlined />} onClick={() => handleEdit(record)} size="small" />
          <Button icon={<DeleteOutlined />} onClick={() => handleDelete(record.id)} danger size="small" />
        </Space>
      ),
    },
  ];

  const envColumns = [
    {
      title: () => (
        <div>
          <div>Code</div>
          <AutoComplete
            placeholder="Filter..."
            value={activeFilters.environments.code}
            onSearch={text => handleEnvironmentSearch(text)}
            onSelect={value => updatePropertyFilter('environments', 'code', value)}
            onChange={value => updatePropertyFilter('environments', 'code', value)}
            onFocus={() => setFilteredEnvironmentList(environmentList)}
            options={environmentList
              .map(e => e.code)
              .filter((v, i, a) => a.indexOf(v) === i)
              .map(code => ({
                label: code,
                value: code
              }))}
            filterOption={false}
            style={{ width: '100%', marginTop: '4px' }}
          />
        </div>
      ),
      dataIndex: 'code',
      key: 'code',
      sorter: true,
      render: (text) => <Tag color="green">{text}</Tag>
    },
    {
      title: () => (
        <div>
          <div>Name</div>
          <AutoComplete
            placeholder="Filter..."
            value={activeFilters.environments.name}
            onSearch={text => handleEnvironmentSearch(text)}
            onSelect={value => updatePropertyFilter('environments', 'name', value)}
            onChange={value => updatePropertyFilter('environments', 'name', value)}
            onFocus={() => setFilteredEnvironmentList(environmentList)}
            options={environmentList
              .map(e => e.name)
              .filter((v, i, a) => a.indexOf(v) === i)
              .map(name => ({
                label: name,
                value: name
              }))}
            filterOption={false}
            style={{ width: '100%', marginTop: '4px' }}
          />
        </div>
      ),
      dataIndex: 'name',
      key: 'name',
      sorter: true
    },
    {
      title: () => (
        <div>
          <div>Project</div>
          <AutoComplete
            placeholder="Filter..."
            value={activeFilters.environments.project}
            onSearch={text => {
              handleProjectSearch(text);
            }}
            onSelect={value => {
              updatePropertyFilter('environments', 'project', value);
            }}
            onChange={value => {
              setActiveFilters(prev => ({
                ...prev,
                environments: { ...prev.environments, project: value }
              }));
            }}
            onFocus={() => setFilteredProjectList(projectList)}
            options={filteredProjectList
              .map(p => p.code)
              .filter((v, i, a) => a.indexOf(v) === i)
              .map(code => ({
                label: code,
                value: code
              }))}
            filterOption={false}
            style={{ width: '100%', marginTop: '4px' }}
          />
        </div>
      ),
      dataIndex: 'project',
      key: 'project',
      sorter: true
    },
    {
      title: 'Actions',
      key: 'actions',
      width: 100,
      render: (_, record) => (
        <Space>
          <Button icon={<EditOutlined />} onClick={() => handleEdit(record)} size="small" />
          <Button icon={<DeleteOutlined />} onClick={() => handleDelete(record.id)} danger size="small" />
        </Space>
      ),
    },
  ];

  const versionColumns = [
    {
      title: () => (
        <div>
          <div>Code</div>
          <AutoComplete
            placeholder="Filter..."
            value={activeFilters.versions.code}
            onSearch={text => handleVersionSearch(text)}
            onSelect={value => updatePropertyFilter('versions', 'code', value)}
            onChange={value => updatePropertyFilter('versions', 'code', value)}
            onFocus={() => setFilteredVersionList(versionList)}
            options={versionList
              .map(v => v.code)
              .filter((v, i, a) => a.indexOf(v) === i)
              .map(code => ({
                label: code,
                value: code
              }))}
            filterOption={false}
            style={{ width: '100%', marginTop: '4px' }}
          />
        </div>
      ),
      dataIndex: 'code',
      key: 'code',
      sorter: true,
      render: (text) => <Tag color="purple">{text}</Tag>
    },
    {
      title: () => (
        <div>
          <div>Name</div>
          <AutoComplete
            placeholder="Filter..."
            value={activeFilters.versions.name}
            onSearch={text => handleVersionSearch(text)}
            onSelect={value => updatePropertyFilter('versions', 'name', value)}
            onChange={value => updatePropertyFilter('versions', 'name', value)}
            onFocus={() => setFilteredVersionList(versionList)}
            options={versionList
              .map(v => v.name)
              .filter((v, i, a) => a.indexOf(v) === i)
              .map(name => ({
                label: name,
                value: name
              }))}
            filterOption={false}
            style={{ width: '100%', marginTop: '4px' }}
          />
        </div>
      ),
      dataIndex: 'name',
      key: 'name',
      sorter: true
    },
    {
      title: () => (
        <div>
          <div>Project</div>
          <AutoComplete
            placeholder="Filter..."
            value={activeFilters.versions.project}
            onSearch={text => {
              handleProjectSearch(text);
            }}
            onSelect={value => {
              updatePropertyFilter('versions', 'project', value);
            }}
            onChange={value => {
              setActiveFilters(prev => ({
                ...prev,
                versions: { ...prev.versions, project: value }
              }));
            }}
            onFocus={() => setFilteredProjectList(projectList)}
            options={filteredProjectList
              .map(p => p.code)
              .filter((v, i, a) => a.indexOf(v) === i)
              .map(code => ({
                label: code,
                value: code
              }))}
            filterOption={false}
            style={{ width: '100%', marginTop: '4px' }}
          />
        </div>
      ),
      dataIndex: 'project',
      key: 'project',
      sorter: true
    },
    {
      title: 'Actions',
      key: 'actions',
      width: 100,
      render: (_, record) => (
        <Space>
          <Button icon={<EditOutlined />} onClick={() => handleEdit(record)} size="small" />
          <Button icon={<DeleteOutlined />} onClick={() => handleDelete(record.id)} danger size="small" />
        </Space>
      ),
    },
  ];

  const renderForm = () => {
    if (activeTab === 'users') {
      return (
        <>
          <Form.Item name="username" label="Username" rules={[{ required: true }]}>
            <Input placeholder="john.doe" />
          </Form.Item>
          <Form.Item label="Aliases">
            <Space direction="vertical" style={{ width: '100%' }}>
              <Input
                placeholder="Enter alias and press Add"
                value={aliasInput}
                onChange={(e) => setAliasInput(e.target.value)}
                onKeyPress={(e) => {
                  if (e.key === 'Enter') {
                    e.preventDefault();
                    addAlias();
                  }
                }}
                addonAfter={
                  <Button type="primary" size="small" onClick={addAlias}>
                    Add
                  </Button>
                }
              />
              {editingAliases.length > 0 && (
                <Space wrap>
                  {editingAliases.map((alias, index) => (
                    <Tag
                      key={index}
                      color="blue"
                      closable
                      onClose={() => removeAlias(alias)}
                      style={{ marginBottom: '4px' }}
                    >
                      {alias}
                    </Tag>
                  ))}
                </Space>
              )}
            </Space>
          </Form.Item>
        </>
      );
    }
    if (activeTab === 'projects') {
      return (
        <>
          <Form.Item name="code" label="Code" rules={[{ required: true }]}>
            <Input placeholder="PROJ-001" />
          </Form.Item>
          <Form.Item name="name" label="Name" rules={[{ required: true }]}>
            <Input placeholder="My Project" />
          </Form.Item>
        </>
      );
    }
    if (activeTab === 'environments') {
      return (
        <>
          <Form.Item name="code" label="Code" rules={[{ required: true }]}>
            <Input placeholder="dev" />
          </Form.Item>
          <Form.Item name="name" label="Name" rules={[{ required: true }]}>
            <Input placeholder="Development" />
          </Form.Item>
          <Form.Item name="project" label="Project Code" rules={[{ required: true }]}>
            <AutoComplete
              placeholder="Type to search projects... (code or name)"
              onSearch={handleProjectSearch}
              onFocus={() => setFilteredProjectList(projectList)} // Show all when focused
              options={filteredProjectList.map(project => ({
                label: `${project.code} - ${project.name}`,
                value: project.code,
                data: project
              }))}
              filterOption={false}
              notFoundContent={filteredProjectList.length === 0 ? 'No projects found' : null}
            />
          </Form.Item>
        </>
      );
    }
    return (
      <>
        <Form.Item name="code" label="Code" rules={[{ required: true }]}>
          <Input placeholder="v1.0.0" />
        </Form.Item>
        <Form.Item name="name" label="Name" rules={[{ required: true }]}>
          <Input placeholder="Version 1.0.0" />
        </Form.Item>
        <Form.Item name="project" label="Project Code" rules={[{ required: true }]}>
          <AutoComplete
            placeholder="Type to search projects... (code or name)"
            onSearch={handleProjectSearch}
            onFocus={() => setFilteredProjectList(projectList)} // Show all when focused
            options={filteredProjectList.map(project => ({
              label: `${project.code} - ${project.name}`,
              value: project.code,
              data: project
            }))}
            filterOption={false}
            notFoundContent={filteredProjectList.length === 0 ? 'No projects found' : null}
          />
        </Form.Item>
      </>
    );
  };

  return (
    <div>
      <Card
        title={<><SettingOutlined /> Core Administration</>}
        extra={
          <Space>
            <Button icon={<ReloadOutlined />} onClick={loadData}>Refresh</Button>
            <Button type="primary" icon={<PlusOutlined />} onClick={handleCreate}>
              Add {activeTab.slice(0, -1)}
            </Button>
          </Space>
        }
      >
        <Tabs activeKey={activeTab} onChange={(tab) => {
          setActiveTab(tab);
          loadTabData(tab);
        }}>
          <TabPane tab={<><UserOutlined /> Users</>} key="users">
            <Space direction="vertical" style={{ width: '100%' }}>
              <Space>
                {selectedUserIds.length === 2 && (
                  <Button
                    type="primary"
                    danger
                    onClick={() => setMergeModalVisible(true)}
                  >
                    Merge {selectedUserIds.length} Users
                  </Button>
                )}
                {selectedUserIds.length > 0 && selectedUserIds.length !== 2 && (
                  <span style={{ color: '#999' }}>
                    Select exactly 2 users to merge (currently selected: {selectedUserIds.length})
                  </span>
                )}
              </Space>
              <Table
                columns={userColumns}
                dataSource={users}
                rowKey="id"
                loading={loading}
                rowSelection={{
                  selectedRowKeys: selectedUserIds,
                  onChange: (selectedKeys) => setSelectedUserIds(selectedKeys),
                  selections: [
                    Table.SELECTION_ALL,
                    Table.SELECTION_NONE,
                  ],
                }}
                pagination={{
                  current: pagination.users.current,
                  pageSize: pagination.users.pageSize,
                  total: pagination.users.total,
                  showSizeChanger: true,
                  showTotal: (total, range) => `${range[0]}-${range[1]} of ${total} items`,
                  pageSizeOptions: ['5', '10', '20', '50'],
                }}
                onChange={(pag, filters, sorter) => handleTableChange(pag, filters, sorter, 'users')}
              />
            </Space>
          </TabPane>
          <TabPane tab={<><ProjectOutlined /> Projects</>} key="projects">
            <Space direction="vertical" style={{ width: '100%' }}>
              <Table
                columns={projectColumns}
                dataSource={projects}
                rowKey="id"
                loading={loading}
                pagination={{
                  current: pagination.projects.current,
                  pageSize: pagination.projects.pageSize,
                  total: pagination.projects.total,
                  showSizeChanger: true,
                  showTotal: (total, range) => `${range[0]}-${range[1]} of ${total} items`,
                  pageSizeOptions: ['5', '10', '20', '50'],
                }}
                onChange={(pag, filters, sorter) => handleTableChange(pag, filters, sorter, 'projects')}
              />
            </Space>
          </TabPane>
          <TabPane tab={<><EnvironmentOutlined /> Environments</>} key="environments">
            <Space direction="vertical" style={{ width: '100%' }}>
              <Table
                columns={envColumns}
                dataSource={environments}
                rowKey="id"
                loading={loading}
                pagination={{
                  current: pagination.environments.current,
                  pageSize: pagination.environments.pageSize,
                  total: pagination.environments.total,
                  showSizeChanger: true,
                  showTotal: (total, range) => `${range[0]}-${range[1]} of ${total} items`,
                  pageSizeOptions: ['5', '10', '20', '50'],
                }}
                onChange={(pag, filters, sorter) => handleTableChange(pag, filters, sorter, 'environments')}
              />
            </Space>
          </TabPane>
          <TabPane tab={<><TagOutlined /> Versions</>} key="versions">
            <Space direction="vertical" style={{ width: '100%' }}>
              <Table
                columns={versionColumns}
                dataSource={versions}
                rowKey="id"
                loading={loading}
                pagination={{
                  current: pagination.versions.current,
                  pageSize: pagination.versions.pageSize,
                  total: pagination.versions.total,
                  showSizeChanger: true,
                  showTotal: (total, range) => `${range[0]}-${range[1]} of ${total} items`,
                  pageSizeOptions: ['5', '10', '20', '50'],
                }}
                onChange={(pag, filters, sorter) => handleTableChange(pag, filters, sorter, 'versions')}
              />
            </Space>
          </TabPane>
          <TabPane tab="Statistics" key="stats">
            <Row gutter={[16, 16]}>
              <Col span={6}>
                <Card><Statistic title="Total Users" value={pagination.users.total} prefix={<UserOutlined />} /></Card>
              </Col>
              <Col span={6}>
                <Card><Statistic title="Projects" value={pagination.projects.total} prefix={<ProjectOutlined />} /></Card>
              </Col>
              <Col span={6}>
                <Card><Statistic title="Environments" value={pagination.environments.total} prefix={<EnvironmentOutlined />} /></Card>
              </Col>
              <Col span={6}>
                <Card><Statistic title="Versions" value={pagination.versions.total} prefix={<TagOutlined />} /></Card>
              </Col>
            </Row>
          </TabPane>
        </Tabs>
      </Card>

      <Modal
        title={`${modalType === 'create' ? 'Create' : 'Edit'} ${activeTab.slice(0, -1)}`}
        open={modalVisible}
        onCancel={() => setModalVisible(false)}
        onOk={() => form.submit()}
        width={600}
      >
        <Form form={form} layout="vertical" onFinish={handleSubmit}>
          {renderForm()}
        </Form>
      </Modal>

      <Modal
        title="Merge Users"
        open={mergeModalVisible}
        onCancel={() => setMergeModalVisible(false)}
        width={600}
        footer={[
          <Button key="cancel" onClick={() => setMergeModalVisible(false)}>
            Cancel
          </Button>,
        ]}
      >
        <p>Select which user should be the primary user. The other user's aliases will be merged into it.</p>
        {selectedUserIds.length === 2 && (
          <div style={{ marginTop: '20px' }}>
            {users
              .filter(u => selectedUserIds.includes(u.id))
              .map(user => (
                <div key={user.id} style={{ marginBottom: '10px' }}>
                  <Button
                    block
                    onClick={() => {
                      const sourceId = selectedUserIds.find(id => id !== user.id);
                      handleMergeUsers(sourceId, user.id);
                    }}
                    type="primary"
                  >
                    Keep <strong>{user.username}</strong> and merge other user's aliases into it
                  </Button>
                </div>
              ))}
          </div>
        )}
      </Modal>
    </div>
  );
};

export default CorePage;


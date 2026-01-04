import React from 'react';
import { Layout, Space, Avatar, Dropdown, Button } from 'antd';
import { UserOutlined, LogoutOutlined, SettingOutlined } from '@ant-design/icons';

const { Header } = Layout;

const AppHeader = () => {
  const menuItems = [
    {
      key: 'profile',
      icon: <UserOutlined />,
      label: 'Profile',
    },
    {
      key: 'settings',
      icon: <SettingOutlined />,
      label: 'Settings',
    },
    {
      type: 'divider',
    },
    {
      key: 'logout',
      icon: <LogoutOutlined />,
      label: 'Logout',
      danger: true,
    },
  ];

  return (
    <Header
      style={{
        background: '#fff',
        padding: '0 24px',
        boxShadow: '0 2px 8px rgba(0,0,0,0.1)',
        display: 'flex',
        justifyContent: 'space-between',
        alignItems: 'center',
      }}
    >
      <h2 style={{ margin: 0 }}>Quality Metrics Dashboard</h2>
      <Space size="large">
        <Dropdown menu={{ items: menuItems }}>
          <Button type="text" icon={<Avatar icon={<UserOutlined />} />} />
        </Dropdown>
      </Space>
    </Header>
  );
};

export default AppHeader;


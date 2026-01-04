import React from 'react';
import { Layout, Menu } from 'antd';
import { useNavigate, useLocation } from 'react-router-dom';
import {
  DashboardOutlined,
  BuildOutlined,
  LineChartOutlined,
  CheckSquareOutlined,
  CodeOutlined,
  CloudServerOutlined,
  ExperimentOutlined,
  SettingOutlined,
  ApiOutlined,
  ThunderboltOutlined,
} from '@ant-design/icons';

const { Sider } = Layout;

const Sidebar = () => {
  const navigate = useNavigate();
  const location = useLocation();

  const items = [
    {
      key: '/',
      icon: <DashboardOutlined />,
      label: 'Dashboard',
      onClick: () => navigate('/'),
    },
    {
      key: 'modules',
      label: 'Modules',
      type: 'group',
    },
    {
      key: '/git',
      icon: <CodeOutlined />,
      label: 'Git',
      onClick: () => navigate('/git'),
    },
    {
      key: '/kube',
      icon: <CloudServerOutlined />,
      label: 'Kubernetes',
      onClick: () => navigate('/kube'),
    },
    {
      key: '/pipeline',
      icon: <BuildOutlined />,
      label: 'Pipeline',
      onClick: () => navigate('/pipeline'),
    },
    {
      key: '/tms',
      icon: <ExperimentOutlined />,
      label: 'Test Management',
      onClick: () => navigate('/tms'),
    },
    {
      key: '/spec',
      icon: <ApiOutlined />,
      label: 'API Specifications',
      onClick: () => navigate('/spec'),
    },
    {
      key: '/metric',
      icon: <ThunderboltOutlined />,
      label: 'Metrics',
      onClick: () => navigate('/metric'),
    },
    {
      key: 'analytics',
      label: 'Analytics',
      type: 'group',
    },
    {
      key: '/quality',
      icon: <LineChartOutlined />,
      label: 'Quality',
      onClick: () => navigate('/quality'),
    },
    {
      key: '/tests',
      icon: <CheckSquareOutlined />,
      label: 'Tests',
      onClick: () => navigate('/tests'),
    },
    {
      key: 'admin',
      label: 'Administration',
      type: 'group',
    },
    {
      key: '/core',
      icon: <SettingOutlined />,
      label: 'Core Admin',
      onClick: () => navigate('/core'),
    },
  ];

  return (
    <Sider
      width={200}
      style={{
        overflow: 'auto',
        height: '100vh',
        position: 'fixed',
        left: 0,
        top: 0,
        bottom: 0,
      }}
    >
      <div
        style={{
          padding: '16px',
          color: 'white',
          fontSize: '18px',
          fontWeight: 'bold',
          marginBottom: '20px',
        }}
      >
        ATHENA
      </div>
      <Menu
        theme="dark"
        mode="inline"
        selectedKeys={[location.pathname]}
        items={items}
      />
    </Sider>
  );
};

export default Sidebar;


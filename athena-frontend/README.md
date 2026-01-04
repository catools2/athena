# Athena Frontend

React-based dashboard for visualizing and interacting with Athena quality metrics across all modules.

## Features

### Core Modules
- **Dashboard**: Comprehensive view of quality metrics across all modules
- **Git Module**: Manage git repositories and commits with full CRUD operations
- **Kubernetes Module**: Monitor and manage Kubernetes pods and containers
- **Pipeline Module**: Monitor CI/CD pipeline health and performance
- **TMS Module**: Complete test management system with items, cycles, and executions
- **API Specifications**: Manage and visualize OpenAPI specifications
- **Metrics Module**: Track and analyze performance metrics and actions

### Analytics
- **Quality Dashboard**: Track code quality metrics from git analysis
- **Test Analytics**: Comprehensive test execution analytics and reporting

### Administration
- **Core Admin**: Manage users, projects, environments, and versions
- **CRUD Operations**: Full create, read, update, delete functionality for all entities
- **Real-time Updates**: WebSocket-based real-time metric updates

## Module Details

### Git Module (`/git`)
- Repository management (CRUD)
- Commit tracking and history
- Author statistics
- Repository synchronization status

### Kubernetes Module (`/kube`)
- Pod management and monitoring
- Container status tracking
- Namespace organization
- Project-based pod filtering

### TMS Module (`/tms`)
- Test item management
- Test cycle planning
- Test execution tracking
- Status and priority management
- Item type configuration

### Pipeline Module (`/pipeline`)
- CI/CD pipeline monitoring
- Execution tracking
- Build and deployment analytics

### API Specifications Module (`/spec`)
- OpenAPI specification management
- API endpoint tracking
- Version control for specs
- Project-based organization

### Metrics Module (`/metric`)
- Performance metric tracking
- Action monitoring
- Duration analytics
- Trend analysis

### Core Administration Module (`/core`)
- **User Management**: Create, update, delete users
- **Project Management**: Full project lifecycle management
- **Environment Management**: Development, staging, production environments
- **Version Management**: Application version tracking

## Installation

```bash
# Install dependencies
npm install

# Start development server
npm start

# Build for production
npm run build

# Run tests
npm test
```

## Environment Configuration

Create a `.env` file in the root directory:

```bash
REACT_APP_API_URL=http://localhost:8080
REACT_APP_WS_URL=ws://localhost:8080
REACT_APP_ENV=development
```

## Project Structure

```
athena-frontend/
├── public/
│   ├── index.html
│   └── favicon.ico
├── src/
│   ├── components/
│   │   ├── Dashboard/
│   │   ├── Sidebar/
│   │   ├── Header/
│   │   └── Common/
│   ├── pages/
│   │   ├── HomePage.jsx
│   │   ├── GitPage.jsx
│   │   ├── KubePage.jsx
│   │   ├── PipelinePage.jsx
│   │   ├── TmsPage.jsx
│   │   ├── SpecPage.jsx
│   │   ├── MetricPage.jsx
│   │   ├── CorePage.jsx (Administration)
│   │   ├── QualityPage.jsx
│   │   ├── TestsPage.jsx
│   │   └── NotFoundPage.jsx
│   ├── services/
│   │   ├── api.js
│   │   ├── gitService.js
│   │   ├── kubeService.js
│   │   ├── pipelineService.js
│   │   ├── tmsService.js
│   │   ├── specService.js
│   │   ├── metricService.js
│   │   ├── coreService.js
│   │   ├── qualityService.js
│   │   └── testService.js
│   ├── hooks/
│   │   ├── useApi.js
│   │   └── useMetrics.js
│   ├── utils/
│   │   ├── formatters.js
│   │   └── constants.js
│   ├── styles/
│   │   ├── index.css
│   │   └── theme.js
│   ├── App.jsx
│   └── index.js
├── Dockerfile
├── docker-compose.yml
├── .env.example
├── package.json
└── README.md
```

## API Integration

The frontend communicates with Athena backend services via REST API:

- **Core Service**: `/api/core/*` - Users, Projects, Environments, Versions
- **Git Service**: `/api/git/*` - Repositories, Commits
- **Kubernetes Service**: `/api/kube/*` - Pods, Containers
- **Pipeline Service**: `/api/pipeline/*` - Pipelines, Executions
- **TMS Service**: `/api/tms/*` - Items, Cycles, Executions, Status, Priority
- **Specification Service**: `/api/spec/*` - API Specs, Paths
- **Metric Service**: `/api/metric/*` - Metrics, Actions
- **Quality Service**: `/api/quality/*` - Quality Metrics
- **Test Service**: `/api/test/*` - Test Results

## CRUD Operations

All module pages support full CRUD operations:

- **Create**: Add new entities via modal forms
- **Read**: View all entities in paginated tables with search/filter
- **Update**: Edit existing entities with pre-filled forms
- **Delete**: Remove entities with confirmation dialogs

## Features by Module

### Common Features
- ✅ Tabbed interface for different entity types
- ✅ Data tables with pagination and sorting
- ✅ Modal forms for create/edit operations
- ✅ Delete confirmation dialogs
- ✅ Statistics and analytics tabs
- ✅ Real-time data refresh
- ✅ Search and filter capabilities

### Git Module
- Repository CRUD operations
- Commit history tracking
- Author statistics
- Repository sync status

### Kubernetes Module
- Pod lifecycle management
- Container monitoring
- Namespace filtering
- Status-based pod grouping

### TMS Module
- Test item management with types, status, priority
- Test cycle planning and tracking
- Test execution recording
- Comprehensive test statistics

### Core Administration
- User account management
- Project configuration
- Environment setup
- Version tracking

## Development

### Code Style

- ESLint for code linting
- Prettier for code formatting
- React best practices
- Ant Design component library

### Testing

- Unit tests with Jest and React Testing Library
- Integration tests for API interactions
- E2E tests for critical user flows

## Docker Deployment

```bash
# Build image
docker build -t athena-frontend:2.0.0 .

# Run container
docker run -p 3000:3000 athena-frontend:2.0.0

# Using docker-compose
docker-compose up -d
```

## Browser Support

- Chrome (latest)
- Firefox (latest)
- Safari (latest)
- Edge (latest)

## Technologies Used

- **React 18** - UI framework
- **React Router 6** - Routing
- **Ant Design 5** - UI component library
- **Axios** - HTTP client
- **Recharts** - Data visualization
- **dayjs** - Date handling

## License

Apache License 2.0


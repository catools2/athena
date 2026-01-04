# Athena Locust Performance Tests

## Run Locally

```bash
locust -f ./tasks/athena_core.py --host=http://localhost:8080 --headless --users=100 --spawn-rate=10 --run-time 10s --loglevel DEBUG
```

## Project Structure

- `tasks/`: User actions and endpoints
- `users/`: Locust `HttpUser` classes combining wait times and task sets
- `config/`: Test constants and env settings
- `data/`: Feeders or external data files

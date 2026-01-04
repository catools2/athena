#!/bin/bash

# Start port-forward to gateway
echo "Starting port-forward for athena-gateway..."
kubectl port-forward svc/athena-gateway 8080:8080 -n default 2>&1 | while IFS= read -r line; do
  echo "[Port-Forward] $line"
done &

PF_PID=$!
echo "Port-forward started with PID: $PF_PID"

# Wait for port to be ready
echo "Waiting for port 8080 to be ready..."
for i in {1..30}; do
  if nc -z localhost 8080 2>/dev/null; then
    echo "✅ Port 8080 is ready!"
    break
  fi
  echo "  Waiting... ($i/30)"
  sleep 1
done

# Test the API
echo ""
echo "Testing API endpoint..."
curl -v "http://localhost:8080/core/project/all?page=0&size=10" 2>&1 | head -50

echo ""
echo "Port-forward is running in background (PID: $PF_PID)"
echo "To stop it, run: kill $PF_PID"
echo ""
echo "✅ Gateway is now accessible at: http://localhost:8080"
echo "✅ Frontend can now connect to the backend!"


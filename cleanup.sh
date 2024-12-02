#!/bin/bash

# Variables
FRONTEND_PORT=3000
BACKEND_PORT=8080
CHAT_PORT=4444

echo "🛑 Cleaning up processes for frontend and backend..."

# Function to kill processes running on specific ports
kill_process_on_port() {
    local PORT=$1
    PID=$(lsof -ti:$PORT)
    if [ -n "$PID" ]; then
        echo "🔧 Stopping process on port $PORT (PID: $PID)..."
        kill $PID
        if [ $? -eq 0 ]; then
            echo "✅ Process on port $PORT stopped successfully."
        else
            echo "❌ Failed to stop process on port $PORT."
        fi
    else
        echo "ℹ️ No process found running on port $PORT."
    fi
}

# Kill the frontend process
kill_process_on_port $FRONTEND_PORT

# Kill the backend process
kill_process_on_port $BACKEND_PORT

# Kill the chat process
kill_process_on_port $CHAT_PORT

echo "✅ Cleanup completed. All processes stopped."

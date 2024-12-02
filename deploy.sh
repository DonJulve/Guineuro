#!/bin/bash

# Variables
FRONTEND_DIR="./frontend"
BACKEND_DIR="./backend"
CHAT_DIR="./guineuro-chat"
FRONTEND_PORT=3000
BACKEND_PORT=8080
CHAT_PORT=4444
API_URL="http://localhost:$BACKEND_PORT"
ENV_FILE="$FRONTEND_DIR/.env"

echo "🚀 Starting local deployment for frontend and backend..."

# 1. Create the .env file for the frontend
echo "🔧 Creating .env file for the frontend..."
echo "REACT_APP_API_URL=$API_URL" > $ENV_FILE
if [ -f $ENV_FILE ]; then
    echo "✅ .env file created successfully with REACT_APP_API_URL=$API_URL"
else
    echo "❌ Failed to create .env file. Exiting..."
    exit 1
fi

# 2. Start the Backend (Spring Boot with Gradle)
echo "🔧 Starting backend (Spring Boot)..."
cd $BACKEND_DIR || exit
./gradlew bootRun >/dev/null 2>&1 &
BACKEND_PID=$!

# Wait a few seconds to allow the backend to start
echo "⏳ Waiting for the backend to start on port $BACKEND_PORT..."
sleep 10

# Check if the backend is running
if lsof -i:$BACKEND_PORT > /dev/null; then
    echo "✅ Backend started successfully on port $BACKEND_PORT."
else
    echo "❌ Failed to start the backend. Exiting..."
    kill $BACKEND_PID
    exit 1
fi

# 3. Start the Chat (Spring Boot with Gradle)
echo "🔧 Starting chat (Spring Boot)..."
cd ..
cd $CHAT_DIR || exit
./gradlew bootRun >/dev/null 2>&1 &
CHAT_PID=$!

# Wait a few seconds to allow the backend to start
echo "⏳ Waiting for the chat to start on port $CHAT_PORT..."
sleep 20

# Check if the backend is running
if lsof -i:$CHAT_PORT > /dev/null; then
    echo "✅ Chat started successfully on port $CHAT_PORT."
else
    echo "❌ Failed to start the chat. Exiting..."
    kill $CHAT_PID
    exit 1
fi

# 4. Start the Frontend (React)
echo "🔧 Starting frontend (React)..."
cd ..
cd $FRONTEND_DIR || exit
npm install >/dev/null 2>&1
npm start >/dev/null 2>&1 & 
FRONTEND_PID=$!

# Verify if the frontend is running
echo "⏳ Waiting for the frontend to start on port $FRONTEND_PORT..."
sleep 5

if lsof -i:$FRONTEND_PORT > /dev/null; then
    echo "✅ Frontend started successfully on port $FRONTEND_PORT."
else
    echo "❌ Failed to start the frontend. Exiting..."
    kill $FRONTEND_PID
    kill $BACKEND_PID
    exit 1
fi

echo "🚀 Local deployment successful! Backend is running at http://localhost:$BACKEND_PORT, chat is running at http://localhost:$CHAT_PORT and frontend at http://localhost:$FRONTEND_PORT."

# Wait for processes to complete
wait


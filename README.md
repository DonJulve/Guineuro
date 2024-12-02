## Boheringer Ingelheim Hackaton

This repository contains the repository of the project presented by the team "equipo amistad" in the Boheringer Ingelheim 2024.

![https://www.boehringer-ingelheim.com/sites/default/files/2024-04/Boehringer_Ingelheim_Dark-Green.png](https://upload.wikimedia.org/wikipedia/commons/thumb/b/ba/Boehringer_Ingelheim_Logo_RGB_Accent_Green.svg/2560px-Boehringer_Ingelheim_Logo_RGB_Accent_Green.svg.png)

## Content of the Project
This project is a application designed to treat and monitor patients with mental disorders. The platform provides tools for both patients and doctors.

## Some screenshots of the final product

<img src="Images/Screenshot_1.png" width="500">
<img src="Images/Screenshot_4.png" width="700">
<img src="Images/Screenshot_3.png" width="500">


### Patients

- The patients can take a daily test, which will be adapted by AI based on the results from previous days. The goal is to improve the patient's mood and reduce the symptoms of the mental disorder.
- The patients will also have access to a chat-bot that can converse with them to help improve their emotional well-being.
- A panic button is available, which sends an alert to a Telegram bot in case of emergency.
### Doctors:

- Doctors can view all the patients under their care, reassign them to another doctor, or remove them from the system.
- They will have access to the patient's complete information, including initial diagnostic results, daily test results, and a graph showing the progression of these results.
- All data will be analyzed by the AI, which will generate reports that are sent to the doctors.

## Technologies Used

- **Frontend** React.
- **Backend** Java + SpringBoot.
- **Guineuro AI** Kotlin + SpringBoot.
  

## How to run the App

### Frontend

```bash
cd <route_to_project>/frontend
npm install
npm start
```

### Backend

```bash
cd <route_to_project>/backend
./gradlew bootRun

```

### Guineuro AI
```bash
cd <route_to_project>/guineuro-chat
./gradlew bootRun

```

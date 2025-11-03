![Status](https://img.shields.io/badge/status-under--construction-orange)
![Kotlin](https://img.shields.io/badge/Kotlin-95%25-blue)
![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-UI-green)
![Firebase](https://img.shields.io/badge/Firebase-Backend-orange)


# 🩺 Doctor Appointment Booking App

A modern Android application for booking doctor appointments, built with **Kotlin**, **Jetpack Compose**, **Firebase**, and **Hilt**, following **Clean Architecture** with **MVVM** and **Kotlin Flow**.

---

## ✨ Features

- 🔐 **User Authentication** – Secure login/signup using Firebase Auth
- 🔑 **Role-Based Authentication** – Supports two user roles:
  - 🧑‍⚕️ **Doctor** – Manage appointments, respond to patient messages, and conduct video consultations.
  - 👩‍⚕️ **Patient** – Browse doctors, book appointments, view history, and chat/video call doctors.
- 🧑‍⚕️ **Doctor Directory** – Browse doctors by specialization
- 📅 **Appointment Booking** – Schedule, view, and manage appointments
- 🕒 **Appointment History** – View past & upcoming appointments
- 💬 **Chat Functionality** – Real-time messaging between doctor and patient 
- 🎥 **Video Calling** – Real-time teleconsultation powered by **PeerJS** 
- 📲 **Modern UI** – Fully responsive UI using Jetpack Compose
- ⚙️ **Scalable Codebase** – Built with Clean Architecture principles

---

## 🛠️ Tech Stack

| Layer            | Tech Used                                     |
|------------------|-----------------------------------------------|
| Language         | Kotlin                                        |
| UI               | Jetpack Compose                               |
| Architecture     | MVVM + Clean Architecture                     |
| State Management | Kotlin Flow, State Hoisting                   |
| Dependency DI    | Hilt                                          |
| Backend          | Firebase (Auth, Firestore)                    |
| Real-time Comm   | PeerJS (WebRTC-based video calling)           |
| Navigation       | Jetpack Navigation Compose                    |
| Data Handling    | Repository Pattern                            |

---


## 📸 Screenshots

<table>
<tr>
    <td><img src="https://github.com/user-attachments/assets/8e0e605d-e225-427c-9ff3-875bfa1f1a7a" alt="Role Screen" width="200"/></td>
    <td><img src="https://github.com/user-attachments/assets/b8d1a464-b355-4854-bfde-fb4c7ae00ecd" alt="Signup" width="200"/></td>
    <td><img src="https://github.com/user-attachments/assets/cad06d27-cd48-4554-abc1-6cdb1cf4fe95" alt="Signin" width="200"/></td>
    <td><img src="https://github.com/user-attachments/assets/3c73f11e-f67b-4bfc-9151-b6c2275f8360" alt="CP1" width="200"/></td>

</tr>
<tr>
    <td><img src="https://github.com/user-attachments/assets/bb175b69-ab62-41cf-a5e6-fb1b45a6034c" alt="CP2" width="200"/></td>
    <td><img src="https://github.com/user-attachments/assets/02a7d096-1b25-497b-9e2b-53483ea585fd" alt="CP3" width="200"/></td>
    <td><img src="https://github.com/user-attachments/assets/c644135a-5388-4b2d-b614-ff37f60324a8" alt="CP4" width="200"/></td>
    <td><img src="https://github.com/user-attachments/assets/ed36ad26-7b63-4086-9c3f-463636b6f122" alt="CP5" width="200"/></td>

</tr>
 
  <tr>
    <td><img src="https://github.com/user-attachments/assets/2e3f03b2-bc5e-4fbd-8d40-d59362497596" alt="Screenshot 1" width="200"/></td>
    <td><img src="https://github.com/user-attachments/assets/97f261ae-1717-4cfc-b963-13256ad410be" alt="Screenshot 2" width="200"/></td>
    <td><img src="https://github.com/user-attachments/assets/2c7b0ca1-8d71-4498-bbb4-ad769384693b" alt="Screenshot 3" width="200"/></td>
    <td><img src="https://github.com/user-attachments/assets/0e754402-4b09-4a06-9e86-4b39354e4fa2" alt="Screenshot 4" width="200"/></td>
  </tr>
  <tr>
    <td><img src="https://github.com/user-attachments/assets/1450f1a7-4814-4333-89ef-adc699f1ea41" alt="Screenshot 5" width="200"/></td>
    <td><img src="https://github.com/user-attachments/assets/fd6ff290-000a-4e88-b5fe-1faf30f59776" alt="Screenshot 6" width="200"/></td>
    <td><img src="https://github.com/user-attachments/assets/34d6fdc9-fb8b-42d0-b37a-cbe828886915" alt="Screenshot 7" width="200"/></td>
    <td><img src="https://github.com/user-attachments/assets/a0633db9-7a1f-4379-b109-c2e7bb9a850d" alt="Screenshot 8" width="200"/></td>
  </tr>
  <tr>
    <td><img src="https://github.com/user-attachments/assets/175aa320-22f3-4cd5-be54-3eeeac251342" alt="Screenshot 9" width="200"/></td>
    <td><img src="https://github.com/user-attachments/assets/e0c6123f-7294-4ade-947b-7516eaa31ef5" alt="Screenshot 10" width="200"/></td>
    <td><img src="https://github.com/user-attachments/assets/a3d47db5-a3a9-436c-8803-7260b9b297ab" alt="Screenshot 11" width="200"/></td>
    <td><img src="https://github.com/user-attachments/assets/def66ae6-b3fa-4793-a2d4-a2e2d038b107" alt="Screenshot 12" width="200"/></td>
  </tr>
<tr>
    <td><img src="https://github.com/user-attachments/assets/13bfbd33-31d1-4330-9f45-bd9e7f541ebc" alt="Video Call" width="200"/></td>
    <td><img src="https://github.com/user-attachments/assets/2a2291f1-3ef3-4488-a6ca-c0ee6a56e017" alt="Whiteboard/Workspace" width="200"/></td>
</tr>

</table>

---

## 🚧 TODOs

- [ ] 🔔 Push Notifications for Appointment Reminders
- [ ] 📆 Doctor Availability Calendar Integration
- [ ] ⭐ Ratings & Reviews for Doctors
- [x] 🎥 Video Calling (Telehealth Support using PeerJS)  
- [ ] 💳 Payment Gateway Integration (for paid consultations)

# Login Exercise - Mobile Security Course
## Overview
This project demonstrates an innovative login process that combines Android sensors and broadcast receivers to enforce specific user conditions before login. Users must fulfill all predefined conditions to complete the login process.

### Screenshots
<div style="display: flex; gap: 10px;">
  <img src="https://github.com/user-attachments/assets/1960a03a-839f-4b6d-8f5b-4e7c2baa8287" alt="Image 1" style="width: 15%; height: 15%;">
  <img src="https://github.com/user-attachments/assets/918a8871-a1b1-442d-a7ab-cf3d54b38c7a" alt="Image 2" style="width: 15%; height: 15%;">
  <img src="https://github.com/user-attachments/assets/5cba85fe-7006-4267-9575-d81d403fa244" alt="Image 3" style="width: 15%; height: 15%;">
  <img src="https://github.com/user-attachments/assets/5dbfe6eb-cbad-47bc-9be4-b256af0f78d1" alt="Image 4" style="width: 15%; height: 15%;">
</div>

## Features
**Interactive Conditions:** Users must interact with their device in specific ways to fulfill the login requirements.
**Sensor Integration:** Utilizes the device's built-in sensors to monitor environmental and user actions.
**Dynamic Feedback:** Visual indicators and dialog messages guide users through each condition.
**Speech Recognition:** Includes voice-based interaction for enhanced accessibility.
**Custom Dialogs:** Provides clear instructions and feedback for each condition.

## Usage
### Login Conditions
To complete the login process, the user must satisfy the following five conditions:

**1. Place the device in a dark environment**
  * **How to complete:** Cover the device's light sensor or move it to a dark area.
  * **Sensor used:** Light Sensor.

**2. Shake the device**
  * **How to complete:** Shake the device vigorously to trigger the condition.
  * **Sensor used:** Accelerometer Sensor.

**3. Connect the device to a charger**
  * **How to complete:** Plug the device into a power source.
  * **Broadcast Receiver used:** Intent.ACTION_POWER_CONNECTED.

**4. Place the device near your face or ear**
  * **How to complete:** Move the device close to your face or ear to activate the proximity sensor.
  * **Sensor used:** Proximity Sensor.

**5. Say "Login" clearly**
  * **How to complete:** Use the speech recognition feature to say the word "Login."
  * **Component used:** Speech Recognition (RecognizerIntent).

### Steps to Login
1. Launch the application.
2. Complete each condition by following the on-screen instructions.
3. After all conditions are marked as complete, press the Login button to proceed to the next screen

## Sensors and Broadcast Receiver Used
### Sensors
**1. Light Sensor**
  * Used to detect the device's surrounding light intensity and determine if it is in a dark environment.
  * Threshold: ≤ 10.0 lux.

**2. Accelerometer Sensor**
  * Monitors device motion to detect shakes
  * Shake Threshold: Δ > 12 in acceleration values over 200 ms.

**3. Proximity Sensor**
  * Detects the distance of nearby objects to determine if the device is near a user's face or ear.
### Broadcast Receiver
**1. Power State Receiver**
  * Responds to the device being connected or disconnected from a power source.
  * Intent.ACTION_POWER_CONNECTED → Marks the charging condition as complete.
  * Intent.ACTION_POWER_DISCONNECTED → Resets the charging condition.
### Additional Component
**1. Speech Recognition**
  * Captures and processes user speech to recognize the word "Login.".
  * Implements RecognizerIntent.ACTION_RECOGNIZE_SPEECH with English language support.


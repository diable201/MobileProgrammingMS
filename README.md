# 📱 Mobile Programming M.Sc

Welcome to the repository for the **Mobile Programming Assignments** and **Final Project**! This repository is focused on learning and mastering Android development using **Jetpack Compose**, **Kotlin**, and other modern tools and frameworks. Through practical assignments and a comprehensive final project, you'll gain hands-on experience in creating functional, efficient, and user-friendly mobile applications.

---

## 📜 Table of Contents

- [About](#about)
- [Features](#features)
- [Assignments](#assignments)
  - [Assignment 1: Kotlin Basics](#assignment-1-kotlin-basics)
  - [Assignment 2: Instagram-like Layout](#assignment-2-instagram-like-layout)
  - [Assignment 3: Fragments, RecyclerView, and ViewModel](#assignment-3-fragments-recyclerview-and-viewmodel)
  - [Assignment 4: Databases and Network Calls](#assignment-4-databases-and-network-calls)
- [Final Project: Mobile Shopping App](#final-project-mobile-shopping-app)
- [Requirements](#requirements)
- [Installation and Setup](#installation-and-setup)
- [Usage](#usage)
- [Contributing](#contributing)
- [License](#license)
- [Acknowledgments](#acknowledgments)

---

## 🛠️ About

This repository contains a series of assignments and a final project designed to:

- Teach the basics of **Kotlin** and **Jetpack Compose**.
- Develop layouts and user interfaces using modern Android tools.
- Handle data persistence, state management, and API integration.
- Build a complete mobile shopping app as a capstone project.

---

## ✨ Features

- 🎨 **Jetpack Compose**: Declarative UI design with modern components.
- 📱 **Responsive Layouts**: Optimized for various screen sizes.
- 📊 **Data Management**: Use of Room database and LiveData.
- 🌐 **Network Communication**: Integration of APIs using Retrofit.
- 🚀 **Scalable Architecture**: MVVM architecture for clean code.

---

## 📚 Assignments

### Assignment 1: Kotlin Basics

#### 📝 Objective

Learn the foundational syntax and concepts of Kotlin.

#### 🚀 Key Features

- Variables and Data Types: Work with `Int`, `String`, `Boolean`, etc.
- Conditional Statements: Check number positivity.
- Loops: Print numbers using `for` and `while` loops.
- Collections: Sum a list of numbers.
- **Set Up the Android Project**:
- Create a new Android project in Android Studio.
- Ensure you have a Kotlin-based project.
- **Design the Layout**:
- Create a new XML layout file (`activity_main.xml`) for a simple Instagram-like user interface.
- Include elements like `ImageView`, `TextView`, and `RecyclerView` for the feed.
- **Create the RecyclerView Adapter**:
- Set up the `RecyclerView` to display a feed of posts with `ImageView` for the picture and `TextView` for the caption.
- **MainActivity Setup**:
- Initialize the `RecyclerView` in `MainActivity` and populate it with sample data.

---

### Assignment 2: Instagram-like Layout

#### 📝 Objective

Design a modern UI layout using **Jetpack Compose** to create an Instagram-like interface.

#### 🚀 Key Features

- **Compose-Based Feed**:
  - Use Jetpack Compose's `LazyColumn` to display a dynamic list of posts.
  - Each post includes a profile picture, username, image, caption, and like button.
- **Custom Components**:
  - Create reusable Compose components for Post, Profile Picture, and Caption.
- **Interactivity**:
  - Add click listeners for likes and post interactions.
  - Display a toast message when posts are liked.
- **Sample Data Integration**:
  - Use a ViewModel to provide mock data and observe state changes.
  - Bind the data dynamically to the UI using Compose state management.

---

### Assignment 3: LazyColumn, and ViewModel

#### 📝 Objective

Explore fragments, LazyColumn (Compose's replacement for RecyclerView), and state management with ViewModel.

#### 🚀 Key Features

1. **Fragments**:
   - Create a basic fragment and implement lifecycle methods.
   - Implement communication between fragments using a shared ViewModel.
   - Manage fragment transactions with Jetpack Navigation.

2. **LazyColumn**:
   - Use Jetpack Compose's `LazyColumn` for displaying dynamic lists of items.
   - Handle item interactions such as clicks using Compose's `Modifier.clickable`.

3. **ViewModel and LiveData**:
   - Store and observe data using `LiveData`.
   - Integrate ViewModel with Compose state management to display real-time updates.
   - Fetch data from a Room database and dynamically reflect changes in the UI using Compose components.

---

### Assignment 4: Databases and Network Calls

#### 📝 Objective

Integrate Room for local storage and Retrofit for API communication.

#### 🚀 Key Features

1. **Room Database**:

   - Define entities and DAO interfaces.
   - Perform database operations in a lifecycle-aware manner.

2. **Retrofit**:

   - Set up Retrofit for API requests.
   - Create data models for JSON responses.
   - Handle API responses gracefully and cache data locally.

---

## 🎓 Final Project: Mobile Shopping App

### 🌟 Overview

Develop a comprehensive shopping application using Jetpack Compose, integrating all the concepts learned throughout the assignments.

#### 💻 Features

- **User Management**: Manage user profiles, login, and registration.
- **Product Listings**: Display a list of products with search and filtering.
- **Shopping Cart**: Add products to a cart and proceed to checkout.
- **Order History**: Track user orders and display order details.
- **Real-time Updates**: Use WebSockets for live notifications.

#### 🛠️ Deliverables

- Fully functional shopping app with Jetpack Compose UI.
- Room database for data storage.
- Retrofit integration for API calls.
- Clean architecture with ViewModel and LiveData.

---

## 💾 Requirements

- **Android Studio**: Latest version
- **Kotlin**: Latest version
- **Jetpack Compose**: Version 1.x+
- **Room Database**: Version 2.x+
- **Retrofit**: Latest version

---

## 📦 Installation and Setup

### 🛠️ Prerequisites

Ensure the following are installed:

- Android Studio with Jetpack Compose support
- Latest Android SDK

### 📥 Steps

1. Clone the repository:
   ```bash
   git clone https://github.com/diable201/MobileProgrammingMS.git
   cd MobileProgrammingMS
   ```
2. Open the project in Android Studio and sync Gradle.
3. Run the app on an emulator or physical device.

---

## ⚙️ Usage

- **Run the App**:
  Launch the application in Android Studio to explore features.
- **Test API Calls**:
  Use mock data or connect to a real API using Retrofit.
- **Modify Layouts**:
  Customize Jetpack Compose components in `MainActivity` and fragments.

---

## 🤝 Contributing

Contributions are welcome! Please fork the repository and submit a pull request for review. Ensure your code follows best practices and includes documentation.

---

## 📜 License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

---

## 🙌 Acknowledgments

- **Kotlin Documentation**: [Kotlin Lang](https://kotlinlang.org/)
- **Jetpack Compose**: [Compose Docs](https://developer.android.com/jetpack/compose)
- **Android Development**: [Android Docs](https://developer.android.com/)


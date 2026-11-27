# Kotlin Multiplatform – Bookpedia

A Kotlin Multiplatform project built to explore and practice cross-platform application development using **Kotlin Multiplatform (KMP)**, **Compose Multiplatform**, and **Ktor Client** for REST API integration.

The project targets **Android, iOS, and Desktop**, with a focus on writing reusable, maintainable, and platform-independent code while sharing business logic and UI across supported platforms.

## Key Technologies & Concepts

- **Kotlin Multiplatform:** Sharing application logic across Android, iOS, and Desktop.
- **Compose Multiplatform:** Building cross-platform user interfaces using a shared UI codebase.
- **Ktor Client:** Making HTTP requests and integrating REST APIs.
- **Kotlin Serialization:** Handling JSON serialization and deserialization.
- **Coroutines & Flow:** Managing asynchronous operations and reactive data streams.
- **Clean Architecture:** Separating presentation, domain, and data layers.
- **Repository Pattern:** Managing data sources and application data operations.
- **State Management:** Managing UI state and handling loading, success, and error states.

## Project Structure

### `/composeApp`

Contains the shared application code for Compose Multiplatform.

- `commonMain`: Shared Kotlin code used across all supported platforms, including UI, business logic, repositories, and network integration.
- `androidMain`: Android-specific implementations.
- `iosMain`: iOS-specific implementations and native integrations.
- `desktopMain`: Desktop-specific implementations.

## Project Goals

- Gain practical experience with Kotlin Multiplatform development.
- Understand how to structure and share code across multiple platforms.
- Practice REST API integration using Ktor Client.
- Implement clean architecture and repository-based data management.
- Improve understanding of Kotlin Coroutines, Flow, and asynchronous programming.
- Explore cross-platform UI development with Compose Multiplatform.

## Purpose

A hands-on learning project focused on strengthening Kotlin development skills and exploring modern multiplatform application architecture.
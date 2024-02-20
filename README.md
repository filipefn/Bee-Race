**Bee Race: A Modular, Testable, Compose-based MVVM Android App**

**Overview:**

Bee Race is a modular Android app built using modern architectures and development practices. It leverages Compose for declarative UI, MVVM for separation of concerns, Hilt for dependency injection, and comprehensive testing (unit) for maintainability and confidence.

**Features:**

- **Modularization:** The app is structured into modules for better organization, independent development, and testability.
- **MVVM:** The design follows the Model-View-ViewModel pattern for clean separation of concerns and ease of testing.
- **Compose:** Jetpack Compose is used for creating visually appealing and performant UIs.
- **Hilt:** Dependency injection is managed with Hilt for cleaner code and easier testing.
- **Testing:** Unit and UI tests ensure code quality and stability.
- **Gradle Catalog:** Sharing dependency versions between projects
  
**Project Structure:**

```
BeeRace
├── app                  # Main application module ans splash
│   ├── build.gradle
│   ├── src
│       ├── main
│           ├── java     # Java source code
│           └── res      # Resources
│               ├── layout
│               └── drawable
│       └── debug/test  # Debug and test configurations
│
├── data                  # Data layer module (models, repositories)
│   ├── build.gradle
│   ├── src
│       ├── main
│           ├── java
│       └── debug/test
│
├── domain                # Business logic and use cases
│   ├── build.gradle
│   ├── src
│       ├── main
│           ├── java
│       └── debug/test
│
├── network                # Everything related to the connection API
│   ├── build.gradle
│   ├── src
│       ├── main
│           ├── java
│       └── debug/test
│
├── commons                # Extensions, Navigations, Components and Themes
│   ├── build.gradle
│   ├── src
│       ├── main
│           ├── java
│
├── feature         # View layer components (ViewModels, screens, etc.)
│       ├── race
│           ├── main
│               ├── java
│           └── debug/test
│       ├── build.gradle
└── build.gradle        # Top-level Gradle build file

```

**Getting Started:**

1. Clone the repository.
2. Install dependencies: `./gradlew build`
3. Run the app: `./gradlew app:installDebug`

**Improvements:**

1. UI tests
2. Create plugins to facilitate the creation of modules

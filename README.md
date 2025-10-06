# android-architecture-modular-template

A clean, multi-module boilerplate for building Android apps using modern best practices.

- Kotlin & Coroutines
- Jetpack Compose for UI
- Hilt for Dependency Injection
- Multi-Module Architecture (app, ui, domain, data)
- JUnit 5 & Mockito for Unit Testing

```mermaid
flowchart TD
    %% --- Styles ---
    classDef layer fill:#222831,stroke:#393E46,stroke-width:2px,rx:25,ry:25,color:#EEEEEE,font-weight:bold;
    classDef ui fill:#00ADB5,stroke:#222831,stroke-width:2px,rx:12,ry:12,color:#222831,font-weight:bold;
    classDef domain fill:#FFD369,stroke:#222831,stroke-width:2px,rx:12,ry:12,color:#222831,font-weight:bold;
    classDef data fill:#F96D00,stroke:#222831,stroke-width:2px,rx:12,ry:12,color:#222831,font-weight:bold;
    classDef tech fill:#393E46,stroke:#222831,stroke-width:1.5px,rx:10,ry:10,color:#EEEEEE,font-size:12px;

    %% --- UI Layer ---
    subgraph UI_Layer["UI Layer"]
        class UI_Layer layer
        Screens(["🖥 Screens\n(Display & User Interaction)"]):::ui
        ViewModels[["🔄 ViewModels\n(State & Logic Binding)"]]:::ui
    end

    %% --- Domain Layer ---
    subgraph Domain_Layer["Domain Layer"]
        class Domain_Layer layer
        BusinessLogic{{"⚙ Core Business Logic"}}:::domain
        RepositoryInterfaces(("📂 Repository Interfaces (Abstract Access to Data)")):::domain
    end

    %% --- Data Layer ---
    subgraph Data_Layer["Data Layer"]
        class Data_Layer layer
        APIKey([🔑 API Key Retrieval]):::data
        DataInteraction["🌐 Data Interaction\n(Network / Local DB)"]:::data
    end

    %% --- Flow with Coroutine annotations ---
    Screens --> ViewModels
    ViewModels -->|async via Coroutines| BusinessLogic
    BusinessLogic --> RepositoryInterfaces
    RepositoryInterfaces --> APIKey
    RepositoryInterfaces --> DataInteraction

    %% --- Tech stack nodes ---
    Compose(["Jetpack Compose"]):::tech
    Coroutines(["Kotlin Coroutines
(async orchestration)"]):::tech
    MultiModule(["Multi-Module
(app/ui/domain/data)"]):::tech
    Hilt(["Hilt (DI)"]):::tech
    Testing(["JUnit5 & Mockito"]):::tech

    %% --- Relationships to stack ---
    Screens -.-> Compose
    ViewModels -.-> Coroutines
    BusinessLogic -.-> Coroutines
    UI_Layer -.-> MultiModule
    Domain_Layer -.-> MultiModule
    Data_Layer -.-> MultiModule

    %% --- Cross-cutting Hilt ---
    Hilt -.-> UI_Layer
    Hilt -.-> Domain_Layer
    Hilt -.-> Data_Layer

    %% --- Cross-cutting Testing ---
    Testing -.-> UI_Layer
    Testing -.-> Domain_Layer
    Testing -.-> Data_Layer

```

### Resources

* **Dependency Injection with Hilt:** [A Complete Guide to Dependency Injection in Jetpack Compose using Hilt](https://medium.com/@jecky999/dependency-injection-in-jetpack-compose-using-hilt-a-complete-guide-0f7bf802d6cb) by Jecky.

* **Unit Testing with Mockito:** [Mocking with Mockito: Simplifying Unit Testing](https://medium.com/@alxkm/mocking-with-mockito-simplifying-unit-testing-in-java-1cc50d78d2c0) by Alexander K.

* **MVVM & ViewModel Testing:** [A Complete Guide to MVVM and ViewModel Testing in Android (Hilt, JUnit, and Mockito)](https://medium.com/@deepak.patidark93/a-complete-guide-to-mvvm-and-viewmodel-testing-in-android-hilt-junit-and-mockito-explained-df54324b8dca) by Deepak Patidar.

* *Gemini*

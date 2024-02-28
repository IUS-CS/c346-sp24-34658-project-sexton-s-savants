# Design Patterns in Quark

## Introduction
Currently, Quark implements the following design pattern:

1. **Singleton Pattern**:
    - **Description**: The Singleton pattern ensures that a class has only one instance and provides a global point of access to it.
    - **Usage**: We use singletons in our `EmailAuth`, `Messages`, and `Users` classes. These classes are instantiated only once, as they manage critical resources (authentication, messages, and user queries).
    - **Considerations**:

## Potential Design Patterns

### 1. **Observer Pattern**
- **Description**: The Observer pattern establishes a one-to-many dependency between objects. When one object (the subject) changes state, all its dependents (observers) are notified and updated automatically.
- **Fit for Our Project**: We plan to use this pattern for message sending. By subscribing to Firebase's observable query, we can efficiently receive updates on new messages.
- **Implementation**: Create an `Observable` class that manages message notifications and allows UI components to register and receive updates.

### 2. **Factory Method Pattern**
- **Description**: The Factory Method pattern defines an interface for creating objects but lets subclasses decide which class to instantiate.
- **Fit for Our Project**: If we need flexibility in adding new types of objects (e.g., different message types), the Factory Method can help.
- **Implementation**: Define an abstract factory interface (`MessageFactory`) and concrete factories for each message type (e.g., `TextMessageFactory`, `ImageMessageFactory`).

## Future Module Design

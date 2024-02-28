# Design Patterns

In our current implementation, we don't really use any design patterns.

For the future of the app, here are a few that may fit well into our design, and how we intend to continue designing modules:

#### Strategy Pattern
We might consider offering users a choice between different encryption algorithms (quantum-resistant or classical). The Strategy pattern can handle this variation and probably assist in testing.

#### Decorator Pattern
We can enhance the EmailAuth class by incorporating additional security measures (e.g., two-factor authentication) and we can use this pattern to add security layers dynamically.

#### Composite Pattern
We can use the Composite pattern to represent hierarchical structures of messages or user profiles.

#### Observer Pattern
We will consider using the Observer pattern for real-time message updates in the UI of our app.

#### Adapter Pattern
Lastly, we can use the Adapter pattern to adapt different message formats for display.

# Rise of Valor
### **About the Game**
Rise of Valor is a multiplayer pixel-art action game where 4-5 players team up to battle waves of enemies in an epic co-op survival mode. Designed to deliver fast-paced gameplay with strategic teamwork, players can communicate via in-game voice chat, coordinate attacks, and dominate the battlefield.

This project is built using Java and JavaFX, combining retro-inspired graphics with modern multiplayer mechanics.




## Project Structure
````
src/
├── client/
│   ├── controllers/        # Handles UI and gameplay interaction
│   ├── models/             # Player, Enemy, Bullet, and GameState models
│   ├── views/              # JavaFX views and scenes
│   └── networking/         # Client-side socket connection
├── server/
│   ├── handlers/           # Processes player actions and updates game state
│   ├── models/             # Server-side representation of game entities
│   ├── utils/              # Helper classes (e.g., encryption, collision detection)
│   └── networking/         # Socket server for managing connections
└── common/                 # Shared utilities and models for client-server communication
````

### **Contributing**
We welcome contributions to improve the game! 
To get started: 
1. Fork the repository. 
2. Create a feature branch: bash Copy code <br>`git checkout -b feature/your-feature-name`
3. Submit a pull request with a detailed description of your changes.”
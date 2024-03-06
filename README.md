## GwentStone

GwentStone is a Java-based project designed to facilitate a turn-based card game experience inspired by popular card games like Gwent. The project is structured around key functionalities encapsulated within various packages.

### Core Functionalities:

- **ChosenDecks:** Manages the selection and initialization of player decks at the start of the game. Decks are loaded from JSON input files, players choose their decks, and cards are sorted, attributed with specific types, and shuffled.

- **ChosenHeroes:** Handles the selection of heroes for each player at the beginning of the game. Heroes are assigned based on JSON input, along with tracking of whether each hero has attacked during a turn.

- **GameTable:** Manages the game board and related attributes, including arrays representing the game board, tracking frozen or attacked minion coordinates, and references for front, back, and tank positions.

- **Card:** Abstract class representing a generic card with additional fields such as position indicating front or back row placement.

- **GameFlow:** Orchestrates game setup, initializes game components, and prepares the game to accept player commands. All input data, including deck selection, hero assignment, and game state, is loaded from JSON files.

- **HasAbility:** Interface for assigning specific abilities to heroes and legendary minions.

### Game Entities:

The project encompasses three main categories of game entities:

- **Environment:** Represents environmental effects cards, each with specific abilities.
- **Hero:** Represents player heroes with unique health values and abilities.
- **Minion:** Represents various types of minions, each with attack, health, and positional attributes, including whether it's a tank.

### Command Handling:

The project includes comprehensive command handling capabilities, supporting a range of actions such as retrieving player decks, heroes, turns, playing cards, managing game state, and interacting with game entities. All commands and game actions are processed through JSON files for seamless integration and data management.

### Gameplay Overview:

GwentStone offers a dynamic two-player turn-based card game experience. Players choose decks and heroes, then compete in a strategic card battle. Each player takes turns playing cards from their hand, managing resources, and deploying minions to attack opponents and defend their own heroes. Victory is achieved by reducing the opponent's hero health to zero or less. The project also tracks player statistics, including wins and total games played, providing insights for gameplay analysis and progression.

### Features:

- Turn-based gameplay mechanics
- Deck and hero selection
- Card deployment and interaction on the game board
- Hero abilities and environmental effects
- Player statistics tracking for wins and total games played
- Comprehensive command handling via JSON files for game control and debugging

GwentStone aims to provide an immersive and strategic card gaming experience while offering robust debugging capabilities and statistical insights for both players and administrators.

---

This description emphasizes the integration of JSON files for input data, ensuring a seamless experience for players and administrators alike.

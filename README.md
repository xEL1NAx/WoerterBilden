# WoerterBilden

**WoerterBilden** is an educational Java application designed to help users enhance their vocabulary, spelling, and language skills through interactive word-based games. The project focuses on combining fun, challenge, and learning by providing a variety of word exercises in a structured and user-friendly interface. The architecture of the application is carefully designed to separate presentation, logic, and data, following a clean MVC-inspired approach.

---

## Project Overview

WoerterBilden allows users to engage with words in multiple ways:

- **WortPuzzle**: A shuffled letter puzzle where the user must reconstruct the original word. This mode improves cognitive association between letters and enhances spelling and memory skills.
- **WortMitLuecke**: A fill-in-the-blank style exercise where parts of a word are hidden. Users must identify the missing letters to complete the word. This strengthens vocabulary recognition and spelling accuracy.

The application is designed to support multiple players and randomize challenges, making each session unique. Words are dynamically loaded from a dedicated word list, allowing the application to be easily extended with new words without changing the core logic.

---

## Key Features

- **Interactive Learning**  
  Users actively engage with words rather than passively memorizing them. The exercises are gamified to maintain motivation and provide instant feedback on performance.

- **Randomized Puzzles**  
  Each playthrough generates unique challenges. For `WortPuzzle`, letters are shuffled differently every time, and for `WortMitLuecke`, different letters may be hidden in each session.

- **Dynamic Word Repository**  
  Words are managed in a central `WortListe` class, which acts as a repository for all vocabulary items. This enables easy expansion and maintenance of the word database.

- **MVC-inspired Architecture**  
  The design separates responsibilities into three primary layers:
  1. **GUI (`dieGUI`)** – Handles all graphical presentation and user input.  
  2. **Controller (`Steuerung`)** – Manages game logic, tasks, turns, and validation.  
  3. **Word List (`WortListe`)** – Provides words and configurations to the controller.  

- **Extensible Design**  
  New game types or word-based exercises can be added by extending the `Wort` base class. Each word class implements required methods like `gibBuchstaben()` or `gedrueckt()` to ensure consistent interaction with the controller and GUI.

---

## Architecture and Class Structure

WoerterBilden is structured to maximize maintainability and clarity. The flow of data and control is unidirectional:

GUI (dieGUI)
|
v
Steuerung (dieSteuerung)
|
v
WortListe (dieWortListe)
|
v
Wort / WortPuzzle / WortMitLuecke


### Classes

- **GUI (`dieGUI`)**  
  Responsible for displaying the interface, puzzles, and feedback. It receives updates and task instructions from the controller but does not perform any game logic itself.

- **Controller (`Steuerung`)**  
  Handles the core logic of the game, including task creation, turn management, answer validation, and updating the GUI. It interacts exclusively with `WortListe` for word retrieval and puzzle creation.

- **Word Repository (`WortListe`)**  
  Stores all words that can be used in the exercises. This class provides words to the controller and ensures that exercises are generated correctly and fairly.

- **Word Classes**  
  - **Wort** – The base class for all word-based exercises.  
  - **WortPuzzle** – Shuffles the letters of a word for the puzzle mode.  
  - **WortMitLuecke** – Masks specific letters in a word to create fill-in-the-blank challenges.

Each word class contains methods to return letters, check answers, and provide a structured interface to the controller, ensuring consistency across different types of exercises.

---

## Gameplay Flow

1. The GUI requests a new task from the controller.  
2. The controller selects a word from the `WortListe` and decides which puzzle type to create.  
3. The word is wrapped in the appropriate word class (`WortPuzzle` or `WortMitLuecke`).  
4. The task is displayed in the GUI for the player.  
5. The player interacts with the puzzle, entering letters or completing blanks.  
6. The controller checks the answer and updates the GUI with feedback.  
7. If multiple players are enabled, the turn switches automatically.

---

## Educational Goals

WoerterBilden is not only a game but also an educational tool with multiple benefits:

- Improves **spelling** and **letter recognition**.  
- Encourages **active learning** and **problem-solving**.  
- Enhances **memory** through repeated exposure to vocabulary.  
- Supports **collaborative learning** in multiplayer mode.  

The combination of fun challenges, randomization, and instant feedback creates a motivating environment for learners of all ages.

---

## Extensibility

The project was designed with future expansion in mind:

- **Adding Words:** Simply update the `WortListe` class with new words.  
- **Adding Puzzle Types:** Extend `Wort` and implement the required interface methods.  
- **Customizable Difficulty:** Additional parameters could adjust the number of missing letters, shuffle complexity, or time limits.  
- **Localization:** The word list can be replaced or extended with words from different languages to make the application multilingual.

---

## Visual Representation

![GUI Screenshot](Screenshot%202025-09-23%20094937.png)

*The screenshot above shows the primary interface where puzzles and fill-in-the-blank exercises are presented. It reflects the minimalistic, user-focused design of the GUI.*

---

## Conclusion

WoerterBilden is a comprehensive tool for interactive vocabulary building. Its structured architecture ensures maintainability and easy extension, while its educational focus ensures that gameplay is both fun and instructive. With the combination of multiple puzzle types, dynamic word selection, and turn-based interaction, WoerterBilden offers a rich, engaging experience for learners of all ages.

# snake-game


## Introduction
Welcome to the Snake Game! This is a classic game where you control a snake to eat food, grow in length, and try to avoid colliding with yourself or the borders. You can also choose the colour of the snake, and occaicionally the special berry appers, which boosts your speed once eaten.

## Features

### 1. Snake Movement Logic
**How to Demo:** Use arrow keys to control the snake's direction, ensuring smooth movement and wrapping from one edge to the opposite if it moves off the board.  
**Notes:** Handling keyboard events, implementing snake movement, and managing edge cases like moving out of bounds.

### 2. Growing Snake and Scoring
**How to Demo:** Snake consumes food items, grows in length, and the score increments accordingly.  
**Notes:** Managing snake body parts with arrays or linked lists, managing score state, and handling collision detection.

### 3. Generating Apples
**How to Demo:** Apples appear randomly on the board and are consumed by the snake.  
**Notes:** Implementing random number generation, ensuring an apple does not spawn in the snake’s body, and managing collision detection.

### 4. Snake Collision with Itself
**How to Demo:** Snake collapses when it collides with itself, displaying a Game Over message.  
**Notes:** Implementing collision detection within the snake’s body and managing game over state.

### 5. Game Over Screen
**How to Demo:** After the game ends, a game over window appears and tells the user their score.  
**Notes:** Managing game state and tracking scores.

### 6. User Interface and Score Display
**How to Demo:** Score and other gameplay information are visibly displayed on the game window.  
**Notes:** Creating and updating UI elements in real-time and ensuring a smooth user experience.

### 7. Styling
**How to Demo:** The user can choose among three colors for the snake.  
**Notes:** Managing new skins and combobox buttons.

### 8. Special Berry
**How to Demo:** The user can eat a special berry which boosts the snake's speed, but can also omit it and just eat regular apples.  
**Notes:** This feature makes the game experience more fun and complex.

## How to Play
1. Use the arrow keys to control the snake.
2. Try to eat the apples that appear on the board.
3. Avoid colliding with the walls or the snake’s own body.
4. You can choose different snake skins and decide whether to eat special berries.

## Installation
1. Clone the repository: `git clone https://github.com/DainiusGelzinis/snake-game.git`
2. Navigate to the project directory
3. Run the game: `StartFrame.java`




# 🐍 Classic Snake Game (Java Swing)

A **classic Snake Game** built using **Java and Swing**. Eat food, grow your tail, and avoid crashing into walls or yourself!

## 🎮 Features
- Smooth 2D animation using `javax.swing`
- Snake grows on eating food
- Game Over and Score display
- Keyboard controls
- Option to restart with Enter key

---

## 🚀 How to Run

### ✅ Prerequisites
Make sure you have **Java JDK** installed. You can run this from any terminal or Java IDE.

### 🧾 Compile

```bash
javac *.java
```

### ▶️ Run

```bash
java SnakeGame
```

> `SnakeGame.java` is the main class that launches the game window.

---

## 🎮 Controls

| Key         | Action              |
|-------------|---------------------|
| ⬅️ Left Arrow  | Move Left           |
| ➡️ Right Arrow | Move Right          |
| ⬆️ Up Arrow    | Move Up             |
| ⬇️ Down Arrow  | Move Down           |
| ⏎ Enter       | Restart after Game Over |

---

## 📁 Project Structure

```
SnakeGame.java        --> Main game launcher (extends JFrame)
GamePanel.java        --> Core game logic (drawing, updates, input)

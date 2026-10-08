# 2D Arkanoid

A Java Swing brick-breaker game. Move the paddle, keep the ball in play, and clear the bricks across different levels.

The project includes a main menu, options, a level manager, and score screens. It is an earlier desktop game project built around separate ball, paddle, brick, and level classes.

## Run from source

Install a JDK with `javac` and run these commands from the repository root:

```sh
mkdir -p out
javac -d out src/*.java
java -cp out:bin GameTest
```

Keep `bin` on the runtime classpath because images, sounds, and the score file are loaded as classpath resources. On Windows, use `java -cp "out;bin" GameTest`. `GameTest` is the application launcher, despite its name; it is not an automated test suite.

## Code map

- [Game](src/Game.java) hosts the game window and loop.
- [Ball](src/Ball.java), [Paddle](src/Paddle.java), and [Brick](src/Brick.java) define the playfield objects.
- [LevelManager](src/LevelManager.java) and [BrickCreator](src/BrickCreator.java) build levels.
- [MainMenu](src/MainMenu.java) and [Scoreboard](src/Scoreboard.java) provide the surrounding UI.


## Screenshots

![image](https://github.com/user-attachments/assets/49677b52-8b1b-4042-9090-4da85545d628)

![image](https://github.com/user-attachments/assets/417b544c-b768-45df-9c66-d0d2bf653bb2)

![image](https://github.com/user-attachments/assets/c5fae50d-ebd8-43a9-9a4d-6edb41da44ee)

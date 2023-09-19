# Quiz_Game
My pet project creating small Quiz game.
Tech used:
- JavaFX
- Jackson
- JUnit 5
- Appache commons

## Overview
This project is a JavaFX-based quiz game application that allows users to play quizzes sourced either from the internet or from local JSON files.
This project was created by me as a personal attempt to practice and hone various skills, including Java programming, JavaFX development, JUnit-based unit testing, JSON data manipulation, and the utilization of Apache Commons libraries. 
Additionally, it serves as a platform to showcase my ability to design user-friendly graphical user interfaces and effectively manage user preferences

### Features

    Main Form:
        Provides the main menu for the application.
        "From Internet" button loads quizzes from the internet using the Loading Form.
        "From File" button loads quizzes from local JSON files using File Chooser and displays the Game Form.

    Loading Form:
        Allows users to specify quiz parameters:
            Number of questions (1 to 50)
            Select category (avaliable from https://opentdb.com/api_config.php)
            Select difficulty (Easy, Medium, or Hard)
        Generates an API request to create a quiz with the chosen parameters.
        Prompts the user to save the generated quiz as JSON using File Chooser.NOTE: Encrypts correct and incorrect answers before saving.
        Displays an alert if quiz parameters are incorrect or not chosen.

    Game Form:
        Presents the quiz questions and answer options in a TabPane.
        The "Results" tab displays statistics for each question, including correct and incorrect answers, and the correct answer rate.
        Users can check their answers on the "Results" tab.
        Alerts are displayed for incomplete quiz parameters or if not all questions are answered.

### Dependencies

    JavaFX for the graphical user interface.
    JUnit 5 for unit testing.
    Jackson for JSON parsing.
    Apache Commons libraries for handling HTML encoding and other utilities.

### How to Run

Clone this repository to your local machine.
Open the project in your Java IDE.
Configure the project to use Java 20.
Run the application by starting the main class.

or 

Download and run [Quiz_Game.jar](https://github.com/SolovevAnton/Quiz_Game/tree/main/out/artifacts/Quiz_Game_jar)
You can run it with provided bat file. 
Note: be sure to have java 20 JRE

### Testing

This project includes comprehensive unit tests using JUnit 5 to ensure the correctness of the application's functionality.

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
This project extends its gratitude to (Open Trivia Database)[https://opentdb.com] for providing the quiz data that makes this game possible.

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

## Screenshots

### Main form:
![image](https://github.com/SolovevAnton/Quiz_Game/assets/121192850/11654623-2bf6-45ed-be00-4083769acb3b)

### Loading form
![image](https://github.com/SolovevAnton/Quiz_Game/assets/121192850/1567fac1-3f04-4b7d-a474-16c8d1fc851f)

![image](https://github.com/SolovevAnton/Quiz_Game/assets/121192850/50c80c15-bc17-410e-bb52-c1eb6e4decc4)

![image](https://github.com/SolovevAnton/Quiz_Game/assets/121192850/ad698124-233b-4099-8ad9-e966747017ce)


### Game Form

![image](https://github.com/SolovevAnton/Quiz_Game/assets/121192850/6624b6f0-f29c-43dd-8c13-8f2311ddc02a)


![image](https://github.com/SolovevAnton/Quiz_Game/assets/121192850/01bdb773-ba6f-4109-900e-dff37906b843)

![image](https://github.com/SolovevAnton/Quiz_Game/assets/121192850/b9fc6650-4037-4940-9698-68ee658e15d7)

![image](https://github.com/SolovevAnton/Quiz_Game/assets/121192850/312ebdbd-71f8-4fdb-a99c-fb1f4b63cfff)



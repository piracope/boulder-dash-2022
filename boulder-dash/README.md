# Boulder Dash

Final ATLG3 project by MOUFIDI Ayoub - 58089.

## Project Structure

```
java/
    controller/
        BoulderDash
    model/
        commands/
            MoveCommand
        tiles/
            ...
        Facade
        Game
        Level
        ...
    util/
        ...
    view/
        console/
            ConsoleView
        javafx/
            MainWindow
            InfoBox
            GameBoard
            MessageBox
    
    MainConsole
    MainGUI
resources/
    levels.json
    sprites.png
```

### Running the game

The game is started by running either MainConsole, which will launch the game in Console mode,
or by using the javafx:run maven command which will run MainGUI, which will launch the JavaFX
view.

### Controller
Both views interact only via the controller, BoulderDash. The controller is responsible for
relaying any user action, like moving, undoing, redoing etc. to the model's Facade.

### Model
The model's Facade is implemented by Game. It is responsible for the game logic, such as whether
a game over occurred, querying values, such as the current level number etc. but most importantly
to make the necessary changes on the underlying Level class.

The Facade also handles reversible user commands (well, only one command : moving) as Commands,
executes them and holds them in an internal history, for undoing and redoing purposes.

The Level is the low-level class that holds a playing level, which is represented by a 2D array
of Tiles. It is responsible for holding the values queried throughout the program and any physical
manipulation of the level's elements.

A Level is composed of Tiles, which are the discrete elements of the game board/level.
Each of them has their own behaviour which is described in those classes (cf. JavaDoc).

You can also find various utility classes and enums in the model package.

### Views
When a change on the model occurs, the Views and their components (if applicable) are notified,
as they registered themselves as observers of the Facade.

When an update is asked, the views update what is shown to the player by observing relevant information
directly from the model's Facade.

#### JavaFX View Components

The various view components are handled by the MainWindow, which is responsible for
putting every component together, and to handle user keyboard input, via MoveHandler and other
internal event filters.

The game is displayed by the GameBoard, which shows a sprite-based representation of the
level. The display is entirely refreshed at each change in the model.

Above the GameBoard is an InfoBox, a pane that shows relevant information like 
level number, remaining diamonds etc. Those values too are refreshed at each change
in the model.

Under the GameBoard is a MessageBox, which shows discrete messages depending on various
game events, such as if an invalid move was requested, if the game was won/lost, etc.

### Resources

levels.json holds all levels, meaning their string map that says where each element is,
the number of diamonds to get and their length and height, information useful when creating
the Level.
They are all loaded by LevelJSONHandler and needed information from a level is given by that class.

sprites.png is a sprite sheet with all elements necessary (and more) for the JavaFX view.
## Remarks

### Assumptions
- This program assumes the info in the resources folder are VALID. If badly formatted,
uncaught exceptions will be thrown.
- We do not take time into account. A player has all the time to complete a level.
- A player has a certain number of lives, and the game is over if that number reaches zero.

### Testing policy
100% code coverage was not achieved, nor sought for.
- Not all methods are explicitly tested, especially for Tiles. These are often simple getters,
setters with no particular behaviour or just methods that return true/false. Methods with more behaviour
were tested.
- Some low-level methods, like Level's, are not explicitly tested, even though they are rich in 
behaviour. This is because they are 1. quite tricky to test and 2. implicitly tested in larger
classes, like Game. And if those Game methods work, it must mean that the underlying Level
methods work too.

### Known bugs
- When a player moves just under a rock, the fall of that rock will be delayed until the next move.
This is not the case for diagonal falls. This makes some levels very difficult, or outright impossible to complete.
- Certain window environments (I'm using dwm) make the InfoBox narrower, which cause it
to flow to the next line. As the window is of a fixed size, it makes the MessageBox
overflow out of the window, which makes some text impossible to read.  
As such, **it is recommended to play the JavaFX view on Windows.** (at least it worked on Windows)
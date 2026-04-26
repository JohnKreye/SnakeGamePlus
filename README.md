# Snake Game Plus

Put all team members' names here:

    Names: Jonathan Kreye
    Java Version: 25

# Final Submission Comments:
*Final List of Design Patterns*
- Builder: Game.GameBuilder inner class used to create complex games that have different combinations of GameLogic, GameBoard, Snake, and IGameDisplay
- Factory: GameFactory() class used to quickly create preset game modes without worrying about game construction
- Interface: IGameDisplay: Used to allow the game to have different display types without tight coupling to concrete classes
- Observer: IKeyObserver & IObserver, IGameDisplay: IKeyObserver and IObserver are now both implemented by the game logic (used for recieving keybinds from the engine, and for recieving requests to update the game state from the engine). IGameDisplay is also an observer, it recieves requests to update the display from the engine, and the game can have mulitple displays.
- Decorator: DecoratedGameLogic, DecoratedSnake: Classes that ChaosGameLogic and TransformingSnake inherit from that are used to modify the behaviour of the underlying game logic; specifically, for both, the decorator pattern allows them to which between different game logics and snake types at any time. This is used in the chaos game mode so that when the player scores, the game logic and the snake type change.

# Mid-Project Review Comments:

*Current Design Patterns*
- Builder: Game.GameBuilder inner class used to create complex games that have different combinations of GameLogic, GameBoard, Snake, and IGameDisplay
- Factory: GameFactory() class used to quickly create preset game modes without worrying about game construction
- Interface: IDynamicDisplay & IGameDisplay: Intefaces used to allow the game to have different display types without tight coupling to concrete classes
- Observer: IKeyObserver & IObserver : IKeyObserver is implemented by GameLogic so that it may recieve key press updates from the display; IObserver is implemented by GameBoard to be notified of game changes, update it's state, and call upon the game logic
  

# Snake Game Plus

Put all team members' names here:

    Names: Jonathan Kreye
    Java Version: 25

#Mid-Project Review Comments:

*Current Design Patterns*
- Builder: Game.GameBuilder inner class used to create complex games that have different combinations of GameLogic, GameBoard, Snake, and IGameDisplay
- Factory: GameFactory() class used to quickly create preset game modes without worrying about game construction
- Interface: IDynamicDisplay & IGameDisplay: Intefaces used to allow the game to have different display types without tight coupling to concrete classes
- Observer: IKeyObserver & IObserver : IKeyObserver is implemented by GameLogic so that it may recieve key press updates from the display; IObserver is implemented by GameBoard to be notified of game changes, update it's state, and call upon the game logic
  

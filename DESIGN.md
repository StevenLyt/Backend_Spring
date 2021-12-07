# Backend Design Document 

In this project, we use the concept of muti-threading to achieve real-time communication and responses from the backend. There are two main thread `GameThread` and `Reception` that keep running forever:
- `GameThread` contains the main game and keeps it running 
- `Recption` handles the situation when a user joins the game of leaves the game. 

The basic logics for the game is that plays can start to play when there are at least three players on the table with non-zero stacks. If there is less than three players, the GameThread will sleep and Recption keeps checking if there is a new player joining. 
When there are more than three players on the table. The GameThread will create an instance called 'Hand' which is a class that handles everything for a game round. (it is commonly called 'hand' in texas hold'm). Hand is the most essential part of the backend design since it contains all the logics of the game
- `Game` contains all the information in this table 
- `Hand` contains all the information in this round 


## Game 

- Add user: if the reception receives a message that a player is joining the game, he/she needs to be added to the game and all the information regarding this player including the amount of buyin and the position he/she chose needs to be recorded by the game
```java
public void addUser(User user, int buyin, int pos)
```

- Delete user: if the reception receives a message that a player is leaving the game, he/she needs to be deleted from the game and all the information regarding this player needs to be cleared by the game
```java
public void deleteUser(String username)
```

- User rebuy: if a player needs more chips in his/her stack, `GameController` will inform `Game` that a player rebuy, and `Game` will update this player's stack
```java
public String rebuy(String username, int amount)
```

- Update position: if the game is running, the position of the dealer needs to be update after each hand. This is achieved by the funtion updatePos()
```java
public void updatePos()
```

## Hand 

A `Hand` has four phases: pre-flop, flop, turn, and river. Each phase contains the betting logics for each player. Each player's action is organized by a class called `Action`:
- There are four kinds of different actions: `CHECK` `CALL` `RAISE` `FOLD`

When `GameController` is given an Action and pass it to `Hand`, this action is first validated and used by `Hand`

- Validate Action: Before carrying out an Action, `Hand` needs to check if an Action is valid 
```java
public boolean checkAction(int pos, Action action)
```

- Add Action: After an Action is validated, it needs to be recorded by `Hand`
```java
public void addAction(Action action)
```
- Do Action: after receiving a valid action from `GameController`, `Hand` needs to use this current action in the betting logics and update all the variables and information in this hand
```java
public void doAction(int pos)
```

After the four phases are finished, a winner needs to be determined by `HandRanker`, and the pot needs to be distributed accordingly and correctly based on the winner and chips each player put in in this hand
- Determine a winner: compares the hand of all the players that are still active which means the player has not fold his/her hand and determines a winner 
```java
private int getWinner()
```
- Distribute the pot: the main pot is taken by the winner among all active players and the side pots are recursively distributed to other winners until there is no more chips in the pot
```java
private void distributePotToWinners(int pos)
```

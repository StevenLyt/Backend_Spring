# PokerSC: Next Generation State-Of-The-Art Online Poker Room 

## Intro & Scope
9-max Texas No-Limit Hold’em. It supports multiplayers and real-time audio chatting.
Enjoy the convenience of having fun with your friends without face-to-face contact in this post-pandemic time period. 
Fairness is our priority. Fisher–Yates shuffle algorithm guarantees each deal is uniformly-randomly made. No form of cheating is possible.
Want to watch how your friends play poker while not actually betting? Join the watching room! You can see the board while players’ own hands are kept private.

## Overview:
This project aims to create an online poker website that supports basic grouping and gaming experience. The development will involve using objects we learn from CSCI201 like Java Object Oriented Programming, multi-thread programming, Internetworking, and creating databases. 	

Now the website is Hosted on http://pokersc.live, feel free to play!

## Backend Technical Specifications

## Multithreading

In this project, we use the concept of muti-threading to achieve real-time communication and responses from the backend. There are three threads that keep running forever: `GameThread`, `Reception` , and the main thread running `Spring`

- `GameThread`  is a `Thread` that contains the main game and keeps it running 
  - `addUser(User user, int buyin, int pos)` adds a user to the current game, with information about buy-in and position. `Game.java` and `Reception.java` does not need to check whether the position is taken since `GameController.java` checks the validity of `pos`. 
  - `rebuy(String name, int amount)` update both a player’s remaining chips and total buy-in. 
  - `updatePos()` is called every time a round is over. It shifts everyone’s position by 1 and mod 8. 
  - `updateUserStats()` is called everytime a round is over. It updates everyone’s `total_round` and `total_profit` in their personal stats. 
  - `deleteUser(String username)` is used to remove a player from the game. 
  - `getBalance(String name)` is used to calculate how much has a player lose or gain right now. 
  - `getUserByUsername(String name)` is a helper function to search a user by his/h
- `Recption` is a `Thread` that interact between `GameController.java` and `Game.java` . Its primarary usage is to add and remove player to/from a game. 
  - `Reception` class has three variables, `usersToAdd`, `usersToLeave`, and `currentGame`.
    - `usersToAdd` contains users that need to be added to the game. 
    - `usersToLeave` contains users that want to quit
    - `currentGame` is the current game.
  - `addPlayer(User user, int buyin, int pos)` adds a user to `usersToAdd` 
  - `removePlayer(User user)` adds a user to `usersToLeave` 
  - `run()` keeps checking if both lists are empty, and if not add/remove elements to/from game.  
- The main`Spring` is a Spring Application that handles all the API calls
  - APIs will be explained further later in the document

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

## Hand Ranking Algorithm

### `HandRanker`

The `HandRanker` class selects the five most optimal cards from the seven-card pool.

- getRank

```java
public PokerHand getRank(List<Card> allCards)
```

It's the main method that get called to return the five most optimal cards as type `PokerHand` out the seven-card input list `List<Card> allCards`.
It runs through all possible scenarios which will be listed below:

- isStraightFlush

```java
public PokerHand getRank(List<Card> allCards)
```

It invokes two other checking methods `isFlush()` and `isStraight()` to check if the there they are both satisfied.

- isFlush

```java
private boolean isFlush(List<Card> allCards, boolean finalResult)
```

First, it converts `List<Card> allCards` input to a `Map<SUIT, Long> suitsMap` using the `getSuitMap()` method.
Second, it get the most frequent SUIT from the cards using the `getMostPopularSuit(suitsMap)` method.
Third, if the the number of occurances is greater than 5, we know there exists a flush and will then return `true`.

- isStraight

```java
private boolean isStraight(List<Card> allCards)
```

This method matches every single combination of the pool to `List<EnumSet<Card.RANK>> STRAIGHTS` and returns accordingly.

- isFourOfAKind

```java
private boolean isFourOfAKind(List<Card> allCards)
```

It runs the cards through method `getHighestCards()` and check whether the size of the of maximum occruance is four.

- isFullHouse

```java
private boolean isFullHouse(List<Card> allCards)
```

It runs the cards through method `getHighestCards()` and check whether there's both occurance three same cards and two same cards.

- isSet

```java
private boolean isSet(List<Card> allCards)
```

Similar to `isFourOfAKind()`, this time it limits the statifaction requirement to three.

- isTwoPair

```java
private boolean isTwoPair(List<Card> allCards)
```

Similar to `isSet()`, it still uses the `getHighestCards()` method to check whether there exists two pair of cards in the pool. Then, it modifies the pokerHand list to contain the most optimal five cards and return true if there exists two pairs.

- isPair

```java
private boolean isPair(List<Card> allCards)
```

Similar to `isTwoPair()`, now only one pair need to present to satisfy the condition.

- isHighCard

```java
private boolean isHighCard(List<Card> allCards)
```

Selects the toppest tanking five cards from the seven cards

## Front-Back interaction: RESTful API

The communication between frontend (React) and backend (Soringboot) is achieved with the help of controllers, which abides by RESTful API design. Each time the frontend requests something, it's esentially sending a `GET`/`POST` request to a predefined endpoint (url address). Every endpoint is bound with a function in the controller class, so that the controller can consume the request and inform the backend correspondingly. 
In this project, we implemented two controllere: `UserController` and `GameController`:

- `UserController` handles user sign-up and log-in.
- `GameController` deals with game-related functionalities, such as joining a game or raise to a certain amount.

Below is detailed specification about each endpoint and related controller functions. 

### UserController

- User sign-up. 
  This would return `failure` if the username is already taken. Otherwise, it woould store the new useer into our database and return the password's hash, which would be used in later information retrieval.

```
POST /signup?username={}&password={}&profile_url={}
```

```java
@PostMapping("/signup")
public String userSignup(@RequestParam String username, @RequestParam String password, @RequestParam String profile_url)
```

- User log-in. 
  This would retun `failure` if either the username doesn't exist or the password is incorrect. If verification is successful, this would return the password's hash.

```
POST /login?username={}&password={}
```

```java
@PostMapping("/login")
public String userLogin(@RequestParam String username, @RequestParam String password)
```

### GameController

- Get current game state for a specific user. To prevent information leak, returned string would only include this specific person's hand and other information that needs to be displayed. This function will return a json string. The following example demonstrates all entries needed: 

```
{
    "users": [
        null,
        null,
        null,
        {"username": "1","currentAction": "null","currentBet": 2,"remainingChips": 298,"totalProfit": -212,"currentProfit": -2,"winRate": 0.36363636363636365,"hand": ["",""],"profileUrl": "pfps/6039.png","ifFold": false,"isDealer": false,"isSelf": false,"isActive": false,"isWinner": false},
        {"username": "reza","currentAction": "null","currentBet": 0,"remainingChips": 1000,"totalProfit": -200,"currentProfit": -200,"winRate": 0.0,"hand": ["",""],"profileUrl": "pfps/1295.png","ifFold": false,"isDealer": true,"isSelf": false,"isActive": false,"isWinner": false},
        null,
        {"username": "Thomas","currentAction": "null","currentBet": 0,"remainingChips": 1200,"totalProfit": -419,"currentProfit": 0,"winRate": 0.0,"hand": ["",""],"profileUrl": "pfps/2031.png","ifFold": true,"isDealer": false,"isSelf": false,"isActive": false,"isWinner": false},
        {"username": "Jason","currentAction": "null","currentBet": 1,"remainingChips": 631,"totalProfit": -396,"currentProfit": 431,"winRate": 0.3333333333333333,"hand": ["",""],"profileUrl": "pfps/7246.png","ifFold": false,"isDealer": false,"isSelf": false,"isActive": false,"isWinner": false}
    ],
    "playersProfits": ["Jason:431","Thomas:0","1:-2","reza:-200"],
    "selfProfit": 0,
    "gameOn": true,
    "remainingChips": [0, 0, 0, 298, 1000, 0, 1200, 631],
    "communityCards": [d3, d6, sk, sj, d9],
    "pot": 3,
    "selfHand": ["h5","hK"],
    "selfPosition":3,
    "minimumRaiseAmount":4,
    "actionPosition":4,
    "dealerPosition":4,
    "state":0,
    "winner":-1,
    "timeLeft":15,
    "canLeave":true,
    "numActionLeft":3
}
```

```
POST /games?username={}&passwordHash={}
```

```java
@PostMapping("/games")
public String getGameState(@RequestParam String username, @RequestParam String passwordHash) 
```

- Join game.
  This will first check if this user isn't in the game yet and the position is valid. If so, a user is added to the game with the specified position and buy-in amount, and `sucess` is returned. Otherwise, this would return `failure`. 

```
POST /games/join?username={}&position={}&buyin={}
```

```java
@PostMapping("/games/join")
public String joinGameByUsername(@RequestParam String username, @RequestParam int position, @RequestParam int buyin) {
```

- Buy-in.
  If user isn't present in the current game, return `failure`, otherwise add the corresponding amount to the user's balance and return `success`.

```
POST /games/buyin?username={}&amount={}
```

```java
@PostMapping("/games/buyin")
public String userBuyin(@RequestParam String username, @RequestParam int amount) 
```

- Leave game. 
  If the user isn't present in the current game, return `failure`, otherwise remove the user from current game and return `success`.

```
POST /games/leave?username={}
```

```java
@PostMapping("/games/leave")
public String leaveGameByUsername(@RequestParam String username)
```

- User in-game actions: 

  - fold
  - check 
  - call 
  - raise (with amount)

   This will first check if this is a valid action, i.e. whether this user has already fold or whether it's their turn to take action. If the action is detected as invalid, `failure` is returned. Otherwise return `success`.

```
POST /games/fold?username={}
POST /games/check?username={}
POST /games/call?username={}
POST /games/raise?username={}amount={}
```

```java
@PostMapping("/games/fold")
public String userFold(@RequestParam String username)
@PostMapping("/games/check")
public String userCheck(@RequestParam String username)
@PostMapping("/games/call")
public String userCall( @RequestParam String username)
@PostMapping("games/raise"
public String userRaise(@RequestParam String username, @RequestParam int amount) 
```

- Send emoji.
  This would take a useername and a integer that denotes an emoji. If the user is found, other users would be informed and `success` is returned. Otherwise return `failure`.

 ```
 POST /games/emoji?username={}&emoji={}
 ```

```java
@PostMapping("games/emoji")
public String sendEmoji(@RequestParam String username, @RequestParam int emoji) 
```

## Frontend Technical Specifications

### Frameworks

- Here we use `ReactJS` as our frontend framework, which is very handy when it comes to component reuse & abstraction, async API fetching, and routing 

- UI templates
  - we reused some pre-designed components from `ANT DESIGN PRO` and `MUI`
  - Most of the UI like the main components and user profiles are designed by ourselves
  - For cards, we used an asset set from the internet

### Routing

```jsx
<Router>
    <Routes>
        <Route path="/" element={<App />} />
        <Route path="/gaming" element={<Gaming guest={false}/>} />
        <Route path="/guest" element={<Gaming guest={true}/>} />
    </Routes>
</Router>
```

We use the React Router for path routing

### User Session

We want a user session that will immediately timeout after the user closes the tab or refreshes, so we create a `UserSession ` like a global variable that have a life span same with the browser tab.

```js
var UserSession = (function() {
    var username = "";
    var passwordHash = "";
  
    var getName = function() {
      return username;
    };
  
    var setName = function(name) {
      username = name;
    };

    var getHash = function() {
      return passwordHash;
    };
  
    var setHash = function(hash) {
      console.log("setHash " + hash);
      passwordHash = hash;
    };
  
    return {
      getName: getName,
      setName: setName,
      getHash: getHash,
      setHash: setHash
    }
  
})();
  
export default UserSession;
```

### API calls

Here is our `loadData` function that calls the API every second to fetch the most recent game state as a `json` from our backend.  We use the `JavaScript`'s `fetch` function to call the api, and use its 	`callback` functions to update our frontend ui.

```jsx
loadData() {
        const requestOptions = {
            method: 'POST',
            headers: { 'Content-Type': 'text/plain;charset=UTF-8' }
        };
        var requestUrl = `http://45.79.72.230:8080/games?username=${UserSession.getName()}&passwordHash=${UserSession.getHash()}`;
        
        console.log(requestUrl)
        fetch(requestUrl, requestOptions)
        .then(response => response.text())
        .then(
            data => {
            console.log(data);
            if(data !== "no user found"){
                console.log(requestUrl+" success");
                const parsed_state = JSON.parse(data);
                this.setParsedStateToState(parsed_state);
            }
            else{
                alert("no user found");
            }
            }
        )
        .catch(err => {
            alert("Encounter Error");
        })
     }
```




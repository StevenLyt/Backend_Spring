# Front-Back interaction: RESTful API
The communication between frontend (React) and backend (Soringboot) is achieved with the help of controllers, which abides by RESTful API design. Each time the frontend requests something, it's esentially sending a `GET`/`POST` request to a predefined endpoint (url address). Every endpoint is bound with a function in the controller class, so that the controller can consume the request and inform the backend correspondingly. 
In this project, we implemented two controllere: `UserController` and `GameController`:
- `UserController` handles user sign-up and log-in.
- `GameController` deals with game-related functionalities, such as joining a game or raise to a certain amount.
  
Below is detailed specification about each endpoint and related controller functions. 
## UserController
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

## GameController

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
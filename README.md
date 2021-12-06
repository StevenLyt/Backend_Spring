# Front-Back interaction: RESTful API
The communication between frontend (React) and backend (Soringboot) is achieved with the help of controllers, which abides by RESTful API design. Each time the frontend requests something, it's esentially sending a `GET`/`POST` request to a predefined endpoint (url address). Every endpoint is bound with a function in the controller class, so that the controller can consume the request and inform the backend correspondingly. 
In this project, we implemented two controllere: `UserController` and `GameController`:
- `UserController` handles user sign-up and log-in.
- `GameController` deals with game-related functionalities, such as joining a game or raise to a certain amount.
  
Below is detailed specification about each endpoint and related controller function. One thing to note is that to avoid confusion and, we decided to include `/api` at the beginning of all endpoint. To achieve this end, we added the following annotation before each controller definition:
```java
@RequestMapping(name ="/api")
```

## UserController
- User sign-up. 
  This would return `failure` if the username is already taken. Otherwise, it woould store the new useer into our database and return the password's hash, which would be used in later information retrieval.
```
POST /api/signup?username={}&password={}&profile_url={}
```
```java
@PostMapping("/signup")
public String userSignup(@RequestParam String username, @RequestParam String password, @RequestParam String profile_url)
```

- User log-in. 
  This would retun `failure` if either the username doesn't exist or the password is incorrect. If verification is successful, this would return the password's hash.

```
POST /api/login?username={}&password={}
```
```java
@PostMapping("/login")
public String userLogin(@RequestParam String username, @RequestParam String password)
```

## GameController

- Get current game state for a specific user. To preveny information leak, returned string would only include this specific person's hand and other information that needs to be displayed. This function will return a json of the following info.
```
sdsds
```
```
POST /api/games?username={}&passwordHash={}
```
```java
@PostMapping("/games")
public String getGameState(@RequestParam String username, @RequestParam String passwordHash) 
```

- Join game.
  This will first check if this user isn't in the game yet and the position is valid. If so, a user is added to the game with the specified position and buy-in amount, and `sucess` is returned. Otherwise, this would return `failure`. 

```
POST /api/games/join?username={}&position={}&buyin={}
```
```java
@PostMapping("/games/join")
public String joinGameByUsername(@RequestParam String username, @RequestParam int position, @RequestParam int buyin) {
```
- Buy-in.
  If user isn't present in the current game, return `failure`, otherwise add the corresponding amount to the user's balance and return `success`.

```
POST /api/games/buyin?username={}&amount={}
```
```java
@PostMapping("/games/buyin")
public String userBuyin(@RequestParam String username, @RequestParam int amount) 
```

- Leave game. 
  If the user isn't present in the current game, return `failure`, otherwise remove the user from current game and return `success`.

```
POST /api/games/leave?username={}
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
POST /api/games/fold?username={}
POST /api/games/check?username={}
POST /api/games/call?username={}
POST /api/games/raise?username={}amount={}
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
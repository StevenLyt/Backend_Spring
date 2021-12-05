# Front-Back interaction API

- Sign up

```
POST /signup?username={}&password={}&profile_url={}
```
- User log-in. 
- If successful, this would return the password's hash, which would be used in later information retrieval.

```
POST /login?username={}&password={}
```

- Fetch user information and stats from database provifng its username/id (probably unnecessary, since log in would return User entity)

```
GET /users/username 
// returns a single user resource with the specified username
```

## Game

- Get current game state for a specific user. To preveny information leak, this only returns this specific person's hand and other information that needs to be displayed.

```
POST /games?username={}&passwordHash={}
```

- Join game

```
POST /games/join?username={}&position={}&buyin={}
```
- User buy-in

```
POST /games/buyin?username={}&amount={}
```

- Leave game

```
POST /games/leave?username={}
```

- User in-game actions

```
POST /games/fold?username={}
POST /games/check?username={}
POST /games/call?username={}
POST /games/raise?username={}amount={}
```

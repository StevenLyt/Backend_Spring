# Front-Back interaction API

- Log in

```
POST /login?username={}&password={}
// returns a User entity if user found and password matches,
// otherwise return null
```

- Sign up

```
POST /signup?username={}&password={}&profile_url={}
```

- Fetch all user information 

```
GET /users 
```

- Fetch user information and stats from database provifng its username/id (probably unnecessary, since log in would return User entity)

```
GET /users/username 
// returns a single user resource with the specified username
```

## Game

- Buy-in

```
POST /games/username/buyin?amount={}
```

- Get current game state for a specific user. This person can only see his/her own cards.

```
POST /games?username={}&passwordHash={}
```

- Join game

```
POST /games?username={}&
```

- Leave game

```
POST /games/{game_id}/{user_id}/leave
```

- Request info of a single game

```
GET /games/{room_id/game_id}
```

- User in-game actions

```
POST /games/{room_id/game_id}/{user_id}/fold
POST /games/{room_id/game_id}/{user_id}/check
POST /games/{room_id/game_id}/{user_id}/call?amount={}
POST /games/{room_id/game_id}/{user_id}/raise?amount={}
```

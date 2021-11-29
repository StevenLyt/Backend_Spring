# Front-Back interation API

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

- Fetch user information and stats from database provifng its username/id (probably unnecessary, since log in would return User entity)

```
GET /users/username 
// returns a single user resource with the specified username
```

- Buy-in

```
POST /user/id/buyin?amount={}
```

## Game

- A user creates a room

```
POST /games
// not sure if we need more details in the url
```

- Join game with game_id

```
POST /games/{game_id}/{user_id}
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

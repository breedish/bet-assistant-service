# bet-assistant-service
=====================

## REST Resource overview

1. *GET* _/competitions/v1_ - returns list of registered competitions
Sample request:
```
GET http://localhost:8080/bet/competitions/v1 
```
Sample response:
```json
["NBA"]
```
2. *GET* _/competitions/v1/{competition}/teams_ - returns list of registered teams for given competition
Sample request:
```
GET http://localhost:8080/bet/competitions/v1/nba/teams 
```
Sample response:
```json
[
    {
        "id": 590,
        "name": "BOSTON",
        "niceName": "boston"
    },
    {
        "id": 42723,
        "name": "BROOKLYN",
        "niceName": "brooklyn"
    }
]
```
3. *GET* _/competitions/v1/{competition}/matches/summary/_ - returns stats of latest matches for team of given competition
Sample request:
 ```
 GET http://localhost:8080/bet/competitions/v1/nba/matches/summary/ 
 ```
Sample response:
 ```json
 {
     "totalMatches": 113,
     "totalGoals": 22477,
     "results": {
         "196": [
             {
                 "isHome": false,
                 "scoreHome": 84,
                 "scoreAway": 101
             },
             {
                 "isHome": true,
                 "scoreHome": 98,
                 "scoreAway": 105
             },
             {
                 "isHome": true,
                 "scoreHome": 95,
                 "scoreAway": 108
             },
             {
                 "isHome": false,
                 "scoreHome": 90,
                 "scoreAway": 98
             },
             {
                 "isHome": false,
                 "scoreHome": 91,
                 "scoreAway": 89
             },
             {
                 "isHome": true,
                 "scoreHome": 112,
                 "scoreAway": 103
             },
             {
                 "isHome": false,
                 "scoreHome": 96,
                 "scoreAway": 104
             },
             {
                 "isHome": false,
                 "scoreHome": 100,
                 "scoreAway": 104
             },
             {
                 "isHome": false,
                 "scoreHome": 97,
                 "scoreAway": 95
             }
         ],
         "201": []
     }
 }
 ```

## Notes
- By default application is deployed under */bet/* context path

### TODO
- [ ] Deploy to OpenShift
- [ ] Refactor NBA Parser
- [ ] Profile overall performance
- [ ] Should we use underlying storage(MongoDB, etc.)???
- [ ] Move to Spring REST HATEOAS
- [ ] Cover with tests

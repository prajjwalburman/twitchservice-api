# twitchservice-api

**Table of Contents**

- [Usage](#usage)
- [Requirements and Downloads](#requirements-and-downloads)
- [How to run](#how-to-run)
- [Contributions](#contributions)
- [Licenses](#licenses)


# Usage
This application can connect with twitch developer portal to fetch all the attributes related to the streamers and the games being streamed.
Based on certain parameter it displays the games that can be streamed to get most exposure, statistics of games being streamed and similar results.

# Requirements and Downloads
  * Java 1.8
  * Spring Framework Boot > 2.x.x
  * You must generate your clientId and clientSecret from twitch developer portal. Follow [these steps](https://dev.twitch.tv/docs/authentication/getting-tokens-oauth).
  
# How to run
1. Import application as a gradle project.

2. Set the following environment variables. You can add/update profiles as per your requirements:
  ```
  spring.profiles.active=local
  ```
  
3. Add you twitch 'clientId' and 'clientsecret' in application.yml
  ```gradle
    twitch:
      clientid: #Enter your clientId here
      clientsecret: #Enter your clientSecret here
      rest:
        helix:
          games: /helix/games
          streams: /helix/streams
        kraken:
          streamsummary: /kraken/streams/summary
        outh:
          appaccesstoken: /oauth2/token
  ```
4. Start the server. You can access the swagger from:
  ```
  https://localhost:8080/swagger-ui.html
  ```
  
# Contributions
Contributions are welcome.
  
# Licenses
`twitchservice-api` are licensed under the Apache 2.0 License. See [LICENSE](LICENSE.md) for details.

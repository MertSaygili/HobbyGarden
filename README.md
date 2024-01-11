# HobbyGarden

## Installation and Running Locally

You need to modify `application.properties` by yourself.

### Linux (or MacOS) users
```bash
git clone git@github.com:MertSaygili/HobbyGarden.git hobby_garden_server
cd hobby_garden_server/
./mvnw spring-boot:run
```

### Windows users
```powershell
git clone "git@github.com:MertSaygili/HobbyGarden.git" hobby_garden_server
cd .\hobby_garden_server\
.\mvnw.cmd spring-boot:run
```

## Running in Container (Docker Compose)


### Linux (or MacOS) users
```bash
git clone git@github.com:MertSaygili/HobbyGarden.git hobby_garden_server
mv containers/docker-compose-example.bak containers/docker-compose.yml
```
Edit `docker-compose.yml` with your favourite text editor.

```bash
sudo systemctl start docker.service # Temporary
sudo systemctl enable --now docker.service # Permanent
cd hobby_garden_server/
./build.sh
```

### Windows users
```powershell
git clone "git@github.com:MertSaygili/HobbyGarden.git" hobby_garden_server
mv .\containers\docker-compose-example.bak .\containers\docker-compose.yml
```
Edit `docker-compose.yml` with your favourite text editor.

```powershell
Start-Service Docker
cd .\hobby_garden_server\
docker build -t "hobby-garden:0.1-eclipse-temurin-17" . --no-cache --network host
docker compose -f ..\containers\docker-compose.yml up -d
```
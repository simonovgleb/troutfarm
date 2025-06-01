start /w cmd /c "mvnw clean package spring-boot:build-image -DskipTests -Dspring-boot.build-image.imageName=troutfarm-backend:latest"
start /w cmd /c "docker compose build"
start cmd /k "docker compose up -d"


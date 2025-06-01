start /w cmd /c "docker compose build db"
start "%~dp0" cmd /k "docker compose up db"
start "%~dp0" cmd /k "mvnw clean package -DskipTests spring-boot:run"

@echo off

cd "backend" && .\mvnw "spring-boot:build-image" && cd "../" && docker "compose" "up" "--build"

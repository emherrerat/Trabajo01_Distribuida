echo Presiona la tecla Enter 
echo Nombre:Elvis Herrera
PAUSE
git clone https://github.com/emherrerat/Trabajo01_Distribuida.git && 
cd ./Trabajo01_Distribuida && cd ./app-authors && gradlew quarkusBuild && 
docker build -t  jaimesalvador/app-authors:1.0.0 . && 
cd .. && 
cd ./app-books && gradlew installDist && 
gradlew copyLibs && 
docker build -t  jaimesalvador/app-books:1.0.0 . && 
cd .. && 
cd ./app-web && gradlew installDist && gradlew copyLibs && 
docker build -t  jaimesalvador/app-web:1.0.0 . && docker push  jaimesalvador/app-web:1.0.0 && 
docker push  jaimesalvador/app-books:1.0.0 && docker push  jaimesalvador/app-authors:1.0.0 && 
cd .. && docker-compose up
PAUSE

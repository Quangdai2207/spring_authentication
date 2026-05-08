This mini project about basic authentication with Spring Boot deployed Docker. including:
    1. Spring security
    2. Associate with JWT
    3. Organize modules in the server BE
    4. Test with FE UI, Swagger UI, Rest Http .http file

Clone repo by ssh
git clone git@github.com:Quangdai2207/spring_authentication.git

clone by http
git clone https://github.com/Quangdai2207/spring_authentication.git

Init file .env to set variable environment. You can looking at the .env.example file to create them before start 
whole the app system.

Run the whole system
run as background: docker compose up -d --build        
runs as process:   docker compose up --build

Stop system
docker compose down <optional [-v]>
Press keys "Ctrl + c" if you run docker as process

Run Service ports:
    1. Server on:              8080
    2. Swagger UI:             http://localhost:8080/swagger-ui/index.html
    3. Fe_test UI:             3000 with http://localhost:3000
    4. postgres on:            5432
    5. postgres admin UI:      5050 with http://localhost:5050/browser/

test with .http 
folder testApis in the Server service with path ./server/src/testApi. test apis directly with .http REST



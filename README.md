# Getting Started

- docker-compose up --build
- go to http://localhost:8080/swagger-ui.html#

# ROLES:

ROLE_ADMIN, ROLE_TRAINER, ROLE_USER

# API vs role

- ROLE_ADMIN :
    - /training-status
    - /training-type
    - /user
    - /group
    - /role
    - /subscription
    - /subscription-type/delete
    - /subscription-type/create 
    - /subscription-type/update

- ROLE_ADMIN, ROLE_TRAINER:
    - /training/{id}/miss - /training/{id}/approve - /training/{id}/complete

- ROLE_USER, ROLE_TRAINER:
    - /training/plan
  
WARNING:
you have to send bearer token with each request in hearer Authorization. ex :
curl --location --request POST 'http://localhost:8080/training/plan' \
--header 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0cmFpbmVyIiwicm9sZXMiOlsiUk9MRV9UUkFJTkVSIl0sImV4cCI6MTYyMTQ2MzQyN30.hdox6jYvq1d1gedfHem-6VjrxxJwyosZjRzbkdDGJpk' \
--header 'Content-Type: application/json' \
--header 'Cookie: JSESSIONID=F3F09939B3D615FD096204041CC2D727' \
--data-raw '{
"dateTraining": "2021-02-02",
"id": null,
"idClient": 1,
"idStatus": null,
"idTrainer": 2,
"idType": 1,
"price": 10.0
}'

#Password@user:
 - trainer@trainer
 - user@user
 - admin@admin
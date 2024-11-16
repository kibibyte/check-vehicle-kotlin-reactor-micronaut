# Check vehicle status application

## Modules

* app
* data
* domain

## Build

```shell script
$ ./gradlew build
```

## Test

```shell script
$ ./gradlew test
```

## Running locally

```shell script
$ docker compose up
```

Example request (3rd party services are wiremocked):

```shell script
curl --request POST \
  --url http://localhost:8080/check \
  --header 'Content-Type: application/json' \
  --data '{
	"vin": "existing_vin",
	"features": ["accident_free","maintenance"]
}'
```

![image](https://github.com/user-attachments/assets/48383037-78ae-483f-9047-f5f080743d8d)


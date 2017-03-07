ENDPOINT=https://sheltered-earth-81944.herokuapp.com/tcss360/reviews
# local web runner deploy
#ENDPOINT=http://localhost:8080/tcss360/reviews
# net beans deploy
#ENDPOINT=http://localhost:8084/sample_maven_web_app/tcss360/users
# manual deploy
#ENDPOINT=http://localhost:8080/sample_maven_web_app-1.0-SNAPSHOT/tcss360/users
curl -X PUT -H "Content-Type: application/json" -d @./great_review.json $ENDPOINT
echo


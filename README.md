
# UserCity_Api_Assignment
Consume User Rest api using Spring cloud Feign.



Filter the user result by city and given distance.


Api need basic authorization - use below user/password in request header

      username: jayee 
  
      password: password


URLs:

http://localhost:8080/swagger-ui.html#

http://localhost:8080/api/v1/users

http://localhost:8080/api/v1/users/1

http://localhost:8080/api/v1/city/london/users

http://localhost:8080/api/v1/city/london/users?distance=50&units=km

http://localhost:8080/api/v1/city/london/users?distance=50&units=mile

http://localhost:8080/api/v1/city/london/users?distance=50&units=meter

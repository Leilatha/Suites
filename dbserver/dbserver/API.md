# API

Unless otherwise stated, requests require Basic Authentication with the account
email as the username, and the password as the password.

Account Management
==================
All account requests are to the path ```/accounts```.

Account Info
------------
To get information on the authenticated account, send a plain GET request with
no parameters.

Account Registration
--------------------
To register an account, send a POST request with Content-Type application/json,
and with a body with the following JSON encoding:
```
{
  "email" : "useremail@email.com",
  "password" : "userpassword",
  "name" : "Myfull Name"
}
```

The response will also be encoded as JSON, with the following format:
```
{
  "success" : <a boolean>,
  "message" : "A message only really relevant on failure"
}
```

This request does not require authentication.

Suite Management
================

Adding a Suite
--------------
Adding a suite will automatically add the authenticated user to the suite.
The request takes the form
```
{ "name" : "suitename" }
```

The response is a JSON object of the form:
```
{
  "success" : <a boolean>,
  "message" : "A message only really relevant on failure"
}
```
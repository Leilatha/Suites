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

Requests are at the path "/suite".

Adding a Suite
--------------
Adding a suite will automatically add the authenticated user to the suite.
The request is a POST request and takes the form
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

Get User Suites
---------------
This request is for getting a list of the authenticated user's suites. Send a
GET request, and you will get as a response a JSON list of objects of the form:
```
{
  "id" : 420,
  "name" : "suitename"
}
```

Suite Invitation
================

Requests are at the path "/invite".

Get User Invites
----------------
Send a GET request. It will return a list of JSON objects in the same format as
getting user suites.

Make an Invitation
------------------
Send a POST request with a JSON body of the following format:
```
{
  "invitee" : "invitee@email.com",
  "suiteId" : 666
}
```

A response will come in the form
```
{
  "success" : <a boolean>,
  "message" : "A message only really relevant on failure"
}
```

Joining a Suite
===============
To join a suite, send a POST request to "/join", with a JSON number matching the
ID of the suite you would like to join. A response comes in the form
```
{
  "success" : <a boolean>,
  "message" : "A message only really relevant on failure"
}
```

Listing Users in a Suite
========================
To list the users of a suite, send a GET request to "/suite/userlist?suiteid=x"
where "x" is the id of the suite you want to list the users of. The response
comes as a JSON list of objects of the form:
```
{
  "id" : 42,
  "email" : an@email.net,
  "name" : "My name",
  "profilePicture" : "pictureaddress"
}
```

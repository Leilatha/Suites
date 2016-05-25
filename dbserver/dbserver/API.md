# API

Unless otherwise stated, requests require Basic Authentication with the account
email as the username, and the password as the password.

Account Management
==================
All account requests are to the path ```/accounts```.

Account Info
------------
To get information on the authenticated account, send a plain GET request with
no parameters. Optionally, you can add the query parameter "email" set to the
email of a user you want to search. If there is no user with that email, the
response will be empty (and have response code 204).

Account Registration
--------------------
To register an account, send a POST request with Content-Type application/json,
and with a body with the following JSON encoding (RegistrationRequest.java):
```
{
  "email" : "useremail@email.com",
  "password" : "userpassword",
  "name" : "Myfull Name"
}
```

The response will also be encoded as JSON, with the following format (GenericResult.java):
```
{
  "success" : <a boolean>,
  "message" : "A message only really relevant on failure"
}
```

This request does not require authentication.

Account Editing
---------------
The same as registration, except you must authenticate.

Suite Management
================

Requests are at the path "/suite".

Adding a Suite
--------------
Adding a suite will automatically add the authenticated user to the suite.
The request is a POST request and takes the form (AddSuiteRequest.java):
```
{ "name" : "suitename" }
```

The response is a JSON object of the form (GenericResult.java):
```
{
  "success" : <a boolean>,
  "message" : "A message only really relevant on failure"
}
```

Get User Suites
---------------
This request is for getting a list of the authenticated user's suites. Send a
GET request, and you will get as a response a JSON list of objects of the
form (core/Suite.java):
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
Send a POST request with a JSON body of the following format (Invitation.java):
```
{
  "invitee" : "invitee@email.com",
  "suiteId" : 666
}
```

A response will come in the form (GenericResult.java):
```
{
  "success" : <a boolean>,
  "message" : "A message only really relevant on failure"
}
```

Joining a Suite
===============
To join a suite, send a POST request to "/join", with a JSON number matching the
ID of the suite you would like to join. A response comes in the form (GenericResult.java):
```
{
  "success" : <a boolean>,
  "message" : "A message only really relevant on failure"
}
```

Leaving a Suite
===============
To leave a suite, send a PUT request to "/suite/leave", with a JSON number matching
the ID of the suite you would like to leave. The response should be empty on
success.

Listing Users in a Suite
========================
To list the users of a suite, send a GET request to "/suite/userlist?suiteid=x"
where "x" is the id of the suite you want to list the users of. The response
comes as a JSON object in the form (UserListResult.java):
```
{
  "success" : true,
  "message" : "Failure message in case of failure",
  "userList" : [ { "id" : 42, "email" : an@email.net,
                   "name" : "My name",
                   "profilePicture" : "pictureaddress"
               }]
}
```

Groceries
=========
All requests go to "/grocery".

List Suite Groceries
--------------------
Send a GET request with the query parameter "suiteid" set to the id of the suite.
The response will come in the form (GroceryListResult.java):
```
{
  "success" : <a boolean>,
  "message" : "Failure message, in case success is false",
  "groceryList" : [{ "id" : 1, "name" : "Grocery Name", "quantity" : 300, "price" : 19.84 }]
}
```

Add Grocery
-----------
Send a POST request with the query parameter "suiteid" set to the id of the
suite, with content of the form (core/Grocery.java):
```
{
  "name" : "Grocery Name",
  "quantity" : 400,
  "price" : 5.22
}
```

A response will come in the form (GenericResult.java):
```
{
  "success" : <a boolean>,
  "message" : "Failure message, irrelevant if succeeded"
}
```

Edit Grocery
------------
Send a PUT request with the query parameter "groceryid" set to the id of the
grocery item to edit, and with content of the same form as adding groceries.
The response will come in the same form too.

Delete Grocery
--------------
Send a DELETE request with the query parameter "groceryid" set to the id of the
grocery item to remove. The response comes in the same form as adding and editing
groceries.

Chores
======

Get Suite Chores
----------------
Send a GET request with the query parameter "suiteid" set to the id of the suite
to the path /chore. The response will come in JSON-encoded ChoreListResult.

Add Chore
---------
Send a POST request with the query parameter "suiteid" set to the id of the suite
to the path /chore, with JSON-encoded AddChoreRequest as the body. The
response will come in the form of JSON-encoded GenericResult.

Edit Chore
----------
Send a PUT request with the query parameter "choreid" set to the id of the chore
to the path /chore, with JSON-encoded AddChoreRequest as the body. The response
will come in the form of JSON-encoded GenericResult.

Delete Chore
------------
Send a DELETE request with the query parameter "choreid" set to the id of the chore
to the path /chore. The response will come in the form of JSON-encoded
GenericResult.

Advance a Chore
---------------
This is to move the chore assignee to the next person. Send a POST request to the
path /chore/advance with the query parameter "choreid" set to the id of the
chore.

PSA
===

Get Suite PSAs
--------------
Send a GET request to /psa with the query parameter "suiteid" set to the suite ID.
The response is a JSON-encoded PSAListResult.

Add a PSA
---------
Send a POST request to /psa with the query parameter "suiteid" set to the suite ID,
and with the content of a JSON-encoded AddPSARequest. The response is a
JSON-encoded GenericResult.

Edit a PSA
----------
Send a PUT request to /psa with the query parameter "psaid" set to the PSA ID,
and with the content of a JSON-encoded AddPSARequest. The response is a
JSON-encoded GenericResult.

Delete a PSA
------------
Send a DELETE request to /psa with the query parameter "psaid" set to the PSA
ID. The response is a JSON-encoded GenericResult.
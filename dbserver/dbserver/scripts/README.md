# Here are some scripts that can be used to test the server.

Manual Requests
===============
curlsuite.sh (in the dbserver folder) can be used to manually write requests to
a Suites server - it is just curl with some default options.

To make a GET request, do
```
./curlsuite.sh -u email:password address:port/somepath
```

To make a POST request, do
```
./curlsuite.sh -u email:password -d "data" address:port/somepath
```

For example, if I wanted to get the account info of bob@bob.com, with the
password "pass", on a server running locally on port 8080, I could do
```
./curlsuite.sh -u bob@bob.com:pass localhost:8080/account
```

To create a suite, I could do
```
./curlsuite.sh -u bob@bob.com:pass -d "{ \"name\":\"Fun Suite\" }" localhost:8080/suite
```

For registration, the user info can be omitted.


Account Creation
================
```./newacc address:port email password name```

Account Info
============
```./accinfo address:port email password```

Suite Creation
==============
```./mksuite address:port email password suitename```

Account Suite List
==================
```./accsuites address:port email password```
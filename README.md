# Highlight

- No libraries other than JDK
- HEAD, GET, DELETE available
  - OOP applied for the each method implementation (HeadRequestHandler, GetRequestHandler, and DeleteRequestHandler extends RequestHandler)
- Logging availble
- Multithreading available

# How-to-run

Add resourse path and port and then run the WebServerMain!

![image](https://github.com/hello-yoochul/Networking/assets/49010295/b929d03e-cbf6-4509-a62f-a9040dee6d86)

## Available URL

- HTML
  - http://localhost:54321/index.html
  - http://localhost:54321/page2.html
  - http://localhost:54321/page3.html

- JPG
  - http://localhost:54321/beer.jpg
  - http://localhost:54321/tp_it.jpg

# Demo

## Chrome
![image](https://github.com/hello-yoochul/Networking/assets/49010295/e3678ea2-ffc5-46e6-af35-76c1d8317493)
![image](https://github.com/hello-yoochul/Networking/assets/49010295/f53f7271-73f5-4198-aa6f-c4a54e353507)
![image](https://github.com/hello-yoochul/Networking/assets/49010295/ef8a257a-e8e4-4a73-bc59-0a7010ebf3b1)
![image](https://github.com/hello-yoochul/Networking/assets/49010295/3d809478-8496-4967-9ded-8d652cf5041a)
![image](https://github.com/hello-yoochul/Networking/assets/49010295/ecd7bd92-cbf4-483e-971a-5d97c6aa88a8)

## CURL

```
curl -s -I -X GET localhost:54321/index.html
curl -X DELETE localhost:54321/beer.jpg
```

## Logging example

`LogUtil` is implementation for logging. Under /log/ directory, log file will be created (if not exist) and log text will be added.

```
-------------------- Client Request Log --------------------
Client IP       : /0:0:0:0:0:0:0:1
Date/Time       : 2023-06-07T20:54:20.019
Request Line    : GET /noFileWithThisName.html HTTP/1.1
Server Response : HTTP/1.1 404 Not Found  Requested path doesn't exist

-------------------- Client Request Log --------------------
Client IP       : /0:0:0:0:0:0:0:1
Date/Time       : 2023-06-07T20:54:23.716
Request Line    : GET /beer.jpg HTTP/1.1
Server Response : HTTP/1.1 200 OK

-------------------- Client Request Log --------------------
Client IP       : /127.0.0.1
Date/Time       : 2023-06-07T20:57:35.133
Request Line    : DELETE /beer.jpg HTTP/1.1
Server Response : HTTP/1.1 200 OK
```

# Requirements

## Basic Requirements

- The main method for your server should take two command-line arguments, the directory from which your server will serve documents to clients and the port on which your server should listen.
- The server should support and respond correctly to HEAD requests.
- The server should support and respond correctly to GET requests.
- The server must be able to return HTML documents requested by a client.
- The server should respond with appropriate error messages when non-existent services or resources are requested.

## Advanced Requirements (Optional)

- Returning of binary images (GIF, JPEG and PNG)
- Multithreading – support multiple concurrent client connection requests up to a specified limit
- Logging – each time requests are made, log them to a file, indicating date/time, request type, response code etc.
- Supporting other methods in addition to GET and HEAD

# Resourse

- [HTTP requests strucutre](https://www.ibm.com/docs/en/cics-ts/5.2?topic=protocol-http-requests)
- [HTTP responses strucutre](https://www.ibm.com/docs/en/cics-ts/5.2?topic=protocol-http-responses)
- [9 HTTP methods and how to use them](https://testfully.io/blog/http-methods/)
- [HTTP request and response examples](https://www.ibm.com/docs/en/netcoolomnibus/7.4?topic=interface-http-request-response-examples)

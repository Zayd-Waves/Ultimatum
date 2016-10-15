var express = require("express");
var fs = require('fs');
var path = require("path");
var bodyParser = require("body-parser");
var mongodb = require("mongodb");
var ObjectID = mongodb.ObjectID;

var app = express();
app.use(express.static(__dirname));
app.use(bodyParser.json());

// Create a database variable outside of the database connection callback to reuse the connection pool in your app.
var db, users, pacts;

// Connect to the database before starting the application server.
mongodb.MongoClient.connect("mongodb://127.0.0.1:27017/ultimatum", function (err, database) {
  if (err) {
    console.log(err);
    process.exit(1);
  }

  // Save database object from the callback for reuse.
  db = database;
  users = db.collection("users");
  pacts = db.collection("pacts");
  console.log("Database connection ready");

  // Initialize the app.
  var server = app.listen(process.env.PORT || 8080, function () {
    var port = server.address().port;
    console.log("App now running on port", port);
  });
});

// CONTACTS API ROUTES BELOW
// Generic error handler used by all endpoints.
function handleError(res, reason, message, code) {
  console.log("ERROR: " + reason);
  res.status(code || 500).json({"error": message});
}

/*  "/users"
 *    GET: finds all users
 *    POST: creates a new user
 */

app.get("/users", function(req, res) {
  users.find({}).toArray(function(err, docs) {
    if (err) {
      return handleError(res, err.message, "Failed to get users.");
    } else {
      res.status(200).json(docs);
    }
  });
});

app.post("/users", function(req, res) {
  var newUser = req.body;
  newUser.pacts = [];
  newUser.createDate = new Date();

  if (!req.body.username) {
    return handleError(res, "Invalid user input", "Username must not be empty.", 400);
  }

  users.findOne({"username": newUser.username}, function(err, userExists) {
    if (err) {
      return handleError(res, err.message, "Failed to check if user already exists.");
    }
    if (userExists) {
      return handleError(res, "Username already taken", "Choose a different username", 400);
    }

    users.insertOne(newUser, function(err, doc) {
      if (err) {
        return handleError(res, err.message, "Failed to create new user.");
      } else {
        res.status(201).json(doc.ops[0]);
      }
    });
  });
});

/*  "/users/:id"
 *    GET: find user by id
 *    PUT: update user by id
 *    DELETE: deletes user by id
 */

app.get("/users/:id", function(req, res) {
});

app.put("/users/:id", function(req, res) {
});

app.delete("/users/:id", function(req, res) {
});

// app.get('/ping', function (req, res) {
//   res.send('Ultimatum''s server.');
// });
//
// app.listen(port, ipaddress, function() {
//     console.log('%s: Node server started on %s:%d ...',
//                 Date(Date.now() ), 'ec2-52-38-253-207.us-west-2.compute.amazonaws.com', 8080);
// });

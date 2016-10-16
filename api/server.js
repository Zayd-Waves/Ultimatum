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
    console.log("Ultimatum API now running on port", port);
  });
});

// API ROUTES BELOW
// Generic error handler used by all endpoints.
function handleError(res, reason, message, code) {
  console.log("ERROR: " + reason);
  // res.status(code || 500).json({"error": message});
  res.status(500).json({"error": message});
}

/*  "/users"
 *    GET: finds all users
 *    POST: creates a new user
 */

app.get("/users", function(req, res) {
  users.find({}).toArray(function(err, docs) {
    if (err) {
      return handleError(res, err.message, "Failed to get all users.");
    } else {
      res.status(200).json(docs);
    }
  });
});

app.post("/users", function(req, res) {
  var newUser = req.body;
  newUser.pacts = [];
  newUser.createDate = new Date();

  if (!newUser.username) {
    return handleError(res, "Invalid user input", "Username must not be empty.", 400);
  }

  users.findOne({ username: newUser.username }, function(err, doc) {
    if (err) {
      return handleError(res, err.message, "Failed to check if user already exists.");
    } else if (doc) {
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

app.get("/usersById/:id", function(req, res) {
  users.findOne({ _id: new ObjectID(req.params.id) }, function(err, doc) {
    if (err) {
      return handleError(res, err.message, "Failed to get user.");
    } else if (!doc) {
      return handleError(res, "Invalid user ID", "User with that ID does not exist.", 400);
    }

    else {
      res.status(200).json(doc);
    }
  });
});

app.put("/usersById/:id", function(req, res) {
  var updateDoc = req.body;
  delete updateDoc._id;

  users.findOne({ _id: new ObjectID(req.params.id) }, function(err, doc) {
    if (err) {
      return handleError(res, err.message, "Failed to check if user already exists.");
    } else if (!doc) {
      return handleError(res, "Invalid user ID", "User with that ID does not exist.", 400);
    }

    users.updateOne({ _id: new ObjectID(req.params.id) }, updateDoc, function(err, doc) {
      if (err) {
        return handleError(res, err.message, "Failed to update user");
      } else {
        res.status(204).end();
      }
    });
  });
});

app.delete("/usersById/:id", function(req, res) {
  users.findOne({ _id: new ObjectID(req.params.id) }, function(err, doc) {
    if (err) {
      return handleError(res, err.message, "Failed to check if user already exists.");
    } else if (!doc) {
      return handleError(res, "Invalid user ID", "User with that ID does not exist.", 400);
    }

    users.deleteOne({ _id: new ObjectID(req.params.id) }, function(err, result) {
      if (err) {
        return handleError(res, err.message, "Failed to delete user");
      } else {
        res.status(204).end();
      }
    });
  });
});

/*  "/usersByName/:username"
 *    GET: find user by id
 *    PUT: update user by id
 *    DELETE: deletes user by id
 */

app.get("/usersByName/:username", function(req, res) {
   users.findOne({ username: req.params.username }, function(err, doc) {
     if (err) {
       return handleError(res, err.message, "Failed to check if user already exists.");
     } else if (!doc) {
       return handleError(res, "Invalid username", "User with that username does not exist.", 400);
     }

     users.findOne({ username: req.params.username }, function(err, doc) {
       if (err) {
         return handleError(res, err.message, "Failed to get user.");
       } else {
         res.status(200).json(doc);
       }
     });
   });
 });

app.put("/usersByName/:username", function(req, res) {
   var updateDoc = req.body;
   delete updateDoc._id;

   users.findOne({ username: req.params.username }, function(err, doc) {
     if (err) {
       return handleError(res, err.message, "Failed to check if user already exists.");
     } else if (!doc) {
       return handleError(res, "Invalid username", "User with that username does not exist.", 400);
     }

     users.updateOne({ username: req.params.username }, updateDoc, function(err, doc) {
       if (err) {
         return handleError(res, err.message, "Failed to update user " + req.params.username);
       } else {
         res.status(204).end();
       }
     });
   });
 });

app.delete("/usersByName/:username", function(req, res) {
   users.findOne({ username: req.params.username }, function(err, doc) {
     if (err) {
       return handleError(res, err.message, "Failed to check if user already exists.");
     } else if (!doc) {
       return handleError(res, "Invalid username", "User with that username does not exist.", 400);
     }

     users.deleteOne({ username: req.params.username }, function(err, result) {
       if (err) {
         return handleError(res, err.message, "Failed to delete user " + req.params.username);
       } else {
         res.status(204).end();
       }
     });
   });
 });

 /*  "/pacts"
  *    GET: finds all pacts
  *    POST: creates a new user
  */

app.get("/pacts", function(req, res) {
   pacts.find({}).toArray(function(err, docs) {
     if (err) {
       return handleError(res, err.message, "Failed to get all pacts.");
     } else {
       res.status(200).json(docs);
     }
   });
 });

app.post("/pacts", function(req, res) {
   var newPact = req.body;
   newPact.createDate = new Date();

   if (!newPact.habit || !newPact.start || !newPact.end || !newPact.length || !newPact.stakes || !newPact.users.length >= 2) {
     return handleError(res, "Pact parameters missing", "Pact is missing one or more parameters.", 400);
   }

   pacts.insertOne(newPact, function(err, doc) {
     if (err) {
       return handleError(res, err.message, "Failed to create new pact.");
     } else {
       res.status(201).json(doc.ops[0]);
     }
   });
 });

 app.get("/pacts/:username", function(req, res) {
    pacts.find({ users: req.params.username }).toArray(function(err, docs) {
      if (err) {
        return handleError(res, err.message, "Failed to check for pact");
      } else if (!docs) {
        return handleError(res, "Not found", "Pact with specified user not found.", 400);
      }

      res.status(200).json(docs);
    });
  });

 /*  "/ping"
  *    GET: check if the API is up.
  */

app.get('/ping', function (req, res) {
  res.send("Ultimatum's server.");
});

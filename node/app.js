var express = require("express");
var bodyparser = require("body-parser");
var connection = require('./connection');
var routes = require('./routes');
var basicAuth = require('basic-auth');

var app = express();
app.use(bodyparser.urlencoded({extended : true}));
app.use(bodyparser.json());

connection.init();
routes.configure(app);

var auth = function (req, res, next) {
  function unauthorized(res) {
    res.set('WWW-Authenticate', 'Basic realm=Authorization Required');
    return res.send(401);
  };

  var user = basicAuth(req);

  if (!user || !user.name || !user.pass) {
    return unauthorized(res);
  };

  if (user.name === 'foo' && user.pass === 'bar') {
    return next();
  } else {
    return unauthorized(res);
  };
};

app.get("/",auth,function(req,res){
	res.send("Hello World");
});

var server = app.listen(80,function(){
	console.log('Server listening on port ' + server.address().port);
});
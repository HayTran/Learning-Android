var express = require("express");
var app = express();
var server = require("http").createServer(app);
var io = require("socket.io").listen(server);
var fs = require("fs");
server.listen(process.env.PORT || 3000);

var arrayUsername = [];
io.sockets.on("connection",function(socket){
  console.log("Has a new user connecting...");
  socket.on("client-gui-username",function (data){
    if (arrayUsername.indexOf(data) > -1){
      console.log("Username duplicated");
      socket.emit('server-gui-ve', data + " sign up failed");
    } else {
      arrayUsername.push(data);
      console.log("Sign up with user: "+ data);
      socket.emit("server-gui-ve", data + " sign up successfully");
    }
  });


});

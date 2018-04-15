var express = require("express");
var app = express();
var http = require("http").Server(app);
var io = require("socket.io")(http);
var port = process.env.PORT || 3001;
var fs = require('fs');
var shortid = require('shortid');

http.listen(port, function ()
{
    console.log("Listening on ", port);
});

console.log('Server started.');

var users = [];

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//-------------------------------------------------------START SOCKET----------------------------------------------------------//
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
io.sockets.on('connection', function(socket){

    var thisClientID = shortid.generate();
    var user = {
        id: thisClientID,
        socketid: socket.id,
    }

    users[thisClientID] = user;
    console.log('Client connected, id: ' + thisClientID + ' socketid: ' + users[thisClientID].socketid);

    socket.on('disconnect', function(){
        delete users[thisClientID];
        console.log('Client disconnected: ' + thisClientID);
    });
});
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//-------------------------------------------------------END SOCKET----------------------------------------------------------//
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

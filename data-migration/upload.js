'use strict';
// imports
const axios = require('axios');

// ------------------------------------------------
// constants
const FILE_PATH = '../../to-do-list-data-backup.json';
//const TO_DO_SERVER_URL = 'https://enigmatic-gorge-65663.herokuapp.com/api/to-do-lists';
const TO_DO_SERVER_URL = 'http://localhost:8080/api/to-do-lists';

// ------------------------------------------------
// process args
let send = false;
let check = false;

const processArg = (arg) => {
	switch(arg) {
	  case 'send':
	    send = true;
	    break;
	  case 'check':
	    check = true;
	    break;
	}
}

process.argv.forEach(processArg)

// ------------------------------------------------
// validate args
const printUsage = () => {
	console.log(`Script to upload data in ${FILE_PATH} to ${TO_DO_SERVER_URL}`);
	console.log('Please provide exactly one of the following args:');
	console.log('	send: Upload the data to the server');
	console.log('	check: Check the result');
}

if (send == check) {
	printUsage();
	process.exit();
}

const data = require(FILE_PATH);


// ------------------------------------------------
// do your thing!
const exitOnError = msg => {
	console.log("ERROR:");
	console.log(msg);
	console.log('aborting');
	process.exit();
}

/**
* Delay for a number of milliseconds
*/
//function sleep(delay) {
//    var start = new Date().getTime();
//    while (new Date().getTime() < start + delay);
//}

if (send) {
	// ------------------------------------------------
	// upload the data
	const config = {
	    headers: {
	        'Content-Length': 0,
	        'Content-Type': 'text/plain'
	    }
	};
	data.forEach(toDoList => {
		axios.post(TO_DO_SERVER_URL, toDoList.name, config)
			.then(response => {
				const createdToDoList = response.data;
				console.log(`created to do list ${createdToDoList.name}`);
				const toDoListId = createdToDoList.id;
				if (typeof toDoListId !== 'number')
					exitOnError('unexpected to do list id: ' + toDoListId);
				toDoList.toDos.forEach(toDo => {
					console.log(`	adding to do: ${toDo.name}`);
					toDo.id = null;
					axios.post(`${TO_DO_SERVER_URL}/${toDoListId}/to-dos`, toDo);
				});
			})
	})
}
if (check) {
	// ------------------------------------------------
	// check what data is on the server
	axios.get(TO_DO_SERVER_URL)
		.then(response => console.log(response.data))
}

function login() {
	$.post(
		"login", {
			command: "login",
			user: $('#user').val(),
			room: $('#room').val()
		},
		function(data) {
			console.log(data);
		});
};
function send() {
	$.post(
		"send", {
			command: "send",
			text: $('#text').val()
		},
		function(data) {
			console.log(data);
			$('#list').html(data);
		});
};
function refresh() {
	$.post(
		"refresh", {
			command: "refresh"
		},
		function(data) {
			console.log(data);
			$('#list').html(data);
		});
};
function loadDoc() {
	$.post(
		"load", {
			command: "load",
			name: "John",
			time: "2pm"
		},
		function(data) {
			console.log(data);
		});
};
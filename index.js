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

function popObject(name, abbrv, price, rec, recText, buzz, buzzText){
	$('#buzzBanner').css('background-color',buzz);
	$('#buzzBanner').text(buzzText);
	$('#stockName').text( abbrv );
	$('#compName').text( name );
	$('#price').text( "$" + price );
	$('#recBanner').css('background-color', rec);
	$('#recBanner').text(recText);
	$('#content').slideDown(400);
}

$( "#next" ).click(function() {
	var name, abbrv, price, rec, recText, buzz, buzzText;
	name = "Google";
	abbrv = "GOOG";
	price = "tree fiddy";
	rec = "#00FF00"
	recText = "DO IT"
	buzz = "#FF0000";
	buzzText = "VOLATILE AS SHIT"
	$('#content').slideUp(400, function() {
		popObject(name, abbrv, price, rec, recText, buzz, buzzText);
	});
});
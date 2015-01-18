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

function populate() {
	$.ajax({
		type: "POST",
		data: {
			command: "test"
		},
		url: "receive",
		success: function(response) {
			console.log(response);
			var json = $.parseJSON(response);
			popObject(json.name, json.abbrv, json.price, json.rec,
				json.recText, json.buzz, json.buzzText);
		}
	})
}

function popObject(name, abbrv, price, rec, recText, buzz, buzzText){
	$('#buzzBanner').css('background-color',buzz);
	$('#buzzBanner').text(buzzText);
	$('#stockName').text( abbrv );
	$('#compName').text( name );
	$('#price').text( "$" + price.toFixed(2) );
	$('#recBanner').css('background-color', rec);
	$('#recBanner').text(recText);
	$('#content').slideDown(400);
}

$(document).ready(function() {
	populate();
})

$( "#next" ).click(function() {
	$('#content').slideToggle(400,function(){
		populate()
	});
	// var name, abbrv, price, rec, recText, buzz, buzzText;

	// name = "Google";
	// abbrv = "GOOG";
	// price = "tree fiddy";
	// rec = "#00FF00"
	// recText = "DO IT"
	// buzz = "#FF0000";
	// buzzText = "VOLATILE AS SHIT"
	// $('#content').slideUp(400, function() {
	// 	popObject(name, abbrv, price, rec, recText, buzz, buzzText);
	// });
});
//소켓 통신 코드추가(아래)
const PORT = 3000; //기존 구름IDE 사용할 때 아래 PORT변수를 2개 사용한다면 PORT2로 변경
var http = require('http').createServer(app);//http서버 객체생성 시 app(express) 프레임웍을 전달
var io = require('socket.io').listen(http);//socket.io 소켓통신 객체생성 시 http서버 객체를 전달.
var msg = {//전역변수로 설정. 이유는,신규사용자가 페이지로딩시 현재상태를 가져갈수 있도록 처리
			msg: '' //초기에는 실행상태를 갖지 않게 한다.
		};
//.listen은 클라이언트에서 서버로 접속을 받기위해 대기하는 명령.
http.listen(PORT, function(){//노드js서버 실행여부 확인, 정상실행되었다면, 익명콜백함수 실행.
    console.log('Socket IO server listening on port : ' + PORT);//서버콘솔에 listening on...이 출력.
});

//.on은 클라이언트에서 서버로 소켓통신(명령또는 데이터를 주고받기)위해 대기하는 명령
io.on('connection', function(socket){//클라이언트에서 connect 소켓연결이 발생하면 callback(socket)함수를 실행.
	console.log(socket.id +' user connected');//서버콘솔에 a user connected를 출력.
	// 접속된 모든 클라이언트에게 메시지를 전송한다
    io.emit('OnOff', msg);//아무버튼액션이 없을때인 초기접속시 전역변수에 저장된 현재 상태값을 클라이언트에 전송한다.
	socket.on('disconnect', function(){//클라이언트에서 disconnect 소켓연결끊김이 발생하면, 익명콜백함수 실행.
		console.log(socket.id + ' user disconnect');//서버콘솔에 user disconnect 출력.
	});
	
	//클라이언트에서 메세지가 수신되면
	socket.on('OnOff', function(data){
		console.log('누르신 버튼은 %s', data.msg);
		msg = {
			msg: data.msg //전송할 데이터가 1개 이기 때문에 꼭 이럴 필요는 없다. 그래도 Json튜플형식으로 보내는 방식을 설명하기 위해...
		};
		// 메시지를 전송한 클라이언트를 제외한 모든 클라이언트에게 메시지를 전송한다
    	//socket.broadcast.emit('OnOff', msg);
		// 접속된 모든 클라이언트에게 메시지를 전송한다
    	io.emit('OnOff', msg);//여기에서 범위를 유추해 볼 수 있다. io > socket
	});
});
	var wsUri ="ws://192.168.1.136:7001";
    //var output; 
     
    function WebSocketinit() {
        //output = document.getElementById("output");
        testWebSocket();
    } 
  
    function WebSocketTest()
    {
       if ("WebSocket" in window)
       {
          alert("您的浏览器支持 WebSocket!");
          
          // 打开一个 web socket
          var ws = new WebSocket("ws://localhost:9998/echo");
           
          ws.onopen = function()
          {
             // Web Socket 已连接上，使用 send() 方法发送数据
             ws.send("发送数据");
             alert("数据发送中...");
          };
           
          ws.onmessage = function (evt) 
          { 
             var received_msg = evt.data;
             alert("数据已接收...");
          };
           
          ws.onclose = function()
          { 
             // 关闭 websocket
             alert("连接已关闭..."); 
          };
       }
       
       else
       {
          // 浏览器不支持 WebSocket
          alert("您的浏览器不支持 WebSocket!");
       }
    }
    
    function testWebSocket() {
        websocket = new WebSocket(wsUri);
        websocket.onopen = function(evt) {
            onOpen(evt)
        };
        websocket.onclose = function(evt) {
            onClose(evt)
        };
        websocket.onmessage = function(evt) {
            onMessage(evt)
        };
        websocket.onerror = function(evt) {
            onError(evt)
        };
    } 
  
    function onOpen(evt) {
        writeToScreen("CONNECTED");
        doSend("WebSocket rocks");
    } 
  
    function onClose(evt) {
        writeToScreen("DISCONNECTED");
    } 
  
    function onMessage(evt) {
        writeToScreen('<span style="color: blue;">RESPONSE: '+ evt.data+'</span>');
        websocket.close();
    } 
  
    function onError(evt) {
        writeToScreen('<span style="color: red;">ERROR:</span> '+ evt.data);
    } 
  
    function doSend(message) {
        writeToScreen("SENT: " + message); 
        websocket.send(message);
    } 
  
    function writeToScreen(message) {
    	console.log(message)
        //var pre = document.createElement("p");
        //pre.style.wordWrap = "break-word";
        //pre.innerHTML = message;
        //output.appendChild(pre);
    } 
  
    window.addEventListener("load", WebSocketinit, false); 
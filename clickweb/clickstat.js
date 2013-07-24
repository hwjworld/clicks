
//gtype=2,采用第二种方式,统一请求ids,请求完替换


//请将localhost:8080替换成服务器ip地址及端口
var baseurl="http://localhost:8080/cs/cs?yjvg=" + getRandomNum();

var procArray = new Array();//存放更新点击数的script 对象
var id1s = new Array();//存放ids页面加载完统一提交

function clickop(id1, pageurl, op,cs) {
	url = baseurl + "&id=" + id1 + "&pageurl=" + pageurl + "&op=" + op + "&cs="+cs +"&r="+Math.random();
	var js_obj = sendReq(url);
	procArray[procArray.length]=js_obj;
	//document.head.appendChild(js_obj);
}

function sendReq(url){
	var js_obj = document.createElement("script");
	js_obj.type = "text/javascript";
	js_obj.setAttribute("src",url);
	return js_obj;
}

function getRandomNum(){
	var f = Math.floor(Math.random()*10);
	var s = 17-f;
	return f+''+s;
}

function getClickedNum(id){
	id1s[id1s.length]=id;
}

function addD(id){
	addOne(id,'n','100');
}
function addC(id){
	addOne(id,'n','010');
}
function addX(id){
	addOne(id,'n','001');	
}
/*
 * 稿件id,链接,顶,踩,星
 */
function addOne(id,url,cs){
	clickop(id,url,"a",cs);
	changeAdd();
}

function changeAdd(){
	for(i=0;i<procArray.length;i++){
		document.body.appendChild(procArray[i]);
	}
	procArray = new Array();
}
function changeNum(id,num){
//	alert(id+'--'+num);
	var dg = document.getElementById(id);
	if(dg)
		dg.innerHTML=num;// + " ";
}

function changeAll(){
	document.body.appendChild(sendReq(baseurl+buildParams()));
	procArray = new Array();
	id1s = new Array();
}

function buildParams(){
	var str = "&id=";
	for(i in id1s){
		str=str+id1s[i]+";";
	}
	return str+="&op=l";
}
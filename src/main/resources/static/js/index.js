$(function(){
	$("#publishBtn").click(publish);
});

function publish() {
	$("#publishModal").modal("hide");

	//获取标题类容
	var title =$("#recipient-name").val();
	var content =$("#message-text").val();
	//发布异步请求（post）
	$.post(
		CONTEXT_PATH+"/discuss/add",
		{"title":title,"content":content},
		function (data) {
			//将字符串转化为json对象
			data=$.parseJSON(data);
			//在提示框中显示返回消息
			$("hintBody").text(data.msg);
			$("#hintModal").modal("show");
			setTimeout(function(){
				$("#hintModal").modal("hide");
				//刷新页面
				if(data.code==0){
					window.location.reload();
				}
			}, 2000);
		}
	);


}
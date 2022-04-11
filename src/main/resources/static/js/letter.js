 $(function(){
	$("#sendBtn").click(send_letter);
	$(".close").click(delete_msg);
});

function send_letter() {
	$("#sendModal").modal("hide");
	//向服务器提交的数据
	var toName=$("#recipient-name").val();
	var content=$("#message-text").val();
	$.post(
		//访问的路径
		CONTEXT_PATH+"/letter/send",
		{"toName":toName,"content":content},
		function (data) {
			date=$.parseJSON(data);
			if(data.code==0){
				$("#hintBody").text("发送成功");
			}
			else{
				$("#hintBody").text(data.msg);
			}
			$("#hintModal").modal("show");
			setTimeout(function(){
				$("#hintModal").modal("hide");
				//重载当前页面
				location.reload();
			}, 2000);
		}
	);

}

function delete_msg() {
	// TODO 删除数据
	$(this).parents(".media").remove();
}
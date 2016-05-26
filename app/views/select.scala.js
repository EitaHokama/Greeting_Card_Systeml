$(function(){

    //確認用。大筋には関係ない。
	$("#button").click(function(){
        alert("カテゴリの個数は " + feedArray.length);
    });

    //カテゴリ = select1 の生成
    for (var i in feedArray){
    	$("#select1").append('<option value="' + feedArray[i].cate + '">' + feedArray[i].cate +'</option>');
		$("#select1").removeAttr("disabled");//触れるように
    }

    //カテゴリが変更されたら要素を入れ替え
    $("#select1").change(function(){
        $("#select2").empty();
        for (var i in feedArray){
            if(feedArray[i].cate == $(this).val()){
			    for (var j in feedArray[i].elem){
 			   		$("#select2").append('<option value="' + feedArray[i].elem[j].url + '">' + feedArray[i].elem[j].name +'</option>');
    			}
            }
    	}
        if($("#select2").val()){
	       printInfo();
        } else {
           $("#info").html("not selected");
        }
        if($(this).val()){
			$("#select2").removeAttr("disabled");//触れるように
        } else {
 			$("#select2").append('<option value="">まずは←を選択</option>');
			$("#select2").attr("disabled", "disabled");//触れなくする
        }
    });

    //2つめが選択（変更）されたら結果を表示
    $("#select2").change(function(){
        if($("#select2").val()){
            printInfo();
        } else {
           $("#info").html("not selected");
        }
    });

    //#infoに選択したものを表示
    function printInfo(){
	       $("#info").html($("#select2").val() + "<br />"
				+ $("#select1 option:selected").text()
				+ " - " + $("#select2 option:selected").text() );
    }

});

//配列の定義

    var feedArray = [
    {   "cate" : "DIY・工具",
	    "elem" : [
	        {"name":"すべて", "url" : "all"},
			{"name":"水周り・水栓・配管" , "url" : "mizumawari"},
			{"name":"建築・住宅資材" , "url" :"kentiku"},
			{"name":"金物" , "url" :"kanamono"}
		]
    },
    {   "cate" : "DVD",
    	"elem" : [
			{"name":"すべて" , "url" :"all"},
			{"name":"外国映画" , "url" :"gaikoku"},
			{"name":"日本映画" , "url" :"nihon"},
			{"name":"アニメ" , "url" :"anime"}
    	]
	}
    ];

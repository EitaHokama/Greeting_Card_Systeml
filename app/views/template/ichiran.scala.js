@import controllers.SelectGC
var bunruiA = new Array();

//部署(分類A) 社員(分類B)のリストを定義
var vunruiB = new Array();
@for(dep <- Department.find.all()){
	bunruiA.push("@dep.department_name");
	bunruiB["@dep.department_name"]= new Array;
		@for(emp <- Employees.find.where().eq("department_id", dep).findList()){
			bunruiB["@dep.department_name"].push("@emp.name");
		}
}

//分類Aの選択リストを作成
var senderDep= @SelectGC.name.get("sender_department") ;
createSelection( selectForm.elements['sender_department'], senderDep , bunruiA, bunruiA);

function addSelOption( selObj, myValue, myText )
	{
		selObj.length++;
		selObj.options[ selObj.length - 1].value = myValue ;
		selObj.options[ selObj.length - 1].text  = myText;

}
//選択リストを作る関数
//引数: ( selectオブジェクト, 見出し, value値配列 , text値配列 )
function createSelection( selObj, midashi, aryValue, aryText )
	{
		selObj.length = 0;
		addSelOption( selObj, midashi, midashi);
		// 初期化
		for( var i=0; i < aryValue.length; i++)
		{
			addSelOption ( selObj , aryValue[i], aryText[i]);
		}
	}

//	分類Aが選択されたときに呼び出される関数

function selectBunruiA(obj)
	{
		// 選択肢を動的に生成
		createSelection(selectForm.elements['sel_bunruiB'], "社員",
				bunruiB[obj.value], bunruiB[obj.value]);

}

	//submit前の処理
	/*function gettext(form){
		var a = selectForm.sel_bunruiA.value;   // 分類1
		var b = selectForm.sel_bunruiB.value;   // 分類2
		// ANDでつなげる
		selectForm.elements['search'].value = a+' AND '+b;
		alert(form1.elements['search'].value );
	}*/
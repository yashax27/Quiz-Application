
<style>

.btn
{
			padding:15px 30px;
			background-color: yellow;
			color:red;
			
}

#next
{
	margin-left:200px;
}
	
	input[type="radio"]
	{
		margin-left:300px;
	}
	
	textarea
	{
		border:none;
		font-size: 30px;
		color:white;
		background-color:black;
		padding-top:50px;
		box-sizing:border-box; // it will ensure that width of the html element will not increase due to addition of padding
		
	}
	
	body
		{
			background-color: grey;
			//background-image: url('images/1.jpg');
			//background-repeat:no-repeat;
			
		}
		
	#errorMessage
	{
		color:red;
		font-size: 30px;
		font-style: italic;
	}
	
	span
	{
		color:black;
		font-size: 20px;
		font-style: italic;
		
	}
	
	
</style>

<script>

var xmlhttp;

//setInterval(getRemainingTime,1000);// 1000 ms - 1 sec  60000ms - 60sec

	function getRemainingTime()
	{
		xmlhttp= new XMLHttpRequest();

		xmlhttp.onload=showtime;
		
		xmlhttp.open("get","showRemainingTime",true);
		
		xmlhttp.send();
		
	}

	function showtime()
	{
			// 05:00 
	var min1=Math.floor(xmlhttp.responseText / 60).toString().padStart(2,'0');
	var sec1= (xmlhttp.responseText-min1*60).toString().padStart(2,'0');

	document.questionForm.show.value=min1 + ":"+sec1;
			
			
			if(xmlhttp.responseText==0)
			{
				alert("Time Up !!")
				//xmlhttp.open("get","endexam",true);
				window.location.href="score";
			}
		
	}

	
	setInterval(getRemainingTime,1000);// 1000 ms - 1 sec  60000ms - 60sec

	function saveAnswer()
	{
		var qno = document.questionForm.qno.value;
		var question = document.questionForm.question.value;
		var submittedAnswer = document.questionForm.option.value;
		
		//alert(qno + " " + question + submittedAnswer);
       //Emp  emp = new Emp()  
		var xmlhttp = new XMLHttpRequest();

		var data = "qno="+qno+"&question="+question+"&submittedAnswer="+submittedAnswer;

		xmlhttp.open("get","storeResponse?" + data,true);
		
		xmlhttp.send();
	}

	function changeColor()
	{
		//alert("page loaded");                              //0       1     2     3
		var allAnswers=document.getElementsByTagName("span");//[span][span][span][span]
		//alert(allAnswers.length);
		for(var i=0;i<allAnswers.length;i++)
		{
			var oldanswer=document.questionForm.previousAnswer.value;
			//alert(allAnswers[i].innerText + " " + oldanswer);
			if(allAnswers[i].innerText==oldanswer)
			{
				//alert("matched");
				allAnswers[i].style.color='orange';
				break;
			}
			
		}
	}
	
</script>

<body onload="changeColor()">

<!-- document.questionForm.show.value=min1 + ":"+sec1; -->
<form name="questionForm">
	<label > Remaining time </label>
	<input  style="border:none" type="text" name="show" value=""><br><br>

	<input  style="border:none" type="text" name="qno" value="${question.qno}"><br><br>
	
	<textarea  rows=3 cols=50 name="question"> ${question.question} </textarea><br><br>
		
	<input  type="radio" name="option" value="${question.option1}" onclick="saveAnswer()"> <span> ${question.option1} </span><br><br>
	
	<input  type="radio" name="option" value="${question.option2}" onclick="saveAnswer()">  <span> ${question.option2} </span> <br><br>
		
	<input  type="radio" name="option" value="${question.option3}" onclick="saveAnswer()"> <span> ${question.option3} </span> <br><br>
	
	<input  type="radio" name="option" value="${question.option4}" onclick="saveAnswer()"> <span> ${question.option4} </span> <br><br>
		
	<input  style="border:none;display:none" type="text" name="previousAnswer" value="${previousAnswer}" ><br><br>
		
		<span id="errorMessage"> ${message} </span><br>
		
	<input id="next" class="btn" type="submit" value="next" formaction="next">
	<input class="btn" type="submit" value="previous" formaction="previous">
	<input class="btn" type="submit" value="end exam" formaction="endexam">

</form>

 

</body>
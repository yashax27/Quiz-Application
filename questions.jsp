



<head>

<style>
		select
		{
		   padding : 30px;
		   	margin-top: 50px;
		   	background-color: black;
		   	color:white;
		   	margin-right:50px;
		}
		
		span
		{
			color:black;
			
			
		}
		
		body
		{
			background-color: grey;
			//background-image: url('images/1.jpg');
			//background-repeat:no-repeat;
		}
		
		input
		{
			padding:15px 30px;
			background-color: yellow;
			color:red;
		}
		
		div
		{
			text-align:center;
			
		}
</style>

	<script>
		//<select name="selectedSubject" onchange="display(this)">
		function display(x)
			{
				if(x.value!='actionNoRequired')
				{
				//Student student = new Student();
					var xmlhttp = new XMLHttpRequest();

					xmlhttp.open("get","getQuestions?subject=" + x.value,true);
				
					xmlhttp.send();
				}
			}
	</script>
</head>



<body>

	<span> ${message} </span>
	
	<form>
		<select name="selectedSubject" onchange="display(this)">
			<option value="actionNoRequired"> Select Subject  </option>
			<option value="gk"> GeneralKnowldge  </option>
			<option value="maths"> maths </option>
		</select>
		
		<input type=submit value="startExam" formaction="startExam">
	</form>
	
	${msg}
	
	
	<div>
		<img id="i1" height=300px width=400px src="images/1.jpg">
	</div>
</body>
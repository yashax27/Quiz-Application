<head>

<style>
#errormessage
{
	color:orange;
	font-size: 20px;
	
}
body
		{
			background-color: black;
			//background-image: url('images/1.jpg');
			//background-repeat:no-repeat;
			margin-left:400px;
			margin-top:100px;
		}
		
input
{
	border:2px solid black;
	border-radius:10px;
	padding:20px;
}

#b1
{
			padding:15px 30px;
			background-color: yellow;
			color:blue;
			
			
}

a:link
{
	border:2px solid black;
	border-radius:10px;
   background-color: yellow;
  color: blue;
  padding: 14px 25px;
  
  text-decoration: none;
  
 
}

a:hover {
  background-color: red;
}

div
{
	border:5px solid blue;
	padding:50px;
	width:50%;
}
</style>


</head>

${message}

<body>
<form>
 <div>
	<input size=30 type="text" name="username" placeholder="Enter username" required><br><br>
	
	<input size=30 type="password" name="password"  placeholder="Enter password"><br><br>
	
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="submit" value="login" formaction="login" id="b1">
	
	<a href="showRegisterPage"> sign up </a>
 </div> 
</form>



<span id="errormessage">${errorMessage}</span>

<br><br>



</body>

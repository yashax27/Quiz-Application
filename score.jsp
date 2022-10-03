<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<head>

<script>
             //  0          1        2       3
var images = ["Image1","Image2","Image3","Image4"];

var currentIndex = -1;
 

function changeImage() {

	var img = document.getElementById("i1");
	
    // change currentIndex in a loop

    currentIndex++;

    if (currentIndex == images.length)
        currentIndex = 0;
     
    //alert(images[currentIndex]);
    img.src = "images/"+images[currentIndex] + ".jpg";
}

setInterval(changeImage,3000);

</script>


<style>

table,th,td
{
	border : 1px solid green;
	//border-collapse:collapse;
	
}

td
{
	color:white;
	
}

th
{
	color:yellow;
}
th,td
{
	padding:10px;
	text-align:center;
}

table
{
	margin-left : 300px;
}

img
{
	
	border-radius:50%;
}

div
{
	text-align:center;
}

body
{
	background-color:black;
}

</style>

</head>

<h1 style="color:white"> Your Score is ${score} </h1>



<table>
	<tr>
		<th>Question Number</th>
		<th>question</th>
		<th>submittedanswer</th>
		<th>correctanswer</th>
	</tr>
	
	<c:forEach var="answer" items="${submittedDetails.values()}">
			<tr>
				<td>${answer.qno}</td>
				<td>${answer.question}</td>
				<td>${answer.submittedAnswer}</td>
				<td>${answer.correctAnswer}</td>
			</tr>
	</c:forEach>
</table>


<h1>Want to attempt exam again?</h1>

<a href="/" style="text-decoration:none">Go to Login</a> <!-- href attribute needs url  -->

<br><br>
<div>
	<img id="i1" height=300px width=400px src="images/Image4.jpg">
</div>
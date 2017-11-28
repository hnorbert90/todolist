
$(document).ready(function() {
	if(window.location.pathname=="/todo/archived"){
		$(".archive-archive").css("display","block");
		$(".archive-active").css("display","none");
		console.log("archive");
		
	}else{
		$(".archive-active").css("display","block");
		$(".archive-archive").css("display","none");
		console.log("active");
	}
	$(".null").html('<td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td>');
	
$("input.deadline").attr("min",new Date().toISOString().slice(0,10));
$("input.deadline").attr("value",new Date().toISOString().slice(0,10));
    $(".modify").click(function(e) {
        $(".update").css("top",e.pageY-10);
        
        $(".update").css("left",e.pageX-620);
    });

});
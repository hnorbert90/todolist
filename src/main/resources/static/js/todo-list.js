function perc2color(perc) {
	var r, g, b = 0;
	if(perc < 50) {
		r = 255;
		g = Math.round(5.1 * perc);
	}
	else {
		g = 255;
		r = Math.round(510 - 5.10 * perc);
	}
	var h = r * 0x10000 + g * 0x100 + b * 0x1;
	return '#' + ('000000' + h.toString(16)).slice(-6);
}

$(document).ready(function() {
	var priority = document.getElementsByClassName("priority");
	for(var i = 0; i < priority.length; i++)
	{
		priority.item(i).style.background=perc2color(100-priority.item(i).getAttribute("value")*10);
		console.log($(".priority")[i]);//.css("background-color",perc2color(priority.item(i).attr("value")));
	}
	
	
            if (window.location.pathname == "/todo/archived") {
                $(".archive-archive").css("display", "block");
                $(".archive-active").css("display", "none");
                console.log("archive");

            } else {
                $(".archive-active").css("display", "block");
                $(".archive-archive").css("display", "none");
                console.log("active");
            }
            $(".null").html('<td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td>');

            $("input.deadline").attr("min", new Date().toISOString().slice(0, 10));
            $("input.deadline").attr("value", new Date().toISOString().slice(0, 10));

            $(".modify").click(function(e) {
                $(".update").css("top", e.pageY - 10);
                $(".update").css("left", e.pageX - 620);
            });

            $(".description").on("mouseenter", function(e) {
            	$("#details"+$(this).parent().attr("class")).addClass( "details-active" );
            	console.log();
                $(".details-active").css("top", e.pageY);
                $(".details-active").css("left", e.pageX+10);
            });

            $(".description").on("mouseleave", function() {
            	$("#details"+$(this).parent().attr("class")).removeClass( "details-active" );
            });
});
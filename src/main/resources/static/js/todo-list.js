String.prototype.contains = function(it) { 
	return this.indexOf(it) != -1; 
	};
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
		console.log($(".priority")[i]);
	}
	
	
            if (window.location.pathname.contains("/todo/archived")) {
                $(".archive-archive").css("display", "table-cell");
                $(".archive-active").css("display", "none");
                $(".noArchiveFunction").css("display", "none");
                $("#newTodo").css("display", "none");
                $(".null").html('<td class="description"></td><td class="priority"></td><td class="created"></td><td class="deadline"><td></td>');
                $(".decreasepriority").css("display", "none");
                $(".increasepriority").css("display", "none");
                $("#archivedbutton").css("background-color", "#066b0b");
                console.log("archive");

            } else {
                $(".archive-active").css("display", "table-cell");
                $(".archive-archive").css("display", "none");
                $(".noArchiveFunction").css("display", " table-cell");
                $(".decreasepriority").css("display", "inline-block");
                $(".increasepriority").css("display", "inline-block");
                $("#activedbutton").css("background-color", "#066b0b");
                console.log("active");
                $(".null").html('<td class="description"></td><td class="priority"></td><td class="created"></td><td class="deadline"></td><td></td><td></td><td></td><td></td>');
            }
            
            

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
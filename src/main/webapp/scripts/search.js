$(function(){
	console.log("main script loaded");
	var availableArtists = new Array();
	var availableTags = new Array();
	
	
	$("#artists").find("li").each(function(){
		availableArtists.push($(this).text());
	});
	$("#tags").find("li").each(function(){
		availableTags.push($(this).text());
	});
	
	$("#searchArtist").autocomplete({
		source: availableArtists,
		 select: function( event, ui ) {
			 $( "#searchArtist").val(ui.item.label);
		 }
	});
	
	$("#searchTag").autocomplete({
		source: availableTags
	});
	
	$("#addArtistButton").click(function(){
		addSearchItem($("#searchArtist").val());
		$("#selectedArtists").append($("<li>").text($("#searchArtist").val()));
		$("#searchArtist").val("");
		
	});
	
	$("#searchArtist").keypress(function(e){
		if(e.which==13 && $( "#searchArtist").val() != ""){
			addSearchItem($("#searchArtist").val());
			$("#selectedArtists").append($("<li>").text($("#searchArtist").val()));
			$("#searchArtist").val("");
		}
	});
	
	$("#addTagButton").click(function(){
		addSearchItem($("#searchTag").val());
		$("#selectedTags").append($("<li>").text($("#searchTag").val()));
		$("#searchTag").val("");
	});
	
	$("#searchTag").keypress(function(e){
		if(e.which==13 && $(this).val() != ""){
			addSearchItem($(this).val());
			$("#selectedTags").append($("<li>").text($(this).val()));
			$(this).val("");
		}
	});
	
	
	$("#search").click(function(){
		var similarArtists = new Array();
		var hasTags = new Array();
		$("#selectedArtists").find("li").each(function(){
			similarArtists.push($(this).text());
		});
		$("#selectedTags").find("li").each(function(){
			hasTags.push($(this).text());
		});
		console.log("Artists: " + similarArtists);
		console.log("Tags: " + hasTags);
	});
	
});

function addSearchItem(item){
	var text = $("#searchStatus").text();
	console.log(item);
	console.log("%"+text+"%");
	if(text == ""){
		$("#searchStatus").text(item);
	}else{
		$("#searchStatus").text(text + ", " + item);
	}
}
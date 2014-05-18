<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<link href="<c:url value="css/jquery/jquery-ui-1.10.4.custom.css"/>"
	rel="stylesheet" />
<script src="<c:url value="/scripts/jquery/jquery-1.10.1.js" />">
<!-- required for FF3 and Opera -->
	
</script>
<script src="<c:url value="/scripts/jquery/jquery-ui.js" />">
<!-- required for FF3 and Opera -->
	
</script>
<script src="<c:url value="/scripts/search.js" />">
<!-- required for FF3 and Opera -->
	
</script>

</head>
<body>
	<h1>${message}</h1>
	<h2>Query: <span id="searchStatus"></span></h2>
	<ul style="display: none" id="artists">
		<c:forEach var="artist" items="${artists}">
			<li>${artist}</li>
		</c:forEach>
	</ul>
	<ul style="display: none" id="tags">
		<c:forEach var="tag" items="${tags}">
			<li>${tag}</li>
		</c:forEach>
	</ul>
	<ul style="display: none" id="selectedArtists"></ul>
	<ul style="display: none" id="selectedTags"></ul>
	
	

	<table>
		<tbody>
			<tr>
				<td>Artist: </td>
				<td><input type="text" id="searchArtist"></input></td>
				<td><button id = "addArtistButton" style="display: none;">Add</button></td>
			</tr>
			<tr>
				<td>Tags: </td>
				<td><input type="text" id="searchTag"></input></td>
				<td><button id = "addTagButton" style="display: none;">Add</button></td>
			</tr>
		</tbody>
	</table>
	<button id="search">Search</button>
	

</body>
</html>
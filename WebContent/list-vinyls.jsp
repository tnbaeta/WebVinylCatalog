<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>

<head>
	<title>Vinyl Tracker App</title>
	
	<link type="text/css" rel="stylesheet" href="css/style.css">
</head>

<body>

	<div id="wrapper">
		<div id="header">
			<h2>Baeta's Vinyl Catalog</h2>
		</div>
	</div>

	<div id="container">
	
		<div id="content">
		
			<!-- put new button: Add Vinyl -->
			
			<input type="button" value="Add Vinyl" 
				   onclick="window.location.href='add-vinyl-form.jsp'; return false;"
				   class="add-vinyl-button"
			/>
			
			<table>
			
				<tr>
					<th>First Name</th>
					<th>Last Name</th>
					<th>Action</th>
				</tr>
				
				<c:forEach var="tempVinyl" items="${VINYL_LIST}">
					
					<!-- set up a link for each vinyl -->
					<c:url var="tempLink" value="VinylControllerServlet">
						<c:param name="command" value="LOAD" />
						<c:param name="vinylId" value="${tempVinyl.id}" />
					</c:url>

					<!--  set up a link to delete a vinyl -->
					<c:url var="deleteLink" value="VinylControllerServlet">
						<c:param name="command" value="DELETE" />
						<c:param name="vinylId" value="${tempVinyl.id}" />
					</c:url>
																		
					<tr>
						<td> ${tempVinyl.album} </td>
						<td> ${tempVinyl.artist} </td>
						<td> 
							<a href="${tempLink}">Update</a> 
							 | 
							<a href="${deleteLink}"
							onclick="if (!(confirm('Are you sure you want to delete this vinyl?'))) return false">
							Delete</a>	
						</td>
					</tr>
				
				</c:forEach>
				
			</table>
		
		</div>
	
	</div>
</body>


</html>








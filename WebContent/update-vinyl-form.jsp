<!DOCTYPE html>
<html>

<head>
	<title>Update Vinyl</title>

	<link type="text/css" rel="stylesheet" href="css/style.css">
	<link type="text/css" rel="stylesheet" href="css/add-vinyl-style.css">	
</head>

<body>
	<div id="wrapper">
		<div id="header">
			<h2>Baeta's Vinyl Catalog</h2>
		</div>
	</div>
	
	<div id="container">
		<h3>Update Vinyl</h3>
		
		<form action="VinylControllerServlet" method="GET">
		
			<input type="hidden" name="command" value="UPDATE" />

			<input type="hidden" name="vinylId" value="${THE_VINYL.id}" />
			
			<table>
				<tbody>
					<tr>
						<td><label>Album:</label></td>
						<td><input type="text" name="album" 
								   value="${THE_VINYL.album}" /></td>
					</tr>

					<tr>
						<td><label>Artist:</label></td>
						<td><input type="text" name="artist" 
								   value="${THE_VINYL.artist}" /></td>
					</tr>
					
					<tr>
						<td><label></label></td>
						<td><input type="submit" value="Save" class="save" /></td>
					</tr>
					
				</tbody>
			</table>
		</form>
		
		<div style="clear: both;"></div>
		
		<p>
			<a href="VinylControllerServlet">Back to List</a>
		</p>
	</div>
</body>

</html>
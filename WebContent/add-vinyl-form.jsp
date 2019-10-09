<!DOCTYPE html>
<html>

<head>
	<title>Add Vinyl</title>

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
		<h3>Add Vinyl</h3>
		
		<form action="VinylControllerServlet" method="GET">
		
			<input type="hidden" name="command" value="ADD" />
			
			<table>
				<tbody>
					<tr>
						<td><label>Album:</label></td>
						<td><input type="text" name="album" /></td>
					</tr>

					<tr>
						<td><label>Artist:</label></td>
						<td><input type="text" name="artist" /></td>
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
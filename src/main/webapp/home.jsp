<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Homepage</title>
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
	<!-- 	Meta tag required to make web page responsive -->
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<link rel="stylesheet" href="css/home.css">
</head>
<body>

	<div class="container-fluid pageHeader">
		<div class = "row pt-3 pb-3">
			<div class = "col-7">
				<h2 class="textColor textShadow">USC CS 310 Stock Portfolio Management</h2>
			</div>
			<div class = "col-5 text-right">
				<h2 class="textColor textShadow" id="logoutLink"><a id="logoutLink" class="formatLink" href="index.jsp" onclick="return doLogout();">Logout</a></h2>
			</div>
		</div>
	</div>

	<!-- current portfolio value -->

	<div class="container-fluid mt-5">
		<div class="row justify-content-center text-center">
			<div class="col-9 borderStyle">
				<h3 class="textColor pt-1 pb-1" id="portfolioVal">Current Portfolio Value: $<span id="currentValue">0.00</span></h3>
			</div>
		</div>
	</div>

	<!-- value change of portfolio -->

	<div class="container-fluid mt-5">
		<div class="row justify-content-center text-center">
			<div class="col-9 borderStyle">
				<h3 class="pt-1 pb-1" id="valueChange">Value Change: <span id="dirArrow"></span><span class="ml-1" id="percentChange">5%</span></h3>
			</div>
		</div>
	</div>



	<!-- graph or portfolio progress -->

	<div class="container-fluid mt-5">
		<div class="row justify-content-center text-center">
			<div class="col-9 borderStyle">
				<h3 class="textColor pt-1 pb-1">Portfolio Progress:</h3>


	      		<div class="text-danger errorMessage" id="graphDateError"></div>

				<!-- form here with a calendar input -->
				<!-- need to add an event listener on the fields in this form -->
				<form method="POST" action="">

					<div class="text-danger pt-2 errorMessage" id="graphError"></div>

				  <div class="form-group">
				    <label for="graphStartDate">Start Date of Graph (must be within a year):</label>
				    <input class="pl-1" type="date" id="graphStartDate" name="graphStartDate">
				  </div>

				   <div class="form-group">
				    <label for="graphEndDate">End Date of Graph:</label>
				    <input class="pl-1" type="date" id="graphEndDate" name="graphEndDate">
				  </div>


				  <div class="form-group">
				  	<div class="row justify-content-center">
				  		
					  	<div class="col-5">
						  <label for="xAxis">Time Increments</label>
						  <select class="form-control" id="xAxis" name="xAxis">
						    <option value="0">Daily</option>
						    <option value="1">Weekly</option>
						    <option value="2">Monthly</option>
						  </select>
						 </div>
					</div>
				</div>

				</form>

				<!-- div for checkbox for S&P -->
				 <div class="form-check mb-2">
				 	<label class="form-check-label" for="sAndPCheck">Graph S&P: </label>
    				<input type="checkbox" class="form-check-input ml-2" id="sAndPCheck">
  				</div>

  				<!--  div for plus/minus icon to zoom in/out -->
  				<form class="form-inline mb-2 justify-content-center">
				  <button type="submit" class="btn btn-light" id="zoomIn"><i class="fa fa-plus"></i></button>
				  <button type="submit" class="btn btn-light" id="zoomOut"><i class="fa fa-minus"></i></button>
				</form>

 
				<!-- place to put graph  -->
				<div class="row justify-content-center">
					<div class="col-8">
						<div id="chart_div"></div>
					</div>
				</div>
				
			</div>
		</div>
	</div>


	<!-- Modal #1 for uploading CSV  -->
	<div class="modal fade" id="portfolioModal" tabindex="-1" role="dialog" aria-labelledby="portfolioModalLabel" aria-hidden="true">
	  <div class="modal-dialog" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title" id="exampleModalLabel">Upload a CSV</h5>
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
	          <span aria-hidden="true">&times;</span>
	        </button>
	      </div>
	      <div class="modal-body">

	      	<div class="text-danger errorMessage" id="csvError"></div>

	        <!-- CSV form -->
	        <form method="POST" id="csvForm" action="home.jsp" onsubmit="return validateCSV();">
	        	<div class="form-group">
	        		
		        	<input type="file" accept=".csv" name="csvFile" id="csvFile">

	        	</div>
	        	<button type="submit" class="btn btn-primary" id="portfolioButton">Upload File</button>
	        </form>
	     	
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-danger" data-dismiss="modal" id="cancelCSV">Cancel</button>
	      </div>
	    </div>
	  </div>
	</div>


	<!-- Modal #2 for adding stocks  -->
	<div class="modal fade" id="addModal" tabindex="-1" role="dialog" aria-labelledby="addModalLabel" aria-hidden="true">
	  <div class="modal-dialog" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title" id="exampleModalLabel">Add a Stock</h5>
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
	          <span aria-hidden="true">&times;</span>
	        </button>
	      </div>
	      <div class="modal-body">

	      	<div class="text-danger errorMessage" id="addStockError"></div>

	      	<div class="text-success successMessage" id="addStockSuccess"></div>

	        <!-- stock add form -->
	        <form method="POST" id="addStockForm" action="home.jsp" onsubmit="return addNewStock();">

	        	 <div class="form-group">
				    <label class="mr-2" for="addStockTicker">Ticker</label>
				    <input type="text" class="form-control mr-2" id="addStockTicker" name="addStockTicker" placeholder="Enter a stock ticker">
				  </div>

				  <div class="form-group">
				    <label class="" for="numShares"># Shares</label>
				    <input type="text" class="form-control mr-2" id="numShares" name="numShares">
				  </div>

				   <div class="form-group">
				    <label id="labelPurchase" class="" for="datePurchased">Date Purchased</label>
				    <input type="date" class="form-control mr-2" id="datePurchased" name="datePurchased">
				  </div>

				   <div class="form-group">
				    <label id="labelSold" class="" for="dateSold">Date Sold (optional)</label>
				    <input type="date" class="form-control mr-2" id="dateSold" name="dateSold">
				  </div>
				    

				 <button type="submit" class="btn btn-success" id="addStockButton">Add Stock</button>
	        </form>
	     	
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-danger" data-dismiss="modal" id="addStockCancel">Cancel</button>
	      </div>
	    </div>
	  </div>
	</div>

	<!-- Modal #3 for viewing stock history information -->
	<div class="modal fade" id="historyModal" tabindex="-1" role="dialog" aria-labelledby="historyModalLabel" aria-hidden="true">
	  <div class="modal-dialog" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title" id="exampleModalLabel">View Stock</h5>
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
	          <span aria-hidden="true">&times;</span>
	        </button>
	      </div>
	      <div class="modal-body">
	        
	      	<div class="text-danger errorMessage" id ="viewStockError"></div>

	        <!-- stock view form -->
	        <form method="POST" id="viewStockForm" action="" onsubmit="return addStockHistory();">

	        	 <div class="form-group">
				    <label class="mr-2" for="stockHistory">Ticker</label>
				    <input type="text" class="form-control mr-2" id="stockHistory" name="stockHistory" placeholder="Enter a stock ticker">
				  </div>

				 <button type="submit" class="btn btn-primary" id="viewStockButton">View Stock</button>
	        </form>



	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-danger" data-dismiss="modal" id="viewCancel">Cancel</button>
	      </div>
	    </div>
	  </div>
	</div>

	<!-- dashboard options -->
	<div class="container-fluid mt-5">

		<div class="row justify-content-center">
			<div class="col-9">
				<div class="card-deck">

					<!-- Upload portfolio card -->
					  <div class="card" id="portfolioCard">
					    <div class="card-body text-center">
					      <p class="card-text">Upload Portfolio</p>
					      <a data-toggle="modal" href="#portfolioModal" data-target="#portfolioModal" class="stretched-link"></a>
					    </div>
					  </div>

					  <!-- Add stock portfolio card -->
					  <div class="card bg-success" id="addCard">
					    <div class="card-body text-center">
					      <p class="card-text">Add Stock</p>
					      <a data-toggle="modal" href="#addModal" data-target="#addModal" class="stretched-link"></a>
					    </div>
					  </div>

					  <!-- Search stock history portfolio card -->
					  <div class="card" id="historyCard">
					    <div class="card-body text-center">
					      <p class="card-text">View Stock</p>
					     <a data-toggle="modal" href="#historyModal" data-target="#historyModal" class="stretched-link"></a>
					    </div>
					  </div>

				</div>
			</div>
		</div>
		
	</div>


	<!-- Modal for confirming whether user wants to delete a stock -->
	<div class="modal fade" id="deleteStockModal" tabindex="-1" role="dialog" aria-labelledby="deleteStockModalLabel" aria-hidden="true">
	  <div class="modal-dialog" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title" id="deleteStockModalLabel">Confirm Deletion</h5>
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
	          <span aria-hidden="true">&times;</span>
	        </button>
	      </div>
	      <div class="modal-body">

	      	<div class="text-danger errorMessage" id ="deletionError"></div>

	        <!-- delete stock form -->
	        <form method="POST" id="deleteForm" action="home.jsp" onsubmit="return deleteStock();">
	        	<button type="submit" class="btn btn-danger" id="confirmDelete">Delete Stock</button>
	        </form>

	        <div id="deleteTickerSymbol" style="display: none;"></div>
	     	
	      </div>

	      <div class="modal-footer">
	        <button type="button" class="btn btn-secondary" data-dismiss="modal" id="cancelDelete">Cancel</button>
	      </div>
	    </div>
	  </div>
	</div>

	<!-- Modal for confirming user wants to remove stock from STOCK HISTORY TABLE: should only delete entry and remove from graph -->

	<div class="modal fade" id="removeViewStock" tabindex="-1" role="dialog" aria-labelledby="removeViewStockLabel" aria-hidden="true">
	  <div class="modal-dialog" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title" id="removeViewStockLabel">Confirm Deletion</h5>
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
	          <span aria-hidden="true">&times;</span>
	        </button>
	      </div>
	      <div class="modal-body">

	      	<div class="text-danger errorMessage" id ="removeStockHistoryError"></div>

	        <!-- remove stock form -->
	        <form method="POST" id="removeStockForm" action="home.jsp" onsubmit="return removeStockHistory();">
	        	<button type="submit" class="btn btn-danger" id="confirmRemoval">Delete Stock</button>
	        </form>

	         <div id="deleteHistoricalTicker" style="display: none;"></div>
	     	
	      </div>

	      <div class="modal-footer">
	        <button type="button" class="btn btn-secondary" data-dismiss="modal" id="cancelRemove">Cancel</button>
	      </div>
	    </div>
	  </div>
	</div>

	<!-- table for displaying stocks in current portfolio -->
	<div class="container-fluid mt-5">
		<div class="row justify-content-center">
			<div class="col-11">
				<h3 class="textColor text-center pt-1 pb-1">My Stocks:</h3>
				<div class="table-responsive">
					<table class="table table-striped table-bordered">
					  <thead>
					    <tr>
					      <th scope="col">Name</th>
					      <th scope="col">Symbol</th>
					      <th scope="col">
					      Graph
					      <button type="button" class="btn btn-secondary ml-1" id="userStockSelectAll">Select All</button>
					      <button type="button" class="btn btn-secondary ml-1" id="userStockDeSelectAll">Deselect All</button>
					  	  </th>
					      <th scope="col">Actions</th>
					    </tr>
					  </thead>
					  <tbody id="stockListBody">
						<!-- dynamically populated below-->
					  </tbody>
					</table>
				</div>
			</div>
		</div>
	</div>



	<!-- table for displaying stock history -->
	<div class="container-fluid mt-5">
		<div class="row justify-content-center">
			<div class="col-11">
				<h3 class="textColor text-center pt-1 pb-1">Stock History:</h3>
				<div class="table-responsive">
					<table class="table table-striped table-bordered">
					  <thead>
					    <tr>
					      <th scope="col">Symbol</th>
					      <th scope="col">Graph</th>
					      <th scope="col">Actions</th>
					    </tr>
					  </thead>
					  <tbody id="stockHistoryBody">
						<!-- dynamically populated below -->
					  </tbody>
					</table>
				</div>
			</div>
		</div>
	</div>


	<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
	<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js" integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN" crossorigin="anonymous"></script>
	<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js" integrity="sha384-B4gt1jrGC7Jh4AgTPSdUtOBvfO8shuf57BaghqFfPlYxofvL8/KUEfYiJOMMV+rV" crossorigin="anonymous"></script>

	<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
	<script type="text/javascript">



  // ----------------------- Logout functionality --------------------------------------------

	  function doLogout() {

	  		var xhttp = new XMLHttpRequest(); //only need this for ajax


			xhttp.open("POST", "/logout", false); //false means synchronous
			xhttp.send();


	  		return true;
	  }



// ------------------- CSV Validation functions -----------------------------------------------
		
		 // function to upload CSV and communicate with CSVServlet
		function validateCSV() {
			var xhttp = new XMLHttpRequest(); //only need this for ajax

			var csvFile = document.getElementById("csvFile").value;
			csvFile = "files/" + csvFile.substring(12);

			xhttp.open("POST", "/csv?filename=" + csvFile, false); //false means synchronous
			xhttp.send();
			
			// handle any error messages
			if(xhttp.responseText.trim() != "Successfully read in file") {
				
				document.getElementById("csvError").innerHTML = xhttp.responseText;
				
			} else {
				// successfully loaded in CSV so list stocks
				listStocks();

				// now quit out of modal
				document.getElementById("cancelCSV").click();

			}
			return false; // redirect to file pointed at by action field above
		}




// ------------------- Add Stock functions -----------------------------------------------


		// function to add stock to user's portfolio
		function addNewStock() {

			// get the ticker symbol and shares of stock trying to add
			var addTicker = document.getElementById("addStockTicker").value;

			var numShares = document.getElementById("numShares").value;

			// now grab the date purchased
			var dateBought = document.getElementById("datePurchased").value;

			// now grab the optional date sold
			var dateSold = document.getElementById("dateSold").value;

			console.log("BOUGHT: " + dateBought);

			console.log("SOLD: " + dateSold);

			// make ajax request to add stock backend
			var xhttp = new XMLHttpRequest(); //only need this for ajax

			xhttp.open("POST", "/addstock?tickersymbol=" + addTicker + "&holdings=" + numShares + "&stockname=" + addTicker + "&buyDate=" + dateBought + "&sellDate=" + dateSold, false); //false means synchronous
			xhttp.send();

			if (xhttp.responseText.trim() != "Added stock.") {
				// error occurred
				document.getElementById("addStockError").innerHTML = xhttp.responseText;

			} else {
				// success so need to update list of stocks, curr port value, value change

 				document.getElementById("addStockTicker").value = "";
				document.getElementById("numShares").value = "";
				document.getElementById("datePurchased").value = "";
				document.getElementById("dateSold").value = "";

				listStocks();

				document.getElementById("addStockCancel").click();


			}
			
		

			return false;
			
			
		}


// ------------------- Deletion functions and helpers -----------------------------------------------


		// function to append hidden ticker symbol to delete modal
		function addSymbol(tickerSymbol) {
			console.log("ADDING " + tickerSymbol);
			document.getElementById("deleteTickerSymbol").innerHTML = tickerSymbol;
		}


		// function to delete stock
		function deleteStock() {
			var xhttp = new XMLHttpRequest(); //only need this for ajax

			var stockName = document.getElementById("deleteTickerSymbol").innerHTML;
			xhttp.open("POST", "/deletestock?stockname=" + stockName, false); //false means synchronous

			xhttp.send();
			
			// handle any error messages
			if(xhttp.responseText.trim() != "Deleted stock.") {
				
				document.getElementById("deletionError").innerHTML = "Error";
				
			} else {

				// upon successful deletion, need to update table
				listStocks();

				// update curr portfolio val

				// update percent change

			}
			return false; // update list of stocks, current portfolio value

		}

		

// ------------------- Listing table of user stocks functions -----------------------------------------------

		// function to dynamically populate user list of stocks
		function listStocks() {
			var xhttp = new XMLHttpRequest(); //only need this for ajax

			xhttp.open("POST", "/list", false); //false means synchronous

			xhttp.send();
			
			// parse response
			console.log(xhttp.responseText.trim());

			// try to split the response into an array to dynamically create the table rows
			var stockArray = xhttp.responseText.trim().split("|");

			// know that the current portfolio value is in first entry of response
			var currValueStr = stockArray[0];
			var valueChangeStr = stockArray[1];
			var currValue = parseFloat(currValueStr);
			var valueChange = parseFloat(valueChangeStr);
			var roundedPortVal = currValue;
			var roundedPortChange = valueChange.toFixed(2) + "%";
			if (currValue > 0) {
				roundedPortVal = currValue.toFixed(2);
			}

			document.getElementById("currentValue").innerHTML = roundedPortVal;
			document.getElementById("percentChange").innerHTML = roundedPortChange;
			document.getElementById("percentChange").style.color = (valueChange < 0) ? "red" : "green";

			document.getElementById("dirArrow").className = ""; // reset

			// set the arrow dir and color
			if (valueChange < 0) {
				document.getElementById("dirArrow").className = "fa fa-caret-down";
				document.getElementById("dirArrow").style.color = "red";
			} else if (valueChange > 0) {
				document.getElementById("dirArrow").className = "fa fa-caret-up";
				document.getElementById("dirArrow").style.color = "green";
			}

			

			// clear the table
			document.getElementById("stockListBody").innerHTML = '';

			var rowNum = 1;

			for (var i = 2; i < stockArray.length - 1; i+=2) {

				let rowHTML = 
					`<tr class="myStock">
					      <td class="stockFullName">${stockArray[i]}</td>
					      <td>${stockArray[i+1]}</td>
					      <td>
					      	<div class="form-check">
							  <input name="toggle" class="form-check-input graphToggle" type="checkbox" value="${stockArray[i+1]}" id="defaultCheck${rowNum}">
							</div>
					      </td>
					      <td><button id="deleteButton${rowNum}" type="button" class="btn btn-outline-danger" data-toggle="modal" data-target="#deleteStockModal" onclick="addSymbol('${stockArray[i]}')">Delete</button></td>
					  </tr>`;

				$("#stockListBody").append(rowHTML);

				rowNum++;


			}
			
		}

		listStocks(); // handles the case when the user has already uploaded the CSV
		//GetPortfolioDataOnLoad(); // deafult graph, everything in portfolio, 3 months ago to today
		google.load("visualization", "1", {packages:["corechart"]});
		google.setOnLoadCallback(GetPortfolioDataOnLoad);



// ------------------- Listing table of view stocks functions -----------------------------------------------

	var viewStockTableID = 1;

	function addHistoricalSymbol(ticker) {
		console.log("removing " + ticker + " from view history table");

		document.getElementById("deleteHistoricalTicker").innerHTML = ticker;

	}


	// function to add entry to View Stocks Table
	function addStockHistory() {

		var historicalTicker = document.getElementById("stockHistory").value;

		console.log("viewing stock history of " + historicalTicker);

		
		var xhttp = new XMLHttpRequest(); //only need this for ajax

		xhttp.open("POST", "/validTicker?ticker=" + historicalTicker, false); //false means synchronous

		xhttp.send();
		
		// parse response
		console.log(xhttp.responseText.trim());

		if(xhttp.responseText.trim() != "Success") {
				
			document.getElementById("viewStockError").innerHTML = "Not a valid stock ticker. Please try again";
				
		} else {

			// need to create an entry in Stock History table with ticker symbol, checkbox, delete button


			let newHistoricalRow = 
				`<tr class="historyRow">
				      <td>${historicalTicker}</td>
				      <td>
				      	<div class="form-check">
						  <input name="toggle" class="form-check-input historyToggle" type="checkbox" value="${historicalTicker}" id="viewStockCheck${viewStockTableID}">
						</div>
				      </td>
				      <td><button id="removeHistoryButton${viewStockTableID}" type="button" class="btn btn-outline-danger" data-toggle="modal" data-target="#removeViewStock" onclick="addHistoricalSymbol('${historicalTicker}')">Delete</button></td>
				  </tr>`;


			$("#stockHistoryBody").append(newHistoricalRow);


			viewStockTableID++;

		}


		document.getElementById("stockHistory").value = "";


		return false;


	}

	// function to delete an entry from Stock History Table
	function removeStockHistory() {

		console.log("HERE removing a stock history");

		// 2 cases -- 1) if the checkbox is checked, need to also remove from graph; 2) if not checked, just remove from table


		// first find the specific row

		var ticker = document.getElementById("deleteHistoricalTicker").innerHTML;

		$("tr.historyRow").each(function() {

        	var currTicker = $(this).find("input.historyToggle").val();

        	if (ticker == currTicker) {

        		var isChecked = $(this).find("input.historyToggle").is(":checked");

        		if (isChecked) {

        			// call function to remove this ticker from graph

        			// TO DO: need to remove this ticker for historical stock from graph

        		}

        		console.log($(this));

        		$(this).remove(); // removes entry from table
        	}



		});

		

		return false;
	}

	// toggle function for stock history table

	$("#stockHistoryBody").on('click', '.historyToggle', function() {

		console.log("clicked checkbox in stock history table");

		// 2 cases -- either clicking a checkbox or unclicking
		if (this.checked == true) {
			console.log("checking so include " + this.value  +" in graph");

			// TO DO -- need to add this stock line to graph
			
			
			SetStartAndEnd();
			//NOTE I changed it so setstartandend is called before checking validgraph
			var validGraph = CheckGraphDateErrors();
			// if not valid graph, then display error
			if(!validGraph){
				this.checked=false;//should not check if theres any errors
			}

			// else graph
			if (validGraph) {
				document.getElementById("graphDateError").innerHTML = "";
				//remove any previous error messages
				//TODO call function to graph that stock 
				queryHistoricalData(this.value);
			}


		} else {
			console.log("unchecking so don't include " + this.value  +" in graph");

			// TO DO -- need to remove this stock line from graph
			
		}

	});







	// ------------------------------------ Graph functions ----------------------------------------------------------

	// ------------------------------------ S & P Graph functionality ---------------------------------------
	$("#sAndPCheck").on('click', function() {
		console.log("Clicking on S & P checkbox");

		if (this.checked == true) {
			console.log("Checking this box so make ajax call to S&P backend");
			GetSAndP();
		} else {
			console.log("Unchecking so remove line from graph");
		}

	});

	// Set up S&P ajax call here
	function GetSAndP() {
		SetStartAndEnd();
		queryHistoricalData("^GSPC");
	}

	// ------------------------------------ Zoom In/Zoom Out -----------------------------------------
	$("#zoomIn").on('click', function() {
		console.log("Clicked plus icon to zoom in");


		// don't touch the return statement
		return false;
	});

	$("#zoomOut").on('click', function() {
		console.log("Clicked minus icon to zoom out");

		// don't touch the return statement
		return false;
	});


	// ------------------------------ Portfolio Graph Line stuff ---------------------------------------

	// put an event listener on start date field
	$("#graphStartDate").on('change', function() {
		console.log("graph start date was altered");
		SetStartAndEnd();
		GetPortfolioData();

	});



	// put an event listener on end date field
	$("#graphEndDate").on('change', function() {
		console.log("graph end date was altered");
		SetStartAndEnd();
		GetPortfolioData();

	});


	// put an event listener on time increment field
	$("#xAxis").on('change', function() {
		console.log("increment changed");
		SetStartAndEnd();
		GetPortfolioData();


	});


	// listener for select all button
	$("#userStockSelectAll").click(function() {
		console.log("Pressed select all");

		$('input[name=toggle]').prop('checked', true);

		// graph should have portfolio line for all stocks here

		// TO-DO: include all stocks in portfolio

	});

	// listener for deselect all button
	$("#userStockDeSelectAll").click(function() {
		console.log("Pressed deselect all");

		$('input[name=toggle]').prop('checked', false);

		// graph shouldn't have portfolio line here

		// TO DO: remove all stocks from portfolio

	});


	// function to check which checkbox on homepage was checked
	$("#stockListBody").on('click', '.graphToggle', function() {
		console.log("clicked a checkbox");

		// 2 cases -- either clicking a checkbox or unclicking
		if (this.checked == true) {
			console.log("checking so include " + this.value  +" in graph");

			// TO DO -- need to include this stock in line and portfolio val, portfolio change
			GetPortfolioData();


		} else {
			console.log("unchecking so don't include " + this.value  +" in graph");

			// TO DO -- need to remove this stock from portfolio line, val, change
			GetPortfolioData();
		}

	});
	function GetPortfolioDataOnLoad() {
		var today = new Date();
		if(today.getDate() < 10)
			var currDate = today.getFullYear()+'-'+(today.getMonth()+1)+'-0'+today.getDate();
		else var currDate = today.getFullYear()+'-'+(today.getMonth()+1)+'-'+today.getDate();
		if (today.getDate()< 10 && (today.getMonth()-2)<10)
			var threeMonthsAgoDate = (today.getFullYear()) + '-0'+(today.getMonth()-2)+'-0'+today.getDate();
		else if (today.getDate()<10)
			var threeMonthsAgoDate = (today.getFullYear()) + '-'+(today.getMonth()-2)+'-0'+today.getDate();
		else if ((today.getMonth()-2)<10)
			var threeMonthsAgoDate = (today.getFullYear()) + '-0'+(today.getMonth()-2)+'-'+today.getDate();
		else var threeMonthsAgoDate = (today.getFullYear()) + '-'+(today.getMonth()-2)+'-'+today.getDate();
		console.log(threeMonthsAgoDate);
		var startDate = threeMonthsAgoDate;
		var endDate = currDate;
		var increment = document.getElementById("xAxis").value;
		var stockString = "";
		$('.graphToggle').each(function() {
			console.log($(this).parent().parent().siblings(".stockFullName").text());
			var stockName = $(this).parent().parent().siblings(".stockFullName").text();
			stockString += stockName + ",";
		});
		var stockList = stockString.slice(0,-1);
		console.log("STOCKLIST: " + stockList);
		console.log("STARTDATE: " + startDate);
		console.log("ENDDATE: " + endDate);
		console.log("INTERVAL: " + increment);
		var xhttp = new XMLHttpRequest(); //only need this for ajax
		xhttp.open("POST", "/GraphHoldingsServlet?lowerBound=" + startDate + "&stockList=" + stockList + "&interval=" + increment + "&upperBound=" + endDate, false); //false means synchronous
		xhttp.send();
		console.log("RESPONSE: " + xhttp.responseText);
		// graph here now
		drawData("Portfolio", xhttp.responseText);

	}
	function GetPortfolioData() {
		SetStartAndEnd();
		var startDate = document.getElementById("graphStartDate").value;
		//console.log("startdate is "+startDate);
		var endDate = document.getElementById("graphEndDate").value;
		var increment = document.getElementById("xAxis").value;

		var stockString = "";

		$('.graphToggle').each(function() {
			// console.log($(this.children()));
			// console.log($(this).checked);
			if ($(this).prop("checked")) {
				// console.log($(this).parent().parent().siblings(".stockFullName").text());
				var stockName = $(this).parent().parent().siblings(".stockFullName").text();
				stockString += stockName + ",";
			}
			
		});

		var stockList = stockString.slice(0,-1);
		console.log(stockList);
		
		var xhttp = new XMLHttpRequest(); //only need this for ajax

		xhttp.open("POST", "/GraphHoldingsServlet?lowerBound=" + startDate + "&stockList=" + stockList + "&interval=" + increment + "&upperBound=" + endDate, false); //false means synchronous
	
		xhttp.send();

		console.log(xhttp.responseText);

		// graph here now
		drawData("Portfolio", xhttp.responseText);
	}


	// function to check graph date errors by checking start date, end date, increments

	function CheckGraphDateErrors() {		
		
		
		
		var startDate = document.getElementById("graphStartDate").value;
		console.log("startdate is "+startDate);
		var endDate = document.getElementById("graphEndDate").value;
		console.log("enddate is "+endDate);
		
		var today = new Date();
		var currDate = today.getFullYear()+'-'+(today.getMonth()+1)+'-'+today.getDate();
		
		if(today.getDate() < 10 ){
			//this puts it in YYYY-MM-DD format
			var currDate = today.getFullYear()+'-'+(today.getMonth()+1)+'-0'+today.getDate();
		}
		else{
			var currDate = today.getFullYear()+'-'+(today.getMonth()+1)+'-'+today.getDate();
		}
		//this puts it in YYYY-MM-DD format
		//console.log("currdate is "+currDate);
		
		if(endDate < startDate){//error for end before start
			console.log(" endDate < startDate");
			document.getElementById("graphDateError").innerHTML = "End Date cannot be before Start Date";
			return false;
		}
		
		//error if going back more than a year
		var lastYear = new Date();
		if(lastYear.getDate()<10){
			var lastYearDate = (today.getFullYear()-1)+'-'+(today.getMonth()+1)+'-0'+today.getDate();
		}
		else{
			var lastYearDate = (today.getFullYear()-1)+'-'+(today.getMonth()+1)+'-'+today.getDate();
		}
		//console.log("date last year was "+lastYearDate);
		if(startDate < lastYearDate || endDate < lastYearDate){
			document.getElementById("graphDateError").innerHTML = "Cannot go back more than a year";
			return false;
		}
		return true;

	}


	function SetStartAndEnd() {

		console.log("HERE in set start and end");
		
		// check is my start empty, if so, grab the date field and set it for start up to a year ago
		
		var startDate = document.getElementById("graphStartDate").value;
		
		var today = new Date();
		if(today.getDate() < 10){
			var currDate = today.getFullYear()+'-'+(today.getMonth()+1)+'-0'+today.getDate();
		}
		else{
			var currDate = today.getFullYear()+'-'+(today.getMonth()+1)+'-'+today.getDate();
		}
		
		var lastYear = new Date();
		if(lastYear.getDate() < 10){
			var lastYearDate = (today.getFullYear()-1)+'-'+(today.getMonth()+1)+'-0'+today.getDate();
		}
		else{
			var lastYearDate = (today.getFullYear()-1)+'-'+(today.getMonth()+1)+'-'+today.getDate();
		}
		
		var threeMonthsAgo = new Date();
		if (threeMonthsAgo.getDate()< 10 && threeMonthsAgo.getMonth()-2< 10){
			var threeMonthsAgoDate = (today.getFullYear()) + '-0'+(today.getMonth()-2)+'-0'+today.getDate();
		} 
		else if (threeMonthsAgo.getDate()< 10){
			var threeMonthsAgoDate = (today.getFullYear()) + '-'+(today.getMonth()-2)+'-0'+today.getDate();
		}
		else if (threeMonthsAgo.getMonth()-2< 10){
			var threeMonthsAgoDate = (today.getFullYear()) + '-0'+(today.getMonth()-2)+'-'+today.getDate();
		}
		else {
			var threeMonthsAgoDate = (today.getFullYear()) + '-'+(today.getMonth()-2)+'-'+today.getDate();
		}
		
		console.log("Date three months ago: " + threeMonthsAgoDate);
		
		var xhttp = new XMLHttpRequest(); //only need this for ajax
		xhttp.open("POST", "/EarliestTransactionServlet", false); //false means synchronous
		xhttp.send()
		var response = xhttp.responseText;
		console.log("received response : " + response + " of length " + response.length);
		
		if(startDate == ""){//TODO if the startDate is empty change to a purchase date of first stock
			//for now use a year ago 
			var set;
			if (response.length > 2) set = response.substring(0, 10);
			else set = threeMonthsAgoDate;
			document.getElementById("graphStartDate").value = set;
			console.log("startDate is now "+document.getElementById("graphStartDate").value);
		}

		// check is my end date empty, if so grab that date field, and set it to current day
		
		var endDate = document.getElementById("graphEndDate").value;
		
		if(endDate == ""){
			console.log("EndDate is now current date is "+currDate);
			document.getElementById("graphEndDate").value = currDate;
			//this sets it to the current date
		}
				
	}
	
function queryHistoricalData(tickerSymbol) {
		
		var startDate = document.getElementById("graphStartDate").value;
		console.log("startdate is "+startDate);
		var endDate = document.getElementById("graphEndDate").value;
		console.log("enddate is "+endDate);
		var increment = document.getElementById("xAxis").value;
		
		var xhttp = new XMLHttpRequest(); //only need this for ajax

		xhttp.open("POST", "/HistoricalDataServlet?lowerBound=" + startDate + "&ticker=" + tickerSymbol + "&interval=" + increment + "&upperBound=" + endDate, false); //false means synchronous
	
		xhttp.send();
		console.log("reached the end");
		//console.log(xhttp.responseText);
		drawData(tickerSymbol, xhttp.responseText);
		// parse response
		//var dateMap = JSON.parse(xhttp.responseText);
		//console.log("ticker symbol is " +tickerSymbol);
		//console.log("the dateMap is "+dateMap);
		//console.log(dateMap["2020-05-12"]);
		
		
	}
	
	google.charts.load('current', {packages: ['corechart', 'line']});
	google.charts.setOnLoadCallback(drawCurveTypes);
	
	function drawData(ticker, stringOfData){
		var data = new google.visualization.DataTable();
		//first we need to put the information into a data table
		var data = new google.visualization.DataTable();
		data.addColumn('date', 'Time');
		data.addColumn('number', ticker);
		console.log("ticker is "+ticker);
		var arr=stringOfData.split(",");
		//console.log("first data point is "+arr[0]);
		//console.log("size of data array is "+arr.length)
		var i;
		for(i =0; i < arr.length; i++){
			var dataLine=arr[i].split(":");
			var date = dataLine[0];
			var value = dataLine[1];
			//console.log("at date "+date+" we have value "+Math.round(value));
			//var day=new Date(date);
			data.addRow([new Date(date), Math.round(value)]);
		}
		
		var options = {
		        hAxis: {
		          title: 'Time'
		        },
		        vAxis: {
		          title: 'Value'
		        },
		        series: {
		          1: {curveType: 'function'}
		        }
		      };

		      var chart = new google.visualization.LineChart(document.getElementById('chart_div'));
		      chart.draw(data, options);
	}
	
	
	function drawCurveTypes() {
	      var data = new google.visualization.DataTable();
	      data.addColumn('number', 'X');
	      data.addColumn('number', 'Practice Portfolio');

	      data.addRows([
	        [0, 0],    [1, 10],   [2, 23],  [3, 17],   [4, 18],  [5, 9],
	        [6, 11],   [7, 27],  [8, 33],  [9, 40],  [10, 32], [11, 35],
	        [12, 30], [13, 40], [14, 42], [15, 47], [16, 44], [17, 48]
	      ]);
	      
	      data.addColumn('number', 'Another Line');
	      var i;
	      for(i=0 ; i<18; i++){
	    	  data.setValue(i, 2, i);
	      }

	      var options = {
	        hAxis: {
	          title: 'Time'
	        },
	        vAxis: {
	          title: 'Value'
	        },
	        series: {
	          1: {curveType: 'function'}
	        }
	      };

	      //var chart = new google.visualization.LineChart(document.getElementById('chart_div'));
	      //chart.draw(data, options);
	    } 


	</script>
</body>
</html>
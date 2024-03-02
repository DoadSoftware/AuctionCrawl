<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>

  <meta charset="utf-8" name="viewport" content="width=device-width, initial-scale=1">
  <title>Output Screen</title>
	
  <script type="text/javascript" src="<c:url value="/webjars/jquery/3.6.0/jquery.min.js"/>"></script>
  <script type="text/javascript" src="<c:url value="/webjars/bootstrap/5.1.3/js/bootstrap.min.js"/>"></script>
  <script type="text/javascript" src="<c:url value="/resources/javascript/index.js"/>"></script>
  <script type="text/javascript" src="<c:url value="/webjars/select2/4.0.13/js/select2.js"/>"></script>
  
  <link rel="stylesheet" href="<c:url value="/webjars/select2/4.0.13/css/select2.css"/>"/>
  <link rel="stylesheet" href="<c:url value="/webjars/bootstrap/5.1.3/css/bootstrap.min.css"/>"/>  
  <link href="<c:url value="/webjars/font-awesome/6.0.0/css/all.css"/>" rel="stylesheet">
  <script type="text/javascript">
  $(document).on("keypress", function(e){
		processUserSelectionData('LOGGER_FORM_KEYPRESS',e.which);
  });
 	setInterval(() => {
 		processAuctionProcedures('READ-MATCH-AND-POPULATE');		
	}, 1000);
  </script>
</head>
<body>
<form:form name="output_form" autocomplete="off" action="POST">
<div class="content py-5" style="background-color: #EAE8FF; color: #2E008B">
  <div class="container">
	<div class="row">
	 <div class="col-md-8 offset-md-2">
       <span class="anchor"></span>
         <div class="card card-outline-secondary">
           <div class="card-header">
             <h3 class="mb-0">Output</h3>
            <!--   <h3 class="mb-0">${licence_expiry_message}</h3>  -->
           </div>
          <div class="card-body">
          
			  <div id="select_graphic_options_div" style="display:none;">
			  </div>
			  <div id="captions_div" class="form-group row row-bottom-margin ml-2" style="margin-bottom:5px;">
			  	<!--  <label class="col-sm-4 col-form-label text-left">${licence_expiry_message} </label> 
			    <label class="col-sm-4 col-form-label text-left">Match: ${session_match.matchFileName} </label> -->
			    <label class="col-sm-4 col-form-label text-left">IP Address: ${session_selected_ip} </label>
			    <label class="col-sm-4 col-form-label text-left">Port Number: ${session_port} </label>
			    <label class="col-sm-4 col-form-label text-left">Broadcaster: ${session_selected_broadcaster} </label>
			    
				<div class="left">
				
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="crawler_graphic_btn" id="crawler_graphic_btn" onclick="processUserSelection(this)"> Crawler </button>
			  		
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="free_Text_graphic_btn" id="free_Text_graphic_btn" onclick="processUserSelection(this)"> FREE TEXT </button>			
			  	
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="remaining_purse_graphic_btn" id="remaining_purse_graphic_btn" onclick="processUserSelection(this)"> Remaining Purse With Squad Size  </button>
			  	
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="remaining_purse_all_graphic_btn" id="remaining_purse_all_graphic_btn" onclick="processUserSelection(this)"> Remaining Purse </button>
			  	
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="zone_graphic_btn" id="zone_graphic_btn" onclick="processUserSelection(this)"> Zone </button>	
			  		
			  	<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="squad_all_graphic_btn" id="squad_all_graphic_btn" onclick="processUserSelection(this)"> Squad All </button>

				<button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="squad_with_role_count_graphic_btn" id="squad_with_role_count_graphic_btn" onclick="processUserSelection(this)"> Squad with role�count�</button>
			  		
			  	</div>	
			  	<div class="left">
			  	<button style="background-color:#f44336;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="animateout_graphic_btn" id="animateout_graphic_btn" onclick="processUserSelection(this)"> AnimateOut </button>
			  	<button style="background-color:#f44336;color:#FEFEFE;" class="btn btn-sm" type="button"
			  		name="clearall_graphic_btn" id="clearall_graphic_btn" onclick="processUserSelection(this)"> Clear All </button>
			  </div>
			  </div>
	       </div>
	    </div>
       </div>
    </div>
  </div>
</div>
<input type="hidden" id="which_keypress" name="which_keypress" value="${session_match.which_key_press}"/>
<input type="hidden" name="selected_broadcaster" id="selected_broadcaster" value="${session_selected_broadcaster}"/>
<input type="hidden" name="selected_which_layer" id="selected_which_layer" value="${selected_layer}"></input>
</form:form>
</body>
</html>
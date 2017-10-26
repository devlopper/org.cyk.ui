$(document).ready(function() {
	//Ready
	#{pageController.onDocumentReadyJavaScript}
	//Loaded
	$(window).on('load', function() { #{pageController.onDocumentLoadJavaScript} });
	//Before unloadind
	$(window).bind('beforeunload', function(){
		#{pageController.onDocumentBeforeUnLoadJavaScript}
		//the custom message for page unload doesn't work on Firefox
		if(#{pageController.onDocumentBeforeUnLoadWarnAsString})
			return '#{pageController.onDocumentBeforeUnLoadWarningMessage}';
	
	});
});  

function clickEditButtonLastRow(dataTableStyleClass){
	$('.'+dataTableStyleClass+' div.ui-datatable-tablewrapper table tbody.ui-datatable-data tr.ui-widget-content').last().find('td div.ui-row-editor span.ui-icon-pencil').each(function(){
		 $(this).click();	
	});
}

function clickEditButtonAllRow(dataTableStyleClass){
	$('.'+dataTableStyleClass+' div.ui-datatable-tablewrapper table tbody.ui-datatable-data tr.ui-widget-content').find('td div.ui-row-editor span.ui-icon-pencil').each(function(){
		 $(this).click();	
	});
}

function hideAllRowEditorButtons(dataTableStyleClass){
	$('.'+dataTableStyleClass+' div.ui-datatable-tablewrapper table tbody.ui-datatable-data tr.ui-widget-content').find('div.ui-row-editor').each(function(){
		 $(this).display='none';
		 $(this).visible='false';
		 $(this).visibility='hidden';	
	});
}			

function clickEditButtonRow(dataTableStyleClass,rowIndex){
	$('.'+dataTableStyleClass+' div.ui-datatable-tablewrapper table tbody.ui-datatable-data tr.ui-widget-content:nth-child('+rowIndex+')').find('td div.ui-row-editor span.ui-icon-pencil').each(function(){
		 $(this).click();	
	});
}
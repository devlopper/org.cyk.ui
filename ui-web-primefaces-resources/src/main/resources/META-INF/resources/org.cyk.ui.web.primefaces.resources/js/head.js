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

function preview(input,previewId) {
    if (input.files &amp;&amp; input.files[0]) {
        var reader = new FileReader();
        reader.onload = function (e) {
            $('#'+previewId).attr('src', e.target.result);
        }
        reader.readAsDataURL(input.files[0]);
    }
}

function playSound(soundfile) {
	document.getElementById("dummysound").
		innerHTML='<embed src="'+soundfile+'" hidden="true" autostart="true" loop="false" />';
}
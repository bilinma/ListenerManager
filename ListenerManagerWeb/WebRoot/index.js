var tab=null;
$( function() {
	  tab = new TabView( {
		containerId :'tab_menu',
		pageid :'page',
		cid :'tab_po',
		position :"top"
	});

	tab.add( {
		id :'index',
		title :"ÕìÌı¿ØÖÆÌ¨",
		url :"listener/listenerConsole.jsp",
		isClosed :false
	});	
	
	tab.activate("index");
});
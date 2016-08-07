//REPRODUCIR UN VIDEO EN WIKITUDE
//Representa el mundo de realidad aumentada
var World = {
	loaded: false,

	init: function initFn() {
		this.createOverlays();
	},
    //Define las caás a partir del tagetCollections.wtc
	createOverlays: function createOverlaysFn() {
		this.tracker = new AR.ClientTracker("assets/targetcollections.wtc", {
			onLoaded: this.worldLoaded
		});
        //Variable que va a cargar el video
		var video = new AR.VideoDrawable("assets/video.mp4", 1.0, {
			offsetY: 0.2,
			offsetX: 0.2,
		});
        //Tracable presenta el elemento en la pantalla pasandole los objetos que queremos dibujar
		var pageOne = new AR.Trackable2DObject(this.tracker, "*", {
			drawables: {
				cam: [video]
			},
            //Desde que punto sera reproducido el video
			onEnterFieldOfVision: function onEnterFieldOfVisionFn() {
				video.play(-1);
			}
		});
	},
    //Cargar el mundo haciendo uso de la pagina index
	worldLoaded: function worldLoadedFn() {
		setTimeout(function() {
			var e = document.getElementById('loadingMessage');
			e.parentElement.removeChild(e);
		}, 2500);
	}
};
//Iniciamos el mundo
World.init();

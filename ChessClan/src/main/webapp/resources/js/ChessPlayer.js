/*
	ChessPlayer
	Requires:
	- JQuery
	- Three.js
	Usage:
		First you need to create new chess player by pointing to target element
		preferably DIV.
		After that using player method loadJSON provide url to the json of the 
		
	
*/

function ChessPlayer(DOMTarget){
	// Fields
	this.target = DOMTarget;
	this.game = null;
	this.currentMove = 0;
        this.numberOfMoves=0;
	this.navigation = "";
	this.canvas = "";
	this.summary = "";
	this.board = "";
	this.models = new Object();
	this.materials = new Array();
	this.boardState = new Array(); // 8x8
	// Methods
	
	// Loads game record
	this.loadJSON = function(url) {
		var _this = this;
		$.ajax({
			url: url,
			success: function(data) {
				_this.game = data;
                                _this.numberOfMoves=0;
                                $.each(data.moves,function(i,v){if(v.isSingular)_this.numberOfMoves++;});
				_this.init();  // Init board
			}
		});
	}
	// Next move
	this.nextMove = function(){
		
		var moves = this.game.moves;
		if(!moves || this.currentMove>=moves.length) return;
		if(moves[this.currentMove].isSingular){
			var move = moves[this.currentMove];
			this.moveFigure(move.fromX,move.fromY,move.toX,move.toY,false);
		}else{
			for(var i = this.currentMove; !moves[i].isSingular; i++){
				var move = moves[i];
				this.moveFigure(move.fromX,move.fromY,move.toX,move.toY,false);
			}
		}
		this.currentMove++;
	}
	// Prev move
	this.prevMove = function(){
		var moves = this.game.moves;
		if(!moves || this.currentMove==0) return;
		this.currentMove--;
		if(moves[this.currentMove].isSingular){
			var move = moves[this.currentMove];
			this.moveFigure(move.toX,move.toY,move.fromX,move.fromY,true);
		}else{
			for(var i = this.currentMove; !moves[i].isSingular; i++){
				var move = moves[i];
				this.moveFigure(move.toX,move.toY,move.fromX,move.fromY,true);
			}
		}
	}
	// Init player
	this.init = function () {
	    if(!this.game){
	        alert("No game has been choosen!");
	        return;
	    }
		var _this = this;
		// Clean up target
		$(this.target).html('');
		// Add canvas and summary
		this.navigation = $('<div>').attr({
			id: 'chess-player-navigation-'+$(this.target).attr("id"),
			class: 'chess-player-navigation'
		});
		this.canvas = $('<canvas>').attr({
			id: 'chess-player-'+$(this.target).attr("id"),
			class: "chess-player-canvas"
		});
		this.summary = $('<div>').attr({
			id: 'chess-player-summary-'+$(this.target).attr("id"),
			class: 'chess-player-summary'
		});	
		// Configure navigation
		// Button "<<"
		this.navigation.append($('<span>').attr({
			class: "chess-player-navigation-button"
		}).html('<<'));
                $(":last",this.navigation).bind("click",_this,function(e){
                    while(_this.currentMove>0){
			e.data.prevMove();
                    }
		});
		// Button "<"
		this.navigation.append($('<span>').attr({
			class: "chess-player-navigation-button"
		}).html('<'));
		$(":last",this.navigation).bind("click",_this,function(e){
			e.data.prevMove();
		});
		// Button ">"
		this.navigation.append($('<span>').attr({
			class: "chess-player-navigation-button"
		}).html('>'));
		$(":last",this.navigation).bind("click",_this,function(e){
			e.data.nextMove();
		});
		// Button ">>"
		this.navigation.append($('<span>').attr({
			class: "chess-player-navigation-button"
		}).html('>>'));
                $(":last",this.navigation).bind("click",_this,function(e){
                    while(_this.currentMove<_this.game.moves.length){
			e.data.nextMove();
                    }
		});
		// Button "Rotate"
		this.navigation.append($('<span>').attr({
			class: "chess-player-navigation-button rotate"
		}).html('&nbsp;'));
		$(":last",this.navigation).bind("click",_this,function(e){
                    _this.camera.position.y *=-1;
                    _this.camera.up.y *=-1;
                    _this.camera.lookAt(_this.scene.position);
                    _this.renderer.render(_this.scene,_this.camera);
		});
                // Players
                this.summary.append($('<span>').attr({
			class: "chess-player-summary-players"
		}).html("Biały: "+this.game.white+(this.game.result=='WHITE_WON'?"(Wygrał)":"")+'<br/>vs<br/>'+" Czarny: "+this.game.black+(this.game.result=='WHITE_WON'?"":"(Wygrał)")));
		// Game info
                this.summary.append($('<span>').attr({
			class: "chess-player-summary-info"
		}).html(this.game.event+', '+this.game.site+'<br/>'+this.game.date));
                //Upload area
                this.summary.append($('<span>').attr({
			class: "chess-player-summary-upload",
                        ondragenter: "this.classList.add('over');",
                        ondragleave: "this.classList.remove('over');"
		}).html('&nbsp;'));
		
		
		// Add nodes to target
		$(this.target).append(this.navigation).append(this.canvas).append(this.summary);
		
		for(var i = 0; i<8; i++){
			this.boardState[i] = new Array();
			for(var j = 0; j<8; j++){
				this.boardState[i][j] = new Array();
			}
		}
		this.initThree();
		this.loadModels();
		
                // Init .pgn loader
                var _this = this;
		plugListenersAndGetJSON(document.getElementById('chess-player-summary-'+$(this.target).attr("id")),function(so){
                    if(so.state=="CorrectlyParsedSucc"){
                        _this.game = so.json;
                        _this.numberOfMoves=0;
                        _this.currentMove = 0;
                        $.each(so.json.moves,function(i,v){if(v.isSingular)_this.numberOfMoves++;});
			_this.init();  // Init board
                    }
                });
	}
	
		// Init Three
	this.initThree = function(){
		// set the scene size
		var WIDTH = $(this.canvas).width(),
		HEIGHT = $(this.canvas).height(),
		SHADOW_MAP_WIDTH=2048,
		SHADOW_MAP_HEIGHT=2048;

		// set some camera attributes
		var VIEW_ANGLE = 30,
		ASPECT = WIDTH / HEIGHT,
		NEAR = 0.1,
		FAR = 10000;

		// create a WebGL renderer, camera
		// and a scene
		var renderer = new THREE.WebGLRenderer({antialias: true, canvas:this.canvas[0]});
		renderer.shadowMapEnabled = true;
		renderer.shadowMapSoft = true;

		renderer.shadowCameraNear = NEAR;
		renderer.shadowCameraFar = FAR;
		renderer.shadowCameraFov = 50;

		renderer.shadowMapBias = 0.0039;
		renderer.shadowMapDarkness = 0.5;
		renderer.shadowMapWidth = SHADOW_MAP_WIDTH;
		renderer.shadowMapHeight = SHADOW_MAP_HEIGHT;
		var camera =
			new THREE.PerspectiveCamera(
				VIEW_ANGLE,
				ASPECT,
				NEAR,
				FAR);

		var scene = new THREE.Scene();

		// add the camera to the scene
		scene.add(camera);

		// the camera starts at 0,0,0
		// so pull it back
		camera.position.y = -600;
		camera.position.z = 600;
		camera.lookAt(scene.position);
		// start the renderer
		renderer.setSize(WIDTH, HEIGHT);
				
		// LIGHTS

		var ambient = new THREE.AmbientLight( 0x444444 );
		scene.add( ambient );
        
		light = new THREE.SpotLight( 0xffffff, 1, 0, Math.PI, 1 );
		light.position.set( 0, 0, 800 );
		light.target.position.set( 0, 0, 0 );
		light.castShadow = true;
		light.shadowCameraNear = 600;
		light.shadowCameraFar = camera.far;
		light.shadowCameraFov = 50;
		light.shadowBias = 0.0001;
		light.shadowDarkness = 0.5;
		light.shadowMapWidth = SHADOW_MAP_WIDTH;
		light.shadowMapHeight = SHADOW_MAP_HEIGHT;
		scene.add( light );
				
		// update reference's
		this.camera = camera;
		this.scene = scene;
		this.renderer = renderer;
	}
	
	this.loadModels = function(){
		var _this = this;
		// Load chess board model and textures
		var objMTLLoader = new THREE.OBJMTLLoader();
		objMTLLoader.addEventListener('load',function ( object ) {
			_this.board = object.content;
			_this.board.castShadow = false;
			_this.board.receiveShadow  = true;
			for(var i = 0; i<_this.board.children.length; i++){
				_this.board.children[i].castShadow=true;
				_this.board.children[i].receiveShadow=true;
			}
			_this.board.position.z=-23;
			_this.scene.add( _this.board );
		});
		objMTLLoader.load( "models/board.obj","models/board.mtl");
		// Loading materials for figures
		var mtlLoader = new THREE.MTLLoader("models/Textures/");
		mtlLoader.addEventListener('load', function(o){
			_this.materials[0]=o.content.create("whiteFigure");
			_this.materials[1]=o.content.create("blackFigure");
			_this.loadFigures();
		});
		mtlLoader.load("models/figureMaterials.mtl");
	
	}
	this.loadFigures = function(){
		var _this = this;
		var currentModel;
		var modelNames = new Array();
		modelNames.push("pawn");
		modelNames.push("knight");
		modelNames.push("bishop");
		modelNames.push("rook");
		modelNames.push("queen");
		modelNames.push("king");
		var objLoader = new THREE.OBJLoader();
		objLoader.addEventListener('load', function(o){
			_this.models[currentModel]=o;
			if(modelNames.length>0){
				currentModel = modelNames.pop();
				objLoader.load("models/"+currentModel+".obj");
			}else{
				_this.initBoard();
			}
		});
		currentModel = modelNames.pop();
		objLoader.load("models/"+currentModel+".obj");
	}
	this.getFigurePosition = function(x,y){
		var obj = new Object();
		obj.x = 50*x-174;
		obj.y = 50*y-174;
		return obj;
	}
	this.moveFigure = function(x,y,toX,toY,rev){
		var figure = this.boardState[x][y].pop();
		if(figure){
			if(rev && this.boardState[x][y].length>0){
				var f = this.boardState[x][y][this.boardState[x][y].length-1].children;
				for(var i=0; i<f.length; i++){
					f[i].visible = true; // set visible if reversing
				}
			}
				
			if(!rev && this.boardState[toX][toY].length>0){
				var f = this.boardState[toX][toY][this.boardState[toX][toY].length-1].children;
				for(var i = 0; i<f.length; i++){
					f[i].visible = false; // set not visible if not reversing
				}
			}
				
			this.boardState[toX][toY].push(figure);
			var pos = this.getFigurePosition(toX,toY);
			figure.position.x = pos.x;
			figure.position.y = pos.y;
			this.renderer.render(this.scene, this.camera);
		}
	}
	this.initBoard = function(){
		// setup pawns
		for(var i = 0; i<16; i++){
			var pos = this.getFigurePosition(i%8,Math.floor(i/8)*7+(i<8?1:-1));
			var pawn = this.models.pawn.content.clone();
			var material = this.materials[i>7?1:0];
			for(var j = 0; j<pawn.children.length; j++){
				pawn.children[j].material=material;
				pawn.children[j].castShadow = true;
				pawn.children[j].receiveShadow  = true;	
                                pawn.children[j].geometry.computeVertexNormals();
			}
			pawn.position.x = pos.x;
			pawn.position.y = pos.y;
			pawn.castShadow = true;
			pawn.receiveShadow  = true;
			this.scene.add(pawn);
			this.boardState[i%8][Math.floor(i/8)*7+(i<8?1:-1)]=[pawn];
		}
		// setup rooks
		for(var i = 0; i<4; i++){
			var pos = this.getFigurePosition((i%2)*7,(i<2?0:7));
			var rook = this.models.rook.content.clone();
			var material = this.materials[i>1?1:0];
			for(var j = 0; j<rook.children.length; j++){
				rook.children[j].material=material;
				rook.children[j].castShadow = true;
				rook.children[j].receiveShadow  = true;	
                                rook.children[j].geometry.computeVertexNormals();
			}
			rook.position.x = pos.x;
			rook.position.y = pos.y;
			rook.castShadow = true;
			rook.receiveShadow  = true;
			this.scene.add(rook);
			this.boardState[(i%2)*7][(i<2?0:7)]=[rook];
		}
		// setup knights
		for(var i = 0; i<4; i++){
			var pos = this.getFigurePosition((i%2)*5+1,(i<2?0:7));
			var knight = this.models.knight.content.clone();
			var material = this.materials[i>1?1:0];
			for(var j = 0; j<knight.children.length; j++){
				knight.children[j].material=material;
				knight.children[j].castShadow = true;
				knight.children[j].receiveShadow  = true;
                                knight.children[j].geometry.computeVertexNormals();
			}
			knight.position.x = pos.x;
			knight.position.y = pos.y;
			knight.castShadow = true;
			knight.receiveShadow  = true;
			this.scene.add(knight);
			this.boardState[(i%2)*5+1][(i<2?0:7)]=[knight];
		}
		// setup bishops
		for(var i = 0; i<4; i++){
			var pos = this.getFigurePosition((i%2)*3+2,(i<2?0:7));
			var bishop = this.models.bishop.content.clone();
			var material = this.materials[i>1?1:0];
			for(var j = 0; j<bishop.children.length; j++){
				bishop.children[j].material=material;
				bishop.children[j].castShadow = true;
				bishop.children[j].receiveShadow  = true;
                                bishop.children[j].geometry.computeVertexNormals();
			}
			bishop.position.x = pos.x;
			bishop.position.y = pos.y;
			bishop.castShadow = true;
			bishop.receiveShadow  = true;
			this.scene.add(bishop);
			this.boardState[(i%2)*3+2][(i<2?0:7)]=[bishop];
		}
		// setup queens
		for(var i = 0; i<2; i++){
			var pos = this.getFigurePosition(4,(i<1?0:7));
			var queen = this.models.queen.content.clone();
			var material = this.materials[i>0?1:0];
			for(var j = 0; j<queen.children.length; j++){
				queen.children[j].material=material;
				queen.children[j].castShadow = true;
				queen.children[j].receiveShadow  = true;
                                queen.children[j].geometry.computeVertexNormals();
			}
			queen.position.x = pos.x;
			queen.position.y = pos.y;
			queen.castShadow = true;
			queen.receiveShadow  = true;
			this.scene.add(queen);
			this.boardState[4][(i<1?0:7)]=[queen];
		}
		// setup kings
		for(var i = 0; i<2; i++){
			var pos = this.getFigurePosition(3,(i<1?0:7));
			var king = this.models.king.content.clone();
			var material = this.materials[i>0?1:0];
			for(var j = 0; j<king.children.length; j++){
				king.children[j].material=material;
				king.children[j].castShadow = true;
				king.children[j].receiveShadow  = true;	
                                king.children[j].geometry.computeVertexNormals();
			}
			king.position.x = pos.x;
			king.position.y = pos.y;
			king.castShadow = true;
			king.receiveShadow  = true;
			this.scene.add(king);
			this.boardState[3][(i<1?0:7)]=[king];
		}
		this.renderer.render(this.scene, this.camera);
	}
}
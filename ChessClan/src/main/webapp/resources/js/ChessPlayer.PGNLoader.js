var JSONPrev = new JSONStateObject(null, null);
var JSONStateAndObject = new JSONStateObject(null, null);
// Podpinanie listenerów
function plugListenersAndGetJSON(object, visualisation)
{//Sprawdzanie obsługi przeglądarek, czy object null etc.
    if (isBrowserCompatible()) {
        if (object === null || object === undefined) {
            JSONStateAndObject.state = 'UndefinedDropZoneExc';
            visualisation(JSONStateAndObject);
        } else {
            object.addEventListener('dragover', handleDragOver, false);
            object.addEventListener('drop', handleFileSelect, false);
            setInterval(function() {
                if (JSONPrev.state != JSONStateAndObject.state || JSONPrev.json != JSONStateAndObject.json)
                {
                    visualisation(JSONStateAndObject);
                    JSONPrev = new JSONStateObject(JSONStateAndObject.state, JSONStateAndObject.json);
                }
            }, 1000);
        }
    } else {
        object.style.visibility = "hidden";
    }
}

function JSONStateObject(state, json)
{
    this.state = state;
    this.json = json;
}
function isBrowserCompatible()
{
    if (window.File && window.FileReader && window.FileList && window.Blob)
        return true;
    else
        return false;
}

function handleDragOver(evt)
{
    evt.stopPropagation();
    evt.preventDefault();
    evt.dataTransfer.dropEffect = 'copy';
}
// sprawdzanie suffixu stringa
function endsWith(str, suffix)
{
    return str.indexOf(suffix, str.length - suffix.length) !== -1;
}
// sprawdzanie czy dopuszczalny typ MIME pliku
function allowedFileMIMEType(file)
{
    var f = file;
    if (f.type.match('image.*') || f.type.match('audio.*') || f.type.match('application.*') || f.type.match('multipart.*'))
        return false;
    return true;
}
// W stringu str1 wyszukuje kolejne wystąpienia str2 (sprawdza czy są), a następnie str4 zastępuje stringiem str3
// i zwraca oczyszczony string
function replaceAll(str1, str2, str3, str4)
{
    var tmp = str1;
    if (tmp == null)
        return null;
    while (tmp.indexOf(str2) !== - 1)
        tmp = tmp.replace(str4, str3);
    return tmp;
}
// Zwraca wnętrze dopasowanego wzorca lub 'BRAK DANYCH'
function getRealValue(str1, str2)
{
    var tmp = str1.match(str2);
    if (tmp != null) {
        return tmp[1];
    } else {
        return 'BRAK DANYCH';
    }
}
// Inicjalizuje i zwraca tablicę - szachownicę 
function initializeChessBoard()
{
    var Board = [['Rw', 'w', 'e', 'e', 'e', 'e', 'b', 'Rb'], ['Nw', 'w', 'e', 'e', 'e', 'e', 'b', 'Nb'],
        ['Bw', 'w', 'e', 'e', 'e', 'e', 'b', 'Bb'], ['Qw', 'w', 'e', 'e', 'e', 'e', 'b', 'Qb'],
        ['Kw', 'w', 'e', 'e', 'e', 'e', 'b', 'Kb'], ['Bw', 'w', 'e', 'e', 'e', 'e', 'b', 'Bb'],
        ['Nw', 'w', 'e', 'e', 'e', 'e', 'b', 'Nb'], ['Rw', 'w', 'e', 'e', 'e', 'e', 'b', 'Rb']];
    return Board;
}
//********************PORUSZANIE PIONKAMI PO PLANSZY***********************
//Roszada królewska w zależności od parametru who(kto) i board(szachownica).
function castlingKingSide(board, who)
{
    var Board = board;
    var jsonSP = '';
    if (who === 0) {
        jsonSP = '{"id":0,"fromX":' + 7 + ',"fromY":' + 0 + ',"toX":' + 5 + ',"toY":' + 0 + ',"isSingular":false}, {"id":0,"fromX":'
                + 4 + ',"fromY":' + 0 + ',"toX":' + 6 + ',"toY":' + 0 + ',"isSingular":true}';
        Board[7][0] = 'e';
        Board[4][0] = 'e';
        Board[5][0] = 'Rw';
        Board[6][0] = 'Kw';
    } else if (who === 1) {
        jsonSP = '{"id":0,"fromX":' + 7 + ',"fromY":' + 7 + ',"toX":' + 5 + ',"toY":' + 7 + ',"isSingular":false}, {"id":0,"fromX":'
                + 4 + ',"fromY":' + 7 + ',"toX":' + 6 + ',"toY":' + 7 + ',"isSingular":true}';
        Board[7][7] = 'e';
        Board[4][7] = 'e';
        Board[5][7] = 'Rb';
        Board[6][7] = 'Kb';
    } else {
        return {state: 'InvalidMoveExc', board: null, json: null};
    }
    return {state: 'OK', board: Board, json: jsonSP};
}
//Roszada hetmańska w zależności od parametru who(kto) i board(szachownica).
function castlingQueenSide(board, who)
{
    var Board = board;
    var jsonSP = '';
    if (who === 0) {
        jsonSP = '{"id":0,"fromX":' + 0 + ',"fromY":' + 0 + ',"toX":' + 3 + ',"toY":' + 0 + ',"isSingular":false}, {"id":0,"fromX":'
                + 4 + ',"fromY":' + 0 + ',"toX":' + 2 + ',"toY":' + 0 + ',"isSingular":true}';
        Board[0][0] = 'e';
        Board[4][0] = 'e';
        Board[3][0] = 'Rw';
        Board[2][0] = 'Kw';
    } else if (who === 1) {
        jsonSP = '{"id":0,"fromX":' + 0 + ',"fromY":' + 7 + ',"toX":' + 3 + ',"toY":' + 7 + ',"isSingular":false}, {"id":0,"fromX":'
                + 4 + ',"fromY":' + 7 + ',"toX":' + 2 + ',"toY":' + 7 + ',"isSingular":true}';
        Board[0][7] = 'e';
        Board[4][7] = 'e';
        Board[3][7] = 'Rb';
        Board[2][7] = 'Kb';
    } else {
        return {state: 'InvalidMoveExc', board: null, json: null};
    }
    return {state: 'OK', board: Board, json: jsonSP};
}
//Poruszanie pionkiem
function movePawn(board, column, row, who) {
    var tmpposy = -1;
    var Board = board;
    var jsonSP = '';
    var side = who === 0 ? 'w' : 'b';
    var direction = who === 0 ? 1 : -1;
    var startposition = who === 0 ? 1 : 6;
    for (var i = 0; i < Board[column].length && tmpposy === -1; i++) {
        if (Board[column][i] === side) {
            if (i === startposition) {
                if (Math.abs(row - startposition) == 1) {
                    tmpposy = i;
                    jsonSP = '{"id":0,"fromX":' + column + ',"fromY":' + tmpposy + ',"toX":' + column + ',"toY":' + row + ',"isSingular":true}';
                    Board[column][tmpposy] = 'e';
                    Board[column][row] = side;
                } else if (Math.abs(row - startposition) == 2) {
                    if (Board[column][i + direction] === 'e') {
                        tmpposy = i;
                        jsonSP += '{"id":0,"fromX":' + column + ',"fromY":' + tmpposy + ',"toX":' + column + ',"toY":' + row + ',"isSingular":true}';
                        Board[column][tmpposy] = 'e';
                        Board[column][row] = side;
                    }
                }
            } else {
                if (Math.abs(row - i) === 1) {
                    tmpposy = i;
                    jsonSP = '{"id":0,"fromX":' + column + ',"fromY":' + tmpposy + ',"toX":' + column + ',"toY":' + row + ',"isSingular":true}';
                    Board[column][tmpposy] = 'e';
                    Board[column][row] = side;
                }
            }
        }
    }
    if (tmpposy === -1) {
        return {state: 'InvalidMoveExc', board: null, json: null};
    }
    return {state: 'OK', board: Board, json: jsonSP}
}

//bicie pionem
function overtakePawn(board, fromPosX, toPosX, toPosY, who) {
    var tmpposy = -1;
    var Board = board;
    var jsonSP = '';
    var side = who === 0 ? 'w' : 'b';
    var direction = who === 0 ? -1 : 1;
    if (Board[fromPosX][toPosY + direction] === side)
        tmpposy = toPosY + direction;
    if (tmpposy === -1) {
        return {state: 'InvalidMoveExc', board: null, json: null};
    }
    else {
        if (Board[toPosX][toPosY] === 'e') {//uwzględnia bicie w przelocie
            jsonSP = '{"id":0,"fromX":' + fromPosX + ',"fromY":' + tmpposy + ',"toX":' + toPosX + ',"toY":' + tmpposy + ',"isSingular":false}';
            jsonSP += '{"id":0,"fromX":' + toPosX + ',"fromY":' + tmpposy + ',"toX":' + toPosX + ',"toY":' + toPosY + ',"isSingular":true}'
            Board[fromPosX][tmpposy] = 'e';
            Board[toPosX][tmpposy] = 'e';
            Board[toPosX][toPosY] = side;
        } else {
            jsonSP = '{"id":0,"fromX":' + fromPosX + ',"fromY":' + tmpposy + ',"toX":' + toPosX + ',"toY":' + toPosY + ',"isSingular":true}';
            Board[fromPosX][tmpposy] = 'e';
            Board[toPosX][toPosY] = side;
        }
    }
    return {state: 'OK', board: Board, json: jsonSP}
}
//poruszanie królem lub królową/hetmanem                           
function moveKingOrQueen(board, doX, doY, figura, who)
{
    var Board = board;
    var jsonSP = '';
    var side = who == 0 ? 'w' : 'b';
    var figure = figura + side;
    var zX;
    var zY;
    var found = false;
    for (var i = 0; i < Board.length && !found; i++) {
        for (var j = 0; j < Board[i].length && !found; j++) {
            if (Board[i][j] === figure) {
                zX = i;
                zY = j;
                found = true;
            }
        }
    }
    if (!found) {
        return {state: 'InvalidMoveExc', board: null, json: null};
    }
    jsonSP += '{"id":0,"fromX":' + zX + ',"fromY":' + zY + ',"toX":' + doX + ',"toY":' + doY + ',"isSingular":true}';
    Board[zX][zY] = 'e';
    Board[doX][doY] = figure;
    return {state: 'OK', board: Board, json: jsonSP};
}
//poruszanie nieokreślonym bishopem                            
function moveUndeterminedBishop(board, posX, posY, who) {
    var Board = board;
    var jsonSP = '';
    var site = who === 0 ? 'w' : 'b';
    var figure = 'B' + site;
    var zX;
    var zY;
    var positions = findAllSelectedFigures(board, figure);
    if (positions.length === 0)
        return {state: 'NoSuchFigureExc', board: null, json: null};
    var found = false;
    for (var i = 0; i < positions.length && !found; i++) {
        var cont = true;
        if (Math.abs(positions[i][0] - posX) === Math.abs(positions[i][1] - posY)) {
            var startX = positions[i][0];
            var startY = positions[i][1];
            
            var stepX = (posX-startX)/Math.abs(posX-startX);
            var stepY = (posY-startY)/Math.abs(posY-startY);
            
            for (var j = 1;  j <= Math.abs(posX-startX) && cont; j++) {
                if (startX + j*stepX === posX && startY + j*stepY === posY) {
                    zX = positions[i][0];
                    zY = positions[i][1];
                    cont = false;
                    found = true;
                }
                else if (Board[startX + j*stepX][startY + j*stepY] !== 'e') {
                    cont = false;
                    found = false;
                }
            }
        }
    }
    if (!found) {
        return {state: 'InvalidMoveExc', board: null, json: null};
    }
    jsonSP = '{"id":0,"fromX":' + zX + ',"fromY":' + zY + ',"toX":' + posX + ',"toY":' + posY + ',"isSingular":true}';
    Board[zX][zY] = 'e';
    Board[posX][posY] = figure;
    return {state: 'OK', board: Board, json: jsonSP};
}
//poruszanie nieokreślonym skoczkiem
function moveUndeterminedKnight(board, posX, posY, who) {
    var Board = board;
    var jsonSP = '';
    var site = who === 0 ? 'w' : 'b';
    var figure = 'N' + site;
    var zX;
    var zY;
    var positions = findAllSelectedFigures(board, figure);
    if (positions.length === 0)
        return {state: 'NoSuchFigureExc', board: null, json: null};
    var found = false;
    for (var i = 0; i < positions.length && !found; i++) {
        // Sprawdzanie czy dopuszczalny ruch dla skoczka, ZROBIONE PROSTO, ALE SKUTECZNIE
        if (positions[i][0] - 2 === posX && positions[i][1] + 1 === posY) {
            zX = positions[i][0];
            zY = positions[i][1];
            found = true;
        }
        else if (positions[i][0] - 1 === posX && positions[i][1] + 2 === posY) {
            zX = positions[i][0];
            zY = positions[i][1];
            found = true;
        }
        else if (positions[i][0] + 1 === posX && positions[i][1] + 2 === posY) {
            zX = positions[i][0];
            zY = positions[i][1];
            found = true;
        }
        else if (positions[i][0] + 2 === posX && positions[i][1] + 1 === posY) {
            zX = positions[i][0];
            zY = positions[i][1];
            found = true;
        }
        else if (positions[i][0] + 2 === posX && positions[i][1] - 1 === posY) {
            zX = positions[i][0];
            zY = positions[i][1];
            found = true;
        }
        else if (positions[i][0] + 1 === posX && positions[i][1] - 2 === posY) {
            zX = positions[i][0];
            zY = positions[i][1];
            found = true;
        }
        else if (positions[i][0] - 1 === posX && positions[i][1] - 2 === posY) {
            zX = positions[i][0];
            zY = positions[i][1];
            found = true;
        }
        else if (positions[i][0] - 2 === posX && positions[i][1] - 1 === posY) {
            zX = positions[i][0];
            zY = positions[i][1];
            found = true;
        }
    }
    if (!found) {
        return {state: 'InvalidMoveExc', board: null, json: null};
    }
    jsonSP = '{"id":0,"fromX":' + zX + ',"fromY":' + zY + ',"toX":' + posX + ',"toY":' + posY + ',"isSingular":true}';
    Board[zX][zY] = 'e';
    Board[posX][posY] = figure;
    return {state: 'OK', board: Board, json: jsonSP};
}
//poruszanie nieokreśloną wieżą                            
function moveUndeterminedRook(board, posX, posY, who)
{
    var Board = board;
    var jsonSP = '';
    var site = who === 0 ? 'w' : 'b';
    var figure = 'R' + site;
    var zX;
    var zY;
    var positions = findAllSelectedFigures(board, figure);
    if (positions.length === 0)
        return {state: 'NoSuchFigureExc', board: null, json: null};
    var found = false;
    for (var i = 0; i < positions.length && !found; i++) {
        var cont = true;
        var start = 0;
        var stop = 8;
        if (posX === positions[i][0]) {
            if (posY > positions[i][1]) {
                start = positions[i][1];
                stop = posY;
            } else {
                stop = positions[i][1];
                start = posY;
            }
            for (var j = start + 1; j <= stop && cont; j++)
            {
                if (j === stop) {
                    zX = positions[i][0];
                    zY = positions[i][1];
                    cont = false;
                    found = true;
                }
                else if (Board[positions[i][0]][j] !== 'e') {
                    cont = false;
                    found = false;
                }
            }
        }
        else if (posY === positions[i][1]) {
            if (posX > positions[i][0]) {
                start = positions[i][0];
                stop = posX;
            } else {
                stop = positions[i][0];
                start = posX;
            }
            for (var j = start + 1; j <= stop && cont; j++)
            {
                if (j === stop) {
                    zX = positions[i][0];
                    zY = positions[i][1];
                    cont = false;
                    found = true;
                }
                else if (Board[j][positions[i][1]] !== 'e') {
                    cont = false;
                    found = false;
                }
            }
        }
    }
    if (!found) {
        return {state: 'InvalidMoveExc', board: null, json: null};
    }
    jsonSP = '{"id":0,"fromX":' + zX + ',"fromY":' + zY + ',"toX":' + posX + ',"toY":' + posY + ',"isSingular":true}';
    Board[zX][zY] = 'e';
    Board[posX][posY] = figure;
    return {state: 'OK', board: Board, json: jsonSP};
}
// wyszukiwanie wszystkich figur dla danego gracza            
function findAllSelectedFigures(board, figure)
{
    var Board = board;
    var a = [];
    for (var i = 0; i < Board.length; i++) {
        for (var j = 0; j < Board[i].length; j++) {
            if (Board[i][j] === figure) {
                a.push([i, j]);
            }
        }
    }
    return a;
}
//poruszanie sparametryzowaną figurą                            
function moveFigureWithParam(board, figura, zX, zY, doX, doY, who)
{
    var Board = board;
    var side = who === 0 ? 'w' : 'b';
    var figure = figura + side;
    var jsonSP = '';
    var tmpposx = -1;
    var tmpposy = -1;
    if (zX == -1) {
        tmpposy = zY;
        for (var i = 0; i < Board.length && tmpposx === -1; i++) {
            if (Board[i][tmpposy] === figure) {
                tmpposx = i;
            }
        }
    } else if (zY === -1) {
        tmpposx = zX;
        for (var i = 0; i < Board[tmpposx].length && tmpposy === -1; i++) {
            if (Board[tmpposx][i] === figure) {
                tmpposy = i;
            }
        }
    } else {
        return {state: 'InvalidMoveExc', board: null, json: null};
    }
    if (tmpposx === -1 || tmpposy === -1) {
        return {state: 'InvalidMoveExc', board: null, json: null};
    }
    jsonSP = '{"id":0,"fromX":' + tmpposx + ',"fromY":' + tmpposy + ',"toX":' + doX + ',"toY":' + doY + ',"isSingular":true}';
    Board[tmpposx][tmpposy] = 'e';
    Board[doX][doY] = figure;
    return {state: 'OK', board: Board, json: jsonSP};
}
// Obsługuje (przekazuje) dalej dowolny ruch
function getMoveInJson(board, move, who) {
    var response = {state: 'OK', board: board, json: null};
    if (move.valueOf() === 'O-O-O'.valueOf())
        response = castlingQueenSide(board, who);
    else if (move.valueOf() === 'O-O'.valueOf())
        response = castlingKingSide(board, who);
    else {
        if (move.length === 2) {
            //posX - numer pola ma osi X, posY - numer pola na osi Y
            var posX = move.charCodeAt(0) - 97;
            var posY = move.charCodeAt(1) - 49;
            response = movePawn(board, posX, posY, who);
        } else if (move.length === 3) {
            if (move.charCodeAt(0) > 96 && move.charCodeAt(0) < 123) {//obsługa dla bicia pionem po przekątnej
                var fromPosX = move.charCodeAt(0) - 97;
                var toPosX = move.charCodeAt(1) - 97;
                var toPosY = move.charCodeAt(2) - 49;
                response = overtakePawn(board, fromPosX, toPosX, toPosY, who);
            } else {
                //figure - figura, posX - numer pola ma osi X, posY - numer pola na osi Y 
                var figure = move.charAt(0);
                var posXs = move.charCodeAt(1) - 97;
                var posYs = move.charCodeAt(2) - 49;
                if (figure === 'K')
                    response = moveKingOrQueen(board, posXs, posYs, 'K', who);
                else if (figure === 'Q')
                    response = moveKingOrQueen(board, posXs, posYs, 'Q', who);
                else if (figure === 'B')
                    response = moveUndeterminedBishop(board, posXs, posYs, who);
                else if (figure === 'N')
                    response = moveUndeterminedKnight(board, posXs, posYs, who);
                else if (figure === 'R')
                    response = moveUndeterminedRook(board, posXs, posYs, who);
            }
        } else if (move.length == 4) {
            // figure - figura, posX - numer pola ma osi X, posY - numer pola na osi Y, toX - docelowo na X, toY - docelowo na Y
            var figure = move.charAt(0);
            var posX = 0;
            var posY = 0;
            if ($.isNumeric(move.charAt(1))) {
                posX = -1;
                posY = move.charCodeAt(1) - 49;
            } else {
                posX = move.charCodeAt(1) - 97;
                posY = -1;
            }
            var toX = move.charCodeAt(2) - 97;
            var toY = move.charCodeAt(3) - 49;
            response = moveFigureWithParam(board, figure, posX, posY, toX, toY, who);
        } else {
            return {state: 'InvalidMoveDescriptionExc', board: null, json: null};
        }
    }
    return response;

}
//--------------------------------------------------------------------------------------------------------------------------------------   
function handleFileSelect(evt) {
    evt.stopPropagation();
    evt.preventDefault();
    //obsługa plików
    var files = evt.dataTransfer.files;
    var f = files[0];
    //Sprawdzanie typu mime oraz suffixu
    if (!(endsWith(f.name, '.pgn') && allowedFileMIMEType(f))) {
        JSONStateAndObject = new JSONStateObject('FileTypeExc', null);
        return;
    }
    // przetwarzanie zawartości pliku
    var blob = f.slice(0, f.size);
    var reader = new FileReader();
    reader.readAsText(blob);
    // po wczytaniu pliku jako tekst - przetwarzaj dalej
    reader.onloadend = function(evt) {
        if (evt.target.readyState == FileReader.DONE) { // DONE == 2
            //jsonOutput - string jsonowy
            var jsonOutput = '{"id":0,"event":"';
            //zaciąganie zawartości pliku do stringa
            var fileContent = evt.target.result;
            //wyciąganie danych o rozgrywce
            jsonOutput += getRealValue(fileContent, /\[Event "(.*)"\]/) + '","site":"' + getRealValue(fileContent, /\[Site "(.*)"\]/) + '","date":"'
                    + getRealValue(fileContent, /\[Date "(.*)"\]/) + '","round":' + getRealValue(fileContent, /\[Round "(.*)"\]/) + ',"white":"'
                    + getRealValue(fileContent, /\[White "(.*)"\]/) + '","black":"' + getRealValue(fileContent, /\[Black "(.*)"\]/) + '","result":"';
            //czyszczenie stringa            
            fileContent = fileContent.replace(/\[Event "(.*)"\]/, '').replace(/\[Site "(.*)"\]/, '').replace(/\[Date "(.*)"\]/, '').replace(/\[Round "(.*)"\]/, '').replace(/\[White "(.*)"\]/, '').replace(/\[Black "(.*)"\]/, '');
            //dodawanie wyniku do danych o turnieju    
            var result = fileContent.match(/\[Result "(.*)"\]/);
            var resultNum;//potrzebne do czyszczenia
            if (result != null) {
                fileContent = fileContent.replace(/\[Result "(.*)"\]/, '');
                result = result[1];
                resultNum = result;
                if (result.valueOf() == "1-0".valueOf())
                    result = "WHITE_WON";
                else if (result.valueOf() == "0-1".valueOf())
                    result = "BLACK_WON";
                else
                    result = "DRAW";
            } else {
                result = 'Brak danych';
            }
            jsonOutput += result + '","moves":[';
            //wyczyszczenie niepotrzebnych tagów
            fileContent = replaceAll(fileContent, ']', '', /(.*)"\]/);
            //usuwanie komentarzy z całości
            fileContent = replaceAll(fileContent, '{', '', /\{(.*)\}/);
            fileContent = replaceAll(fileContent, ';', '', /;.*$/m);
            //usuwanie wyniku oraz oznaczeń bicia
            fileContent = fileContent.replace(resultNum, '');
            fileContent = replaceAll(fileContent, 'x', '', 'x');
            //Usuwanie numeru ruchu, oznaczenia szacha i trimowanie
            var iterate = 1;
            while (fileContent.indexOf(iterate + '.') !== -1) {
                fileContent = fileContent.replace(iterate + '.', ' ');
                iterate++;
            }
            fileContent = replaceAll(fileContent, '+', ' ', '+');
            fileContent = fileContent.trim();
            //Wrzucanie ruchów do tablicy i inicjalizacja szachownicy
            var moves = fileContent.split(/\s+/);
            var chessboard = initializeChessBoard()
            // Iterowanie po ruchach i dalsze przetwarzanie
            for (var itermoves = 0; itermoves < moves.length; itermoves++) {
                if (itermoves > 0)
                    jsonOutput += ', ';
                var stateBoardAndJson = getMoveInJson(chessboard, moves[itermoves], itermoves % 2);
                if (stateBoardAndJson.state === 'OK') {
                    chessboard = stateBoardAndJson.board;
                    jsonOutput += stateBoardAndJson.json;
                } else {
                    JSONStateAndObject = new JSONStateObject('CannotParseMoveExc', null);
                    return;
                }
            }
            //Po przetworzeniu wszystkich ruchów doklej zamknięcie ciągu                                       
            jsonOutput += ']}'
            //Konweruj string do obiektu json - konwersja dopiero po utworzeniu poprawnego obiektu
            var jsonObject = $.parseJSON(jsonOutput);
            JSONStateAndObject = new JSONStateObject('CorrectlyParsedSucc', jsonObject);
        }
    }
}   
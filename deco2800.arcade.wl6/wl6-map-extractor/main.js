

/**
 * A node.js program for viewing and extracting wolfenstein maps
 */



/**
 * this code defines the order of the levels in the binary file
 */
var innerLevelOrder = ["boss", "l1", "l2", "l3", "l4", "l5", "l6", "l7", "l8", "secret"];
var levelOrder = [];
for (var i = 1; i <= 6; i++) {
    for (var j = 0; j < 10; j++) {
        levelOrder.push("e" + i + innerLevelOrder[j]);
    }
}
console.log(levelOrder);






/**
 * Array transpose function from
 * http://stackoverflow.com/questions/4492678/to-swap-rows-with-columns-of-matrix-in-javascript-or-jquery
 */
Array.prototype.transpose = function() {
    "use strict";
    
    // Calculate the width and height of the Array
    var a = this,
    w = a.length ? a.length : 0,
    h = a[0] instanceof Array ? a[0].length : 0;
    
    // In case it is a zero matrix, no transpose routine needed.
    if(h === 0 || w === 0) { return []; }
    
    /**
    * @var {Number} i Counter
    * @var {Number} j Counter
    * @var {Array} t Transposed data is stored in this array.
    */
    var i, j, t = [];
    
    // Loop through every item in the outer array (height)
    for(i=0; i<h; i++) {
        
        // Insert a new row (array)
        t[i] = [];
        
        // Loop through every item per item in outer array (width)
        for(j=0; j<w; j++) {
            
            // Save transposed data.
            t[i][j] = a[j][i];
        }
    }
    
    return t;
};



/**
 * main
 */
(function () {
    "use strict";
    
    
    var http = require("http"),
        fs = require("fs"),
        url = require("url");
    
    
    
    http.createServer(function (req, res) {
        
        console.log("got", req.url);
        
        if (req.url === "/") {
            fs.readFile("doc.html", function(err, data) {
                res.end(data);
            });
        } else if (req.url === "/get") {
            res.end(JSON.stringify(get()));
        }
        
    }).listen(8000);
    
    
    
    function get() {

        var buf = fs.readFileSync("wl6maps.lvl"),
            ptr = 0,
            header = "",
            content = {},
            lvl, lyr, x, y;
        
        for (var i = 0; i < 8; i++) {
            header += String.fromCharCode(buf[ptr]);
            ptr++;
        }
        
        for (lvl = 0; lvl < numLevels(); lvl++) {
            var lName = getLevelName(lvl);
            content[lName] = [];
            console.log("getting level", lvl, getLevelName((lvl)));
            for (lyr = 0; lyr < 2; lyr++) {
                content[lName].push([]);
                for (x = 0; x < 64; x++) {
                    content[lName][lyr].push([]);
                    for (y = 0; y < 64; y++) {
                        content[lName][lyr][x].push((buf[ptr] << 8) + (buf[ptr + 1]));
                        ptr += 2;
                    }
                }
                content[lName][lyr] = content[lName][lyr].transpose();
            }
        }
        
        

        return content;
        
        
    }
    
    function numLevels() {
        return 60;
    }
    
    
    
    function getLevelName(number) {
        return levelOrder[number];
    }
    
    
    var d = get();
    for (var level in d) {
        if (d.hasOwnProperty(level)) {
            fs.writeFile("out/" + level + ".json", JSON.stringify(d[level]).replace(/]/g, "]\n"));
        }
    }
      
    
}());
















































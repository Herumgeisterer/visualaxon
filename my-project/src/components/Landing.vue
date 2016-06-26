<template>
  <div class="hello">
    <h1>{{ msg }}</h1>

    <div id="diagram"></div>
  </div>
</template>

<script>
  var cytoscape = require('cytoscape')
  var $ = require('jquery')

  export default {
    data () {
      return {
        // note: changing this line won't causes changes
        // with hot-reload because the reloaded component
        // preserves its current state and we are modifying
        // its initial state.
        msg: 'Main!'
      }
    }
  }

  $.ajax('static/output.json')
    .done(function (data) {
      var cy = cytoscape({
        container: $('#diagram'),
        hideEdgesOnViewport: false,
        hideLabelsOnViewport: false,

        style: cytoscape.stylesheet()
          .selector('node')
          .css({
            'content': 'data(name)',
            'text-valign': 'center',
            'color': 'white',
            'text-outline-width': 2,
            'backgrund-color': '#999',
            'text-outline-color': '#999'
          })
          .selector('$node > node')
          .css({
            'padding-top': '10px',
            'padding-left': '10px',
            'padding-bottom': '10px',
            'padding-right': '10px',
            'text-valign': 'top',
            'text-halign': 'center',
            'background-color': '#bbb'
          })
          .selector(':parent')
          .css({
            'background-opacity': 0.333
          })
          .selector('edge')
          .css({
            'curve-style': 'bezier',
            'target-arrow-shape': 'triangle',
            'target-arrow-color': '#999',
            'line-color': '#999',
            'width': 1
          }),
        elements: data
      })

      cy.layout({
        name: 'grid',

        fit: true, // whether to fit the viewport to the graph
        padding: 30, // padding used on fit
        boundingBox: undefined, // constrain layout bounds; { x1, y1, x2, y2 } or { x1, y1, w, h }
        avoidOverlap: true, // prevents node overlap, may overflow boundingBox if not enough space
        avoidOverlapPadding: 10, // extra spacing around nodes when avoidOverlap: true
        condense: false, // uses all available space on false, uses minimal space on true
        rows: undefined, // force num of rows in the grid
        cols: undefined, // force num of columns in the grid
        position: function (node) {
        }, // returns { row, col } for element
        sort: undefined, // a sorting function to order the nodes; e.g. function(a, b){ return a.data('weight') - b.data('weight') }
        animate: false, // whether to transition the node positions
        animationDuration: 500, // duration of animation in ms if enabled
        animationEasing: undefined, // easing of animation if enabled
        ready: undefined, // callback on layoutready
        stop: undefined // callback on layoutstop
      })

//      cy.ready(function () {
//        $('#diagram').cytoscapeNavigator({
//          viewLiveFramerate: 0,
//          thumbnailEventFramerate: 60,
//          thumbnailLiveFramerate: true,
//          dblClickDelay: 200
//        })
//      })
    })
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style>
  h1 {
    color: #42b983;
  }

  html {
    height: 100%;
  }

  body {
    display: flex;
    align-items: center;
    justify-content: center;
    height: 100%;
  }

  #app {
    color: #2c3e50;
    margin-top: -100px;
    max-width: 600px;
    font-family: Source Sans Pro, Helvetica, sans-serif;
    text-align: center;
  }

  #app a {
    color: #42b983;
    text-decoration: none;
  }

  #diagram {
    width: 100%;
    height: 100%;
    position: absolute;
    left: 0;
    top: 0;
    z-index: 999;
  }
</style>

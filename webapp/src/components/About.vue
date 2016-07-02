<template>
  <div class="about">
    <div id="cy"></div>
  </div>
</template>

<script>
  var $ = require("jquery");
  var cytoscape = require("cytoscape");
  var nodes = require("../data/output.json");
  var cytoscapeCola = require("cytoscape-cola");

  cytoscapeCola(cytoscape, window.cola);

  var initCytoscape = function () {
    var cy = cytoscape({
      container: document.getElementById('cy'),
      elements: nodes,
      hideEdgesOnViewport: true,
      textureOnViewport: true,
      style: cytoscape.stylesheet()
              .selector('node')
              .css({
//                'content': 'data(name)',
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
              })
    });

    cy.layout({
      name: 'cola',
      animate: true, // whether to show the layout as it's running
      refresh: 10, // number of ticks per frame; higher is faster but more jerky
      maxSimulationTime: 4000, // max length in ms to run the layout
      ungrabifyWhileSimulating: false, // so you can't drag nodes during layout
      fit: true, // on every layout reposition of nodes, fit the viewport
      padding: 30, // padding around the simulation
      boundingBox: undefined, // constrain layout bounds; { x1, y1, x2, y2 } or { x1, y1, w, h }

      // layout event callbacks
      ready: function () {
      }, // on layoutready
      stop: function () {
      }, // on layoutstop

      // positioning options
      randomize: true, // use random node positions at beginning of layout
      avoidOverlap: true, // if true, prevents overlap of node bounding boxes
      handleDisconnected: true, // if true, avoids disconnected components from overlapping
      nodeSpacing: function (node) {
        if (node.isParent()) {
          return 30;
        }
        return 10;
      }, // extra spacing around nodes
      flow: undefined, // use DAG/tree flow layout if specified, e.g. { axis: 'y', minSeparation: 30 }
      alignment: undefined, // relative alignment constraints on nodes, e.g. function( node ){ return { x: 0, y: 1 } }

      // different methods of specifying edge length
      // each can be a constant numerical value or a function like `function( edge ){ return 2; }`
      edgeLength: undefined, // sets edge length directly in simulation
      edgeSymDiffLength: undefined, // symmetric diff edge length in simulation
      edgeJaccardLength: undefined, // jaccard edge length in simulation

      // iterations of cola algorithm; uses default values on undefined
      unconstrIter: undefined, // unconstrained initial layout iterations
      userConstIter: undefined, // initial layout iterations with user-specified constraints
      allConstIter: undefined, // initial layout iterations with all constraints including non-overlap

      // infinite layout options
      infinite: false // overrides all other options for a forces-all-the-time mode
    });

    var defaults = {
      container: false // can be a HTML or jQuery element or jQuery selector
      , viewLiveFramerate: 0 // set false to update graph pan only on drag end; set 0 to do it instantly; set a number (frames per second) to update not more than N times per second
      , thumbnailEventFramerate: 30 // max thumbnail's updates per second triggered by graph updates
      , thumbnailLiveFramerate: false // max thumbnail's updates per second. Set false to disable
      , dblClickDelay: 200 // milliseconds
      , removeCustomContainer: true // destroy the container specified by user on plugin destroy
      , rerenderDelay: 100 // ms to throttle rerender updates to the panzoom for performance
    };
  };

  export default {
    ready: function () {
      initCytoscape();
    }
  }
</script>

<style>
  #cy {
    width: 100%;
    height: 100%;
    position: absolute;
  }
</style>

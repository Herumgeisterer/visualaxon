<template>
  <div class="cywrapper">
    <div id="cy"></div>
  </div>
</template>

<script>
  var $ = require("jquery");
  var cytoscape = require("cytoscape");
  var cytoscapeCola = require("cytoscape-cola");

  cytoscapeCola(cytoscape, window.cola);

  var initCytoscape = function (elements) {
    var cy = cytoscape({
      container: document.getElementById('cy'),
      elements: elements,
      hideEdgesOnViewport: true,
      textureOnViewport: true,
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
              })
    });

    cy.layout({
      name: 'cola',
      animate: true,
      refresh: 10, // number of ticks per frame; higher is faster but more jerky
      maxSimulationTime: 4000, // max length in ms to run the layout
      fit: true, // on every layout reposition of elements, fit the viewport
      padding: 30,
      // layout event callbacks
      ready: function () {
      }, // on layoutready
      stop: function () {
      }, // on layoutstop
      randomize: true,
      nodeSpacing: function (node) {
        if (node.isParent()) {
          return 30;
        }
        return 10;
      }
    });
  };

  export default {
    events: {
      'nodesRead': function (data) {
        initCytoscape(data);
      }
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

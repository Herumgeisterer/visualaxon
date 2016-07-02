<template>
  <div class="section no-pad-bot" id="index-banner">
    <div class="container">
      <br><br>
      <h1 class="header center orange-text">Axonvisualizer</h1>
      <div class="row center">
        <h5 class="header col s12 light">A maven plugin which helps you visualize your Axonframework based application</h5>
      </div>
      <div class="row center">
        <a id="upload-button" class="btn-large waves-effect waves-light blue-grey">Load json</a>
      </div>
      <br><br>

    </div>
  </div>


  <div class="container">
    <div class="section">

      <!--   Icon Section   -->
      <div class="row">
        <div class="col s12 m4">
          <div class="icon-block">
            <h2 class="center light-blue-text"><i class="material-icons">code</i></h2>
            <h5 class="center"><a href="http://www.axonframework.org/" target="_blank">Axonframework</a></h5>

            <p class="light">The axonframework helps you build applications based on the CQRS principle and eventsourcing pattern</p>
          </div>
        </div>

        <div class="col s12 m4">
          <div class="icon-block">
            <h2 class="center light-blue-text"><i class="material-icons">picture_in_picture</i></h2>
            <h5 class="center">Visualize</h5>

            <p class="light">Want to know which command leads to which event?
                             How many eventhandler are handling this event and where they are?
                             This tool allows you to keep track of these things</p>
          </div>
        </div>

        <div class="col s12 m4">
          <div class="icon-block">
            <h2 class="center light-blue-text"><i class="material-icons">info_outline</i></h2>
            <h5 class="center">???</h5>

            <p class="light"></p>
          </div>
        </div>

        <div class="col s12">
          <div class="icon-block">
            <h2 class="center light-blue-text"><i class="material-icons">info</i></h2>
            <h5 class="center">Getting started</h5>

            <p class="center">Include the maven plugin in your project</p>
            <p class="center">run mvn axonviszualizer:generate</p>
            <p class="center">Come back to this page and load the generated json file</p>
          </div>
        </div>
      </div>
    </div>
  </div>

  <footer class="page-footer blue-grey lighten-2">
    <div class="container">
      <div class="row">
        <div class="col l6 s12">
        </div>
      </div>
    </div>
    <div class="footer-copyright">
    </div>
  </footer>
</template>

<script>
  export default {
    data() {
      return {
        title: 'Home!'
      }
    }
  }
</script>

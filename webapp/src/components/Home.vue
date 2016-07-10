<template xmlns:v-on="http://www.w3.org/1999/xhtml">
  <div class="home">

    <div class="section no-pad-bot" id="index-banner">
      <div class="container">
        <br><br>
        <h1 class="header center orange-text">VisualAxon</h1>
        <div class="row center">
          <h5 class="header col s12 light">A maven plugin which helps you visualize your Axonframework based application</h5>
        </div>
        <div class="row center">
          <!--<input type="file" v-on="change:load">-->
          <a id="load-button" class="btn-large waves-effect waves-light blue-grey" v-on:click="onClick">Load json</a>

          <input id="file-input" type="file" @change="onFileChange" v-show="false">
        </div>
      </div>

      <div class="container">
        <div class="section">
          <div class="row">
            <div class="col s12 m4">
              <div class="icon-block">
                <h2 class="center light-blue-text"><i class="material-icons">code</i></h2>
                <h5 class="center">Axonframework</h5>

                <p class="light">The axonframework helps you build applications based on the CQRS principle and eventsourcing pattern. More information on the framework can be found <a
                        href="http://www.axonframework.org/" target="_blank">here</a>.</p>
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
                <h5 class="center">Easy to use</h5>

                <p class="light">This tool is available as maven plugin or standalone jar executable. Read the Readme on <a href="https://github.com/Herumgeisterer/visualaxon" target="_blank">github.com</a>
                                 to learn how to use this tool.</p>
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

    </div>
  </div>
</template>

<script>
  import router from '../main.js';
  import store from '../store.js';
  import $ from 'jquery';

  export default {
    methods: {
      onClick: function () {
        $('#file-input').click();
      },
      onFileChange: function (e) {
        var files = e.target.files || e.dataTransfer.files;
        if (!files.length)
          return;
        var file = files[0];
        var reader = new FileReader();

        var self = this;
        reader.onload = function () {
          self.fileLoaded(this.result);
        };

        reader.readAsText(file);
      },
      fileLoaded: function (file) {
        var json = JSON.parse(file);
        this.storeJson(json);
      },
      storeJson: function (json) {
        store.state.nodes = json;
        router.go({
          path: 'view'
        });
      }
    }
  }
</script>
<style>
  .page-footer {
    bottom: 0;
    position: fixed;
    width: 100%;
  }
</style>
